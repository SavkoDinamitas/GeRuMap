package raf.dsw.gerumap.app.gui.swing.stateDepartment;

import raf.dsw.gerumap.app.core.ApplicationFramework;
import raf.dsw.gerumap.app.errorHandler.Message;
import raf.dsw.gerumap.app.errorHandler.MessageType;
import raf.dsw.gerumap.app.gui.swing.commands.AbstractCommand;
import raf.dsw.gerumap.app.gui.swing.commands.implementation.RenameChildCommand;
import raf.dsw.gerumap.app.gui.swing.commands.implementation.RenameElementCommand;
import raf.dsw.gerumap.app.gui.swing.view.MainFrame;
import raf.dsw.gerumap.app.gui.swing.view.MindMapPanel;
import raf.dsw.gerumap.app.gui.swing.view.PojamPainter;
import raf.dsw.gerumap.app.gui.swing.view.dialogs.RenamePojamDijalog;
import raf.dsw.gerumap.app.mapRepository.implementation.Pojam;

import javax.swing.*;
import java.awt.*;

public class PromenaImenaPojmaState implements State{
    @Override
    public void misKliknut(int X, int Y, MindMapPanel mapView) {
        //provera za duzinu reci
        int letterWidth = mapView.getGraphics().getFontMetrics().stringWidth("a");
        int brojSlova = (Pojam.getWidth() - 10) / letterWidth;
        X = (int) (X / mapView.getScale());
        Y = (int) (Y / mapView.getScale());
        if(mapView.getSelectionModel().size() == 1 && mapView.getSelectionModel().get(0) instanceof PojamPainter){
            RenamePojamDijalog rpd = new RenamePojamDijalog(mapView, ((PojamPainter) mapView.getSelectionModel().get(0)).getPojam().getName());
            rpd.setBrojSlova(brojSlova);
            rpd.setVisible(true);
            if(rpd.getPojamName() == null)
                return;
            //((PojamPainter) mapView.getSelectionModel().get(0)).getPojam().setName(rpd.getPojamName().toUpperCase());
            AbstractCommand rename = new RenameElementCommand(((PojamPainter) mapView.getSelectionModel().get(0)).getPojam(), rpd.getPojamName().toUpperCase());
            ApplicationFramework.getInstance().getGui().getCommandManager().addCommand(rename);
            SwingUtilities.updateComponentTreeUI(MainFrame.getInstance().getMapTree().vratiTreeView());
            mapView.getSelectionModel().clear();
        }
        else if(mapView.getSelectionModel().isEmpty()){
            for(var x : mapView.getPainterList()){
                if(x.elementAt(new Point(X, Y)) && x instanceof PojamPainter){
                    RenamePojamDijalog rpd = new RenamePojamDijalog(mapView, ((PojamPainter) x).getPojam().getName());
                    rpd.setBrojSlova(brojSlova);
                    rpd.setVisible(true);
                    if(rpd.getPojamName() == null)
                        return;
                    //((PojamPainter) x).getPojam().setName(rpd.getPojamName().toUpperCase());
                    AbstractCommand rename = new RenameElementCommand(((PojamPainter) x).getPojam(), rpd.getPojamName().toUpperCase());
                    ApplicationFramework.getInstance().getGui().getCommandManager().addCommand(rename);
                    SwingUtilities.updateComponentTreeUI(MainFrame.getInstance().getMapTree().vratiTreeView());
                }
            }
        }
        else{
            ApplicationFramework.getInstance().getMessageGenerator().generateMessage(MessageType.WARNING, "Nije moguce promeniti ime vise od jednog pojma");
        }
    }

    @Override
    public void misPovucen(int X, int Y, MindMapPanel mapView) {

    }

    @Override
    public void misPusten(int X, int Y, MindMapPanel mapView) {

    }

    @Override
    public void misSkrolovan(int X, int Y, MindMapPanel mapView, int rotacija) {

    }
}
