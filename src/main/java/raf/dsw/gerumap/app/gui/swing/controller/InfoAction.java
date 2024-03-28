package raf.dsw.gerumap.app.gui.swing.controller;

import raf.dsw.gerumap.app.gui.swing.view.dialogs.InfoDialog;

import java.awt.event.ActionEvent;

public class InfoAction extends AbstractGeRuMapAction
{

    public InfoAction()
    {
        putValue(NAME, "Info");
        putValue(SHORT_DESCRIPTION, "Informacije o kreatorima");
    }
    @Override
    public void actionPerformed(ActionEvent e)
    {
        InfoDialog.getInstance().getSlikaD().setIcon(loadIcon("/images/DA.jpg", 100, 100));
        InfoDialog.getInstance().getSlikaL().setIcon(loadIcon("/images/LS.png", 100, 100));
        InfoDialog.getInstance().start();
    }
}
