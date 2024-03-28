package raf.dsw.gerumap.app.gui.swing.controller;

import raf.dsw.gerumap.app.core.ApplicationFramework;

import java.awt.*;
import java.awt.event.ActionEvent;

public class RedoAction extends AbstractGeRuMapAction{

    public RedoAction(){
        putValue(NAME, "Redo");
        putValue(SHORT_DESCRIPTION, "Redo");
        putValue(SMALL_ICON, loadIcon("/images/redo.png", 30, 30));
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        ApplicationFramework.getInstance().getGui().getCommandManager().doCommand();
    }
}
