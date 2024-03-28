package raf.dsw.gerumap.app.gui.swing.controller;

import raf.dsw.gerumap.app.core.ApplicationFramework;
import raf.dsw.gerumap.app.errorHandler.MessageGenerator;
import raf.dsw.gerumap.app.errorHandler.MessageType;
import raf.dsw.gerumap.app.gui.swing.tree.model.MapTreeItem;
import raf.dsw.gerumap.app.gui.swing.view.MainFrame;
import raf.dsw.gerumap.app.mapRepository.implementation.ProjectExplorer;

import java.awt.event.ActionEvent;

// Klasa koja predstavlja Action koji brise odredjeni element i svu njegovu decu iz stabla
public class DeleteAction extends AbstractGeRuMapAction{

    public DeleteAction(){
        putValue(NAME, "Delete");
        putValue(SHORT_DESCRIPTION, "Delete the element");
        putValue(SMALL_ICON, loadIcon("/images/thrash.png", 30, 30));
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        MapTreeItem selected = (MapTreeItem) MainFrame.getInstance().getMapTree().getSelectedNode();
        if(selected != null && !(selected.getMapNode() instanceof ProjectExplorer)) {
            MainFrame.getInstance().getMapTree().deleteChild(selected);
        }
        else{
            //System.err.println("Nije selektovan cvor u stablu");
            if(selected == null)
                ApplicationFramework.getInstance().getMessageGenerator().generateMessage(MessageType.ERROR, "Nije selektovan cvor koji treba da se izbrise");
            else
                ApplicationFramework.getInstance().getMessageGenerator().generateMessage(MessageType.ERROR, "Nije moguce izbrisati ProjectExplorer");
        }
    }
}
