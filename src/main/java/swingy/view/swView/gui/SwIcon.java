package swingy.view.swView.gui;

import lombok.Getter;

import javax.swing.*;

public enum SwIcon {

    DANGER ("danger"),
    DEAD ("dead"),
    GRASS ("grass"),
    HERO ("hero"),
    ARCHER ("archer"),
    ASSASSIN ("assassin"),
    SORCERER ("sorcerer"),
    TANK ("tank"),
    WARRIOR ("warrior"),
    HEART ("heart"),
    SWORD ("sword"),
    SHIELD ("shield"),
    TITLE ("title");

    @Getter
    private String      name;

    SwIcon(String name) {
        this.name = name;
    }

    public boolean equals(SwIcon other) {
        return (other.name.equals(this.name));
    }
}
