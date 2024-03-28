package raf.dsw.gerumap.app.gui.swing.view.dialogs;

import lombok.Getter;
import lombok.Setter;
import raf.dsw.gerumap.app.core.ApplicationFramework;
import raf.dsw.gerumap.app.errorHandler.MessageType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

@Getter
@Setter
public class SaveTemplateDialog extends JDialog {

    private String ime;

    public SaveTemplateDialog(){
        setModal(true);
        initialise();
        setVisible(true);
    }

    private void initialise(){
        setTitle("Save Mind Map");
        setLayout(new GridLayout(3, 1));
        JLabel autor = new JLabel("Ime templejta: ");
        JTextField textField = new JTextField();
        JButton ok = new JButton("Gotovo");
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!textField.getText().equals("")) {
                    if(!vecImaIme(textField.getText())){
                        ime = textField.getText();
                        dispose();
                    }
                    else{
                        ApplicationFramework.getInstance().getMessageGenerator().generateMessage(MessageType.ERROR, "Zadato ime template-a vec postoji!");
                    }
                }
                else{
                    ApplicationFramework.getInstance().getMessageGenerator().generateMessage(MessageType.ERROR, "Ime ne sme biti prazno!");
                }
            }
        });
        add(autor);
        add(textField);
        add(ok);
        setSize(200, 200);
        setLocationRelativeTo(null);
    }

    private boolean vecImaIme(String name){
        try {
            File file = new File(getClass().getResource("/templates/" + name + ".json").getFile());
            return true;
        }
        catch (NullPointerException xd){
            return false;
        }
    }
}
