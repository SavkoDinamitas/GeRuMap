package raf.dsw.gerumap.app.gui.swing.controller;

import raf.dsw.gerumap.app.gui.swing.view.MainFrame;

import java.awt.event.ActionEvent;

public class NewVezaAction extends AbstractGeRuMapAction{

    public NewVezaAction(){
        putValue(NAME, "Nova veza");
        putValue(SHORT_DESCRIPTION, "Pravljenje nove asocijacije");
        putValue(SMALL_ICON, loadIcon("/images/veza.png", 25, 25));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        MainFrame.getInstance().getCurrentProjectView().startDodavanjeVezeState();
    }
}
