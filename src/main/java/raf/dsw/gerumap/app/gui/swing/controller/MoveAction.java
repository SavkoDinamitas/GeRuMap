package raf.dsw.gerumap.app.gui.swing.controller;

import raf.dsw.gerumap.app.gui.swing.view.MainFrame;

import java.awt.event.ActionEvent;

public class MoveAction extends AbstractGeRuMapAction{

    public MoveAction(){
        putValue(NAME, "Pomeranje");
        putValue(SHORT_DESCRIPTION, "Pomeranje pojma");
        putValue(SMALL_ICON, loadIcon("/images/movePojam.png", 25, 25));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        MainFrame.getInstance().getCurrentProjectView().startPomeranjeState();
    }
}
