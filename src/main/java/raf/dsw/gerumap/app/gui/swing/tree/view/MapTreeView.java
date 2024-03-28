package raf.dsw.gerumap.app.gui.swing.tree.view;

import raf.dsw.gerumap.app.gui.swing.tree.controller.DoubleClickListener;
import raf.dsw.gerumap.app.gui.swing.tree.controller.MapTreeCellEditor;
import raf.dsw.gerumap.app.gui.swing.tree.controller.MapTreeSelectionListener;
import raf.dsw.gerumap.app.gui.swing.tree.controller.RightClickListener;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

// Klasa koja efektivno predstavlja nas Tree u aplikaciji
public class MapTreeView extends JTree {
    public MapTreeView(DefaultTreeModel defaultTreeModel){
        super();
        setModel(defaultTreeModel);
        MapTreeCellRenderer ruTreeCellRenderer = new MapTreeCellRenderer();
        addTreeSelectionListener(new MapTreeSelectionListener());
        setCellEditor(new MapTreeCellEditor(this, ruTreeCellRenderer));
        setCellRenderer(ruTreeCellRenderer);
        setEditable(true);
        addMouseListener(new RightClickListener());
        addMouseListener(new DoubleClickListener());
        setToggleClickCount(0);
    }




}
