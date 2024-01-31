package org.atech;

public class EmailAlertsListener implements EventListener {
    private String email;
    private String message;
    private String nome;
    private String lastMessage;
    
    
    public EmailAlertsListener(String nome) {
        this.nome = nome;
    }
      
    public EmailAlertsListener(String email, String message) {
        this.email = email;
        this.message = message;
    }

    @Override
    public void update(Object data) {
        lastMessage = String.format(message, data);
        // enviar email (ou imprimir no console, dependendo da implementação real)
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