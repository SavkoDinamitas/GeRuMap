package raf.dsw.gerumap.app.gui.swing.controller;

import raf.dsw.gerumap.app.core.ApplicationFramework;
import raf.dsw.gerumap.app.errorHandler.MessageGenerator;
import raf.dsw.gerumap.app.errorHandler.MessageType;
import raf.dsw.gerumap.app.gui.swing.view.MainFrame;
import raf.dsw.gerumap.app.mapRepository.implementation.Project;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;

public class SaveAction extends AbstractGeRuMapAction{

    public SaveAction(){
        putValue(NAME, "Save");
        putValue(SHORT_DESCRIPTION, "Save project");
        putValue(SMALL_ICON, loadIcon("/images/save.png", 30, 30));
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser jfc = new JFileChooser();

        if(MainFrame.getInstance().getMapTree().getSelectedNode() == null){
            ApplicationFramework.getInstance().getMessageGenerator().generateMessage(MessageType.WARNING, "Niste selektovali projekat za cuvanje");
            return;
        }

        if (!(MainFrame.getInstance().getMapTree().getSelectedNode().getMapNode() instanceof Project)) {
            ApplicationFramework.getInstance().getMessageGenerator().generateMessage(MessageType.WARNING, "Selektovani cvor mora biti tipa projekat da bi mogao da se sacuva");
            return;
        }

        Project project = (Project) MainFrame.getInstance().getMapTree().getSelectedNode().getMapNode();
        File projectFile = null;

        if (!project.isChanged()) {
            return;
        }

        if (project.getFilePath() == null || project.getFilePath().isEmpty()) {
            if (jfc.showSaveDialog(MainFrame.getInstance()) == JFileChooser.APPROVE_OPTION) {
                projectFile = jfc.getSelectedFile();
                project.setFilePath(projectFile.getPath());
            } else {
                return;
            }

        }


        ApplicationFramework.getInstance().getSerializer()
                .saveProject(project);

        project.setChanged(false);
        ApplicationFramework.getInstance().getMessageGenerator().generateMessage(MessageType.INFO, "Projekat je sacuvan");
    }
}
