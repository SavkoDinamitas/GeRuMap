package raf.dsw.gerumap.app.gui.swing.view;

import javafx.util.Pair;
import lombok.Getter;
import lombok.Setter;
import raf.dsw.gerumap.app.gui.swing.controller.MindMapPanelListener;
import raf.dsw.gerumap.app.mapRepository.implementation.MindMap;
import raf.dsw.gerumap.app.mapRepository.implementation.Pojam;
import raf.dsw.gerumap.app.observer.ISubscriber;
import raf.dsw.gerumap.app.observer.Notification;
import raf.dsw.gerumap.app.observer.NotificationType;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter

public class MindMapPanel extends JPanel implements ISubscriber {

    private MindMap map;
    private String naziv;

    private double scale = 1.0;

    private JViewport viewport;

    private ScrollPaneMindMap jscrollpane;

    private List<Painter> painterList;

    private List<Painter> selectionModel = new ArrayList<>();

    private Rectangle2D pravougaonikSelekcije;
    private Point p1;

    private Point p2;

    private Dimension pocetnaDimenzija;

    private boolean centralizovan = false;

    private boolean ovoJeLosJezik = false;

    private List<Pojam> centralizovaniPojmovi = new ArrayList<>();

    public MindMapPanel(MindMap map)
    {
        super();
        this.map = map;
        this.naziv = map.getName();
        this.map.addSubscriber(this);
        this.setBackground(Color.WHITE);
        painterList = new ArrayList<Painter>();
        addMouseListener(new MindMapPanelListener(this));
        addMouseMotionListener(new MindMapPanelListener(this));
        addMouseWheelListener(new MindMapPanelListener(this));
        pocetnaDimenzija = new Dimension(2000, 2000);
        setPreferredSize(pocetnaDimenzija);
    }

