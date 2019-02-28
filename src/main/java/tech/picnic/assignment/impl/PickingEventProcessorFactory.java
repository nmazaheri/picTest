package tech.picnic.assignment.impl;

import com.google.auto.service.AutoService;
import java.time.Duration;
import tech.picnic.assignment.api.EventProcessorFactory;
import tech.picnic.assignment.api.StreamProcessor;

@AutoService(EventProcessorFactory.class)
public final class PickingEventProcessorFactory implements EventProcessorFactory {
    @Override
    public StreamProcessor createProcessor(int maxEvents, Duration maxReadTime) {
        return new StreamProcessorImpl(maxEvents, maxReadTime);
    }
}
