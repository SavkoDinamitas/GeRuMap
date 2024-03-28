package raf.dsw.gerumap.app.gui.swing.tree.controller;

import raf.dsw.gerumap.app.gui.swing.tree.model.MapTreeItem;
import raf.dsw.gerumap.app.gui.swing.view.MainFrame;
import raf.dsw.gerumap.app.gui.swing.view.MindMapPanel;
import raf.dsw.gerumap.app.gui.swing.view.ProjectView;
import raf.dsw.gerumap.app.mapRepository.composite.MapNode;
import raf.dsw.gerumap.app.mapRepository.implementation.MindMap;
import raf.dsw.gerumap.app.mapRepository.implementation.Project;
import raf.dsw.gerumap.app.observer.Notification;
import raf.dsw.gerumap.app.observer.NotificationType;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;

public class DoubleClickListener implements MouseListener {
    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getClickCount() == 2)
        {
            MapTreeItem selectedItem = MainFrame.getInstance().getMapTree().getSelectedNode();
            if(selectedItem.getMapNode() instanceof Project)
            {
                Project currentProject = (Project) selectedItem.getMapNode();
                if(MainFrame.getInstance().getProjectView(currentProject) == null){
                    MainFrame.getInstance().getProjectViewList().add(new ProjectView(currentProject));
                }
                currentProject.notifySubscribers(new Notification(currentProject, NotificationType.SHOW));
                MainFrame.getInstance().setCurrentProjectView(MainFrame.getInstance().getProjectView(currentProject));
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
