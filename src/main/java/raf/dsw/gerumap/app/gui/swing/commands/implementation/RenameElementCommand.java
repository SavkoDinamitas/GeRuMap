package raf.dsw.gerumap.app.gui.swing.commands.implementation;

import raf.dsw.gerumap.app.gui.swing.commands.AbstractCommand;
import raf.dsw.gerumap.app.mapRepository.implementation.Pojam;

import java.awt.*;

public class RenameElementCommand extends AbstractCommand {

    private String prosloIme;

    private String ime;

    private Pojam pojam;

    public RenameElementCommand(Pojam pojam, String ime){
        prosloIme = pojam.getName();
        this.ime = ime;
        this.pojam = pojam;
    }

    @Override
    public void doCommand() {
        pojam.setName(ime);
    }

    @Override
    public void undoCommand() {
        pojam.setName(prosloIme);
    }
}
