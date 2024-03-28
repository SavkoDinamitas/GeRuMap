package raf.dsw.gerumap.app.gui.swing.tree;

import raf.dsw.gerumap.app.gui.swing.tree.model.MapTreeItem;
import raf.dsw.gerumap.app.gui.swing.tree.view.MapTreeView;
import raf.dsw.gerumap.app.mapRepository.implementation.MindMap;
import raf.dsw.gerumap.app.mapRepository.implementation.Project;
import raf.dsw.gerumap.app.mapRepository.implementation.ProjectExplorer;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;

public interface MapTree {
    MapTreeView generateTree(ProjectExplorer projectExplorer);
    void addChild(MapTreeItem parent);

    void deleteChild(MapTreeItem child);

    DefaultTreeModel vratiModel();

    JTree vratiTreeView();

    MapTreeItem getSelectedNode();

    void loadProject(Project p);

    void loadTemplate(MindMap map);

    void expandTree();
}
