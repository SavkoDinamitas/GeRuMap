package raf.dsw.gerumap.app.gui.swing.view.dialogs;

import lombok.Getter;
import lombok.Setter;
import raf.dsw.gerumap.app.core.ApplicationFramework;
import raf.dsw.gerumap.app.errorHandler.MessageType;
import raf.dsw.gerumap.app.gui.swing.view.MainFrame;
import raf.dsw.gerumap.app.gui.swing.view.MindMapPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Getter
@Setter
public class NewPojamDialog extends JDialog {
    private String pojamName;
    private JTextField textField;

    private MindMapPanel mapView;

    private int brojSlova;

    public NewPojamDialog(MindMapPanel mapPanel){
        mapView = mapPanel;
        setModal(true);
        initialise();
    }

    private void initialise(){
        setLayout(new GridLayout(3, 1));
        JLabel autor = new JLabel("Ime pojma: ");
        textField = new JTextField();
        JButton ok = new JButton("Gotovo");
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!textField.getText().equals("") && textField.getText().length() <= brojSlova) {
                    if(!vecImaIme(textField.getText())){
                        pojamName = textField.getText();
                        dispose();
                    }
                    else{
                        ApplicationFramework.getInstance().getMessageGenerator().generateMessage(MessageType.ERROR, "Zadato ime pojma vec postoji!");
                    }
                }
                else{
                    ApplicationFramework.getInstance().getMessageGenerator().generateMessage(MessageType.ERROR, "Pojam ne sme biti duzi od " + brojSlova +  " slova i ne sme biti prazan!");
                }
            }
        });
        add(autor);
        add(textField);
        add(ok);
        setSize(200, 200);
        setLocationRelativeTo(null);
    }

    private boolean vecImaIme(String s){
        for(var x : mapView.getMap().getChildren()){
            if(x.getName().equals(s.toUpperCase()))
                return true;
        }
        return false;
    }
}
