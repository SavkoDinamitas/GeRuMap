package raf.dsw.gerumap.app.gui.swing.commands.implementation;

import raf.dsw.gerumap.app.gui.swing.commands.AbstractCommand;
import raf.dsw.gerumap.app.gui.swing.tree.model.MapTreeItem;
import raf.dsw.gerumap.app.gui.swing.view.MindMapPanel;
import raf.dsw.gerumap.app.mapRepository.composite.MapNode;

public class RenameChildCommand extends AbstractCommand {
    private MapTreeItem xd;

    private String newName;

    private String lastName;

    public RenameChildCommand(MapTreeItem xd, String newName){
        this.xd = xd;
        this.newName = newName;
        lastName = xd.getName();
    }

    @Override
    public void doCommand() {
        xd.setName(newName);
    }

    @Override
    public void undoCommand() {
        xd.setName(lastName);
    }
}
