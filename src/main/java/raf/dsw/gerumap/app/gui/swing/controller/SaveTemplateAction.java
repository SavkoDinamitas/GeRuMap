package raf.dsw.gerumap.app.gui.swing.controller;

import raf.dsw.gerumap.app.core.ApplicationFramework;
import raf.dsw.gerumap.app.errorHandler.MessageType;
import raf.dsw.gerumap.app.gui.swing.view.MainFrame;
import raf.dsw.gerumap.app.mapRepository.implementation.MindMap;

import java.awt.event.ActionEvent;
import java.io.File;

public class SaveTemplateAction extends AbstractGeRuMapAction{
    public SaveTemplateAction(){
        putValue(NAME, "Save Template");
        putValue(SHORT_DESCRIPTION, "Save mind map as template");
        putValue(SMALL_ICON, loadIcon("/images/template.png", 30, 30));
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(MainFrame.getInstance().getMapTree().getSelectedNode() == null){
            ApplicationFramework.getInstance().getMessageGenerator().generateMessage(MessageType.WARNING, "Niste selektovali mapu uma za cuvanje");
            return;
        }

        if (!(MainFrame.getInstance().getMapTree().getSelectedNode().getMapNode() instanceof MindMap)) {
            ApplicationFramework.getInstance().getMessageGenerator().generateMessage(MessageType.WARNING, "Selektovani cvor mora biti tipa MindMap da bi mogao da se sacuva");
            return;
        }

        MindMap mindMap = (MindMap) MainFrame.getInstance().getMapTree().getSelectedNode().getMapNode();

        ApplicationFramework.getInstance().getSerializer().saveTemplate(mindMap);

        ApplicationFramework.getInstance().getMessageGenerator().generateMessage(MessageType.INFO, "Mapa uma je uspesno sacuvana");

    }
}
