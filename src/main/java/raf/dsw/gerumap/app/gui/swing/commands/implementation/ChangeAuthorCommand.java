package raf.dsw.gerumap.app.gui.swing.commands.implementation;

import raf.dsw.gerumap.app.gui.swing.commands.AbstractCommand;
import raf.dsw.gerumap.app.gui.swing.view.dialogs.EditAuthorDialog;
import raf.dsw.gerumap.app.mapRepository.implementation.Project;

public class ChangeAuthorCommand extends AbstractCommand {
    private Project project;

    private String lastName;

    public ChangeAuthorCommand(Project p){
        project = p;
        lastName = p.getAutor();
    }

    @Override
    public void doCommand() {
        project.setAutor(EditAuthorDialog.getInstance().getATx().getText());
    }

    @Override
    public void undoCommand() {
        project.setAutor(lastName);
    }
}
