package raf.dsw.gerumap.app.gui.swing.controller;

import raf.dsw.gerumap.app.gui.swing.view.MainFrame;

import java.awt.event.ActionEvent;

public class UkloniAction extends AbstractGeRuMapAction{
    public UkloniAction(){
        putValue(NAME, "Delete");
        putValue(SHORT_DESCRIPTION, "Uklanjanje selektovanog objekta");
        putValue(SMALL_ICON, loadIcon("/images/deletePojam.png", 25, 25));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        MainFrame.getInstance().getCurrentProjectView().startBrisanjeState();
    }
}
