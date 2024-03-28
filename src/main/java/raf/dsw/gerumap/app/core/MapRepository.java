package raf.dsw.gerumap.app.core;

import raf.dsw.gerumap.app.mapRepository.composite.MapNode;
import raf.dsw.gerumap.app.mapRepository.composite.MapNodeComposite;
import raf.dsw.gerumap.app.mapRepository.implementation.ProjectExplorer;

// Interface koji sluzi za ProjectExplorer i dodavanje elemenata na njega
public interface MapRepository {
    ProjectExplorer getProjectExplorer();

    void addChild(MapNodeComposite parent, MapNode child);
}
