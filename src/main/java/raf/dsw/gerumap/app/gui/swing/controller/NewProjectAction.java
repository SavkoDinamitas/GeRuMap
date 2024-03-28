package raf.dsw.gerumap.app.gui.swing.controller;

import raf.dsw.gerumap.app.core.ApplicationFramework;
import raf.dsw.gerumap.app.errorHandler.MessageGenerator;
import raf.dsw.gerumap.app.errorHandler.MessageType;
import raf.dsw.gerumap.app.gui.swing.tree.model.MapTreeItem;
import raf.dsw.gerumap.app.gui.swing.view.MainFrame;
import raf.dsw.gerumap.app.mapRepository.implementation.MindMap;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

// Klasa pravi element odredjenog tipa u zavisnosti od trenutnog elementa i na trenutni element dodaje novonapravljeni element
public class NewProjectAction extends  AbstractGeRuMapAction
{
    public  NewProjectAction()
    {
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        putValue(SMALL_ICON, loadIcon("/images/plusic.png", 30, 30));
        putValue(NAME, "New Project");
        putValue(SHORT_DESCRIPTION, "New Project");
    }

    public void actionPerformed(ActionEvent arg0)
    {
        MapTreeItem selected = (MapTreeItem) MainFrame.getInstance().getMapTree().getSelectedNode();
        if(selected == null)
        {
            ApplicationFramework.getInstance().getMessageGenerator().generateMessage(MessageType.WARNING, "Nije moguce dodati novi cvor");
        }
        else if (selected.getMapNode() instanceof MindMap){
            ApplicationFramework.getInstance().getMessageGenerator().generateMessage(MessageType.INFO, "Ne moze se dodati dete na cvor tipa MindMap");
        }
        else
        {
            MainFrame.getInstance().getMapTree().addChild(selected);
        }

    }
}
