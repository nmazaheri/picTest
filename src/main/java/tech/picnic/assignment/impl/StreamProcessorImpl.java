package tech.picnic.assignment.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import tech.picnic.assignment.api.StreamProcessor;
import tech.picnic.assignment.model.Article;
import tech.picnic.assignment.model.OutputPick;
import tech.picnic.assignment.model.OutputPicker;
import tech.picnic.assignment.model.Pick;
import tech.picnic.assignment.model.Picker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StreamProcessorImpl implements StreamProcessor {

    private int maxEvents;
    private long endTime;
    private ObjectMapper objectMapper = new ObjectMapper();
    private BufferedReader br;

    public StreamProcessorImpl(int maxEvents, Duration maxReadTime) {
        this.maxEvents = maxEvents;
        endTime = System.currentTimeMillis() + maxReadTime.toMillis();
    }

    @Override
    public void process(InputStream source, OutputStream sink) throws IOException {
        List<Pick> picks = handleInput(source);
        List<Pick> filteredPicks = filter(picks);
        List<OutputPicker> outputPickers = aggregate(filteredPicks);
        sort(outputPickers);
        objectMapper.writeValue(sink, outputPickers);
    }

    private void sort(List<OutputPicker> outputPickers) {
        for (OutputPicker op: outputPickers) {
            Collections.sort(op.getPicks(), new OutputPick.TimestampComparator());
        }
        Collections.sort(outputPickers, new OutputPicker.ActiveSinceComparator());
    }

    private List<Pick> handleInput(InputStream source) throws IOException {
        br = new BufferedReader(new InputStreamReader(source));

        List<Pick> results = new ArrayList<>();
        int counter = 0;
        String line;
        while (maxEvents > counter && endTime > System.currentTimeMillis()) {
            line = br.readLine();
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
        List<Pick> results = new ArrayList<>();
        for (Pick pick : picks) {
            if (Article.TemperatureZone.AMBIENT.equals(pick.getArticle().getTemperature_zone())) {
                results.add(pick);
            }
        }
        return results;
    }

    private List<OutputPicker> aggregate(List<Pick> picks) {
        Map<Picker, OutputPicker> map = new HashMap<>();
        for (Pick pick : picks) {
            Picker picker = pick.getPicker();
            initMap(map, picker);
            map.get(picker).addItem(pick.getArticle().getName(), pick.getTimestamp());
        }
        List<OutputPicker> output = new ArrayList<>(map.values());
        return output;
    }

    private void initMap(Map<Picker, OutputPicker> result, Picker picker) {
        if (result.containsKey(picker)) {
            return;
        }
        OutputPicker outputPicker = new OutputPicker(picker.getName(), picker.getActive_since(), picker.getId());
        result.put(picker, outputPicker);
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
