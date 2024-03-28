package raf.dsw.gerumap.app.mapRepository.implementation;

import lombok.NoArgsConstructor;
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

// ProjectExplorer je IPublisher za ProjectView klasu iz razloga sto se moze desiti da se iz ProjectExplorer-a obrise Project koji trenutno prikazujemo u ProjectView-u
public class ProjectExplorer extends MapNodeComposite {

    public  ProjectExplorer(String name)
    {
        super(name, null);
        subscriberList = new ArrayList<>();
    }
    /*
    @Override
    public void addChild(MapNode child) {
        if(child != null && child instanceof Project){
            Project project = (Project) child;
            if(!this.getChildren().contains(project)){
                this.getChildren().add(project);
            }
        }
    }
    */
    @Override
    public void addChild(MapNode child)
    {
        if(child != null && child instanceof Project)
        {
            Project project = (Project)child;
            boolean t = true;
            for(MapNode m : this.getChildren())
            {
                if(project.getName().equals(m.getName()))
                {
                   t = false;
                   break;
                }
            }
            if(t)
            {
                this.getChildren().add(project);
            }
        }
    }

    @Override
    public void deleteChild(MapNode child) {
        getChildren().remove(child);
        notifySubscribers(new Notification(child, NotificationType.DELETE));

    }
}
