package raf.dsw.gerumap.app.gui.swing.view;

import lombok.Getter;
import lombok.Setter;
import raf.dsw.gerumap.app.mapRepository.implementation.Pojam;

import javax.lang.model.element.Element;
import java.awt.*;
import java.awt.geom.Line2D;
import java.lang.annotation.ElementType;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class VezaPainter implements Painter
{
    private Shape oblik;
    private float stroke = 2.7f;
    private Color color = Color.BLACK;
    private Pojam from;
    private Pojam to;

    @Override
    public void paint(Graphics2D g) {
        List<Point> tackeOd = new ArrayList<>();
        tackeOd.add(new Point(from.getLokacija().x, from.getLokacija().y - Pojam.getHeight()/2));
        tackeOd.add(new Point(from.getLokacija().x, from.getLokacija().y + Pojam.getHeight()/2));
        tackeOd.add(new Point(from.getLokacija().x - Pojam.getWidth()/2, from.getLokacija().y));
        tackeOd.add(new Point(from.getLokacija().x + Pojam.getWidth()/2, from.getLokacija().y));

        List<Point> tackeDo = new ArrayList<>();
        tackeDo.add(new Point(to.getLokacija().x, to.getLokacija().y - Pojam.getHeight()/2));
        tackeDo.add(new Point(to.getLokacija().x, to.getLokacija().y + Pojam.getHeight()/2));
        tackeDo.add(new Point(to.getLokacija().x - Pojam.getWidth()/2, to.getLokacija().y));
        tackeDo.add(new Point(to.getLokacija().x + Pojam.getWidth()/2, to.getLokacija().y));

        //pitagora za najkracu liniju
        int minrastojanje = Integer.MAX_VALUE;
        Point finalod = new Point();
        Point finalDo = new Point();
        for(var p1 : tackeOd){
            for(var p2 : tackeDo){
                int pitagora = (int) Math.sqrt(Math.abs(p1.x - p2.x) * Math.abs(p1.x - p2.x) + Math.abs(p1.y - p2.y) * Math.abs(p1.y - p2.y));
                if(pitagora < minrastojanje){
                    finalod = p1;
                    finalDo = p2;
                    minrastojanje = pitagora;
                }
            }
        }

        g.setColor(color);
        g.setStroke(new BasicStroke(stroke));
        oblik = new Line2D.Double(finalod, finalDo);
        g.draw(oblik);
    }

    @Override
    public boolean elementAt(Point p) {
        int box1 = p.x - 5;
        int box2 = p.y - 5;
        int width = 10;
        int height = 10;
        if(oblik.intersects(box1, box2, width, height))
            return true;
        return false;
    }

    @Override
    public Shape getShape() {
        return oblik;
    }
}
