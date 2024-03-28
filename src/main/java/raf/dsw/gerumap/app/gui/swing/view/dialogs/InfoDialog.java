package raf.dsw.gerumap.app.gui.swing.view.dialogs;

import lombok.Getter;
import lombok.Setter;
import raf.dsw.gerumap.app.gui.swing.view.MainFrame;

import javax.swing.*;
import java.awt.*;

@Getter
@Setter
public class InfoDialog extends JDialog {
    private static InfoDialog instance;

    private JLabel slikaL;
    private JLabel slikaD;

    private InfoDialog(){
        super(MainFrame.getInstance(), "Info", true);
    }

    public void start(){
        setVisible(true);
    }
    private void initialise(){
        setLayout(new GridLayout(2, 2));

        JPanel sD = new JPanel();
        JPanel sL = new JPanel();
        sD.setLayout(new GridBagLayout());
        sL.setLayout(new GridBagLayout());
        setSize(400, 400);
        setLocationRelativeTo(null);

        slikaD = new JLabel();
        //slikaD.setIcon(loadIcon("/images/DA.jpg", 100, 100));
        sD.add(slikaD);
        slikaL = new JLabel();
        //slikaL.setIcon(loadIcon("/images/LS.png", 100, 100));
        sL.add(slikaL);

        JLabel labelD = new JLabel("Dimitrije Andzic RN 16/21");
        JLabel labelL = new JLabel("Lazar Savic RN 85/22");

        add(sD);
        add(labelD);
        add(sL);
        add(labelL);

        //setVisible(true);
    }

    public static InfoDialog getInstance(){
        if(instance == null)
        {
            instance = new InfoDialog();
            instance.initialise();
        }
        return instance;
    }
}
