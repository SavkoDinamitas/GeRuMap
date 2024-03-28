package raf.dsw.gerumap.app.gui.swing.tree;

import lombok.Getter;
import lombok.Setter;
import raf.dsw.gerumap.app.core.ApplicationFramework;
import raf.dsw.gerumap.app.errorHandler.MessageType;
import raf.dsw.gerumap.app.gui.swing.commands.AbstractCommand;
import raf.dsw.gerumap.app.gui.swing.commands.implementation.AddChildCommand;
import raf.dsw.gerumap.app.gui.swing.commands.implementation.DeleteChildCommand;
import raf.dsw.gerumap.app.gui.swing.tree.model.MapTreeItem;
import raf.dsw.gerumap.app.gui.swing.tree.view.MapTreeView;
import raf.dsw.gerumap.app.gui.swing.view.MainFrame;
import raf.dsw.gerumap.app.gui.swing.view.PojamPainter;
import raf.dsw.gerumap.app.mapRepository.composite.MapNode;
import raf.dsw.gerumap.app.mapRepository.composite.MapNodeComposite;
import raf.dsw.gerumap.app.mapRepository.factory.FactoryUtils;
import raf.dsw.gerumap.app.mapRepository.factory.MapNodeFactory;
import raf.dsw.gerumap.app.mapRepository.implementation.MindMap;
import raf.dsw.gerumap.app.mapRepository.implementation.Pojam;
import raf.dsw.gerumap.app.mapRepository.implementation.Project;
import raf.dsw.gerumap.app.mapRepository.implementation.ProjectExplorer;
import raf.dsw.gerumap.app.observer.Notification;
import raf.dsw.gerumap.app.observer.NotificationType;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import java.awt.desktop.AppForegroundListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

@Getter
@Setter

// Klasa koja sadrzi sva polja i atribute potrebna za kompletnu funkcionalnost apstraktnog pojma MapTree
public class MapTreeImplementation implements MapTree{
    private MapTreeView treeView;
    private DefaultTreeModel treeModel;

    @Override
    public MapTreeView generateTree(ProjectExplorer projectExplorer) {
        MapTreeItem root = new MapTreeItem(projectExplorer);
        treeModel = new DefaultTreeModel(root);
        treeView = new MapTreeView(treeModel);
        return treeView;
    }

    @Override
    public MapTreeItem getSelectedNode() {
        return (MapTreeItem) treeView.getLastSelectedPathComponent();
    }

    private MapNode createChild(MapNode parent) {
        // za Anu :)
        MapNodeFactory totalnoBeskorisnaStvar = FactoryUtils.getFactory(parent);
        return totalnoBeskorisnaStvar.getNode(parent);
    }

    @Override
    public void addChild(MapTreeItem parent) {
        if (!(parent.getMapNode() instanceof MapNodeComposite)){
            ApplicationFramework.getInstance().getMessageGenerator().generateMessage(MessageType.INFO, "Ne moze se dodati dete na cvor tipa Pojam");
            return;
        }


        MapNode child = createChild(parent.getMapNode());
        AbstractCommand addChild = new AddChildCommand(parent, child);
        ApplicationFramework.getInstance().getGui().getCommandManager().addCommand(addChild);
    }

    public void deleteChild(MapTreeItem selected){
        /*//brisanje deteta iz naseg modela
        ((MapNodeComposite)(selected.getMapNode()).getParent()).deleteChild(selected.getMapNode());
        //brisanje deteta iz ugradjenog view modela
        treeModel.removeNodeFromParent(selected);
        SwingUtilities.updateComponentTreeUI(treeView);
        //resavanje broja na novom projektu
        ((MapNodeComposite)(selected.getMapNode()).getParent()).setNomenklatura(((MapNodeComposite)(selected.getMapNode()).getParent()).getNomenklatura() + 1);*/
        AbstractCommand deleteCommand = new DeleteChildCommand(selected);
        ApplicationFramework.getInstance().getGui().getCommandManager().addCommand(deleteCommand);
    }

