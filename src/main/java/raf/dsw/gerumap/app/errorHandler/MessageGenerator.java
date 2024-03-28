package raf.dsw.gerumap.app.errorHandler;

import raf.dsw.gerumap.app.observer.IPublisher;
import raf.dsw.gerumap.app.observer.ISubscriber;
import raf.dsw.gerumap.app.observer.Notification;
import raf.dsw.gerumap.app.observer.NotificationType;

import java.util.ArrayList;
import java.util.Date;
import java.sql.Timestamp;
import java.util.List;

// Singleton klasa koja generise Message i obavestava sve svoje Subscriber-e o kakvom je Message-u rec
public class MessageGenerator implements IPublisher {
    private List<ISubscriber> subscriberList = new ArrayList<>();

    public MessageGenerator(){

    }

    public void generateMessage(MessageType messageType, String sadrzaj){
        Message m = new Message(sadrzaj, messageType, new Timestamp((new Date()).getTime()));
        notifySubscribers(new Notification(m, NotificationType.MESSAGE));
        //return m;
    }

    @Override
    public void addSubscriber(ISubscriber iSubscriber) {
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
        }
    }
}
