package raf.dsw.gerumap.app;

import raf.dsw.gerumap.app.core.ApplicationFramework;
import raf.dsw.gerumap.app.core.Gui;
import raf.dsw.gerumap.app.core.MapRepository;
import raf.dsw.gerumap.app.core.Serializer;
import raf.dsw.gerumap.app.gui.swing.SwingGui;
import raf.dsw.gerumap.app.mapRepository.MapRepositoryImplementation;
import raf.dsw.gerumap.app.serializer.JacksonSerializer;

public class AppCore
{
    public static void main(String[] args)
    {
        Gui gui = new SwingGui();
        ApplicationFramework appCore = ApplicationFramework.getInstance();
        //brisanje stvari koje se vec nalaze u log.txt
        appCore.clearLog();
        MapRepository mapRepository = new MapRepositoryImplementation();
        Serializer serializer = new JacksonSerializer();
        appCore.initialise(gui, mapRepository, serializer);
        appCore.run();
    }
}
