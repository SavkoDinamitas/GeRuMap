package raf.dsw.gerumap.app.gui.swing.tree.view;

import raf.dsw.gerumap.app.gui.swing.tree.model.MapTreeItem;
import raf.dsw.gerumap.app.mapRepository.implementation.Pojam;
import raf.dsw.gerumap.app.mapRepository.implementation.MindMap;
import raf.dsw.gerumap.app.mapRepository.implementation.Project;
import raf.dsw.gerumap.app.mapRepository.implementation.ProjectExplorer;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;
import java.net.URL;

// Klasa koja svakoj komponenti/Cell-u unutar naseg Tree-a dodeljuje ikonicu
public class MapTreeCellRenderer extends DefaultTreeCellRenderer {
    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
        URL imageURL = null;
        MapTreeItem treeItem = (MapTreeItem)value;
        if (treeItem.getMapNode() instanceof ProjectExplorer) {
            imageURL = getClass().getResource("/images/tdiagram.gif");
        }
        else if (treeItem.getMapNode() instanceof Project) {
            imageURL = getClass().getResource("/images/tproject.gif");
        }

        else if (treeItem.getMapNode() instanceof MindMap) {
            imageURL = getClass().getResource("/images/mindMap.png");
        }

        else if (treeItem.getMapNode() instanceof Pojam) {
            imageURL = getClass().getResource("/images/element.png");
        }

        Icon icon = null;
        if (imageURL != null)
            icon = new ImageIcon(imageURL);
        this.setIcon(icon);
        return this;
    }
}
