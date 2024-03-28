package raf.dsw.gerumap.app.gui.swing.stateDepartment;

import raf.dsw.gerumap.app.core.ApplicationFramework;
import raf.dsw.gerumap.app.gui.swing.commands.AbstractCommand;
import raf.dsw.gerumap.app.gui.swing.commands.implementation.ChangeStyleCommand;
import raf.dsw.gerumap.app.gui.swing.view.MindMapPanel;
import raf.dsw.gerumap.app.gui.swing.view.PojamPainter;
import raf.dsw.gerumap.app.gui.swing.view.VezaPainter;
import raf.dsw.gerumap.app.gui.swing.view.dialogs.EditStyleDialog;

import java.awt.*;

public class MenjanjeStilaState implements State{
    @Override
    public void misKliknut(int X, int Y, MindMapPanel mapView) {
        X = (int) (X / mapView.getScale());
        Y = (int) (Y / mapView.getScale());
        if(!mapView.getSelectionModel().isEmpty()){
            EditStyleDialog esd = new EditStyleDialog();
            esd.setVisible(true);
            if(esd.getStroke() == 0)
                return;
            Color boja = esd.getColor();
            float stroke = esd.getStroke();
            AbstractCommand menjanjeStila = new ChangeStyleCommand(mapView, boja, stroke);
            ApplicationFramework.getInstance().getGui().getCommandManager().addCommand(menjanjeStila);
            mapView.getSelectionModel().clear();
        }
        else{
            for (var x : mapView.getPainterList()){
                if(x.elementAt(new Point(X, Y))){
                    EditStyleDialog esd = new EditStyleDialog();
                    esd.setVisible(true);
                    if(esd.getStroke() == 0)
                        return;
                    Color boja = esd.getColor();
                    float stroke = esd.getStroke();

                    AbstractCommand menjanjeStila = new ChangeStyleCommand(mapView, boja, stroke, x);
                    ApplicationFramework.getInstance().getGui().getCommandManager().addCommand(menjanjeStila);
                    return;
                }
            }
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
