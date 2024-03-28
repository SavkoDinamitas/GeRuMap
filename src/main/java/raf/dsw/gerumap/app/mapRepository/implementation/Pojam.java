package raf.dsw.gerumap.app.mapRepository.implementation;


import javafx.util.Pair;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import raf.dsw.gerumap.app.mapRepository.composite.MapNode;
import raf.dsw.gerumap.app.observer.IPublisher;
import raf.dsw.gerumap.app.observer.ISubscriber;
import raf.dsw.gerumap.app.observer.Notification;
import raf.dsw.gerumap.app.observer.NotificationType;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Pojam extends MapNode implements IPublisher
{
    private float strokic;
    private Color color;

    private Point lokacija;

    private static int width = 90;

    private static int height = 50;

    private List<Pojam> vezeList = new ArrayList<Pojam>();

    private transient List<ISubscriber> subscriberList = new ArrayList<>();

    private String type = "Pojam";

    public Pojam(String ime, MapNode cale)
    {
        super(ime, cale);
    }

    public Pojam(String ime, MapNode cale, float strokic, Color color)
    {
        super(ime, cale);
        this.strokic = strokic;
        this.color = color;
    }

    public static int getWidth(){
        return width;
    }

    public static int getHeight(){
        return height;
    }

    public void dodajVezu(Pojam p){
        if(!vezeList.contains(p)){
            vezeList.add(p);
            notifySubscribers(new Notification(new Pair<Pojam, Pojam>(this, p), NotificationType.DODATAVEZA));
        }
    }

    public void obrisiVezu(Pojam p){
        vezeList.remove(p);
        notifySubscribers(new Notification(new Pair<Pojam, Pojam>(this, p), NotificationType.OBRISANAVEZA));
    }

    @Override
    public void addSubscriber(ISubscriber iSubscriber) {
        if(!subscriberList.contains(iSubscriber))
            subscriberList.add(iSubscriber);
    }

    @Override
    public void removeSubscriber(ISubscriber iSubscriber) {
        subscriberList.remove(iSubscriber);
    }

    @Override
    public void notifySubscribers(Notification notification) {
        for(var x : subscriberList){
            x.update(notification);
            //mora ovde jer inace crkne serijalizacija
            ((Project)getParent().getParent()).setChanged(true);
        }
        //((Project)getParent().getParent()).setChanged(true);
    }

    public void setColor(Color c){
        color = c;
        notifySubscribers(new Notification(this, NotificationType.MENJANJESTILA));
    }

    public void setName(String s){
        super.setName(s.toUpperCase());
        notifySubscribers(new Notification(this, NotificationType.RENAME));
    }

    public void promeniLokacije(int dx, int dy)
    {
        lokacija.x += dx;
        lokacija.y += dy;
        notifySubscribers(new Notification(this, NotificationType.POMERANJE));
    }

    public void setLokacija(Point p){
        lokacija = p;
        notifySubscribers(new Notification(this, NotificationType.POMERANJE));
    }
}
