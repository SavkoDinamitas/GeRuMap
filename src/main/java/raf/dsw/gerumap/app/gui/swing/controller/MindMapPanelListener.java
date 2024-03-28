package raf.dsw.gerumap.app.gui.swing.controller;

import raf.dsw.gerumap.app.gui.swing.view.MainFrame;
import raf.dsw.gerumap.app.gui.swing.view.MindMapPanel;

import java.awt.event.*;

public class MindMapPanelListener implements MouseListener, MouseMotionListener, MouseWheelListener
{
    private MindMapPanel mindMapPanel;

    public MindMapPanelListener(MindMapPanel mindMapPanel)
    {
        this.mindMapPanel = mindMapPanel;
    }
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        MainFrame.getInstance().getCurrentProjectView().getStateManager().getCurrentState().misKliknut(e.getX(), e.getY(), mindMapPanel);
        //System.out.println("xdd");
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        MainFrame.getInstance().getCurrentProjectView().getStateManager().getCurrentState().misPusten(e.getX(), e.getY(), mindMapPanel);
        //System.out.println("xdd22");
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        MainFrame.getInstance().getCurrentProjectView().getStateManager().getCurrentState().misPovucen(e.getX(), e.getY(), mindMapPanel);
        //System.out.println("xdd333");
    }

    @Override
    public void mouseMoved(MouseEvent e) {
       // System.out.println(e.getX() + " " + e.getY());
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        MainFrame.getInstance().getCurrentProjectView().getStateManager().getCurrentState().misSkrolovan(e.getX(), e.getY(), mindMapPanel, e.getWheelRotation());
    }
}