    public void loadProject(Project p){
        p.setParent(ApplicationFramework.getInstance().getMapRepository().getProjectExplorer());
        p.setName("Project" + (((MapNodeComposite)p.getParent()).getChildren().size() + ApplicationFramework.getInstance().getMapRepository().getProjectExplorer().getNomenklatura()));
        for(var x : p.getChildren()){
            x.setParent(p);
            for(var y : ((MapNodeComposite)x).getChildren()){
                y.setParent(x);
            }
        }
        MapTreeItem root = (MapTreeItem) MainFrame.getInstance().getMapTree().vratiModel().getRoot();
        MapTreeItem projekat = new MapTreeItem(p);
        root.add(projekat);
        for(var x : p.getChildren()){
            MapTreeItem mindMap = new MapTreeItem(x);
            projekat.add(mindMap);
            for(var y : ((MapNodeComposite)x).getChildren()){
                mindMap.add(new MapTreeItem(y));
            }
        }


        // Resavanje duplikata kod pojmova
        for(var x : p.getChildren())
        {
            MindMap mindMap = (MindMap) x;
            List<Pojam> originalPojmoviList = new ArrayList<>();
            for(var y : mindMap.getChildren())
            {
                Pojam pojam = (Pojam) y;
                originalPojmoviList.add(pojam);
            }
            // Gleda originals
            for(int i = 0; i < originalPojmoviList.size(); i++)
            {
                // Gleda vezeList originals-a
                for(int j = 0; j < (originalPojmoviList.get(i)).getVezeList().size(); j++)
                {
                    // Trazi isto ime iz originals i trenutne kopije koju posmatramo
                    for(int k = 0; k < originalPojmoviList.size(); k++)
                    {
                        if((originalPojmoviList.get(i)).getVezeList().get(j).getName().equals(originalPojmoviList.get(k).getName()))
                        {
                            (originalPojmoviList.get(i)).getVezeList().set(j, originalPojmoviList.get(k));
                            break;
                        }
                    }
                }
            }
        }

        /*
        List<Pojam> komedija = new ArrayList<>();
        //popravljanje masnih veza :)
        for(var x : p.getChildren()){
            for(var y : ((MapNodeComposite)x).getChildren()){
                Pojam pojam = (Pojam) y;
                //kopiranje liste
                komedija.clear();
                for(var pp : pojam.getVezeList()){
                    komedija.add(pp);
                }
                pojam.getVezeList().clear();
                for(var veza : komedija){
                    for(var isti : ((MapNodeComposite)x).getChildren()){
                        if(veza.getName().equals(isti.getName()))
                            pojam.getVezeList().add((Pojam) isti);
                        break;
                    }
                }
            }
        }
        */

        ApplicationFramework.getInstance().getMapRepository().getProjectExplorer().addChild(p);
        expandTree();
        SwingUtilities.updateComponentTreeUI(MainFrame.getInstance().getMapTree().vratiTreeView());
    }

    public void loadTemplate(MindMap map){
        MapTreeItem trenutni = MainFrame.getInstance().getMapTree().getSelectedNode();
        MindMap current = (MindMap) trenutni.getMapNode();

        for(var x : map.getChildren()){
            current.addChild(x);
            trenutni.add(new MapTreeItem(x));
            x.setParent(current);
        }

        // Resavanje duplikata kod pojmova
        List<Pojam> originalPojmoviList = new ArrayList<>();
        for(var y : current.getChildren())
        {
            Pojam pojam = (Pojam) y;
            originalPojmoviList.add(pojam);
        }
        // Gleda originals
        for(int i = 0; i < originalPojmoviList.size(); i++)
        {
            // Gleda vezeList originals-a
            for(int j = 0; j < (originalPojmoviList.get(i)).getVezeList().size(); j++)
            {
                // Trazi isto ime iz originals i trenutne kopije koju posmatramo
                for(int k = 0; k < originalPojmoviList.size(); k++)
                {
                    if((originalPojmoviList.get(i)).getVezeList().get(j).getName().equals(originalPojmoviList.get(k).getName()))
                    {
                        (originalPojmoviList.get(i)).getVezeList().set(j, originalPojmoviList.get(k));
                        break;
                    }
                }
            }
        }
        expandTree();
        current.notifySubscribers(new Notification(current, NotificationType.SHOWVEZE));
        ApplicationFramework.getInstance().getMessageGenerator().generateMessage(MessageType.INFO, "Uspesno ucitana mapa uma!");
    }

    @Override
    public DefaultTreeModel vratiModel() {
        return treeModel;
    }

    @Override
    public JTree vratiTreeView() {
        return treeView;
    }

    public void expandTree(){
        expandTree1(treeView);
    }

    private void expandTree1(JTree tree) {
        TreeNode root = (TreeNode) tree.getModel().getRoot();
        expandAll(tree, new TreePath(root));
    }

    private void expandAll(JTree tree, TreePath path) {
        TreeNode node = (TreeNode) path.getLastPathComponent();

        if (node.getChildCount() >= 0) {
            Enumeration enumeration = node.children();
            while (enumeration.hasMoreElements()) {
                TreeNode n = (TreeNode) enumeration.nextElement();
                TreePath p = path.pathByAddingChild(n);

                expandAll(tree, p);
            }
        }
        tree.expandPath(path);
    }
}
