package raf.dsw.gerumap.app.gui.swing.stateDepartment;

import raf.dsw.gerumap.app.core.ApplicationFramework;
import raf.dsw.gerumap.app.gui.swing.commands.AbstractCommand;
import raf.dsw.gerumap.app.gui.swing.commands.implementation.AddVezaCommand;
import raf.dsw.gerumap.app.gui.swing.view.MindMapPanel;
import raf.dsw.gerumap.app.gui.swing.view.PojamPainter;
import raf.dsw.gerumap.app.gui.swing.view.VezaPainter;
import raf.dsw.gerumap.app.mapRepository.implementation.Pojam;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DodavanjeVezeState implements State{
    private Pojam pojamod;
    private Pojam pojamdo;
    @Override
    public void misKliknut(int X, int Y, MindMapPanel mapView) {
        X = (int) (X / mapView.getScale());
        Y = (int) (Y / mapView.getScale());
        for(var painter : mapView.getPainterList()){
            if(painter instanceof PojamPainter){
                if(painter.elementAt(new Point(X, Y))){
                    pojamod = ((PojamPainter) painter).getPojam();
                }
            }
        }
    }

    @Override
    public void misPovucen(int X, int Y, MindMapPanel mapView) {
        X = (int) (X / mapView.getScale());
        Y = (int) (Y / mapView.getScale());
        mapView.repaint();
        if(pojamod != null)
        {
            mapView.setP1(new Point(pojamod.getLokacija().x, pojamod.getLokacija().y));
            mapView.setP2(new Point(X, Y));
        }
        /*
        Graphics2D g = (Graphics2D) mapView.getGraphics();
        g.setStroke(new BasicStroke(2.7f));
        if(pojamod != null)
        {
            g.drawLine(pojamod.getLokacija().x, pojamod.getLokacija().y, X, Y);
        }
        */
        //mapView.repaint();
    }

    @Override
    public void misPusten(int X, int Y, MindMapPanel mapView) {
        X = (int) (X / mapView.getScale());
        Y = (int) (Y / mapView.getScale());
        if(pojamod != null && !pojamod.equals(pojamdo)){
            for(var painter : mapView.getPainterList()){
                if(painter instanceof PojamPainter){
                    if(painter.elementAt(new Point(X, Y))){
                        pojamdo = ((PojamPainter) painter).getPojam();
                    }
                }
            }
            //provera da veza ne postoji vec
            if(pojamdo != null && !pojamdo.getVezeList().contains(pojamod)){

                AbstractCommand dodavanjeVeze = new AddVezaCommand(pojamod, pojamdo, mapView);
                ApplicationFramework.getInstance().getGui().getCommandManager().addCommand(dodavanjeVeze);
            }
            // Provera da li se nalazi ciklus u vezama izmedju pojmova

        }
        pojamod = null;
        pojamdo = null;
        mapView.setP1(null);
        mapView.setP2(null);
        mapView.repaint();
    }

    @Override
    public void misSkrolovan(int X, int Y, MindMapPanel mapView, int rotacija) {

    }
}
