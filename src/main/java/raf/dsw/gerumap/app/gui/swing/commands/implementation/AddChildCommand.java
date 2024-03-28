package raf.dsw.gerumap.app.gui.swing.commands.implementation;

import lombok.Getter;
import lombok.Setter;
import raf.dsw.gerumap.app.core.ApplicationFramework;
import raf.dsw.gerumap.app.gui.swing.commands.AbstractCommand;
import raf.dsw.gerumap.app.gui.swing.tree.MapTreeImplementation;
import raf.dsw.gerumap.app.gui.swing.tree.model.MapTreeItem;
import raf.dsw.gerumap.app.gui.swing.view.MainFrame;
import raf.dsw.gerumap.app.gui.swing.view.MindMapPanel;
import raf.dsw.gerumap.app.mapRepository.composite.MapNode;
import raf.dsw.gerumap.app.mapRepository.composite.MapNodeComposite;

import javax.swing.*;
import javax.swing.tree.TreePath;

@Getter
@Setter
public class AddChildCommand extends AbstractCommand {

    private MapTreeItem parent;

    private MapNode child;

    private MapTreeItem deteItem;

    public AddChildCommand(MapTreeItem parent, MapNode child){
        this.parent = parent;
        this.child = child;
    }

    @Override
    public void doCommand() {
        //default pozivanje add na ugradjenu klasu za prikaz
        if(deteItem == null)
            deteItem = new MapTreeItem(child);
        parent.add(deteItem);

        //dodadavnje deteta u nasu kompoziciju
        ((MapNodeComposite) parent.getMapNode()).addChild(child);
        for (int k = 0; k < MainFrame.getInstance().getMapTree().vratiTreeView().getRowCount(); k++) {
            MainFrame.getInstance().getMapTree().vratiTreeView().expandRow(k);
        }
    }

    @Override
    public void undoCommand() {
       ((MapNodeComposite)(deteItem.getMapNode()).getParent()).deleteChild(deteItem.getMapNode());
        //brisanje deteta iz ugradjenog view modela
        MainFrame.getInstance().getMapTree().vratiModel().removeNodeFromParent(deteItem);
        SwingUtilities.updateComponentTreeUI(MainFrame.getInstance().getMapTree().vratiTreeView());
        //resavanje broja na novom projektu
    }
}
