package raf.dsw.gerumap.app.gui.swing.controller;

import raf.dsw.gerumap.app.gui.swing.view.MainFrame;

import java.awt.event.ActionEvent;

public class EditStyleAction extends AbstractGeRuMapAction{

    public EditStyleAction(){
        putValue(NAME, "Menjanje stila");
        putValue(SHORT_DESCRIPTION, "Menjanje stila pojma");
        putValue(SMALL_ICON, loadIcon("/images/editStyle.png", 25, 25));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        MainFrame.getInstance().getCurrentProjectView().startMenjanjeStilaState();
    }
}
