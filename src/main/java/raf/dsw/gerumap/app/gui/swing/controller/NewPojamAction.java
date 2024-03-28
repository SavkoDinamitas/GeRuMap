package raf.dsw.gerumap.app.gui.swing.controller;

import raf.dsw.gerumap.app.gui.swing.view.MainFrame;

import java.awt.event.ActionEvent;

public class NewPojamAction extends AbstractGeRuMapAction{

    public NewPojamAction(){
        putValue(NAME, "Novi pojam");
        putValue(SHORT_DESCRIPTION, "Pravljenje novog pojma");
        putValue(SMALL_ICON, loadIcon("/images/newPojam.png", 25, 25));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        MainFrame.getInstance().getCurrentProjectView().startDodavanjePojmaState();
    }
}
