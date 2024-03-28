package raf.dsw.gerumap.app.observer;

import lombok.Getter;
import lombok.Setter;
import raf.dsw.gerumap.app.mapRepository.composite.MapNode;

@Getter
@Setter
public class Notification {

    private Object objectOfNotification;
    private NotificationType notificationType;
    private String renameKomedija;

    public Notification(Object objectOfNotification, NotificationType notificationType)
    {
        this.objectOfNotification = objectOfNotification;
        this.notificationType = notificationType;
    }

    public Notification(Object objectOfNotification, NotificationType notificationType, String renameKomedija)
    {
        this(objectOfNotification, notificationType);
        this.renameKomedija = renameKomedija;
    }

}
