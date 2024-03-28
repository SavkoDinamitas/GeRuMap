package raf.dsw.gerumap.app.gui.swing.stateDepartment;

import lombok.Getter;
import lombok.Setter;
import raf.dsw.gerumap.app.gui.swing.view.MindMapPanel;

import java.awt.*;
import java.awt.geom.Rectangle2D;

@Getter
@Setter
public class SelekcijaState implements State{

    private Point pocetna;
    @Override
    public void misKliknut(int X, int Y, MindMapPanel mapView) {
        X = (int) (X / mapView.getScale());
        Y = (int) (Y / mapView.getScale());
        pocetna = new Point(X, Y);
        for(var x : mapView.getPainterList()){
            if(x.elementAt(new Point(X, Y))){
                mapView.getSelectionModel().clear();
                mapView.getSelectionModel().add(x);
                mapView.repaint();
                return;
            }
        }
        mapView.getSelectionModel().clear();
        mapView.repaint();
    }

    @Override
    public void misPovucen(int X, int Y, MindMapPanel mapView)
    {
        X = (int) (X / mapView.getScale());
        Y = (int) (Y / mapView.getScale());
        mapView.getSelectionModel().clear();
        Rectangle2D pravougaonikSelekcije = null;
        if(pocetna != null)
        {
            int manjiX = Math.min(pocetna.x, X);
            int manjiY = Math.min(pocetna.y, Y);
            pravougaonikSelekcije = new Rectangle2D.Double(manjiX, manjiY, Math.abs(X - pocetna.x), Math.abs(Y - pocetna.y));
            /*
            Graphics2D g2 = (Graphics2D) mapView.getGraphics();
            g2.setStroke(new BasicStroke(1));
            g2.setColor(Color.blue);
            g2.draw(pravougaonikSelekcije);
            */
            for(var x : mapView.getPainterList()){
                if(x.getShape().intersects(pravougaonikSelekcije) && !mapView.getSelectionModel().contains(x)){
                    //System.out.println("Mast");
                    mapView.getSelectionModel().add(x);
                }
            }
        }
        mapView.setPravougaonikSelekcije(pravougaonikSelekcije);
        mapView.repaint();
    }

    @Override
    public void misPusten(int X, int Y, MindMapPanel mapView) {
        mapView.setPravougaonikSelekcije(null);
        mapView.repaint();
    }

    @Override
    public void misSkrolovan(int X, int Y, MindMapPanel mapView, int rotacija) {

    }
}
