package raf.dsw.gerumap.app.gui.swing.view;

import lombok.Getter;
import lombok.Setter;
import raf.dsw.gerumap.app.core.ApplicationFramework;
import raf.dsw.gerumap.app.errorHandler.Logger;
import raf.dsw.gerumap.app.errorHandler.LoggerFactory;
import raf.dsw.gerumap.app.gui.swing.controller.ActionManager;
import raf.dsw.gerumap.app.gui.swing.tree.MapTree;
import raf.dsw.gerumap.app.gui.swing.tree.MapTreeImplementation;
import raf.dsw.gerumap.app.gui.swing.tree.model.MapTreeItem;
import raf.dsw.gerumap.app.mapRepository.implementation.Project;

import java.awt.event.MouseEvent;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter

// Klasa koju inicijalizuje nas GUI i sadrzi sva polja potrebna za potpunu funkcionalnost nase aplikacije (centar cele aplikacije)
public class MainFrame extends JFrame {
    private static MainFrame instance;
    private ActionManager actionManager;
    private JMenuBar menu;
    private JToolBar toolBar;
    private MapTree mapTree;

    private JSplitPane splitPane;

    private List<ProjectView> projectViewList;

    private ProjectView currentProjectView;

    private MainFrame()
    {

    }
    private void initialise()
    {
        actionManager = new ActionManager();
        mapTree = new MapTreeImplementation();
        projectViewList = new ArrayList<>();
        initialiseGUI();
    }
    private void initialiseGUI()
    {
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        setSize(screenWidth / 2, screenHeight / 2);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("GeRuMap App");

        menu = new MyMenuBar();
        setJMenuBar(menu);

        toolBar = new Toolbar();
        add(toolBar, BorderLayout.NORTH);

        JTree projectExplorer = mapTree.generateTree(ApplicationFramework.getInstance().getMapRepository().getProjectExplorer());
        JPanel desktop = new JPanel();

        // Leva strana SplitPane-a
        JScrollPane scrollPane = new JScrollPane(projectExplorer);
        scrollPane.setMinimumSize(new Dimension(200, 150));
        // Desna strana SplitPane-a
        ProjectView projectView = new ProjectView();

        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollPane, projectView);
        getContentPane().add(splitPane, BorderLayout.CENTER);
        splitPane.setDividerLocation(250);
        splitPane.setOneTouchExpandable(true);
    }

    public ProjectView getProjectView(Project project)
    {
        for(ProjectView p : projectViewList)
        {
            if(p.getProjekat().equals(project))
            {
                return p;
            }
        }
        return null;
    }

    public static MainFrame getInstance()
    {
        if(instance == null)
        {
            instance = new MainFrame();
            instance.initialise();
        }
        return instance;
    }
}
