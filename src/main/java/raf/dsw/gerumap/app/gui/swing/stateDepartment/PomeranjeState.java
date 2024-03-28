package raf.dsw.gerumap.app.gui.swing.stateDepartment;

import raf.dsw.gerumap.app.core.ApplicationFramework;
import raf.dsw.gerumap.app.gui.swing.commands.AbstractCommand;
import raf.dsw.gerumap.app.gui.swing.commands.implementation.MoveElementCommand;
import raf.dsw.gerumap.app.gui.swing.view.MindMapPanel;
import raf.dsw.gerumap.app.gui.swing.view.PojamPainter;

import javax.swing.*;
import java.awt.*;

public class PomeranjeState implements State{

    private Point prethodna;
    private Point pocetna;

    private Point delta;
    @Override
    public void misKliknut(int X, int Y, MindMapPanel mapView) {
        X = (int) (X / mapView.getScale());
        Y = (int) (Y / mapView.getScale());
        delta = new Point(0, 0);
        prethodna = new Point(X, Y);
        pocetna = new Point(X, Y);
    }

    @Override
    public void misPovucen(int X, int Y, MindMapPanel mapView) {
        X = (int) (X / mapView.getScale());
        Y = (int) (Y / mapView.getScale());
        Point promenaPravca = new Point(X - prethodna.x, Y - prethodna.y);
        delta = new Point(delta.x + promenaPravca.x, delta.y + promenaPravca.y);

        if(!mapView.getSelectionModel().isEmpty()){
            for(var x : mapView.getSelectionModel()){
                if(x instanceof PojamPainter){
                    //((PojamPainter) x).getPojam().setLokacija(new Point(X, Y));
                    ((PojamPainter) x).getPojam().promeniLokacije(promenaPravca.x, promenaPravca.y);
                    prethodna = new Point(X, Y);
                }
            }
        }
        else{
           //los nacin sa skrolbarovima (mnogo secka)
            /*int pomerajX = promenaPravca.x * mapView.getJScrollPane().getHorizontalScrollBar().getMaximum() / mapView.getWidth();
            int pomerajY = promenaPravca.y * mapView.getJScrollPane().getVerticalScrollBar().getMaximum() / mapView.getHeight();
            mapView.getJScrollPane().getHorizontalScrollBar().setValue(mapView.getJScrollPane().getHorizontalScrollBar().getValue() - pomerajX);
            mapView.getJScrollPane().getVerticalScrollBar().setValue(mapView.getJScrollPane().getVerticalScrollBar().getValue() - pomerajY);
            pocetna = new Point(X, Y);*/

            Point dragTacka = new Point(X, Y);
            JViewport viewport = mapView.getViewport();
            Point viewPos = viewport.getViewPosition();
            int maxViewPosX = mapView.getWidth() - viewport.getWidth();
            int maxViewPosY = mapView.getHeight() - viewport.getHeight();

            if(mapView.getWidth() > viewport.getWidth()) {
                viewPos.x -= dragTacka.x - prethodna.x;

                if(viewPos.x < 0) {
                    viewPos.x = 0;
                    prethodna.x = dragTacka.x;
                }

                if(viewPos.x > maxViewPosX) {
                    viewPos.x = maxViewPosX;
                    prethodna.x = dragTacka.x;
                }
            }

            if(mapView.getHeight() > viewport.getHeight()) {
                viewPos.y -= dragTacka.y - prethodna.y;

                if(viewPos.y < 0) {
                    viewPos.y = 0;
                    prethodna.y = dragTacka.y;
                }

                if(viewPos.y > maxViewPosY) {
                    viewPos.y = maxViewPosY;
                    prethodna.y = dragTacka.y;
                }
            }
            viewport.setViewPosition(viewPos);
        }
    }

    @Override
    public void misPusten(int X, int Y, MindMapPanel mapView) {
        for(var x : mapView.getSelectionModel()){
            if(x instanceof PojamPainter){
                for(var y : mapView.getPainterList()){
                    if(y instanceof  PojamPainter && !y.equals(x) && x.getShape().intersects(y.getShape().getBounds2D())){
                        for(var s : mapView.getSelectionModel()){
                            if(s instanceof PojamPainter){
                                ((PojamPainter) s).getPojam().setLokacija(new Point(((PojamPainter) s).getPojam().getLokacija().x
                                - delta.x, ((PojamPainter) s).getPojam().getLokacija().y - delta.y));
                            }
                        }
                        return;
                    }
                }
            }
        }
        if(!mapView.getSelectionModel().isEmpty()) {
            AbstractCommand pomeranje = new MoveElementCommand(delta, mapView);
            ApplicationFramework.getInstance().getGui().getCommandManager().addCommand(pomeranje);
        }
    }

    @Override
    public void misSkrolovan(int X, int Y, MindMapPanel mapView, int rotacija) {
    }
}
