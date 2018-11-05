package swingy.view.map;

import lombok.Getter;
import swingy.model.character.SwCharacter;
import swingy.tools.SwCoord;
import swingy.model.map.SwMap;
import swingy.view.swView.gui.SwIcon;
import swingy.view.swView.gui.SwIconFactory;
import swingy.view.swView.gui.SwViewGUI;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;

public class SwMapViewGUI extends SwViewGUI {

    @Getter
    private JScrollPane               mapScrollPanel;
    private JPanel                    mapPanel;

    public SwMapViewGUI() {
        this.mapPanel = new JPanel();
        this.mapPanel.setBackground(Color.WHITE);
        this.mapScrollPanel = new JScrollPane(this.mapPanel);
        this.mapScrollPanel.setBackground(Color.WHITE);
        this.mapScrollPanel.setPreferredSize(mapPanel.getPreferredSize());
    }

    /*
     * Public Method
     */
    public void setDisplayMap(int size, SwCharacter[] characters)
    {
        SwCoord pos = new SwCoord(0, 0);
        JLabel                  label;

        mapPanel.removeAll();
        mapPanel.repaint();

        mapPanel.setLayout(new GridLayout(size, size));
        mapPanel.setBorder(new LineBorder(Color.LIGHT_GRAY));
        for (int i = 0; i < size; i++)
        {
            pos.setY(i);
            for (int j = 0; j < size; j++)
            {
                pos.setX(j);
                label = _setMapLabel(SwMap.getCharactersOnPosition(characters, pos));
                mapPanel.add(label);
            }
        }
        mapPanel.setVisible(true);
    }

    /*
     * Private Methods
     */
    private JLabel _setMapLabel(ArrayList<SwCharacter> characters)
    {
        JLabel                  label;
        ImageIcon               image = null;

        label = new JLabel();
        label.setBackground(Color.WHITE);
        if (characters.size() != 0)
            image = SwIconFactory.getIcon(characters.get(0));
        if (image == null)
            image = SwIconFactory.getIcon(SwIcon.GRASS);
        label.setIcon(image);
        return (label);
    }
}
