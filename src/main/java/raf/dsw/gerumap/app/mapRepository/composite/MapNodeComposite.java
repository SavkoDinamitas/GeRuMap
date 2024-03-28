package raf.dsw.gerumap.app.mapRepository.composite;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raf.dsw.gerumap.app.mapRepository.implementation.MindMap;
import raf.dsw.gerumap.app.mapRepository.implementation.Pojam;
import raf.dsw.gerumap.app.mapRepository.implementation.Project;
import raf.dsw.gerumap.app.mapRepository.implementation.ProjectExplorer;
import raf.dsw.gerumap.app.observer.IPublisher;
import raf.dsw.gerumap.app.observer.ISubscriber;
import raf.dsw.gerumap.app.observer.Notification;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = MindMap.class, name = "MindMap"),
        @JsonSubTypes.Type(value = Project.class, name = "Project"),
})
// Apstraktna klasa koja predstavja Node koja takodje moze da ima decu
public abstract class MapNodeComposite extends MapNode implements IPublisher{

    protected transient List<ISubscriber> subscriberList = new ArrayList<>();
    private List<MapNode> children;

    private int nomenklatura;

    private String type = "MapNodeComposite";

    public MapNodeComposite(String name, MapNode parent){
        super(name, parent);
        this.children = new ArrayList<>();
        nomenklatura = 1;
    }

    public  MapNodeComposite(String name, MapNode parent, List<MapNode> children){
        super(name, parent);
        this.children = children;
    }

    public abstract void addChild(MapNode child);

    public abstract void deleteChild(MapNode child);

    public MapNode MapNodegetChildByName(String name){
        for(MapNode xd : children){
            if(name.equals(xd.getName()))
                return xd;
        }
        return null;
    }

    public void addSubscriber(ISubscriber sub)
    {
        if(!subscriberList.contains(sub))
            subscriberList.add(sub);
    }

    @Override
    public void removeSubscriber(ISubscriber sub)
    {
        subscriberList.remove(sub);
    }

    @Override
    public void notifySubscribers(Notification notification)
    {
        for(int i = 0; i < subscriberList.size(); i++)
        {
                subscriberList.get(i).update(notification);
        }
        if(getParent() instanceof Project){
            ((Project)getParent()).setChanged(true);
        }
        if(getParent() instanceof ProjectExplorer){
            ((Project) this).setChanged(true);
        }
    }
}
