package raf.dsw.gerumap.app.gui.swing.commands;

import raf.dsw.gerumap.app.gui.swing.view.MindMapPanel;

public abstract class AbstractCommand {

    public abstract void doCommand();

    public abstract void undoCommand();
}
