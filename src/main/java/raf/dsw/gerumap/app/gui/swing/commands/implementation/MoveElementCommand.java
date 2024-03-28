package raf.dsw.gerumap.app.gui.swing.commands.implementation;

import raf.dsw.gerumap.app.gui.swing.commands.AbstractCommand;
import raf.dsw.gerumap.app.gui.swing.view.MindMapPanel;
import raf.dsw.gerumap.app.gui.swing.view.Painter;
import raf.dsw.gerumap.app.gui.swing.view.PojamPainter;
import raf.dsw.gerumap.app.mapRepository.implementation.Pojam;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MoveElementCommand extends AbstractCommand {

    private Point delta;

    private boolean flagic = false;

    private List<Painter> selektovani = new ArrayList<>();

    private MindMapPanel panel;

    public MoveElementCommand(Point delta, MindMapPanel mapPanel){
        this.delta = delta;
        for(var x : mapPanel.getSelectionModel()){
            selektovani.add(x);
        }
        panel = mapPanel;
    }
    @Override
    public void doCommand() {
        if(!flagic)
            flagic = true;
        else{
            for(var s : selektovani){
                if(s instanceof PojamPainter){
                    ((PojamPainter) s).getPojam().setLokacija(new Point(((PojamPainter) s).getPojam().getLokacija().x
                            + delta.x, ((PojamPainter) s).getPojam().getLokacija().y + delta.y));
                }
            }
        }
        // Proveravam da li je neki pojam idalje na centru ekrana
        boolean xd = false;
        for(var x : panel.getMap().getChildren())
        {
            Pojam pojam = (Pojam) x;
            if(pojam.getLokacija().x == panel.getPocetnaDimenzija().width / 2 && pojam.getLokacija().y == panel.getPocetnaDimenzija().height / 2)
            {
                xd = true;
                break;
            }
        }
        panel.setCentralizovan(xd);
        System.out.println(panel.samoNeRadiGetterIzgleda());
    }

    @Override
    public void undoCommand() {
        for(var s : selektovani){
            if(s instanceof PojamPainter){
                ((PojamPainter) s).getPojam().setLokacija(new Point(((PojamPainter) s).getPojam().getLokacija().x
                        - delta.x, ((PojamPainter) s).getPojam().getLokacija().y - delta.y));
            }
        }
        // Proveravam da li je neki pojam idalje na centru ekrana
        boolean xd = false;
        for(var x : panel.getMap().getChildren())
        {
            Pojam pojam = (Pojam) x;
            if(pojam.getLokacija().x == panel.getPocetnaDimenzija().width / 2 && pojam.getLokacija().y == panel.getPocetnaDimenzija().height / 2)
            {
                xd = true;
                break;
            }
        }
        panel.setCentralizovan(xd);
        System.out.println(panel.samoNeRadiGetterIzgleda());
    }
}
