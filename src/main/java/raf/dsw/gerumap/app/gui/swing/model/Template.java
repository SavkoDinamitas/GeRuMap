package raf.dsw.gerumap.app.gui.swing.model;

import lombok.AllArgsConstructor;
import raf.dsw.gerumap.app.mapRepository.implementation.MindMap;

//Klasa koja predstavlja neku MindMap-u kao Template
public class Template
{
    private String nazivTemplate;
    private MindMap mapaUma;

    public Template(String nazivTemplate, MindMap mapaUma)
    {
        this.nazivTemplate = nazivTemplate;
        this.mapaUma = mapaUma;
    }
}
