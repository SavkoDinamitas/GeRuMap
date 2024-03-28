package raf.dsw.gerumap.app.gui.swing.controller;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

// Klasa koja sadrzi instance svih akcija
public class ActionManager
{
    private ExitAction exitAction;
    private NewProjectAction newProjectAction;
    private EditAction editAction;
    private InfoAction infoAction;
    private DeleteAction deleteAction;
    private ChangeAuthorNameAction changeAuthorAction;

    private EditStyleAction editStyleAction;

    private NewPojamAction newPojamAction;

    private  NewVezaAction newVezaAction;

    private RenamePojamAction renamePojamAction;

    private SelectionAction selectionAction;

    private  UkloniAction ukloniAction;

    private MoveAction moveAction;

    private ZoomAction zoomAction;

    private UndoAction undoAction;

    private RedoAction redoAction;

    private OpenAction openAction;

    private SaveAction saveAction;

    private LoadTemplateAction loadTemplateAction;
    private SaveTemplateAction saveTemplateAction;
    private CaptureMindMapAction captureMindMapAction;

    private CenterPojamAction centerPojamAction;
    public ActionManager()
    {
        initialiseActions();
    }

    private void initialiseActions()
    {
        exitAction = new ExitAction();
        newProjectAction = new NewProjectAction();
        editAction = new EditAction();
        infoAction = new InfoAction();
        deleteAction = new DeleteAction();
        changeAuthorAction = new ChangeAuthorNameAction();
        editStyleAction = new EditStyleAction();
        newPojamAction = new NewPojamAction();
        newVezaAction = new NewVezaAction();
        renamePojamAction = new RenamePojamAction();
        selectionAction = new SelectionAction();
        ukloniAction = new UkloniAction();
        moveAction = new MoveAction();
        zoomAction = new ZoomAction();
        undoAction = new UndoAction();
        redoAction = new RedoAction();
        saveAction = new SaveAction();
        openAction = new OpenAction();
        saveTemplateAction = new SaveTemplateAction();
        loadTemplateAction = new LoadTemplateAction();
        captureMindMapAction = new CaptureMindMapAction();
        centerPojamAction = new CenterPojamAction();
    }
}
