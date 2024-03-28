package raf.dsw.gerumap.app.gui.swing.view;

import lombok.Getter;
import lombok.Setter;
import raf.dsw.gerumap.app.mapRepository.implementation.Pojam;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.List;
@Getter
@Setter

public class PojamPainter implements Painter
{
    private Pojam pojam;
    private Shape oblik;

    @Override
    public void paint(Graphics2D g)
    {
        g.setColor(pojam.getColor());
        g.setStroke(new BasicStroke(pojam.getStrokic()));
        oblik = new Ellipse2D.Double(pojam.getLokacija().x - Pojam.getWidth()/2, pojam.getLokacija().y - Pojam.getHeight()/2, Pojam.getWidth(), Pojam.getHeight());
        g.draw(oblik);
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 10));
        int textWidth = g.getFontMetrics().stringWidth(pojam.getName());

        g.drawString(pojam.getName(), pojam.getLokacija().x - textWidth/2, pojam.getLokacija().y);
    }

    @Override
    public boolean elementAt(Point p)
    {
        if(oblik.contains(p))
            return true;
        return false;
    }

    @Override
    public Shape getShape() {
        return oblik;
    }
}
