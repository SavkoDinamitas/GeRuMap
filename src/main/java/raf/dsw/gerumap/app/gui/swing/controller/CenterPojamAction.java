package raf.dsw.gerumap.app.gui.swing.controller;

import raf.dsw.gerumap.app.core.ApplicationFramework;
import raf.dsw.gerumap.app.errorHandler.MessageType;
import raf.dsw.gerumap.app.gui.swing.commands.AbstractCommand;
import raf.dsw.gerumap.app.gui.swing.commands.implementation.CenterPojamCommand;
import raf.dsw.gerumap.app.gui.swing.view.MainFrame;
import raf.dsw.gerumap.app.gui.swing.view.MindMapPanel;
import raf.dsw.gerumap.app.gui.swing.view.PojamPainter;
import raf.dsw.gerumap.app.gui.swing.view.ScrollPaneMindMap;

import java.awt.*;
import java.awt.event.ActionEvent;

public class CenterPojamAction extends AbstractGeRuMapAction
{
    public CenterPojamAction()
    {
        putValue(NAME, "Center Pojam");
        putValue(SHORT_DESCRIPTION, "Center a pojam on the panel");
        putValue(SMALL_ICON, loadIcon("/images/center.png", 30, 30));
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        ScrollPaneMindMap komedija = (ScrollPaneMindMap) MainFrame.getInstance().getCurrentProjectView().getTabbedPane().getSelectedComponent();
        if(komedija.getTvojaKeva().getSelectionModel().size() != 1)
        {
            ApplicationFramework.getInstance().getMessageGenerator().generateMessage(MessageType.ERROR, "Morate izabrati iskljucivo jedan pojam za centriranje");
            return;
        }
        Point centarEkrana = new Point(komedija.getTvojaKeva().getWidth() / 2, komedija.getTvojaKeva().getHeight() / 2);
        //System.out.println("x: " + komedija.getTvojaKeva().getWidth() / 2 + " y: " + komedija.getTvojaKeva().getHeight() / 2);
        //komedija.getViewport().setLocation(new Point(0, 0));
        System.out.println("x: " + centarEkrana.x + " y: " + centarEkrana.y);
        AbstractCommand centriranje = new CenterPojamCommand(centarEkrana, ((PojamPainter)(komedija.getTvojaKeva().getSelectionModel().get(0))).getPojam(), komedija.getTvojaKeva());
        ApplicationFramework.getInstance().getGui().getCommandManager().addCommand(centriranje);
        komedija.getTvojaKeva().getSelectionModel().clear();
    }
}
