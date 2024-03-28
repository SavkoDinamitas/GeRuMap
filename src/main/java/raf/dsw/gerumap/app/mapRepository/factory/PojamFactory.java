package raf.dsw.gerumap.app.mapRepository.factory;

import raf.dsw.gerumap.app.mapRepository.composite.MapNode;
import raf.dsw.gerumap.app.mapRepository.implementation.Pojam;

import java.awt.*;

public class PojamFactory extends MapNodeFactory{
    @Override
    protected MapNode createNode() {
        return new Pojam("Pojam", null, 2.7f, Color.BLACK);
    }
}
