package raf.dsw.gerumap.app.gui.swing.view;

import javax.swing.*;

public class MindMapBar extends JToolBar {

    public MindMapBar(){
        super(VERTICAL);
        setFloatable(false);

        add(MainFrame.getInstance().getActionManager().getNewPojamAction());
        add(MainFrame.getInstance().getActionManager().getNewVezaAction());
        add(MainFrame.getInstance().getActionManager().getSelectionAction());
        add(MainFrame.getInstance().getActionManager().getMoveAction());
        add(MainFrame.getInstance().getActionManager().getUkloniAction());
        add(MainFrame.getInstance().getActionManager().getEditStyleAction());
        add(MainFrame.getInstance().getActionManager().getRenamePojamAction());
        add(MainFrame.getInstance().getActionManager().getZoomAction());
    }
}
