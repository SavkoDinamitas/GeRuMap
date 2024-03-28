package raf.dsw.gerumap.app.gui.swing.view.dialogs;

import lombok.Getter;
import lombok.Setter;
import raf.dsw.gerumap.app.gui.swing.tree.model.MapTreeItem;
import raf.dsw.gerumap.app.gui.swing.view.MainFrame;
import raf.dsw.gerumap.app.mapRepository.implementation.Project;

import javax.swing.*;
import java.awt.*;
@Getter
@Setter

// Klasa koja predstavlja Dialog/Window na kome imamo JLabel i JButton pomocu kog cemo da promenimo ime autora
public class EditAuthorDialog extends JDialog {
    private static EditAuthorDialog instance;
    private JTextField aTx;

    private EditAuthorDialog(){
        super(MainFrame.getInstance(), "Autor", true);
    }

    public void start(){
        MapTreeItem selected = (MapTreeItem) MainFrame.getInstance().getMapTree().getSelectedNode();
        aTx.setText(((Project)selected.getMapNode()).getAutor());
        setVisible(true);

    }
    private void initialise(){
        Toolkit kit = Toolkit.getDefaultToolkit();
        setLayout(new GridLayout(3, 1));
        JLabel autor = new JLabel("Naziv autora");
        aTx = new JTextField();
        JButton ok = new JButton(MainFrame.getInstance().getActionManager().getChangeAuthorAction());
        add(autor);
        add(aTx);
        add(ok);
        setSize(200, 200);
        setLocationRelativeTo(null);
        //setVisible(true);
    }

    public static EditAuthorDialog getInstance()
    {
        if(instance == null)
        {
            instance = new EditAuthorDialog();
            instance.initialise();
        }
        return instance;
    }
}
