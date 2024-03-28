package raf.dsw.gerumap.app.errorHandler;

// Klasa koja pravi Logger sa odgovarajucim konstruktorom u zavisnosti od potrebe
// Zasniva se na Simple Factory Method-u, koji funkcionise isto kao Factory Method, uz napomenu da ga samo zanima pravljenje novih objekata, a ne i implementacija metoda itd.
public class LoggerFactory {
    public LoggerFactory(){

    }

    public Logger createLogger(String loggerType){
        if (loggerType.toUpperCase().equals("FILELOGGER"))
            return new FileLogger();

        else  if (loggerType.toUpperCase().equals("CONSOLELOGGER"))
            return new ConsoleLogger();

        return null;
    }
}
