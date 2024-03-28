package raf.dsw.gerumap.app.gui.swing.view;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class MyMenuBar extends JMenuBar
{
    public MyMenuBar()
    {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        fileMenu.add(MainFrame.getInstance().getActionManager().getExitAction());
        fileMenu.add(MainFrame.getInstance().getActionManager().getNewProjectAction());
        fileMenu.add(MainFrame.getInstance().getActionManager().getEditAction());
        fileMenu.add(MainFrame.getInstance().getActionManager().getDeleteAction());
        fileMenu.add(MainFrame.getInstance().getActionManager().getOpenAction());
        fileMenu.add(MainFrame.getInstance().getActionManager().getSaveAction());

        JMenu helpMenu = new JMenu("Help");
        //helpMenu.add(MainFrame.getInstance().getActionManager().getEditAction());
        helpMenu.add(MainFrame.getInstance().getActionManager().getInfoAction());

        menuBar.add(fileMenu);
        menuBar.add(helpMenu);

        this.add(menuBar);
    }
}
