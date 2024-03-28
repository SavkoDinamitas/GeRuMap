package raf.dsw.gerumap.app.core;

import raf.dsw.gerumap.app.gui.swing.commands.CommandManager;
import raf.dsw.gerumap.app.observer.ISubscriber;

public interface Gui extends ISubscriber
{
    void start();

    void disableUndoCommand();

    void disableRedoCommand();

    void enableUndoCommand();

    void enableRedoCommand();
    CommandManager getCommandManager();
}
