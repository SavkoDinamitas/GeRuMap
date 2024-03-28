package raf.dsw.gerumap.app.core;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raf.dsw.gerumap.app.errorHandler.Logger;
import raf.dsw.gerumap.app.errorHandler.LoggerFactory;
import raf.dsw.gerumap.app.errorHandler.MessageGenerator;

import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Setter
@Getter

// Klasa koja zapravo pokrece nasu aplikaciju
public class ApplicationFramework
{
    protected Gui gui;

    protected MapRepository mapRepository;

    private MessageGenerator messageGenerator;

    private Logger consoleLogger;

    private Logger fileLogger;

    private Serializer serializer;

    public void run(){
        gui.start();
    }

    public void initialise(Gui gui, MapRepository mapRepository, Serializer serializer)
    {
        this.gui = gui;
        this.mapRepository = mapRepository;
        this.serializer = serializer;
        messageGenerator = new MessageGenerator();
        //simple factory za Loggere
        LoggerFactory xd = new LoggerFactory();
        consoleLogger = xd.createLogger("consolelogger");
        fileLogger = xd.createLogger("filelogger");
    }

    private static ApplicationFramework instance;

    private ApplicationFramework(){

    }
    public static ApplicationFramework getInstance(){
        if(instance==null){
            instance = new ApplicationFramework();
        }
        return instance;
    }

    public void clearLog()
    {
        try
        {
            File resource = new File(getClass().getClassLoader().getResource("logger/log.txt").getFile());
            Path path = Paths.get(resource.toURI());
            BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardCharsets.UTF_8);
            bufferedWriter.write("");
            bufferedWriter.close();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
