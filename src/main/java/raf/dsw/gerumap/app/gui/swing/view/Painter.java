package raf.dsw.gerumap.app.gui.swing.view;

import java.awt.*;
import java.util.List;

public interface Painter
{
    public void paint(Graphics2D g);

    public boolean elementAt(Point p);

    public Shape getShape();

}
