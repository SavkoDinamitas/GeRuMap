package raf.dsw.gerumap.app.gui.swing.controller;

import raf.dsw.gerumap.app.core.ApplicationFramework;
import raf.dsw.gerumap.app.errorHandler.MessageType;
import raf.dsw.gerumap.app.gui.swing.tree.model.MapTreeItem;
import raf.dsw.gerumap.app.gui.swing.view.dialogs.EditAuthorDialog;
import raf.dsw.gerumap.app.gui.swing.view.MainFrame;
import raf.dsw.gerumap.app.mapRepository.implementation.Project;

import java.awt.event.ActionEvent;

// Klasa koja nam otvara Dialog/Window u kom menjamo ime autora Project-a
public class EditAction extends AbstractGeRuMapAction
{
    public EditAction()
    {
        putValue(NAME, "Edit");
        putValue(SHORT_DESCRIPTION, "Change author name");
        putValue(SMALL_ICON, loadIcon("/images/edit.png", 30, 30));
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        MapTreeItem selected = (MapTreeItem) MainFrame.getInstance().getMapTree().getSelectedNode();
        if(selected != null && selected.getMapNode() instanceof Project) {
            EditAuthorDialog.getInstance().start();
        }
        else{
            ApplicationFramework.getInstance().getMessageGenerator().generateMessage(MessageType.ERROR, "Autor moze da se promeni samo nad cvorovima tipa Project");
        }
    }
}
