package tech.picnic.assignment.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import org.apache.commons.lang3.StringUtils;
import tech.picnic.assignment.api.StreamProcessor;
import tech.picnic.assignment.model.Article;
import tech.picnic.assignment.model.OutputPicker;
import tech.picnic.assignment.model.Pick;
import tech.picnic.assignment.model.Picker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StreamProcessorImpl implements StreamProcessor {

    private int maxEvents;
    private Clock fixedEndTime;
    private ObjectMapper objectMapper = new ObjectMapper();
    private BufferedReader br;

    public StreamProcessorImpl(int maxEvents, Duration maxReadTime) {
        this.maxEvents = maxEvents;
        Clock fixed = Clock.fixed(Instant.now(), ZoneId.systemDefault());
        this.fixedEndTime = Clock.offset(fixed, maxReadTime);
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
    }

    @Override
    public void process(InputStream source, OutputStream sink) throws IOException {
        List<Pick> picks = handleInput(source);
        List<Pick> filteredPicks = filter(picks);
        List<OutputPicker> outputPickers = aggregateAndSort(filteredPicks);
        objectMapper.writeValue(sink, outputPickers);
    }


    private List<Pick> handleInput(InputStream source) throws IOException {
        br = new BufferedReader(new InputStreamReader(source));

        List<Pick> results = new ArrayList<>();
        int counter = 0;
        String line;
        while (maxEvents > counter && Instant.now().isBefore(fixedEndTime.instant())) {
            line = br.readLine(); // could result in max duration being surpassed
            if (StringUtils.isBlank(line)) {
                continue;
            }
            counter++;
            Pick pick = parse(line);
            if (pick != null) {
                results.add(pick);
            }
        }
        return results;
    }

    private Pick parse(String line) {
        try {
            return objectMapper.readValue(line, Pick.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<Pick> filter(List<Pick> picks) {
        return picks.stream().filter(pick -> Article.TemperatureZone.AMBIENT.equals(pick.getArticle().getTemperatureZone())).collect(Collectors.toList());
    }

    private List<OutputPicker> aggregateAndSort(List<Pick> picks) {
        Map<Picker, OutputPicker> map = new HashMap<>();
        picks.forEach(pick -> {
            Picker picker = pick.getPicker();
            map.computeIfAbsent(picker, p -> new OutputPicker(p.getName(), p.getActiveSince(), p.getId()));
            map.get(picker).addItem(pick.getArticle().getName(), pick.getTimestamp());
        });
        return map.values().stream().sorted(getComparator()).collect(Collectors.toList());
    }

    private static Comparator<OutputPicker> getComparator() {
        return Comparator.comparing(OutputPicker::getActiveSince).thenComparing(OutputPicker::getId);
    }

    public Clock getFixedEndTime() {
        return fixedEndTime;
    }

    public void setFixedEndTime(Clock fixedEndTime) {
        this.fixedEndTime = fixedEndTime;
    }

    @Override
    public void close() {
        try {
            if (br != null) {
                br.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
