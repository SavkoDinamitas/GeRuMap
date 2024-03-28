package raf.dsw.gerumap.app.gui.swing.stateDepartment;

import raf.dsw.gerumap.app.core.ApplicationFramework;
import raf.dsw.gerumap.app.gui.swing.commands.AbstractCommand;
import raf.dsw.gerumap.app.gui.swing.commands.implementation.DeleteElementCommand;
import raf.dsw.gerumap.app.gui.swing.tree.model.MapTreeItem;
import raf.dsw.gerumap.app.gui.swing.view.MainFrame;
import raf.dsw.gerumap.app.gui.swing.view.MindMapPanel;
import raf.dsw.gerumap.app.gui.swing.view.PojamPainter;
import raf.dsw.gerumap.app.gui.swing.view.VezaPainter;
import raf.dsw.gerumap.app.mapRepository.composite.MapNode;

import javax.swing.tree.TreeModel;
import java.awt.*;

public class BrisanjeState implements State{
    @Override
    public void misKliknut(int X, int Y, MindMapPanel mapView) {
        X = (int) (X / mapView.getScale());
        Y = (int) (Y / mapView.getScale());
//        if(!mapView.getSelectionModel().isEmpty()){
//            for(var x : mapView.getSelectionModel()){
//                if(x instanceof PojamPainter){
//                    brisanjeDeteta(MainFrame.getInstance().getMapTree().vratiModel(), (MapTreeItem) MainFrame.getInstance().getMapTree().vratiModel().getRoot(), (PojamPainter) x);
//                }
//                else{
//                    VezaPainter vp = (VezaPainter) x;
//                    vp.getFrom().obrisiVezu(vp.getTo());
//                    vp.getTo().obrisiVezu(vp.getFrom());
//                }
//            }
//            mapView.getSelectionModel().clear();
//        }
//        else{
//            for(var x : mapView.getPainterList()){
//                if(x.elementAt(new Point(X, Y))){
//                    if(x instanceof PojamPainter){
//                        brisanjeDeteta(MainFrame.getInstance().getMapTree().vratiModel(), (MapTreeItem) MainFrame.getInstance().getMapTree().vratiModel().getRoot(), (PojamPainter) x);
//                    }
//                    else{
//                        mapView.getPainterList().remove(x);
//                        VezaPainter vp = (VezaPainter) x;
//                        vp.getFrom().obrisiVezu(vp.getTo());
//                        vp.getTo().obrisiVezu(vp.getFrom());
//                    }
//                    return;
//                }
//            }
//        }
        boolean flagic = false;
        if(mapView.getSelectionModel().isEmpty())
        {
            for(var x : mapView.getPainterList())
            {
                if(x.elementAt(new Point(X, Y)))
                {
                    flagic = true;
                }
            }
        }
        else
        {
            flagic = true;
        }
        if(flagic)
        {
            AbstractCommand brisanjeState = new DeleteElementCommand(new Point(X, Y), mapView);
            ApplicationFramework.getInstance().getGui().getCommandManager().addCommand(brisanjeState);
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

    private void brisanjeDeteta(TreeModel treeModel, MapTreeItem otac, PojamPainter brisanje)
    {
        for(int i = 0; i < treeModel.getChildCount(otac); i++)
        {
            MapTreeItem dete = (MapTreeItem)treeModel.getChild(otac, i);
            MapNode xdd = dete.getMapNode();
            if(xdd.equals(brisanje.getPojam()))
            {
                //nasli smo ga u stablu
                MainFrame.getInstance().getMapTree().deleteChild(dete);

                return;
            }
            if(!treeModel.isLeaf(dete))
            {
                brisanjeDeteta(treeModel, dete, brisanje);
            }
        }
    }
}
