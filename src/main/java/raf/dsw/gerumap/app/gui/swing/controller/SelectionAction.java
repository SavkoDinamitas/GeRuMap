package raf.dsw.gerumap.app.gui.swing.controller;

import raf.dsw.gerumap.app.gui.swing.view.MainFrame;

import java.awt.event.ActionEvent;

public class SelectionAction extends AbstractGeRuMapAction{

    public SelectionAction(){
        putValue(NAME, "Selekcija");
        putValue(SHORT_DESCRIPTION, "Selektovanje elementa");
        putValue(SMALL_ICON, loadIcon("/images/select.png", 25, 25));
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        MainFrame.getInstance().getCurrentProjectView().startSelekcijaState();
    }
}
