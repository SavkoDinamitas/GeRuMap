package raf.dsw.gerumap.app.gui.swing.tree.controller;

import lombok.Getter;
import lombok.Setter;
import raf.dsw.gerumap.app.core.ApplicationFramework;
import raf.dsw.gerumap.app.errorHandler.MessageGenerator;
import raf.dsw.gerumap.app.errorHandler.MessageType;
import raf.dsw.gerumap.app.gui.swing.commands.AbstractCommand;
import raf.dsw.gerumap.app.gui.swing.commands.implementation.RenameChildCommand;
import raf.dsw.gerumap.app.gui.swing.tree.MapTreeImplementation;
import raf.dsw.gerumap.app.gui.swing.tree.model.MapTreeItem;
import raf.dsw.gerumap.app.gui.swing.tree.view.MapTreeView;
import raf.dsw.gerumap.app.gui.swing.view.MainFrame;
import raf.dsw.gerumap.app.mapRepository.composite.MapNode;
import raf.dsw.gerumap.app.mapRepository.composite.MapNodeComposite;
import raf.dsw.gerumap.app.mapRepository.implementation.ProjectExplorer;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellEditor;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellEditor;
import java.awt.*;
import java.awt.event.*;
import java.util.EventObject;
import java.awt.event.MouseEvent;


// Klasa koja implementira ActionListener da bi nakon korisnikove interakcije sa Cell-om naseg Tree-a mogla da promeni ime Cell-a
public class MapTreeCellEditor extends DefaultTreeCellEditor implements ActionListener {

    private Object clickedOnObject = null;

    private JTextField edit = null;
    public MapTreeCellEditor(JTree tree, DefaultTreeCellRenderer renderer) {
        super(tree, renderer);
    }

    public Component getTreeCellEditorComponent(JTree arg0, Object arg1, boolean arg2, boolean arg3, boolean arg4, int arg5) {
        //super.getTreeCellEditorComponent(arg0,arg1,arg2,arg3,arg4,arg5);
        clickedOnObject = arg1;
        edit = new JTextField(arg1.toString());
        edit.addActionListener(this);
        return edit;
    }
    public boolean isCellEditable(EventObject eventObject)
    {
        if(eventObject instanceof MouseEvent)
        {
            MouseEvent mouseEvent = (MouseEvent) eventObject;

            if(mouseEvent.getClickCount() == 5)
            {
                return true;
            }
        }
        return false;
    }

    public void actionPerformed(ActionEvent e){

        if (!(clickedOnObject instanceof MapTreeItem))
            return;

        MapTreeItem clicked = (MapTreeItem) clickedOnObject;
        String promenjenaVrednost = e.getActionCommand();
        if(promenjenaVrednost.equals("")){
            //System.err.println("Ne moze prazno ime da se stavi");
            ApplicationFramework.getInstance().getMessageGenerator().generateMessage(MessageType.ERROR, "Ne moze prazno ime da se stavi");
            return;
        }
        if(clicked.getMapNode() instanceof ProjectExplorer || promenjenaVrednost.toUpperCase().equals(clicked.getMapNode().getName().toUpperCase()))
        {
            AbstractCommand changeName = new RenameChildCommand(clicked, promenjenaVrednost);
            ApplicationFramework.getInstance().getGui().getCommandManager().addCommand(changeName);
            return;
        }
        MapTreeItem parentOfClicked = new MapTreeItem(clicked.getMapNode().getParent());
        //clicked.setName(e.getActionCommand());
        // Provera da li ima element sa istim imenom pored naseg trenutno gledanog elementa
        boolean xdd = false;
        int mnogoJasno = 0;
        for(MapNode m : ((MapNodeComposite)parentOfClicked.getMapNode()).getChildren())
        {
            if(m.getName().toUpperCase().contains(promenjenaVrednost.toUpperCase()))
            {
                mnogoJasno++;
            }
            if(promenjenaVrednost.toUpperCase().equals(m.getName().toUpperCase()))
            {
                xdd = true;
            }
        }
        if(xdd)
        {
            AbstractCommand changeName = new RenameChildCommand(clicked, promenjenaVrednost + "(" + mnogoJasno + ")");
            ApplicationFramework.getInstance().getGui().getCommandManager().addCommand(changeName);
        }
        else
        {
            AbstractCommand changeName = new RenameChildCommand(clicked, promenjenaVrednost);
            ApplicationFramework.getInstance().getGui().getCommandManager().addCommand(changeName);
        }
    }
}
