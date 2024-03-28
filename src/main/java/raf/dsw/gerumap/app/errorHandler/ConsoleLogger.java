package raf.dsw.gerumap.app.errorHandler;

import raf.dsw.gerumap.app.core.ApplicationFramework;
import raf.dsw.gerumap.app.observer.Notification;

public class ConsoleLogger implements Logger {

    public ConsoleLogger(){
        ApplicationFramework.getInstance().getMessageGenerator().addSubscriber(this);
    }
    @Override
    public void update(Notification notification) {
        log(((Message)notification.getObjectOfNotification()).toString());
    }

    @Override
    public void log(String sadrzaj) {
        System.out.println(sadrzaj);
    }
}
