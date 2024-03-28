package raf.dsw.gerumap.app.mapRepository.implementation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raf.dsw.gerumap.app.gui.swing.view.MainFrame;
import raf.dsw.gerumap.app.gui.swing.view.ProjectView;
import raf.dsw.gerumap.app.mapRepository.composite.MapNode;
import raf.dsw.gerumap.app.mapRepository.composite.MapNodeComposite;
import raf.dsw.gerumap.app.observer.IPublisher;
import raf.dsw.gerumap.app.observer.ISubscriber;
import raf.dsw.gerumap.app.observer.Notification;
import raf.dsw.gerumap.app.observer.NotificationType;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
// Project je IPublisher za ProjectView klasu koja je njegov ISubscribe-er
public class Project extends MapNodeComposite {
    private String autor;

    private String filePath;

    private boolean changed = true;

    private String type = "Project";

    public Project(){
        super(null, null);
    }

    public Project(String name, MapNode parent)
    {
        super(name, parent);
    }
    /*
    public Project(String name, MapNode parent, String autor){
        super(name, parent);
        this.autor = autor;
    }
    */
    /*
    @Override
    public void addChild(MapNode child) {
        if(child != null && child instanceof MindMap){
            MindMap mapa = (MindMap) child;
            if(!this.getChildren().contains(mapa)){
                this.getChildren().add(mapa);
            }
        }
    }
    */
    @Override
    public void addChild(MapNode child)
    {
        if(child != null && child instanceof MindMap)
        {
            MindMap map = (MindMap)child;
            boolean t = true;
            for(MapNode m : this.getChildren())
            {
                if(map.getName().equals(m.getName()))
                {
                    t = false;
                    break;
                }
            }
            if(t)
            {
                this.getChildren().add(map);
                //map.addSubscriber(MainFrame.getInstance().getProjectView(this));
                notifySubscribers(new Notification(child, NotificationType.ADD));
            }
        }
    }

    @Override
    public void deleteChild(MapNode child) {
        getChildren().remove(child);
        notifySubscribers(new Notification(child, NotificationType.DELETE));
    }

    @Override
    public void setName(String name)
    {
        super.setName(name);
        notifySubscribers(new Notification(this, NotificationType.RENAME));
    }

    public void setAutor(String autor) {
        this.autor = autor;
        notifySubscribers(new Notification(this, NotificationType.RENAME));
    }
}
