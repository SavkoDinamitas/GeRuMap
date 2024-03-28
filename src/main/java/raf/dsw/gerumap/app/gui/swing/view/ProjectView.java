package raf.dsw.gerumap.app.gui.swing.view;

import lombok.Getter;
import lombok.Setter;
import raf.dsw.gerumap.app.mapRepository.composite.MapNode;
import raf.dsw.gerumap.app.mapRepository.implementation.MindMap;
import raf.dsw.gerumap.app.mapRepository.implementation.Pojam;
import raf.dsw.gerumap.app.mapRepository.implementation.Project;
import raf.dsw.gerumap.app.mapRepository.implementation.ProjectExplorer;
import raf.dsw.gerumap.app.observer.ISubscriber;
import raf.dsw.gerumap.app.observer.Notification;
import raf.dsw.gerumap.app.observer.NotificationType;
import raf.dsw.gerumap.app.gui.swing.stateDepartment.StateManager;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

@Getter
@Setter
public class ProjectView extends JPanel implements ISubscriber {

    private JTabbedPane tabbedPane;
    private JLabel imeProjekta;
    private JLabel imeAutora;

    private MindMapBar mindMapBar;

    private Project projekat;

    private StateManager stateManager;

    private List<MindMapPanel> allPanels = new ArrayList<>();

    public ProjectView(Project projekat)
    {
        initialise();
        this.projekat = projekat;
        this.projekat.addSubscriber(this);
        ((ProjectExplorer)this.projekat.getParent()).addSubscriber(this);
        stateManager = new StateManager();
    }

    public ProjectView(){
        initialise();
    }

    public void initialise()
    {
        tabbedPane = new JTabbedPane();
        imeProjekta = new JLabel("Projekat...");
        imeAutora = new JLabel("Autor...");
        imeAutora.setHorizontalTextPosition(SwingConstants.CENTER);
        imeProjekta.setHorizontalTextPosition(SwingConstants.CENTER);
        setLayout(new BorderLayout());
        setAlignmentX(CENTER_ALIGNMENT);
        JPanel whenThe = new JPanel(new GridLayout(2, 1));
        whenThe.setAlignmentX(CENTER_ALIGNMENT);
        imeProjekta.setHorizontalAlignment(SwingConstants.CENTER);
        imeAutora.setHorizontalAlignment(SwingConstants.CENTER);
        whenThe.add(imeProjekta);
        whenThe.add(imeAutora);
        add(whenThe, BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);
        //toolbar
        mindMapBar = new MindMapBar();
        add(mindMapBar, BorderLayout.EAST);
        mindMapBar.setVisible(false);
        imeProjekta.setVisible(false);
        imeAutora.setVisible(false);
        tabbedPane.setVisible(false);
    }

    public void createTabs(List<ScrollPaneMindMap> tabNames)
    {
        // tabbedPane.removeAll();
        for(var s : tabNames)
        {
            tabbedPane.addTab(s.getTvojaKeva().getNaziv(), s);
        }
    }

    public void showComponents()
    {
        imeProjekta.setVisible(true);
        imeAutora.setVisible(true);
        tabbedPane.setVisible(true);
        mindMapBar.setVisible(true);

    }

    public void hideComponents()
    {
        imeProjekta.setVisible(false);
        imeAutora.setVisible(false);
        tabbedPane.setVisible(false);
        mindMapBar.setVisible(false);
    }

