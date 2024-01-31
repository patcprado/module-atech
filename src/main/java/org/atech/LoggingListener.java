package org.atech;

import java.io.File;

public class LoggingListener implements EventListener {
    private final File log;
    private String message;
    private String nome;
    private String lastMessage;
    
    public LoggingListener(String logFilename, String message) {
        this.log = new File(logFilename);
        this.message = message;
    }
  
    @Override
    public void update(Object data) {
        lastMessage = String.format(message, data);
        System.out.println(lastMessage);
    }

    @Override
    public void update(String mensagem) {
        lastMessage = nome + " recebeu a mensagem: " + mensagem;
        System.out.println(lastMessage);
    }

    public String getLastMessage() {
        return lastMessage;
    }
}