package raf.dsw.gerumap.app.mapRepository;

import raf.dsw.gerumap.app.core.MapRepository;
import raf.dsw.gerumap.app.mapRepository.composite.MapNode;
import raf.dsw.gerumap.app.mapRepository.composite.MapNodeComposite;
import raf.dsw.gerumap.app.mapRepository.factory.FactoryUtils;
import raf.dsw.gerumap.app.mapRepository.factory.MapNodeFactory;
import raf.dsw.gerumap.app.mapRepository.implementation.ProjectExplorer;

// Klasa koja implementira MapRepository da ispuni funkcionalnosti potrebne za nas ProjectExplorer
public class MapRepositoryImplementation implements MapRepository {

    private ProjectExplorer projectExplorer;

    public MapRepositoryImplementation()
    {
        //projectExplorer = new ProjectExplorer("My project explorer");
        MapNodeFactory gimmick = FactoryUtils.getFactory(null);
        projectExplorer = (ProjectExplorer) gimmick.getNode(null);
    }
    @Override
    public ProjectExplorer getProjectExplorer() {
        return projectExplorer;
    }

    @Override
    public void addChild(MapNodeComposite parent, MapNode child) {
        parent.addChild(child);
    }
}
