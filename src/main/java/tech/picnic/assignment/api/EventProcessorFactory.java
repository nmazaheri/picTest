package tech.picnic.assignment.api;

import java.time.Duration;

/**
 * An interface for the construction of {@link StreamProcessor}s which limit the amount of data they
 * process.
 *
 * <p><strong>Important:</strong> do not touch this interface! Don't move, rename or otherwise
 * modify it.
 */
public interface EventProcessorFactory extends AutoCloseable {
    /**
     * Returns a new {@link StreamProcessor} which limits input processing as configured.
     *
     * @param maxEvents the desired and maximum number of events to be processed
     * @param maxReadTime the maximum amount of time to wait for the desired number of events to
     *     come in; the processor must stop reading from its input once this time has elapsed
     * @return a {@link StreamProcessor} which reads events from its input stream and writes the
     *     result of processing said events out its output stream
     */
    StreamProcessor createProcessor(int maxEvents, Duration maxReadTime);

    /** Closes this factory, relinquishing any underlying resources. */
    @Override
    default void close() {}
}
