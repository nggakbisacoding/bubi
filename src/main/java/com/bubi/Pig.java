package com.bubi;

import javax.swing.*;
import java.util.Random;

public class Pig extends Animal {
    public Pig(JLabel[] bushLabels) {
        this.bushLabels = bushLabels;
        ImageIcon pigImage = new ImageIcon("Assets/pig.png");
        animalLabel = new JLabel(pigImage);
        animalLabel.setSize(pigImage.getIconWidth(), pigImage.getIconHeight());
    }

    @Override
    public void moveAnimalToRandomBush() {
        Random random = new Random();
        int randomBushIndex = random.nextInt(bushLabels.length);
        JLabel randomBush = bushLabels[randomBushIndex];

        int x = randomBush.getX() + randomBush.getWidth() / 1 - animalLabel.getWidth() / 2 - 14;
        int y = randomBush.getY() + randomBush.getHeight() / 6 - animalLabel.getHeight() / 6 - 19;

        animalLabel.setLocation(x, y);
    }
}
