package raf.dsw.gerumap.app.gui.swing.controller;

import raf.dsw.gerumap.app.core.ApplicationFramework;
import raf.dsw.gerumap.app.gui.swing.commands.AbstractCommand;

import java.awt.*;
import java.awt.event.ActionEvent;

public class UndoAction extends AbstractGeRuMapAction {

    public UndoAction (){
        putValue(NAME, "Undo");
        putValue(SHORT_DESCRIPTION, "Undo");
        putValue(SMALL_ICON, loadIcon("/images/undo.png", 30, 30));
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        ApplicationFramework.getInstance().getGui().getCommandManager().undoCommand();
    }
}
