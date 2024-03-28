package raf.dsw.gerumap.app.gui.swing.commands.implementation;

import raf.dsw.gerumap.app.gui.swing.commands.AbstractCommand;
import raf.dsw.gerumap.app.gui.swing.view.MindMapPanel;
import raf.dsw.gerumap.app.gui.swing.view.VezaPainter;
import raf.dsw.gerumap.app.mapRepository.implementation.Pojam;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddVezaCommand extends AbstractCommand {

    private Pojam pojamod;

    private Pojam pojamdo;

    private MindMapPanel mapView;

    private boolean naopacke = false;

    public AddVezaCommand(Pojam pojamod, Pojam pojamdo, MindMapPanel mapView){
        this.pojamod = pojamod;
        this.pojamdo = pojamdo;
        this.mapView = mapView;
    }
    @Override
    public void doCommand() {
        if(naopacke){
            pojamdo.dodajVezu(pojamod);
            return;
        }

        pojamod.dodajVezu(pojamdo);
        //pojamdo.dodajVezu(pojamod);

        //mascenje sa ciklusom

        HashMap<Pojam, List<Pojam>> mapaPojamPojmovi = new HashMap<>();
        for(var x : mapView.getMap().getChildren())
        {
            Pojam pojam = (Pojam) x;
            mapaPojamPojmovi.put(pojam, pojam.getVezeList());
        }
        // Ispisivanje trenutne mape
        //ispisiMapu(mapaPojamPojmovi);
        boolean istina = daLiSeVraca(mapaPojamPojmovi, pojamod, pojamod, true);
        if(istina)
        {
            //System.out.println("Ima ciklus");
        }
        else
        {
            //System.out.println("Nema ciklus");
        }
        // Ukoliko ima ciklusa sledi popravljanje
        if(istina)
        {
            pojamod.obrisiVezu(pojamdo);
            pojamdo.dodajVezu(pojamod);
            naopacke = true;

            // Dodajemo vezu odnosno menjamo smer vec postojece veze
            /*for(Pojam x : mapaPojamPojmovi.get(pojamod))
            {
                mapaPojamPojmovi.get(x).add(pojamod);
            }*/
            // Brisemo nepotrebne smerove veza iz ciljanogPojma
            //mapaPojamPojmovi.get(pojamod).clear();
        }

        //ispisiMapu(mapaPojamPojmovi);
        istina = daLiSeVraca(mapaPojamPojmovi, pojamod, pojamod, true);
        if(istina)
        {
            //System.out.println("Ima ciklus");
        }
        else
        {
            //System.out.println("Nema ciklus");
        }
        // Ispisi sadrzaj veza svih pojmova u mapi
        //System.out.println("Sadrzaj veza svih pojmova u mapi");
        /*for(var x : mapView.getMap().getChildren())
        {
            Pojam pojam = (Pojam) x;
            System.out.print(pojam.getName() + " -> ");
            for(Pojam vezaPojam : pojam.getVezeList())
            {
                System.out.print(vezaPojam.getName() + " ");
            }
            System.out.println();
        }*/

    }

    @Override
    public void undoCommand() {
        if (naopacke){
            pojamdo.obrisiVezu(pojamod);
            return;
        }
        pojamod.obrisiVezu(pojamdo);
        //pojamdo.obrisiVezu(pojamod);
    }

    void ispisiMapu(HashMap<Pojam, List<Pojam>> mapaPojamPojmovi)
    {
        System.out.println("Trenutne veze:");
        for(Map.Entry<Pojam, List<Pojam>> x : mapaPojamPojmovi.entrySet())
        {
            System.out.print(x.getKey().getName() + " -> ");
            for(var y : x.getValue())
            {
                System.out.print(y.getName() + " ");
            }
            System.out.println();
        }
        return;
    }

    boolean daLiSeVraca(HashMap<Pojam, List<Pojam>> mapaPojamPojmovi, Pojam startingPojam, Pojam currentPojam, boolean xd)
    {
        if(mapaPojamPojmovi.get(currentPojam).size() == 0)
        {
            return false;
        }
        // Ako smo se nekako vratili nazad na pocetni, ignorisacemo prvi put rekurzivni poziv
        if(xd)
        {
            xd = false;
        }
        else
        {
            if(startingPojam == currentPojam)
            {
                return true;
            }
        }
        for(var x : mapaPojamPojmovi.get(currentPojam))
        {
            if(daLiSeVraca(mapaPojamPojmovi, startingPojam, x, xd))
            {
                return true;
            }
        }
        return false;
    }
}
