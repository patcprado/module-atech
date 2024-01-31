package org.atech;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.Date;

public class RunApplication {
        private static String path = "/atech/";
        private static String peopleFile = path + "people.txt";
        private static String testFile = path + "testFile.txt";
        private static String logFile = path + "atech.log";

        public static void main(String[] args) throws IOException {

            Map<String, String> pessoas = readPeopleFile(peopleFile);
            ArrayList<String> report = new ArrayList<String>();

            // Criando um pool de threads
            ExecutorService executorService = Executors.newFixedThreadPool(pessoas.size());

            // Iterando sobre as pessoas e executando ação simulada em threads separadas
            Set<String> chaves = pessoas.keySet();
            for (String chave : chaves) {
                String nome = chave;
                String email = pessoas.get(chave);

                executorService.execute(() -> simulaAction(nome, email, report));
            }

            // Encerrando o pool de threads
            executorService.shutdown();

            creatReportFile(report);

        }

        private static void creatReportFile(ArrayList<String> report) throws IOException {
            FileWriter arq;
            String data = getDatahora().replaceAll("[/:\\s]", "");
            arq = new FileWriter(path + "Report"+ data + ".txt");
            PrintWriter gravarArqDiff = new PrintWriter(arq);
            for (String line : report) {
                gravarArqDiff.print(line+"\n");
            }
            gravarArqDiff.close();
            System.out.println("\nFinished.");
        }

        private static void addReport(String nome, String action, ArrayList<String> report) {
            report.add(nome + "|" + getDatahora() + "|" + action);
        }

        private static void simulaAction(String nome, String email, ArrayList<String> report) {
            Editor editor = new Editor();
            LoggingListener loggerOriginal = new LoggingListener(logFile, nome + " has opened the file: %s");
            EmailAlertsListener emailAlertsOriginal = new EmailAlertsListener(email, nome + " has changed the file: %s");
            editor.events.subscribe("open", loggerOriginal);
            editor.events.subscribe("save", emailAlertsOriginal);

            // Simulação de abertura de arquivo
            addReport(nome, "open", report);
            editor.openFile(testFile);

            // Simulação de salvamento de arquivo (save com 50% de probabilidade )
            if (Math.random() < 0.7) {
                addReport(nome, "save", report);
                editor.saveFile();
            }

            // Simulação de exclusão de arquivo (delete com 20% de probabilidade)
            if (Math.random() < 0.2) {
                System.out.println("\u001B[1;31mFile "+ testFile +" was delete for " + nome + ". \u001B[0m");
                addReport(nome, "delete", report);
                editor.deleteFile();
            }
        }

        public static String getDatahora() {
            Date data = new Date();
            SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            return formatador.format(data);
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
