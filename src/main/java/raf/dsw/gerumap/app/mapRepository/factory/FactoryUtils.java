package raf.dsw.gerumap.app.mapRepository.factory;

import raf.dsw.gerumap.app.mapRepository.composite.MapNode;
import raf.dsw.gerumap.app.mapRepository.implementation.MindMap;
import raf.dsw.gerumap.app.mapRepository.implementation.Project;
import raf.dsw.gerumap.app.mapRepository.implementation.ProjectExplorer;

// Klasa koja nam vraca odredjenu instancu MapNodeFactory-ja u zavisnosti od toga kog je tipa zadat Parent
public class FactoryUtils {
    public static MapNodeFactory getFactory(MapNode parent){
        if(parent == null)
            return new ProjectExplorerFactory();
        if (parent instanceof ProjectExplorer)
            return new ProjectFactory();
        if (parent instanceof Project)
            return new MindMapFactory();
        if (parent instanceof MindMap)
            return new PojamFactory();
        return null;
    }
}
