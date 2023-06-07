package me.alek.packetlibrary.utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class AsyncFuture {

    private final List<Runnable> listeners = new ArrayList<>();

    public AsyncFuture() {

    }

    public AsyncFuture(Runnable... listeners) {
        this.listeners.addAll(Arrays.asList(listeners));
    }

    public AsyncFuture(Collection<Runnable> listeners) {
        this.listeners.addAll(listeners);
    }

    public void addListener(Runnable... listeners) {
        this.listeners.addAll(Arrays.asList(listeners));
    }

    public boolean hasListener() {
        return !listeners.isEmpty();
    }

    public void done() {
        for (Runnable listener : listeners) {
            listener.run();
        }
    }
}
