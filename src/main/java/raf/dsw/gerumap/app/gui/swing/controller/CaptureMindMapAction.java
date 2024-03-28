package raf.dsw.gerumap.app.gui.swing.controller;

import raf.dsw.gerumap.app.core.ApplicationFramework;
import raf.dsw.gerumap.app.errorHandler.MessageType;
import raf.dsw.gerumap.app.gui.swing.view.MainFrame;
import raf.dsw.gerumap.app.gui.swing.view.MindMapPanel;
import raf.dsw.gerumap.app.gui.swing.view.ScrollPaneMindMap;
import raf.dsw.gerumap.app.gui.swing.view.dialogs.CaptureChoiceDialog;
import raf.dsw.gerumap.app.mapRepository.implementation.Project;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CaptureMindMapAction extends AbstractGeRuMapAction{

    public CaptureMindMapAction()
    {
        putValue(NAME, "Capture MindMap");
        putValue(SHORT_DESCRIPTION, "Take screenshot");
        putValue(SMALL_ICON, loadIcon("/images/capture.png", 30, 30));
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        // Cilj mi je da pokazem koji je trenutni MindMapPanel
        if(MainFrame.getInstance().getCurrentProjectView() == null || MainFrame.getInstance().getCurrentProjectView().getProjekat() == null || MainFrame.getInstance().getCurrentProjectView().getTabbedPane() == null || (ScrollPaneMindMap) MainFrame.getInstance().getCurrentProjectView().getTabbedPane().getSelectedComponent() == null)
        {
            ApplicationFramework.getInstance().getMessageGenerator().generateMessage(MessageType.ERROR, "Nije otvorena nijedna MindMap-a");
            return;
        }
        Project currentProject = MainFrame.getInstance().getCurrentProjectView().getProjekat();

        ScrollPaneMindMap currentScrollPane = (ScrollPaneMindMap) MainFrame.getInstance().getCurrentProjectView().getTabbedPane().getSelectedComponent();
        MindMapPanel currentPanel = currentScrollPane.getTvojaKeva();
        // Zelim da korisnik izabere da li ce da capture-uje ceo panel ili samo centralizovan deo
        if(((ScrollPaneMindMap) MainFrame.getInstance().getCurrentProjectView().getTabbedPane().getSelectedComponent()).getTvojaKeva().samoNeRadiGetterIzgleda())
        {
            CaptureChoiceDialog captureChoiceDialog = new CaptureChoiceDialog(((ScrollPaneMindMap) MainFrame.getInstance().getCurrentProjectView().getTabbedPane().getSelectedComponent()).getTvojaKeva());
            if(captureChoiceDialog.isClosed())
            {
                return;
            }
        }
        BufferedImage capturedImage = currentPanel.createImage();
        if(capturedImage == null)
        {
            ApplicationFramework.getInstance().getMessageGenerator().generateMessage(MessageType.WARNING, "MindMap-a je prazna tako da se nije sacuvala");
            return;
        }

        JFileChooser jfc = new JFileChooser();
        jfc.setFileFilter(new FileNameExtensionFilter("*.png", "png"));
        if(jfc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION)
        {
            File file = jfc.getSelectedFile();
            try
            {
                ImageIO.write(capturedImage, "png", new File(file.getAbsolutePath()));
                ApplicationFramework.getInstance().getMessageGenerator().generateMessage(MessageType.INFO, "Slika je uspesno sacuvana");
            }
            catch (IOException ex)
            {
                ApplicationFramework.getInstance().getMessageGenerator().generateMessage(MessageType.ERROR, "Slika nije uspesno sacuvana");
            }
        }
    }
}
