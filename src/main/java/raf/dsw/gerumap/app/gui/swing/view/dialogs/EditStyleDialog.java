package raf.dsw.gerumap.app.gui.swing.view.dialogs;

import lombok.Getter;
import lombok.Setter;
import raf.dsw.gerumap.app.core.ApplicationFramework;
import raf.dsw.gerumap.app.errorHandler.MessageType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Getter
@Setter
public class EditStyleDialog extends JDialog {
    private Color color;
    private int stroke;

    public EditStyleDialog(){
        setModal(true);
        initialise();
    }

    private void initialise(){
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));
        jPanel.setSize(500, 500);
        setSize(500, 500);
        setLocationRelativeTo(null);
        JColorChooser colorPicker = new JColorChooser(Color.BLACK);
        JTextField tf = new JTextField();
        JLabel boja = new JLabel("Izaberite boju pojma");
        boja.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel strokic = new JLabel("Unesite debljinu linije pojma");
        strokic.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton gotovo = new JButton("Podesi");
        gotovo.setAlignmentX(Component.CENTER_ALIGNMENT);
        gotovo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(Integer.parseInt(tf.getText()) < 10 && Integer.parseInt(tf.getText()) > 0){
                        color = colorPicker.getColor();
                        stroke = Integer.parseInt(tf.getText());
                        dispose();
                    }
                    else {
                        ApplicationFramework.getInstance().getMessageGenerator().generateMessage(MessageType.ERROR, "Sirina linije mora biti veca od 0 i manja od 6");
                    }
                }
                catch (RuntimeException g){
                    ApplicationFramework.getInstance().getMessageGenerator().generateMessage(MessageType.ERROR, "Unesite realan broj u polje za sirinu");
                }
            }
        });

        jPanel.add(boja);
        jPanel.add(colorPicker);
        jPanel.add(strokic);
        jPanel.add(tf);
        jPanel.add(gotovo);
        add(jPanel);
    }
}
