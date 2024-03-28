package raf.dsw.gerumap.app.gui.swing.controller;

import raf.dsw.gerumap.app.gui.swing.view.MainFrame;

import java.awt.event.ActionEvent;

public class ZoomAction extends AbstractGeRuMapAction{

    public ZoomAction(){
        putValue(NAME, "Zoom");
        putValue(SHORT_DESCRIPTION, "Zumiranje mape uma");
        putValue(SMALL_ICON, loadIcon("/images/zoom.png", 25, 25));
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        MainFrame.getInstance().getCurrentProjectView().startUvelicavanjeState();
    }
}
