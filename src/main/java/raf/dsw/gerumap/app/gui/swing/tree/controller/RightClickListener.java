package raf.dsw.gerumap.app.gui.swing.tree.controller;


import raf.dsw.gerumap.app.gui.swing.tree.MapTree;
import raf.dsw.gerumap.app.gui.swing.tree.MapTreeImplementation;
import raf.dsw.gerumap.app.gui.swing.tree.view.MapTreeView;
import raf.dsw.gerumap.app.gui.swing.view.MainFrame;

import javax.swing.plaf.basic.BasicTreeUI;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class RightClickListener implements MouseListener
{
    private static Robot robot = null;
    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON3)
        {
            MapTreeView treeView = (((MapTreeImplementation)MainFrame.getInstance().getMapTree())).getTreeView();
            int currentRow = treeView.getClosestRowForLocation(e.getX(), e.getY());
            treeView.setSelectionRow(currentRow);
            System.out.println("Uspesno smo kliknuli desnim klikom");
            try
            {
                robot = new Robot();
            }
            catch (AWTException a)
            {
                a.printStackTrace();
            }
            int i = 5;
            while(i != 0)
            {
                robot.mousePress(MouseEvent.BUTTON1_DOWN_MASK);
                robot.mouseRelease(MouseEvent.BUTTON1_DOWN_MASK);
                i--;
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
