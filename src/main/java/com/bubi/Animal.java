package com.bubi;

import javax.swing.*;

public abstract class Animal {
    protected JLabel animalLabel;
    protected JLabel[] bushLabels;

    public JLabel getAnimalLabel() {
        return animalLabel;
    }

    public abstract void moveAnimalToRandomBush();
}