    @Override
    public void update(Notification notification) {

        if(notification.getNotificationType() == NotificationType.SHOW)
        {
            MainFrame.getInstance().getSplitPane().setRightComponent(this);
            Project currentProject = (Project) notification.getObjectOfNotification();
            if(tabbedPane.getTabCount() > 0)
            {
                showComponents();
                imeProjekta.setText(currentProject.getName());
                if(currentProject.getAutor() == null || currentProject.getAutor().equals(""))
                {
                    imeAutora.setText("Nema autora");
                }
                else
                {
                    imeAutora.setText(currentProject.getAutor());
                }
                return;
            }

            List<ScrollPaneMindMap> tabNames = new ArrayList<>();

            for(MapNode m : currentProject.getChildren())
            {
                ((MindMap)m).addSubscriber(this);
                MindMapPanel xdd = new MindMapPanel((MindMap) m);
                xdd.update(new Notification(m, NotificationType.SHOWMAP));
                allPanels.add(xdd);
                projekat.addSubscriber(xdd);
                tabNames.add(new ScrollPaneMindMap(xdd));
            }

            createTabs(tabNames);
            imeProjekta.setText(currentProject.getName());
            if(currentProject.getAutor() == null || currentProject.getAutor().equals(""))
            {
                imeAutora.setText("Nema autora");
            }
            else
            {
                imeAutora.setText(currentProject.getAutor());
            }
            showComponents();
        }
        else if(notification.getNotificationType() == NotificationType.ADD)
        {
            MindMapPanel mindMapPanel = new MindMapPanel((MindMap)notification.getObjectOfNotification());
            for (var c : allPanels){
                if(c.getMap().equals(notification.getObjectOfNotification())){
                    mindMapPanel = c;
                }
            }

            allPanels.add(mindMapPanel);
            projekat.addSubscriber(mindMapPanel);
            ((MindMap)notification.getObjectOfNotification()).addSubscriber(this);
            tabbedPane.addTab(mindMapPanel.getMap().getName(), new ScrollPaneMindMap(mindMapPanel));
        }

        else if(notification.getNotificationType() == NotificationType.DELETE)
        {
            if(notification.getObjectOfNotification() instanceof Project)
            {
                // MainFrame.getInstance().getMapTree().getSelectedNode().getMapNode() instanceof Project &&
                if( MainFrame.getInstance().getMapTree().getSelectedNode().getMapNode().equals(projekat))
                {
                    hideComponents();
                }
                return;
            }

            for(int i = 0; i < tabbedPane.getTabCount(); i++)
            {
                MindMapPanel mindMapPanel = ((ScrollPaneMindMap) tabbedPane.getComponentAt(i)).getTvojaKeva();
                if(mindMapPanel.getNaziv().equals(((MindMap)notification.getObjectOfNotification()).getName()))
                {
                    tabbedPane.remove(i);
                    return;
                }
            }
        }

        else if(notification.getNotificationType() == NotificationType.RENAME)
        {
            if(notification.getObjectOfNotification() instanceof Project)
            {
                Project project = ((Project)notification.getObjectOfNotification());
                imeProjekta.setText(project.getName());
                if(project.getAutor() == null || project.getAutor().equals(""))
                {
                    imeAutora.setText("Nema autora");
                }
                else
                {
                    imeAutora.setText(project.getAutor());
                }
            }
            else if(notification.getObjectOfNotification() instanceof MindMap)
            {
                MindMap mindMap = (MindMap) notification.getObjectOfNotification();
                for(int i = 0; i < tabbedPane.getTabCount(); i++)
                {
                    MindMapPanel mindMapPanel = ((ScrollPaneMindMap) tabbedPane.getComponentAt(i)).getTvojaKeva();
                    if(mindMapPanel.getNaziv().equals(notification.getRenameKomedija()))
                    {
                        mindMapPanel.setNaziv(mindMap.getName());
                        tabbedPane.setTitleAt(i, mindMap.getName());
                        return;
                    }
                }
            }
        }

        else
        {
           // System.out.println("Samo umremo");
        }

        /*
        MindMapPanel xdd = (MindMapPanel) tabbedPane.getComponentAt(0);
        System.out.println(xdd == null);
        System.out.println(xdd.getNaziv());
        */


    }

    public void startDodavanjePojmaState(){
        stateManager.setDodavanjePojmaState();
    }

    public void startDodavanjeVezeState(){
        stateManager.setDodavanjeVezeState();
    }

    public void startSelekcijaState(){
        stateManager.setSelekcijaState();
    }

    public void startBrisanjeState(){
        stateManager.setBrisanjeState();
    }

    public void startMenjanjeStilaState(){
        stateManager.setMenjanjeStilaState();
    }

    public void startPromenaImenaPojmaState(){
        stateManager.setPromenaImenaPojmaState();
    }

    public void startPomeranjeState(){
        stateManager.setPomeranjeState();
    }

    public void startUvelicavanjeState(){stateManager.setUvelicavanjeState();}

}
