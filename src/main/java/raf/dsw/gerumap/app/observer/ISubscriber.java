package raf.dsw.gerumap.app.observer;

// Klasa koja dobija informaciju od klase koja je IPublisher
public interface ISubscriber {

    void update(Notification notification);


}
