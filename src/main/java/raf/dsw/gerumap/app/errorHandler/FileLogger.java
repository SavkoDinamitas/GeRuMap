package raf.dsw.gerumap.app.errorHandler;

import raf.dsw.gerumap.app.core.ApplicationFramework;
import raf.dsw.gerumap.app.observer.Notification;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileLogger implements Logger{

    public FileLogger(){
        ApplicationFramework.getInstance().getMessageGenerator().addSubscriber(this);
    }

    @Override
    public void update(Notification notification) {
        log(((Message)notification.getObjectOfNotification()).toString());

    }

    @Override
    public void log(String naziv) {
        try {
            File resource = new File(getClass().getClassLoader().getResource("logger/log.txt").getFile());
            //Path path = Paths.get(resource.toURI());
            FileWriter fw = new FileWriter(resource, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fw);
            // append
            bufferedWriter.write(naziv + "\n");
            bufferedWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
