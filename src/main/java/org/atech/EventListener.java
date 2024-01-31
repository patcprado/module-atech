package org.atech;

//Observer
public interface EventListener {
    void update(Object data);

    void update(String mensagem);
}