package raf.dsw.gerumap.app.mapRepository.factory;

import raf.dsw.gerumap.app.mapRepository.composite.MapNode;
import raf.dsw.gerumap.app.mapRepository.composite.MapNodeComposite;
import raf.dsw.gerumap.app.mapRepository.implementation.MindMap;
import raf.dsw.gerumap.app.mapRepository.implementation.Project;

public class MindMapFactory extends MapNodeFactory{
    @Override
    protected MapNode createNode() {
        return new MindMap("MindMap", null);
    }
}
