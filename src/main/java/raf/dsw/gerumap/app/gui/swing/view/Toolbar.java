package raf.dsw.gerumap.app.gui.swing.view;

import com.sun.tools.javac.Main;

import javax.swing.*;

public class Toolbar extends JToolBar
{
    public Toolbar()
    {
        super(HORIZONTAL);
        setFloatable(false);

        add(MainFrame.getInstance().getActionManager().getExitAction());
        add(MainFrame.getInstance().getActionManager().getNewProjectAction());
        add(MainFrame.getInstance().getActionManager().getDeleteAction());
        add(MainFrame.getInstance().getActionManager().getEditAction());
        addSeparator();
        add(MainFrame.getInstance().getActionManager().getUndoAction());
        add(MainFrame.getInstance().getActionManager().getRedoAction());
        add(MainFrame.getInstance().getActionManager().getSaveAction());
        add(MainFrame.getInstance().getActionManager().getOpenAction());
        addSeparator();
        add(MainFrame.getInstance().getActionManager().getSaveTemplateAction());
        add(MainFrame.getInstance().getActionManager().getLoadTemplateAction());
        addSeparator();
        add(MainFrame.getInstance().getActionManager().getCaptureMindMapAction());
        addSeparator();
        add(MainFrame.getInstance().getActionManager().getCenterPojamAction());
    }
}
