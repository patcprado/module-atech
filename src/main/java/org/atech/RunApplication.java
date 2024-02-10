package org.atech;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//Aplicação principal
public class RunApplication {
        private final static String path = "/Users/Patricia/desen/module-atech/src/main";
        private final static String peopleFile = path + "/resources/people.txt";

        private final static String observList = path + "/resources/observadoresList.txt";

        private final static String objFileObserved = path + "/resources/observe-me.txt";
        private final static String logfile = path + "/resources/atech.log";

        public static void main(String[] args)  {

            //pessoas que vão alterar o objeto.
            Map<String, String> pessoas = readPeopleFile(peopleFile);

            // Criando um pool de threads
            ExecutorService executorService = Executors.newFixedThreadPool(pessoas.size());

            // Iterando sobre as pessoas e executando ação simulada em threads separadas
            Set<String> chaves = pessoas.keySet();
            for (String user : chaves) {
                executorService.execute(() -> simulaAction(user));
            }
            // Encerrando o pool de threads
            executorService.shutdown();
            System.out.println("\nFinished.");

        }

        private static void simulaAction(String user) {
            Map<String, String> clients = readPeopleFile(observList);

            // Notify - envia mensagem para fila que houve alteração no estado: open, save, delete
            Set<String> chaves = clients.keySet();
            for (String cliente : chaves) {
                Editor editor = new Editor();

                String emailClient = clients.get(cliente);

                notifyClients(editor,cliente, user);
                // Simulação de salvamento de arquivo (save com 70% de probabilidade)
                if (Math.random() < 0.7) {
                    sendMailWhenChanges(editor, cliente, emailClient, user);
                }
                // Simulação de exclusão de arquivo (delete com 20% de probabilidade)
                if (Math.random() < 0.2) {
                    sendMailWhenDelete(editor, cliente, emailClient, user);
                }
            }
        }

    private static void notifyClients(Editor editor, String nomeClient, String user) {
        LoggingListener loggerOriginal = new LoggingListener(logfile, "Notify " + nomeClient + ": " + user + " has opened the file: %s");

        //Emite a notificação OPEN assim que ela ocorrer.
        editor.events.subscribe("open", loggerOriginal);
        editor.openFile(objFileObserved);
        }

    private static void sendMailWhenChanges(Editor editor, String nomeClient, String emailClient, String user) {
        EmailAlertsListener emailAlertsChanges = new EmailAlertsListener(emailClient, "Notify " + nomeClient + ": "+ user + " has changed the file: %s");
        //Emite a notificação SAVE assim que ela ocorrer.
        editor.events.subscribe("save", emailAlertsChanges);
        editor.saveFile();
    }

    private static void sendMailWhenDelete(Editor editor, String nomeClient, String emailClient, String user) {
        EmailAlertsListener emailAlertsDelete = new EmailAlertsListener(emailClient, "Notify " + nomeClient + ": "+ user + " has \u001B[1;31m delete \u001B[0m the file: %s");
        editor.events.subscribe("delete", emailAlertsDelete);
        editor.deleteFile();
        }

        private static Map<String, String> readPeopleFile(String peopleFile) {
            Map<String, String> pessoas = new HashMap<>();

            Path path = Paths.get(peopleFile);

            try {
                List<String> linhas = Files.readAllLines(path);
                for (String linha : linhas) {
                    String[] partes = linha.split(";");
                    if (partes.length == 2) {
                        pessoas.put(partes[0], partes[1]);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return pessoas;
        }
}
