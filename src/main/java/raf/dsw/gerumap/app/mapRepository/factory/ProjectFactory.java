package raf.dsw.gerumap.app.mapRepository.factory;

import raf.dsw.gerumap.app.mapRepository.composite.MapNode;
import raf.dsw.gerumap.app.mapRepository.composite.MapNodeComposite;
import raf.dsw.gerumap.app.mapRepository.implementation.Project;
import raf.dsw.gerumap.app.mapRepository.implementation.ProjectExplorer;

public class ProjectFactory extends MapNodeFactory{

    @Override
    public MapNode createNode() {
        return new Project("Project", null);
    }
}
