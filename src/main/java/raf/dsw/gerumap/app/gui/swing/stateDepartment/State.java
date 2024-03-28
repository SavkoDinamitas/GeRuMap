package raf.dsw.gerumap.app.gui.swing.stateDepartment;

import raf.dsw.gerumap.app.gui.swing.view.MindMapPanel;

public interface State {
    void misKliknut(int X, int Y, MindMapPanel mapView);

    void misPovucen(int X, int Y, MindMapPanel mapView);

    void misPusten(int X, int Y, MindMapPanel mapView);

    void misSkrolovan(int X, int Y, MindMapPanel mapView, int rotacija);
}
