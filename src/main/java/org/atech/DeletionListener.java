package org.atech;

//ConcreteObserver
public class DeletionListener implements EventListener {
    private String nome;

    public DeletionListener(String nome) {
        this.nome = nome;
    }

    @Override
    public void update(Object data) {
        System.out.println(nome + " has deleted the file : %s" + data);
    }

    @Override
    public void update(String mensagem) {
        // Implementação, se necessário
    }
}