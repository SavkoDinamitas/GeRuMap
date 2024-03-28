package raf.dsw.gerumap.app.mapRepository.factory;

import raf.dsw.gerumap.app.mapRepository.composite.MapNode;
import raf.dsw.gerumap.app.mapRepository.implementation.ProjectExplorer;

// Jedina Potklasa MapNodeFactory-ja koja se zapravo ne poziva nigde
// Sada se i ova klasa poziva tako da nije izostavljena :)
public class ProjectExplorerFactory extends MapNodeFactory{

    @Override
    public MapNode createNode() {
        return new ProjectExplorer("Project Explorer");
    }
}
