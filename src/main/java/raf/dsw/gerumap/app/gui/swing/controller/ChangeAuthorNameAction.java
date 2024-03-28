package raf.dsw.gerumap.app.gui.swing.controller;

import raf.dsw.gerumap.app.core.ApplicationFramework;
import raf.dsw.gerumap.app.errorHandler.MessageType;
import raf.dsw.gerumap.app.gui.swing.commands.AbstractCommand;
import raf.dsw.gerumap.app.gui.swing.commands.implementation.ChangeAuthorCommand;
import raf.dsw.gerumap.app.gui.swing.tree.model.MapTreeItem;
import raf.dsw.gerumap.app.gui.swing.view.dialogs.EditAuthorDialog;
import raf.dsw.gerumap.app.gui.swing.view.MainFrame;
import raf.dsw.gerumap.app.mapRepository.implementation.Project;

import java.awt.event.ActionEvent;

// Klasa koja predstavlja Action koje ce dugme unutar Dialog-a za promenu autora Project-a izvrsiti
public class ChangeAuthorNameAction extends AbstractGeRuMapAction{

    public ChangeAuthorNameAction(){putValue(NAME, "Change author");}

    @Override
    public void actionPerformed(ActionEvent e) {
        MapTreeItem selected = (MapTreeItem) MainFrame.getInstance().getMapTree().getSelectedNode();
        if(!EditAuthorDialog.getInstance().getATx().getText().equals(""))
        {
            AbstractCommand editAuthor = new ChangeAuthorCommand((Project) selected.getMapNode());
            ApplicationFramework.getInstance().getGui().getCommandManager().addCommand(editAuthor);
            EditAuthorDialog.getInstance().dispose();
        }
        else
        {
            ApplicationFramework.getInstance().getMessageGenerator().generateMessage(MessageType.ERROR, "Nije moguce staviti prazan string za autora");
        }

        //System.out.println(((Project)selected.getMapNode()).getName() + " " + ((Project)selected.getMapNode()).getAutor());
    }
}
