package raf.dsw.gerumap.app.gui.swing.controller;

import raf.dsw.gerumap.app.core.ApplicationFramework;
import raf.dsw.gerumap.app.errorHandler.MessageType;
import raf.dsw.gerumap.app.gui.swing.tree.model.MapTreeItem;
import raf.dsw.gerumap.app.gui.swing.view.MainFrame;
import raf.dsw.gerumap.app.mapRepository.implementation.MindMap;
import raf.dsw.gerumap.app.mapRepository.implementation.Project;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;

public class LoadTemplateAction extends AbstractGeRuMapAction{

    public LoadTemplateAction(){
        putValue(NAME, "LoadTemplate");
        putValue(SHORT_DESCRIPTION, "Load mind map template from file");
        putValue(SMALL_ICON, loadIcon("/images/loadTemplate.png", 30, 30));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        MapTreeItem selected = MainFrame.getInstance().getMapTree().getSelectedNode();
        if(selected == null || !(selected.getMapNode() instanceof MindMap)){
            ApplicationFramework.getInstance().getMessageGenerator().generateMessage(MessageType.ERROR, "Niste selektovali Mind Map objekat");
            return;
        }

        if(selected.getMapNode() instanceof MindMap && ((MindMap) selected.getMapNode()).getChildren().size() != 0){
            ApplicationFramework.getInstance().getMessageGenerator().generateMessage(MessageType.WARNING, "Izaberite praznu mapu uma");
            return;
        }

        JFileChooser jfc = new JFileChooser(getClass().getResource("/templates/").getPath());

        if (jfc.showOpenDialog(MainFrame.getInstance()) == JFileChooser.APPROVE_OPTION) {
            try {
                File file = jfc.getSelectedFile();
                MindMap map = ApplicationFramework.getInstance().getSerializer().loadTemplate(file);
                MainFrame.getInstance().getMapTree().loadTemplate(map);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
