package raf.dsw.gerumap.app.gui.swing.commands.implementation;

import javafx.util.Pair;
import raf.dsw.gerumap.app.gui.swing.commands.AbstractCommand;
import raf.dsw.gerumap.app.gui.swing.view.MindMapPanel;
import raf.dsw.gerumap.app.gui.swing.view.Painter;
import raf.dsw.gerumap.app.gui.swing.view.PojamPainter;
import raf.dsw.gerumap.app.gui.swing.view.VezaPainter;
import raf.dsw.gerumap.app.mapRepository.implementation.Pojam;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ChangeStyleCommand extends AbstractCommand {

    private List<Painter> selektovani = new ArrayList<>();

    private List<Pair<Color, Float>> pocetak = new ArrayList<>();

    private Color boja;

    private float stroke;

    private MindMapPanel mapPanel;

    public ChangeStyleCommand(MindMapPanel mapPanel, Color c, float s){
        this.mapPanel = mapPanel;
        this.boja = c;
        this.stroke = s;
        for (var x : mapPanel.getSelectionModel()){
            selektovani.add(x);
            if(x instanceof PojamPainter){
                pocetak.add(new Pair<>(((PojamPainter) x).getPojam().getColor(), ((PojamPainter) x).getPojam().getStrokic()));
            }
            else{
                VezaPainter vp = (VezaPainter) x;
                pocetak.add(new Pair<>(vp.getColor(), vp.getStroke()));
            }
        }
    }

    public ChangeStyleCommand(MindMapPanel mapPanel, Color c, float s, Painter kliknut){
        this.mapPanel = mapPanel;
        this.boja = c;
        this.stroke = s;
        selektovani.add(kliknut);
        if(kliknut instanceof PojamPainter){
            pocetak.add(new Pair<>(((PojamPainter) kliknut).getPojam().getColor(), ((PojamPainter) kliknut).getPojam().getStrokic()));
        }
        if(kliknut instanceof VezaPainter){
            pocetak.add(new Pair<>(((VezaPainter) kliknut).getColor(), ((VezaPainter) kliknut).getStroke()));
        }
    }

    @Override
    public void doCommand() {
        for(var x : selektovani){
            if(x instanceof PojamPainter){
                ((PojamPainter) x).getPojam().setStrokic(stroke);
                ((PojamPainter) x).getPojam().setColor(boja);
            }
            else{
                VezaPainter trenutni = (VezaPainter) x;
               for(var y : mapPanel.getPainterList()){
                   if(y instanceof VezaPainter && ((((VezaPainter) y).getFrom().equals(trenutni.getFrom()) && ((VezaPainter) y).getTo().equals(trenutni.getTo())) ||
                            (((VezaPainter) y).getTo().equals(trenutni.getFrom()) && ((VezaPainter) y).getFrom().equals(trenutni.getTo())))){
                        ((VezaPainter) y).setColor(boja);
                        ((VezaPainter) y).setStroke(stroke);
                        mapPanel.repaint();
                        break;
                    }
               }
            }
        }
    }

    @Override
    public void undoCommand() {
        for(int i = 0; i < selektovani.size(); i++){
            Painter x = selektovani.get(i);
            if(x instanceof PojamPainter){
                ((PojamPainter) x).getPojam().setStrokic(pocetak.get(i).getValue());
                ((PojamPainter) x).getPojam().setColor(pocetak.get(i).getKey());
            }
            else{
                VezaPainter trenutni = (VezaPainter) x;
                for(var y : mapPanel.getPainterList()){
                    if(y instanceof VezaPainter && ((((VezaPainter) y).getFrom().equals(trenutni.getFrom()) && ((VezaPainter) y).getTo().equals(trenutni.getTo())) ||
                            (((VezaPainter) y).getTo().equals(trenutni.getFrom()) && ((VezaPainter) y).getFrom().equals(trenutni.getTo())))){
                        ((VezaPainter) y).setColor(pocetak.get(i).getKey());
                        ((VezaPainter) y).setStroke(pocetak.get(i).getValue());
                        mapPanel.repaint();
                        break;
                    }
                }
            }
        }
    }
}
