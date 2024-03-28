package raf.dsw.gerumap.app.gui.swing.controller;

import raf.dsw.gerumap.app.core.ApplicationFramework;
import raf.dsw.gerumap.app.gui.swing.view.MainFrame;
import raf.dsw.gerumap.app.mapRepository.implementation.Project;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;

public class OpenAction extends AbstractGeRuMapAction{

    public OpenAction(){
        putValue(NAME, "Open");
        putValue(SHORT_DESCRIPTION, "Open file from directory");
        putValue(SMALL_ICON, loadIcon("/images/open.png", 30, 30));
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser jfc = new JFileChooser();

        if (jfc.showOpenDialog(MainFrame.getInstance()) == JFileChooser.APPROVE_OPTION) {
            try {
                File file = jfc.getSelectedFile();
                Project p = ApplicationFramework.getInstance().getSerializer().loadProject(file);
                MainFrame.getInstance().getMapTree().loadProject(p);
                p.setChanged(false);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
