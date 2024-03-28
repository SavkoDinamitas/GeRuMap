package raf.dsw.gerumap.app.gui.swing.stateDepartment;

import raf.dsw.gerumap.app.gui.swing.view.MindMapPanel;

import java.awt.*;

public class UvelicavanjeState implements State{
    @Override
    public void misKliknut(int X, int Y, MindMapPanel mapView) {
    }

    @Override
    public void misPovucen(int X, int Y, MindMapPanel mapView) {

    }

    @Override
    public void misPusten(int X, int Y, MindMapPanel mapView) {

    }

    @Override
    public void misSkrolovan(int X, int Y, MindMapPanel mapView, int rotacija) {
        double scalePocetak = mapView.getScale();
        if(rotacija > 0 && mapView.getScale() > 0.6){
            mapView.setScale(mapView.getScale() / 1.1);
        }
        else if( rotacija < 0 && mapView.getScale() < 2){
            mapView.setScale(mapView.getScale() * 1.1);
        }

        mapView.setPreferredSize(new Dimension((int) (mapView.getPocetnaDimenzija().getWidth() * mapView.getScale()), (int)(mapView.getPocetnaDimenzija().getHeight() * mapView.getScale())));
        mapView.revalidate();
        /*for(var x : mapView.getMap().getChildren()){
            Pojam xd = (Pojam) x;
            xd.setLokacija(new Point((int)(xd.getPocetnaLokacija().x * mapView.getScale()), (int)(xd.getPocetnaLokacija().y * mapView.getScale())));
        }*/
        //nazalost java nema built-in scaling za viewport :(

        //zumiranje na mesto misa ali malo je lose


        /*int x1 = X;
        int y1 = Y;*/
        //prilikom zuma: x = x*sc
        int x1 = (int)Math.round(X / scalePocetak);
        int x2 = (int)Math.round(x1 * mapView.getScale());
        int dx = (int)(X - x2);
        int y1 = (int)Math.round(Y / scalePocetak);
        int y2 = (int)Math.round(y1 * mapView.getScale());
        int dy = (int)(Y - y2);
        //System.out.println("U zumu: " + x1 + " " + y1);
        //System.out.println(x2 + " " + y2);
        //int dy = (int) (Y * scalePocetak - Y * mapView.getScale());
        //x, y   x - x/sc y - y/s
        if(scalePocetak != mapView.getScale())
            mapView.getViewport().setViewPosition(new Point(mapView.getViewport().getViewPosition().x - dx, mapView.getViewport().getViewPosition().y - dy));

        mapView.repaint();
    }
}
