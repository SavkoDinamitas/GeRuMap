package raf.dsw.gerumap.app.observer;

// Klasa koja ce nekoj ISubcriber klasi da posalje obavestenje
public interface IPublisher {

    void addSubscriber(ISubscriber iSubscriber);

    void removeSubscriber(ISubscriber iSubscriber);

    void notifySubscribers(Notification notification);

}
