package raf.dsw.gerumap.app.errorHandler;

import raf.dsw.gerumap.app.observer.ISubscriber;

// Klase koje implementiraju Logger se odmah pri njihovom instanciranju stavljaju kao ISubscriber-i odredjenom IPublisher-u
public interface Logger extends ISubscriber {
    void log(String sadrzaj);
}
