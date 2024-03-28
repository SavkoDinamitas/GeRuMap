package raf.dsw.gerumap.app.gui.swing.commands.implementation;

import com.sun.tools.javac.Main;
import raf.dsw.gerumap.app.gui.swing.commands.AbstractCommand;
import raf.dsw.gerumap.app.gui.swing.tree.model.MapTreeItem;
import raf.dsw.gerumap.app.gui.swing.view.MainFrame;
import raf.dsw.gerumap.app.gui.swing.view.PojamPainter;
import raf.dsw.gerumap.app.mapRepository.composite.MapNode;
import raf.dsw.gerumap.app.mapRepository.composite.MapNodeComposite;

import javax.swing.*;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import java.util.Enumeration;

public class DeleteChildCommand extends AbstractCommand {

    private MapTreeItem child;

    private MapNode parent;

    public DeleteChildCommand(MapTreeItem child){
        this.child = child;
        parent = child.getMapNode().getParent();
    }

    @Override
    public void doCommand() {
        ((MapNodeComposite) parent).deleteChild(child.getMapNode());
        MainFrame.getInstance().getMapTree().vratiModel().removeNodeFromParent(child);
        SwingUtilities.updateComponentTreeUI(MainFrame.getInstance().getMapTree().vratiTreeView());
        //resavanje broja na novom projektu
        ((MapNodeComposite)(child.getMapNode()).getParent()).setNomenklatura(((MapNodeComposite)(child.getMapNode()).getParent()).getNomenklatura() + 1);
    }

    @Override
    public void undoCommand() {
        ((MapNodeComposite) parent).addChild(child.getMapNode());
        trazenjeCvora(MainFrame.getInstance().getMapTree().vratiModel(), (MapTreeItem)MainFrame.getInstance().getMapTree().vratiModel().getRoot(), parent);
        //otvaranje stabla
        MainFrame.getInstance().getMapTree().expandTree();
        ((MapNodeComposite)(child.getMapNode()).getParent()).setNomenklatura(((MapNodeComposite)(child.getMapNode()).getParent()).getNomenklatura() - 1);
    }

    private void trazenjeCvora(TreeModel treeModel, MapTreeItem otac, MapNode parent)
    {
        if(otac.getMapNode().equals(parent))
        {
            //nasli smo ga u stablu
            otac.add(child);
            return;
        }
        for(int i = 0; i < treeModel.getChildCount(otac); i++)
        {
            MapTreeItem dete = (MapTreeItem)treeModel.getChild(otac, i);
            MapNode xdd = dete.getMapNode();
            if(xdd.equals(parent))
            {
                //nasli smo ga u stablu
                dete.add(child);
                return;
            }
            if(!treeModel.isLeaf(dete))
            {
                trazenjeCvora(treeModel, dete, parent);
            }
        }
    }
}
