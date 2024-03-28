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

@Getter
@Setter
@NoArgsConstructor
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = MapNodeComposite.class, name = "MapNodeComposite"),
        @JsonSubTypes.Type(value = Pojam.class, name = "Pojam"),
})
// Apstraktna klasa koja predstavlja Node koji moze da ima samo roditelja
public abstract class MapNode {
    private String name;
    private transient MapNode parent;

    public MapNode(String ime, MapNode parent){
        this.name = ime;
        this.parent = parent;
    }
}
