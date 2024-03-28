package raf.dsw.gerumap.app.gui.swing.stateDepartment;

import raf.dsw.gerumap.app.core.ApplicationFramework;
import raf.dsw.gerumap.app.errorHandler.MessageType;
import raf.dsw.gerumap.app.gui.swing.commands.AbstractCommand;
import raf.dsw.gerumap.app.gui.swing.commands.implementation.AddChildCommand;
import raf.dsw.gerumap.app.gui.swing.tree.model.MapTreeItem;
import raf.dsw.gerumap.app.gui.swing.view.MainFrame;
import raf.dsw.gerumap.app.gui.swing.view.MindMapPanel;
import raf.dsw.gerumap.app.gui.swing.view.Painter;
import raf.dsw.gerumap.app.gui.swing.view.PojamPainter;
import raf.dsw.gerumap.app.gui.swing.view.dialogs.NewPojamDialog;
import raf.dsw.gerumap.app.mapRepository.composite.MapNode;
import raf.dsw.gerumap.app.mapRepository.implementation.Pojam;

import javax.swing.tree.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DodavanjePojmaState implements State{
    @Override
    public void misKliknut(int X, int Y, MindMapPanel mapView) {
        X = (int) (X / mapView.getScale());
        Y = (int) (Y / mapView.getScale());
        //provera da li moze, ako moze dodavanje na model onda iscrtas painterom
        Painter painter = new PojamPainter();
        boolean flag = false;
        //provera da li se ne preklapa sa ostalim elementima
        List<Point> tacke = new ArrayList<>();
        tacke.add(new Point(X - Pojam.getWidth()/2, Y - Pojam.getHeight()/2));
        tacke.add(new Point(X - Pojam.getWidth()/2, Y + Pojam.getHeight()/2));
        tacke.add(new Point(X + Pojam.getWidth()/2, Y - Pojam.getHeight()/2));
        tacke.add(new Point(X + Pojam.getWidth()/2, Y + Pojam.getHeight()/2));
        tacke.add(new Point(X , Y - Pojam.getHeight()/2));
        tacke.add(new Point(X, Y + Pojam.getHeight()/2));
        tacke.add(new Point(X + Pojam.getWidth()/2, Y));
        tacke.add(new Point(X - Pojam.getWidth()/2, Y));
        tacke.add(new Point(X, Y));

        for(Point point : tacke){
            for(Painter painter1 : mapView.getPainterList()){
                if(painter1 instanceof PojamPainter){
                    if(painter1.elementAt(point))
                        flag = true;
                }
            }
        }
        //ako nema preklapanja onda crtamo
        if(!flag)
        {
            //provera koliko sme da bude dugacak string
            int letterWidth = mapView.getGraphics().getFontMetrics().stringWidth("a");
            int brojSlova = (Pojam.getWidth() - 10) / letterWidth;
            NewPojamDialog djg = new NewPojamDialog(mapView);
            djg.setBrojSlova(brojSlova);
            djg.setVisible(true);
            String imePojma = djg.getPojamName();
            if(imePojma != null){
                TreeModel treeModel = MainFrame.getInstance().getMapTree().vratiModel();
                if(treeModel != null)
                {
                    MapTreeItem rootXDD = (MapTreeItem)treeModel.getRoot();
                    hocuSmrt(treeModel, rootXDD, mapView, X, Y, (PojamPainter) painter, imePojma);
                }
            }
        }
        else{
            ApplicationFramework.getInstance().getMessageGenerator().generateMessage(MessageType.ERROR, "Pojmovi ne smeju da se preklapaju prilikom kreiranja!");
        }
    }
    private void hocuSmrt(TreeModel treeModel, MapTreeItem otac, MindMapPanel mapView, int X, int Y, PojamPainter painter, String imePojma)
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

                Pojam novonapravljenPojam = new Pojam("Pojam", mapView.getMap(), 2.7f, Color.BLACK);
                novonapravljenPojam.setLokacija(new Point(X, Y));
                novonapravljenPojam.setName(imePojma.toUpperCase());
                //novonapravljenPojam.addSubscriber(mapView);
                AbstractCommand dodajDete = new AddChildCommand(dete, novonapravljenPojam);
                ApplicationFramework.getInstance().getGui().getCommandManager().addCommand(dodajDete);

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
                hocuSmrt(treeModel, dete, mapView, X, Y, painter, imePojma);
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
