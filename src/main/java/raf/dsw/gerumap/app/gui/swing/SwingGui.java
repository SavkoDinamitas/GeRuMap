package raf.dsw.gerumap.app.gui.swing;

import lombok.Getter;
import lombok.Setter;
import raf.dsw.gerumap.app.core.ApplicationFramework;
import raf.dsw.gerumap.app.core.Gui;
import raf.dsw.gerumap.app.errorHandler.Message;
import raf.dsw.gerumap.app.errorHandler.MessageGenerator;
import raf.dsw.gerumap.app.errorHandler.MessageType;
import raf.dsw.gerumap.app.gui.swing.commands.CommandManager;
import raf.dsw.gerumap.app.gui.swing.view.MainFrame;
import raf.dsw.gerumap.app.observer.Notification;

import javax.swing.*;
@Getter
@Setter
public class SwingGui extends JFrame implements Gui
{
    private CommandManager commandManager;
    private MainFrame mainFrame;
    public SwingGui()
    {

    }
    @Override
    public void start()
    {
        //pravi se MainFrame
        mainFrame = MainFrame.getInstance();
        mainFrame.setVisible(true);
        ApplicationFramework.getInstance().getMessageGenerator().addSubscriber(this);
        commandManager = new CommandManager();
        disableRedoCommand();
        disableUndoCommand();
    }

    @Override
    public void update(Notification notification) {
        if(((Message)notification.getObjectOfNotification()).getMessageType() == MessageType.INFO){
            JOptionPane.showMessageDialog(MainFrame.getInstance(), ((Message)notification.getObjectOfNotification()).getSadrzaj(), "Info", JOptionPane.INFORMATION_MESSAGE);
        }
        else if(((Message)notification.getObjectOfNotification()).getMessageType() == MessageType.WARNING){
            JOptionPane.showMessageDialog(MainFrame.getInstance(), ((Message)notification.getObjectOfNotification()).getSadrzaj(), "Warning", JOptionPane.WARNING_MESSAGE);
        }
        else if(((Message)notification.getObjectOfNotification()).getMessageType() == MessageType.ERROR){
            JOptionPane.showMessageDialog(MainFrame.getInstance(), ((Message)notification.getObjectOfNotification()).getSadrzaj(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void disableUndoCommand(){
        MainFrame.getInstance().getActionManager().getUndoAction().setEnabled(false);
    }

    public void disableRedoCommand(){
        MainFrame.getInstance().getActionManager().getRedoAction().setEnabled(false);
    }

    public void enableUndoCommand(){
        MainFrame.getInstance().getActionManager().getUndoAction().setEnabled(true);
    }

    public void enableRedoCommand(){
        MainFrame.getInstance().getActionManager().getRedoAction().setEnabled(true);
    }
}
