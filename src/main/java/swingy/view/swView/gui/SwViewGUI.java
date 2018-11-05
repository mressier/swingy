package swingy.view.swView.gui;

import swingy.tools.SwGameAction;
import swingy.view.swView.SwView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public abstract class SwViewGUI extends SwView {

    private JFrame              currentFrame = null;

    protected ButtonClicker     inputHandler;

    /*
     * Constructor
     */
    protected  SwViewGUI() {
        this.inputHandler = new ButtonClicker();
    }

    /*
     * Public Methods
     */
    protected JFrame         initFrame(String title, int width, int height)
    {
        JFrame newFrame = new JFrame(title);

        newFrame.setSize(width, height);
        newFrame.setPreferredSize(new Dimension(width, height));
        if (this.currentFrame != null)
            this.currentFrame.setVisible(false);
        this.currentFrame = newFrame;
        return (newFrame);
    }

    public void         success(String message)
    {
        JOptionPane.showMessageDialog(this.currentFrame, message, "Successs", JOptionPane.INFORMATION_MESSAGE);
    }

    public void         error(String message)
    {
        JOptionPane.showMessageDialog(this.currentFrame, message, "Failure", JOptionPane.ERROR_MESSAGE);
    }

    /*
     * Protected Method
     */
    protected JButton _generateButton(String name, String actionCmd) {
        return (_initButton(new JButton(name), actionCmd));
    }

    protected JButton _generateButton(String name, Object actionCmd) {
        return (_initButton(new JButton(name), actionCmd.toString()));
    }

    protected JButton _initButton(JButton myBtn, String actionCmd) {
        myBtn.setActionCommand(actionCmd);
        myBtn.addActionListener(this.inputHandler);
        return (myBtn);
    }

    protected class ButtonClicker implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            triggerActionPerformed(e.getActionCommand());
        }
    }

}
