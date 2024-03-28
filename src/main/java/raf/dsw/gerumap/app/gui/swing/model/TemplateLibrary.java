package raf.dsw.gerumap.app.gui.swing.model;

import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;


public class TemplateLibrary
{
    private static TemplateLibrary instance;

    public List<Template> templateList;

    private TemplateLibrary()
    {
        templateList = new ArrayList<>();
    }
    public static synchronized TemplateLibrary getInstance()
    {
        if(instance == null)
        {
            instance = new TemplateLibrary();
        }
        return instance;
    }


}
