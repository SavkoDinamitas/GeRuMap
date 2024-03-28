package raf.dsw.gerumap.app.gui.swing.commands;

import lombok.Getter;
import lombok.Setter;
import raf.dsw.gerumap.app.core.ApplicationFramework;
import raf.dsw.gerumap.app.gui.swing.view.MainFrame;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CommandManager {
    private List<AbstractCommand> komande = new ArrayList<>();

    private int trenutnaKomanda = 0;

    public void addCommand(AbstractCommand command){
        while(trenutnaKomanda < komande.size()){
            komande.remove(trenutnaKomanda);
        }
        komande.add(command);
        doCommand();
    }

    public void doCommand(){
        if(trenutnaKomanda < komande.size()){
            komande.get(trenutnaKomanda++).doCommand();

            SwingUtilities.updateComponentTreeUI(MainFrame.getInstance().getMapTree().vratiTreeView());
            //enable undo button
            ApplicationFramework.getInstance().getGui().enableUndoCommand();
        }
        if(trenutnaKomanda == komande.size()){
            //disable redo button
            ApplicationFramework.getInstance().getGui().disableRedoCommand();
        }
    }

    public void undoCommand(){
        if(trenutnaKomanda > 0){
            //enable redo
            ApplicationFramework.getInstance().getGui().enableRedoCommand();
            komande.get(--trenutnaKomanda).undoCommand();
            SwingUtilities.updateComponentTreeUI(MainFrame.getInstance().getMapTree().vratiTreeView());
        }
        if(trenutnaKomanda == 0){
            //disable undo
            ApplicationFramework.getInstance().getGui().disableUndoCommand();
        }
    }
}
