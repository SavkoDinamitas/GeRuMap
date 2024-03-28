package raf.dsw.gerumap.app.errorHandler;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

@Getter
@Setter

// Klasa koja sadrzi sve potrebne informacije o poruci koja ce se prikazati na GUI-ju
public class Message {
    private String sadrzaj;

    private MessageType messageType;

    private Timestamp timestamp;

    public Message(String sadrzaj, MessageType messageType, Timestamp timestamp){
        this.sadrzaj = sadrzaj;
        this.messageType = messageType;
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "[" + messageType.toString() + "] " + (new SimpleDateFormat("yyyy.MM.dd HH:mm")).format(timestamp) + " " + sadrzaj;
    }
}
