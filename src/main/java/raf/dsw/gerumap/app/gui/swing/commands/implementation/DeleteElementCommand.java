package raf.dsw.gerumap.app.gui.swing.commands.implementation;

import javafx.util.Pair;
import raf.dsw.gerumap.app.core.ApplicationFramework;
import raf.dsw.gerumap.app.gui.swing.commands.AbstractCommand;
import raf.dsw.gerumap.app.gui.swing.tree.model.MapTreeItem;
import raf.dsw.gerumap.app.gui.swing.view.MainFrame;
import raf.dsw.gerumap.app.gui.swing.view.MindMapPanel;
import raf.dsw.gerumap.app.gui.swing.view.Painter;
import raf.dsw.gerumap.app.gui.swing.view.PojamPainter;
import raf.dsw.gerumap.app.gui.swing.view.VezaPainter;
import raf.dsw.gerumap.app.mapRepository.composite.MapNode;
import raf.dsw.gerumap.app.mapRepository.composite.MapNodeComposite;
import raf.dsw.gerumap.app.mapRepository.implementation.MindMap;
import raf.dsw.gerumap.app.mapRepository.implementation.Pojam;

import javax.swing.*;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class DeleteElementCommand extends AbstractCommand {

    private Point mis;

    private MindMapPanel mapView;

    private List<Pojam> obrisaniPojmovi = new ArrayList<>();

    private List<Pair<Pojam, Pojam>> obrisaneVeze = new ArrayList<>();

    private List<Painter> selektovani = new ArrayList<>();

    public DeleteElementCommand(Point mis, MindMapPanel mapPanel){
        this.mapView = mapPanel;
        this.mis = mis;
        for(var x : mapPanel.getSelectionModel()){
            selektovani.add(x);
        }
    }

    @Override
    public void doCommand() {
        if(!selektovani.isEmpty()){
            for(var x : selektovani){
                if(x instanceof PojamPainter){
                    obrisaniPojmovi.add(((PojamPainter) x).getPojam());
                    for(var veza : ((PojamPainter) x).getPojam().getVezeList()){
                        obrisaneVeze.add(new Pair<>(((PojamPainter) x).getPojam(), veza));
                        veza.obrisiVezu(((PojamPainter) x).getPojam());
                    }
                    ((PojamPainter) x).getPojam().getVezeList().clear();
                    for(var y : ((MindMap)(((PojamPainter) x).getPojam().getParent())).getChildren()){
                        if(((Pojam) y).getVezeList().contains(((PojamPainter) x).getPojam())){
                            ((Pojam) y).obrisiVezu(((PojamPainter) x).getPojam());
                            obrisaneVeze.add(new Pair<Pojam, Pojam>((Pojam) y, ((PojamPainter) x).getPojam()));
                        }
                    }
                    brisanjeDeteta(MainFrame.getInstance().getMapTree().vratiModel(), (MapTreeItem) MainFrame.getInstance().getMapTree().vratiModel().getRoot(), (PojamPainter) x);
                    mapView.getMap().deleteChild(((PojamPainter) x).getPojam());
                }
                else{
                    VezaPainter vp = (VezaPainter) x;
                    obrisaneVeze.add(new Pair<>(vp.getFrom(), vp.getTo()));
                    vp.getFrom().obrisiVezu(vp.getTo());
                    //vp.getTo().obrisiVezu(vp.getFrom());
                }
            }
            mapView.getSelectionModel().clear();
        }
        else{
            for(var x : mapView.getPainterList()){
                if(x.elementAt(mis)){
                    if(x instanceof PojamPainter){
                        obrisaniPojmovi.add(((PojamPainter) x).getPojam());
                        for(var veza : ((PojamPainter) x).getPojam().getVezeList()){
                            obrisaneVeze.add(new Pair<>(((PojamPainter) x).getPojam(), veza));
                            veza.obrisiVezu(((PojamPainter) x).getPojam());
                        }
                        ((PojamPainter) x).getPojam().getVezeList().clear();
                        for(var y : ((MindMap)(((PojamPainter) x).getPojam().getParent())).getChildren()){
                            if(((Pojam) y).getVezeList().contains(((PojamPainter) x).getPojam())){
                                ((Pojam) y).obrisiVezu(((PojamPainter) x).getPojam());
                                obrisaneVeze.add(new Pair<Pojam, Pojam>((Pojam) y, ((PojamPainter) x).getPojam()));
                            }
                        }
                        brisanjeDeteta(MainFrame.getInstance().getMapTree().vratiModel(), (MapTreeItem) MainFrame.getInstance().getMapTree().vratiModel().getRoot(), (PojamPainter) x);
                        mapView.getMap().deleteChild(((PojamPainter) x).getPojam());
                    }
                    else{
                        VezaPainter vp = (VezaPainter) x;
                        obrisaneVeze.add(new Pair<>(vp.getFrom(), vp.getTo()));
                        vp.getFrom().obrisiVezu(vp.getTo());
                        //vp.getTo().obrisiVezu(vp.getFrom());
                    }
                    return;
                }
            }
        }
    }

    @Override
    public void undoCommand() {
        for(var x : obrisaniPojmovi){
            mapView.getMap().addChild(x);
            hocuSmrt(MainFrame.getInstance().getMapTree().vratiModel(), (MapTreeItem) (MainFrame.getInstance().getMapTree().vratiModel().getRoot()), mapView, x);
        }

        for(var x : obrisaneVeze){
            x.getKey().dodajVezu(x.getValue());
            //x.getValue().dodajVezu(x.getKey());
        }

        obrisaneVeze.clear();
        obrisaniPojmovi.clear();

        MainFrame.getInstance().getMapTree().expandTree();
    }

    private void brisanjeDeteta(TreeModel treeModel, MapTreeItem otac, PojamPainter brisanje)
    {
        for(int i = 0; i < treeModel.getChildCount(otac); i++)
        {
            MapTreeItem dete = (MapTreeItem)treeModel.getChild(otac, i);
            MapNode xdd = dete.getMapNode();
            if(xdd.equals(brisanje.getPojam()))
            {
                //nasli smo ga u stablu
                //MainFrame.getInstance().getMapTree().deleteChild(dete);
                //((MapNodeComposite) parent).deleteChild(child.getMapNode());
                MainFrame.getInstance().getMapTree().vratiModel().removeNodeFromParent(dete);
                SwingUtilities.updateComponentTreeUI(MainFrame.getInstance().getMapTree().vratiTreeView());

                return;
            }
            if(!treeModel.isLeaf(dete))
            {
                brisanjeDeteta(treeModel, dete, brisanje);
            }
        }
    }

    private void hocuSmrt(TreeModel treeModel, MapTreeItem otac, MindMapPanel mapView, Pojam p)
    {
        for(int i = 0; i < treeModel.getChildCount(otac); i++)
        {
            MapTreeItem dete = (MapTreeItem)treeModel.getChild(otac, i);
            MapNode xdd = dete.getMapNode();
            if(xdd.equals(mapView.getMap()))
            {
                // System.out.println("rinbo");
                // Moras da otvoris onom kvacicom u tree-u izgledace ti kao da se nista nije desilo u suprotnom :)
                //MainFrame.getInstance().getMapTree().addChild(dete);
                MapTreeItem xddd = new MapTreeItem(p);
                dete.add(xddd);
                //giga losota ali mora zbog mapTreeItem autizma
                for(var x : ApplicationFramework.getInstance().getGui().getCommandManager().getKomande()){
                    if(x instanceof AddChildCommand){
                        if(((AddChildCommand) x).getChild().equals(p)){
                            ((AddChildCommand) x).setDeteItem(xddd);
                        }
                    }
                }


                /*TreePath tp = new TreePath(dete.getPath());
                for (int k = 0; k < MainFrame.getInstance().getMapTree().vratiTreeView().getRowCount(); k++) {
                    MainFrame.getInstance().getMapTree().vratiTreeView().expandRow(k);
                }*/
//                painter.setPojam(novonapravljenPojam);
//                mapView.getPainterList().add(painter);

                //painter.paint(g);


                return;
            }
            if(!treeModel.isLeaf(dete))
            {
                hocuSmrt(treeModel, dete, mapView, p);
            }
        }
    }
}
