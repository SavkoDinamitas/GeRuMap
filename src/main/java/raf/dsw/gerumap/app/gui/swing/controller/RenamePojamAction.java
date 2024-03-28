package raf.dsw.gerumap.app.gui.swing.controller;

import raf.dsw.gerumap.app.gui.swing.view.MainFrame;

import java.awt.event.ActionEvent;

public class RenamePojamAction extends AbstractGeRuMapAction{

    public RenamePojamAction(){
        putValue(NAME, "Rename");
        putValue(SHORT_DESCRIPTION, "Promena imena pojma");
        putValue(SMALL_ICON, loadIcon("/images/renamePojam.png", 25, 25));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        MainFrame.getInstance().getCurrentProjectView().startPromenaImenaPojmaState();
    }
}
