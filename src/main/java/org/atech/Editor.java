package org.atech;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
public class Editor {
    public EventManager events;
    private Path file;

    public Editor() {
        this.events = new EventManager();
    }

    // Evento Open
    public void openFile(String path) {
        this.file = Path.of(path);
        //this.file = Paths.get(path);
        events.notify("open", file.getFileName().toString());
    }
    
    // Evento Delete
    public void deleteFile() {
        // Lógica para exclusão do arquivo
        events.notify("delete", file.getFileName().toString());
    }

    // Evento Save
    public void saveFile() {
        try {
            if (file != null) {
                String conteudo = "conteúdo do arquivo";
                Files.write(file, conteudo.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
                
                events.notify("save", file.getFileName().toString());

            } else {
                System.out.println("Nenhum arquivo aberto para salvar.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // ...
}