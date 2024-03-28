package raf.dsw.gerumap.app.gui.swing.commands.implementation;

import javafx.util.Pair;
import raf.dsw.gerumap.app.gui.swing.commands.AbstractCommand;
import raf.dsw.gerumap.app.gui.swing.view.*;
import raf.dsw.gerumap.app.mapRepository.implementation.MindMap;
import raf.dsw.gerumap.app.mapRepository.implementation.Pojam;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class CenterPojamCommand extends AbstractCommand {

    private Point centarEkrana;

    private Point delta = new Point();

    private Pojam selektovanPojam;

    private MindMapPanel mapPanel;

    private Point pocetniViewPort;

    private int maksDubina;

    private List<Point> pocetak = new ArrayList<>();

    private List<Pojam> deca = new ArrayList<>();

    private Color pboja;

    private float pStroke;

    public CenterPojamCommand(Point point, Pojam pojam, MindMapPanel mapPanel)
    {
        centarEkrana = point;
        selektovanPojam = pojam;
        pboja = selektovanPojam.getColor();
        pStroke = selektovanPojam.getStrokic();
        this.mapPanel = mapPanel;
        pocetniViewPort = ((ScrollPaneMindMap) MainFrame.getInstance().getCurrentProjectView().getTabbedPane().getSelectedComponent()).getViewport().getViewPosition();
        for(var x : mapPanel.getMap().getChildren()){
            Pojam xx = (Pojam) x;
            pocetak.add(xx.getLokacija());
            deca.add(xx);
        }
    }

    @Override
    public void doCommand()
    {
        ((ScrollPaneMindMap) MainFrame.getInstance().getCurrentProjectView().getTabbedPane().getSelectedComponent()).getViewport().setViewPosition(new Point(((ScrollPaneMindMap) MainFrame.getInstance().getCurrentProjectView().getTabbedPane().getSelectedComponent()).getTvojaKeva().getWidth() / 2 - ((ScrollPaneMindMap) MainFrame.getInstance().getCurrentProjectView().getTabbedPane().getSelectedComponent()).getViewport().getWidth() / 2,
                ((ScrollPaneMindMap) MainFrame.getInstance().getCurrentProjectView().getTabbedPane().getSelectedComponent()).getTvojaKeva().getHeight() / 2 - ((ScrollPaneMindMap) MainFrame.getInstance().getCurrentProjectView().getTabbedPane().getSelectedComponent()).getViewport().getHeight() / 2));
        //((PojamPainter) selektovanPojam).getPojam().setLokacija(centarEkrana);
        delta.setLocation(centarEkrana.x - selektovanPojam.getLokacija().x, centarEkrana.y - selektovanPojam.getLokacija().y);
        selektovanPojam.promeniLokacije(delta.x, delta.y);
        for(var x : mapPanel.getPainterList()){
            if(x instanceof PojamPainter && ((PojamPainter) x).getPojam().equals(selektovanPojam)){
                ((PojamPainter) x).getPojam().setStrokic(6);
                ((PojamPainter) x).getPojam().setColor(Color.GREEN);
                break;
            }
        }
        mapPanel.setCentralizovan(true);
        System.out.println(mapPanel.samoNeRadiGetterIzgleda());
        System.out.println("Velicina panela: " + mapPanel.getPocetnaDimenzija().width + " " + mapPanel.getPocetnaDimenzija().height);
        System.out.println("Koordinate centralnog pojma: " + selektovanPojam.getLokacija().x + " " + selektovanPojam.getLokacija().y);
        List<Pair<Pojam, Integer>> pojmovi = new ArrayList<>();

        BFS(selektovanPojam, new ArrayList<>(), mapPanel.getMap(), pojmovi);
        mapPanel.getCentralizovaniPojmovi().clear();
        for(var x : pojmovi)
        {
            mapPanel.getCentralizovaniPojmovi().add(x.getKey());
        }
        Point referenca = selektovanPojam.getLokacija();
        int visinaDiff = 30;
        int sirinaDiff = 30;
        List<Pojam> gornji = new ArrayList<>();
        List<Pojam> donji = new ArrayList<>();

        //krece mast
        //pocetna raspodela na gornje i donje
//        for(int i = 1; i <= 1; i++){
//            int brojNaDubini = 0;
//            List<Pojam> ubacujem = new ArrayList<>();
//            for(var x : pojmovi){
//                if(x.getValue() == i){
//                    brojNaDubini++;
//                    ubacujem.add(x.getKey());
//                }
//            }
//
//            int ukupnaSirina = (brojNaDubini / 2 - 1) * Pojam.getWidth() + (brojNaDubini / 2 - 1)*sirinaDiff;
//            Point krecem = new Point(referenca.x - ukupnaSirina / 2, referenca.y - visinaDiff);
//            for(int j = 0; j < ubacujem.size() / 2; j++){
//                Pojam tr = ubacujem.get(j);
//                gornji.add(tr);
//                tr.setLokacija(new Point(krecem));
//                krecem = new Point(krecem.x + Pojam.getWidth() + sirinaDiff, krecem.y);
//            }
//            if(brojNaDubini % 2 == 1){
//                ukupnaSirina = (brojNaDubini / 2) * Pojam.getWidth() + (brojNaDubini / 2)*sirinaDiff;
//            }
//            krecem = new Point(referenca.x - ukupnaSirina / 2, referenca.y + visinaDiff);
//            for(int j = ubacujem.size() / 2; j < ubacujem.size(); j++){
//                Pojam tr = ubacujem.get(j);
//                donji.add(tr);
//                tr.setLokacija(new Point(krecem));
//                krecem = new Point(krecem.x + Pojam.getWidth() + sirinaDiff, krecem.y);
//            }
//        }
//
//        for(int i = 2; i <= maksDubina; i++){
//            System.out.println("LOL");
//            int visinskaRazlika = i*visinaDiff;
//
//            List<Pojam> povezaniGore = new ArrayList<>();
//            List<Pojam> povezaniDole = new ArrayList<>();
//
//            for(var t : gornji){
//                for(var x : pojmovi){
//                    if(x.getValue() == i && (x.getKey().getVezeList().contains(t) || t.getVezeList().contains(x.getKey()))){
//                        povezaniGore.add(x.getKey());
//                    }
//                }
//            }
//
//            for(var t : donji){
//                for(var x : pojmovi){
//                    if(x.getValue() == i && (x.getKey().getVezeList().contains(t) || t.getVezeList().contains(x.getKey()))){
//                        povezaniDole.add(x.getKey());
//                    }
//                }
//            }
//            gornji.clear();
//            donji.clear();
//            int ukupnaSirina = (povezaniGore.size() - 1) * Pojam.getWidth() + (povezaniGore.size() - 1)*sirinaDiff;
//            Point krecem = new Point(referenca.x - ukupnaSirina / 2, referenca.y - visinskaRazlika);
//            for(int j = 0; j < povezaniGore.size(); j++){
//                Pojam tr = povezaniGore.get(j);
//                gornji.add(tr);
//                tr.setLokacija(new Point(krecem));
//                krecem = new Point(krecem.x + Pojam.getWidth() + sirinaDiff, krecem.y);
//            }
//
//            ukupnaSirina = (povezaniDole.size() - 1) * Pojam.getWidth() + (povezaniDole.size() - 1)*sirinaDiff;
//            krecem = new Point(referenca.x - ukupnaSirina / 2, referenca.y + visinskaRazlika);
//            for(int j = 0; j < povezaniDole.size(); j++){
//                Pojam tr = povezaniDole.get(j);
//                donji.add(tr);
//                tr.setLokacija(new Point(krecem));
//                krecem = new Point(krecem.x + Pojam.getWidth() + sirinaDiff, krecem.y);
//            }
//        }

        for(int i = 1; i <= 1; i++){
            int brojNaDubini = 0;
            List<Pojam> ubacujem = new ArrayList<>();
            for(var x : pojmovi){
                if(x.getValue() == i){
                    brojNaDubini++;
                    ubacujem.add(x.getKey());
                }
            }

            int ukupnaVisina = (brojNaDubini / 2 - 1) * Pojam.getHeight() + (brojNaDubini / 2 - 1)*visinaDiff;
            Point krecem = new Point(referenca.x - sirinaDiff - Pojam.getWidth(), referenca.y - ukupnaVisina / 2);
            for(int j = 0; j < ubacujem.size() / 2; j++){
                Pojam tr = ubacujem.get(j);
                gornji.add(tr);
                tr.setLokacija(new Point(krecem));
                krecem = new Point(krecem.x, krecem.y + Pojam.getHeight() + visinaDiff);
            }
            if(brojNaDubini % 2 == 1){
                ukupnaVisina = (brojNaDubini / 2) * Pojam.getHeight() + (brojNaDubini / 2)*visinaDiff;
            }
            krecem = new Point(referenca.x + sirinaDiff + Pojam.getWidth(), referenca.y - ukupnaVisina / 2);
            for(int j = ubacujem.size() / 2; j < ubacujem.size(); j++){
                Pojam tr = ubacujem.get(j);
                donji.add(tr);
                tr.setLokacija(new Point(krecem));
                krecem = new Point(krecem.x, krecem.y + Pojam.getHeight() + visinaDiff);
            }
        }

        for(int i = 2; i <= maksDubina; i++){
            int visinskaRazlika = i*(sirinaDiff + Pojam.getWidth());

            List<Pojam> povezaniGore = new ArrayList<>();
            List<Pojam> povezaniDole = new ArrayList<>();

            for(var t : gornji){
                for(var x : pojmovi){
                    if(x.getValue() == i && (x.getKey().getVezeList().contains(t) || t.getVezeList().contains(x.getKey()))){
                        povezaniGore.add(x.getKey());
                    }
                }
            }

            for(var t : donji){
                for(var x : pojmovi){
                    if(x.getValue() == i && (x.getKey().getVezeList().contains(t) || t.getVezeList().contains(x.getKey()))){
                        povezaniDole.add(x.getKey());
                    }
                }
            }
            gornji.clear();
            donji.clear();
            int ukupnaVisina = (povezaniGore.size() - 1) * Pojam.getHeight() + (povezaniGore.size() - 1)*visinaDiff;
            Point krecem = new Point(referenca.x - visinskaRazlika, referenca.y - ukupnaVisina / 2);
            for(int j = 0; j < povezaniGore.size(); j++){
                Pojam tr = povezaniGore.get(j);
                gornji.add(tr);
                tr.setLokacija(new Point(krecem));
                krecem = new Point(krecem.x, krecem.y + Pojam.getHeight() + visinaDiff);
            }

            ukupnaVisina = (povezaniDole.size() - 1) * Pojam.getHeight() + (povezaniDole.size() - 1)*visinaDiff;
            krecem = new Point(referenca.x + visinskaRazlika , referenca.y - ukupnaVisina / 2);
            for(int j = 0; j < povezaniDole.size(); j++){
                Pojam tr = povezaniDole.get(j);
                donji.add(tr);
                tr.setLokacija(new Point(krecem));
                krecem = new Point(krecem.x, krecem.y + Pojam.getHeight() + visinaDiff);
            }
        }
    }

    @Override
    public void undoCommand()
    {
        ((ScrollPaneMindMap) MainFrame.getInstance().getCurrentProjectView().getTabbedPane().getSelectedComponent()).getViewport().setViewPosition(pocetniViewPort);
        selektovanPojam.promeniLokacije(-delta.x, -delta.y);
        mapPanel.setCentralizovan(false);
        System.out.println(mapPanel.samoNeRadiGetterIzgleda());
        selektovanPojam.setColor(pboja);
        selektovanPojam.setStrokic(pStroke);
        for(int i = 0; i < deca.size(); i++){
            deca.get(i).setLokacija(pocetak.get(i));
        }
    }

    private void BFS(Pojam p, List<Pojam> selektovani, MindMap map, List<Pair<Pojam, Integer>> rezultat){
        Queue<Pair<Pojam, Integer>> red = new LinkedList<>();

        selektovani.add(p);

        red.add(new Pair<>(p, 0));

        while (!red.isEmpty()){
            p = red.element().getKey();
            int dubina = red.element().getValue();
            maksDubina = dubina;
            rezultat.add(new Pair<>(p, dubina));
            //System.out.println(p.getName() + " " + dubina);
            red.remove();

            for(var x : map.getChildren()){
                Pojam xx = (Pojam) x;
                if(xx.getVezeList().contains(p) || p.getVezeList().contains(xx)){
                    if(!selektovani.contains(xx)){
                        selektovani.add(xx);
                        red.add(new Pair<>(xx, dubina + 1));
                    }
                }
            }
        }
    }
}
