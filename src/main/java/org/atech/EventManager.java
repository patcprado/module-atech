package org.atech;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

//Subject(Publisher)
public class EventManager {
    private final Map<String, List<EventListener>> listeners;

    public EventManager() {
        this.listeners = new HashMap<>();
    }

    public void subscribe(String eventType, EventListener listener) {
        listeners.computeIfAbsent(eventType, k -> new LinkedList<>()).add(listener);
    }

    public void unsubscribe(String eventType, EventListener listener) {
        listeners.getOrDefault(eventType, new LinkedList<>()).remove(listener);
    }

    public void notify(String eventType, Object data) {
        for (EventListener listener : listeners.getOrDefault(eventType, new LinkedList<>())) {
            listener.update(data);
        }
    }
    
}