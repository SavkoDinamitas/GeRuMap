package raf.dsw.gerumap.app.core;

import raf.dsw.gerumap.app.mapRepository.implementation.MindMap;
import raf.dsw.gerumap.app.mapRepository.implementation.Project;

import java.io.File;

public interface Serializer {
    Project loadProject(File file);

    void saveProject(Project project);

    void saveTemplate(MindMap map);

    MindMap loadTemplate(File file);
}