    @Override
    public void update(Notification notification) {
        if(notification.getNotificationType() == NotificationType.SHOWMAP){
            for(var x : map.getChildren()){
                PojamPainter pp = new PojamPainter();
                pp.setPojam((Pojam) x);
                painterList.add(pp);
                ((Pojam) x).addSubscriber(this);
                for(var xd : ((Pojam) x).getVezeList()){
                    boolean flag = false;
                    for(var p : painterList){
                        if(p instanceof PojamPainter && ((PojamPainter) p).getPojam().equals(x)){
                            flag = true;
                        }
                    }
                    if(flag){
                        VezaPainter vp = new VezaPainter();
                        vp.setFrom((Pojam) x);
                        vp.setTo(xd);
                        vp.setStroke(((Pojam) x).getStrokic());
                        vp.setColor(((Pojam) x).getColor());
                        painterList.add(vp);
                    }
                }
            }
        }
        //za ucitavanje mind mape
        if(notification.getNotificationType() == NotificationType.SHOWVEZE){
            for(var x : map.getChildren()){
                for(var xd : ((Pojam) x).getVezeList()){
                    boolean flag = false;
                    for(var p : painterList){
                        if(p instanceof PojamPainter && ((PojamPainter) p).getPojam().equals(x)){
                            flag = true;
                        }
                    }
                    if(flag){
                        VezaPainter vp = new VezaPainter();
                        vp.setFrom((Pojam) x);
                        vp.setTo(xd);
                        vp.setStroke(((Pojam) x).getStrokic());
                        vp.setColor(((Pojam) x).getColor());
                        painterList.add(vp);
                    }
                }
            }
        }

        if(notification.getNotificationType() == NotificationType.DELETEPOJAM){
            Painter p = new PojamPainter();
            for(var komedija : painterList){
                if(komedija instanceof PojamPainter){
                    PojamPainter xd = (PojamPainter) komedija;
                    if(xd.getPojam().equals(notification.getObjectOfNotification())){
                        p = xd;
                    }
                }
            }
            painterList.remove(p);

            for(var x : selectionModel){
                if(x instanceof PojamPainter && ((PojamPainter) x).getPojam().equals(notification.getObjectOfNotification())){
                    selectionModel.remove(x);
                    break;
                }
            }
            /*List<Painter> veze = new ArrayList<>();
            for(var xd : painterList){
                if(xd instanceof VezaPainter){
                    if(((VezaPainter) xd).getTo().equals(notification.getObjectOfNotification()) ||
                    ((VezaPainter) xd).getFrom().equals(notification.getObjectOfNotification())){
                        veze.add(xd);
                    }
                }
            }
            for(var x : veze){
                painterList.remove(x);
            }*/
        }

        if(notification.getNotificationType() == NotificationType.ADDPOJAM && ((Pojam)notification.getObjectOfNotification()).getLokacija() != null){
            Pojam pojam = (Pojam) notification.getObjectOfNotification();
            pojam.addSubscriber(this);
            PojamPainter pp = new PojamPainter();
            pp.setPojam(pojam);
            painterList.add(pp);

//            for(var x : pojam.getVezeList()){
//                VezaPainter vp = new VezaPainter();
//                vp.setFrom(pojam);
//                vp.setTo(x);
//                vp.setStroke(2.7f);
//                vp.setColor(Color.BLACK);
//                painterList.add(vp);
//            }
        }

        if(notification.getNotificationType() == NotificationType.DODATAVEZA){
            Pair<Pojam, Pojam> par = (Pair<Pojam, Pojam>) notification.getObjectOfNotification();
            if(!par.getValue().getVezeList().contains(par.getKey())){
                VezaPainter vp = new VezaPainter();
                //default vrednosti
                vp.setFrom(par.getKey());
                vp.setTo(par.getValue());
                vp.setColor(par.getKey().getColor());
                painterList.add(vp);
            }
        }

        if(notification.getNotificationType() == NotificationType.OBRISANAVEZA){
            Pair<Pojam, Pojam> par = (Pair<Pojam, Pojam>) notification.getObjectOfNotification();
            for(var x : painterList){
                if(x instanceof VezaPainter){
                    VezaPainter vp = (VezaPainter) x;
                    if((vp.getFrom().equals(par.getKey()) && vp.getTo().equals(par.getValue())) ||
                            (vp.getFrom().equals(par.getValue()) && vp.getTo().equals(par.getKey()))){
                        painterList.remove(vp);
                        break;
                    }
                }
            }

            for (var x : selectionModel){
                if(x instanceof VezaPainter){
                    VezaPainter vp = (VezaPainter) x;
                    if((vp.getFrom().equals(par.getKey()) && vp.getTo().equals(par.getValue())) ||
                            (vp.getFrom().equals(par.getValue()) && vp.getTo().equals(par.getKey()))){
                        selectionModel.remove(vp);
                        break;
                    }
                }
            }
        }

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        //zoom
        g2.scale(scale, scale);
        //viewport.setSize(new Dimension((int)(viewport.getPreferredSize().getWidth() * scale),(int) (viewport.getPreferredSize().getHeight() * scale)));



        for(var p : painterList){
            p.paint(g2);
        }

        for(var x : selectionModel){
            if(x instanceof PojamPainter){
                float dash1[] = {10.0f};
                BasicStroke dashed = new BasicStroke(1.0f,
                        BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f);
                g2.setStroke(dashed);
                g2.setColor(Color.blue);
                g2.drawRect(((PojamPainter)x).getPojam().getLokacija().x - Pojam.getWidth()/2, ((PojamPainter)x).getPojam().getLokacija().y - Pojam.getHeight()/2, Pojam.getWidth(), Pojam.getHeight());
            }
            else {
                //System.out.println("Usao sam");
                float dash1[] = {10.0f};
                BasicStroke dashed = new BasicStroke(1.0f,
                        BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f);
                g2.setStroke(dashed);
                g2.setColor(Color.blue);
                int X = Math.min(((VezaPainter) x).getFrom().getLokacija().x, ((VezaPainter) x).getTo().getLokacija().x);
                int Y = Math.min(((VezaPainter) x).getFrom().getLokacija().y, ((VezaPainter) x).getTo().getLokacija().y);
                int width = Math.abs(((VezaPainter) x).getFrom().getLokacija().x - ((VezaPainter) x).getTo().getLokacija().x);
                int height = Math.abs(((VezaPainter) x).getFrom().getLokacija().y - ((VezaPainter) x).getTo().getLokacija().y);
                g2.drawRect(X, Y, width, height);
            }
        }
        g2.scale(1, 1);
        if(pravougaonikSelekcije != null)
        {
            float dash[] = {5.0f};
            BasicStroke dashed = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f);
            g2.setStroke(dashed);
            g2.setColor(Color.GRAY);
            g2.draw(pravougaonikSelekcije);
        }
        if(p1 != null && p2 != null)
        {
            g2.setStroke(new BasicStroke(2.7f));
            g2.drawLine(p1.x, p1.y, p2.x, p2.y);
        }
        //System.out.println("Komedija");
    }

    public BufferedImage createImage()
    {
        // Ovo radi ali bude ogromne beline
        /*
        BufferedImage bufferedImage = new BufferedImage(pocetnaDimenzija.width, pocetnaDimenzija.height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bufferedImage.createGraphics();
        this.paint(g);
        g.dispose();
        return bufferedImage;
        */
        int desiredWidth = Integer.MIN_VALUE;
        int desiredHeight = Integer.MIN_VALUE;
        int minWidth = Integer.MAX_VALUE;
        int minHeight = Integer.MAX_VALUE;
        if(!ovoJeLosJezik)
        {
            for(Painter p : painterList)
            {
                if(p instanceof PojamPainter)
                {
                    if(desiredWidth < ((PojamPainter) p).getPojam().getLokacija().x + Pojam.getWidth())
                    {
                        desiredWidth = ((PojamPainter) p).getPojam().getLokacija().x + Pojam.getWidth();
                    }
                    if(desiredHeight < ((PojamPainter) p).getPojam().getLokacija().y + Pojam.getHeight())
                    {
                        desiredHeight = ((PojamPainter) p).getPojam().getLokacija().y + Pojam.getHeight();
                    }
                }
            }
            for (Painter p : painterList){
                if(p instanceof PojamPainter){
                    if(minWidth > ((PojamPainter) p).getPojam().getLokacija().x - Pojam.getWidth())
                        minWidth = ((PojamPainter) p).getPojam().getLokacija().x - Pojam.getWidth();
                    if(minHeight > ((PojamPainter) p).getPojam().getLokacija().y - Pojam.getHeight())
                        minHeight = ((PojamPainter) p).getPojam().getLokacija().y - Pojam.getHeight();
                }
            }
        }
        else
        {
            Pojam centralniPojam = null;
            for(var x : painterList)
            {
                if(x instanceof PojamPainter)
                {
                    if(((PojamPainter) x).getPojam().getLokacija().x == getPocetnaDimenzija().width / 2 && ((PojamPainter) x).getPojam().getLokacija().y == getPocetnaDimenzija().height / 2)
                    {
                        centralniPojam = ((PojamPainter) x).getPojam();
                        break;
                    }
                }
            }
            desiredWidth = centralniPojam.getLokacija().x + Pojam.getWidth();
            desiredHeight = centralniPojam.getLokacija().y + Pojam.getHeight();
            minWidth = centralniPojam.getLokacija().x - Pojam.getWidth();
            minHeight = centralniPojam.getLokacija().y - Pojam.getHeight();
            // Sada uporedjujemo samo sa pojmovima koji su povezani sa centralnim
            for(Pojam x : centralizovaniPojmovi)
            {
                if(desiredWidth < x.getLokacija().x + Pojam.getWidth())
                {
                    desiredWidth = x.getLokacija().x + Pojam.getWidth();
                }
                if(desiredHeight < x.getLokacija().y + Pojam.getHeight())
                {
                    desiredHeight = x.getLokacija().y + Pojam.getHeight();
                }
            }
            for(Pojam x : centralizovaniPojmovi)
            {
                if(minWidth > x.getLokacija().x - Pojam.getWidth())
                    minWidth = x.getLokacija().x - Pojam.getWidth();
                if(minHeight > x.getLokacija().y - Pojam.getHeight())
                    minHeight = x.getLokacija().y - Pojam.getHeight();
            }
        }

        // ako nema nista na mindmap-i
        if(desiredWidth == Integer.MIN_VALUE)
        {
            return null;
        }
        minWidth = Math.max(0, minWidth);
        minHeight = Math.max(0, minHeight);
        BufferedImage bufferedImage = new BufferedImage(desiredWidth, desiredHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bufferedImage.createGraphics();
        this.paint(g);
        g.dispose();
        minWidth = Math.max(0, minWidth);
        minHeight = Math.max(0, minHeight);
        BufferedImage bufferedImageCropped = bufferedImage.getSubimage(minWidth, minHeight, desiredWidth - minWidth, desiredHeight - minHeight);
        return bufferedImageCropped;
    }

    public boolean samoNeRadiGetterIzgleda()
    {
        return centralizovan;
    }

}
