package raf.dsw.gerumap.app.gui.swing.view;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;

@Getter
@Setter
public class ScrollPaneMindMap extends JScrollPane {
    private MindMapPanel tvojaKeva;

    public ScrollPaneMindMap(MindMapPanel m) {
        super(m);
        tvojaKeva = m;
        setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        setViewportView(tvojaKeva);
        tvojaKeva.setViewport(this.getViewport());
    }
}
