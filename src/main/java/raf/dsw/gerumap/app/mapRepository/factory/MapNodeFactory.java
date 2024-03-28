package raf.dsw.gerumap.app.mapRepository.factory;

import raf.dsw.gerumap.app.mapRepository.composite.MapNode;
import raf.dsw.gerumap.app.mapRepository.composite.MapNodeComposite;
import raf.dsw.gerumap.app.mapRepository.implementation.ProjectExplorer;


// Apstraktna klasa cija je jedina svrha da nam vrati odredjenu potklasu MapNode-a u zavisnosti od zadatog Parent-a
// Zasniva se na Factory Method-u, koji se koristi za pravljenje klasa u pozadini, odnosno pravljenje klasa sakriveno od korisnika
public abstract class MapNodeFactory {
    public MapNode getNode(MapNode parent){
        String repeatedFiles = "";
        MapNode xdd = createNode();
        xdd.setParent(parent);
        //ako nije project explorer, radimo brojanje
        if(parent != null)
        {
            repeatedFiles = (((MapNodeComposite) parent).getChildren().size() + ((MapNodeComposite) parent).getNomenklatura()) + "";
            xdd.setName(xdd.getName() + repeatedFiles);
        }

        return xdd;
    }
    protected abstract MapNode createNode();
}
