package org.atech;
public class Application {
    public static void config() {
        Editor editor = new Editor();

        LoggingListener logger = new LoggingListener(
            "/path/to/log.txt",
            "Someone has opened the file: %s");
        editor.events.subscribe("open", logger);

        EmailAlertsListener emailAlerts = new EmailAlertsListener(
            "admin@example.com",
            "Someone has changed the file: %s");
        editor.events.subscribe("save", emailAlerts);
         
        DeletionListener deletionListener = new DeletionListener("Observer name");
        editor.events.subscribe("delete", deletionListener);
    }
  
}