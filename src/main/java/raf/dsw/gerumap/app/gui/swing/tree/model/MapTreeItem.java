package raf.dsw.gerumap.app.gui.swing.tree.model;

import lombok.Getter;
import lombok.Setter;
import raf.dsw.gerumap.app.mapRepository.composite.MapNode;
import raf.dsw.gerumap.app.mapRepository.implementation.ProjectExplorer;

import javax.swing.tree.DefaultMutableTreeNode;

@Getter
@Setter

// Klasa koja predstavlja tip podatka koji se nalazi u nasem MapTree-ju
public class MapTreeItem extends DefaultMutableTreeNode {
    private MapNode mapNode;

    public MapTreeItem(MapNode nodeModel){
        this.mapNode = nodeModel;
    }

    @Override
    public String toString(){
        return mapNode.getName();
    }

    public String getName()
    {
        return mapNode.getName();
    }

    public void setName(String name){
        this.mapNode.setName(name);
    }

    // Ovde ne treba da se implementira getParent i setParent jer klasa nasledjuje DefaultMutableTreeNode
}
