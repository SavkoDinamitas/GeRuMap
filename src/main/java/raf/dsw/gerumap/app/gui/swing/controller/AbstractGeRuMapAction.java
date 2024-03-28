package raf.dsw.gerumap.app.gui.swing.controller;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

// Apstraktna metoda koju extend-uju ostale Action klase i sluzi samo da bi iz nje mogla da se pozove metoda loadIcon za svaku od ostalih Action klasa
public abstract class AbstractGeRuMapAction extends AbstractAction
{
    public Icon loadIcon(String fileName, int width, int height)
    {
        Icon icon = null;
        URL ImageURL = getClass().getResource(fileName);
        if(ImageURL != null)
        {
            Image img = new ImageIcon(ImageURL).getImage();
            Image newImg = img.getScaledInstance(width, height, Image.SCALE_DEFAULT);
            icon = new ImageIcon(newImg);
        }
        else
        {
            System.err.println("File " + fileName + " not found");
        }
        return icon;
    }
}
