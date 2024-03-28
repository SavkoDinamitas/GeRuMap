package raf.dsw.gerumap.app.mapRepository.implementation;

import lombok.NoArgsConstructor;
import raf.dsw.gerumap.app.mapRepository.composite.MapNode;
import raf.dsw.gerumap.app.mapRepository.composite.MapNodeComposite;
import raf.dsw.gerumap.app.observer.Notification;
import raf.dsw.gerumap.app.observer.NotificationType;

import java.util.ArrayList;

@NoArgsConstructor
// MindMap je IPublisher za MindMapPanel klasu koja je njen ISubscribe-er
public class MindMap extends MapNodeComposite {

    private String type = "MindMap";

    public MindMap(String name, MapNode parent)
    {
        super(name, parent);
        subscriberList = new ArrayList<>();
    }

    @Override
    public void addChild(MapNode child)
    {
        if(child != null && child instanceof Pojam)
        {
            Pojam pojam = (Pojam) child;
            boolean t = true;
            for(MapNode m : this.getChildren())
            {
                if(pojam.getName().equals(m.getName()))
                {
                    t = false;
                    break;
                }
            }
            if(t)
            {
                this.getChildren().add(pojam);
                notifySubscribers(new Notification(child, NotificationType.ADDPOJAM));
            }
        }
    }

    @Override
    public void deleteChild(MapNode child) {
        getChildren().remove(child);
        notifySubscribers(new Notification(child, NotificationType.DELETEPOJAM));
    }


    public void setName(String name)
    {
        String stariName = getName();
        super.setName(name);
        notifySubscribers(new Notification(this, NotificationType.RENAME, stariName));
        //System.out.println("Komedija");
    }

}
