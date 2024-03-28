package raf.dsw.gerumap.app.gui.swing.view.dialogs;

import raf.dsw.gerumap.app.core.ApplicationFramework;
import raf.dsw.gerumap.app.errorHandler.MessageType;
import raf.dsw.gerumap.app.gui.swing.view.MindMapPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CaptureChoiceDialog extends JDialog
{
    private MindMapPanel mapPanel;

    private boolean closed = false;

    public CaptureChoiceDialog(MindMapPanel mapPanel)
    {
        this.mapPanel = mapPanel;
        setModal(true);
        initialize();
        setVisible(true);
    }

    private void initialize()
    {
        setLayout(new GridLayout(4, 1));
        JLabel pitanje = new JLabel("Da li zelite da capture-ujete ceo panel ili samo centralizovan deo?");
        JRadioButton ceoPanelButton = new JRadioButton("Ceo");
        JRadioButton samoCentralizovanButton = new JRadioButton("Samo centralizovan");
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(ceoPanelButton);
        buttonGroup.add(samoCentralizovanButton);
        JButton finish = new JButton("Finish");
        finish.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(ceoPanelButton.isSelected())
                {
                    // mapPanel.setCentralizovan(false);
                    mapPanel.setOvoJeLosJezik(false);
                    dispose();
                }
                else if(samoCentralizovanButton.isSelected())
                {
                    //mapPanel.setCentralizovan(true);
                    mapPanel.setOvoJeLosJezik(true);
                    dispose();
                }
                else
                {
                    ApplicationFramework.getInstance().getMessageGenerator().generateMessage(MessageType.ERROR, "Morate izabrati tacno jednu opciju");
                }
            }
        });
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                closed = true;
            }
        });
        add(pitanje);
        add(ceoPanelButton);
        add(samoCentralizovanButton);
        add(finish);
        setSize(400, 300);
        setLocationRelativeTo(null);
    }

    public boolean isClosed()
    {
        return closed;
    }

}
