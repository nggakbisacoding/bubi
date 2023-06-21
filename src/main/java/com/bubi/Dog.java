package com.bubi;

import java.awt.Graphics;
import javax.swing.*;
import java.util.Random;

public class Dog extends Animal {
    public Dog(JLabel[] bushLabels) {
        this.bushLabels = bushLabels;
        ImageIcon dogImage = new ImageIcon("Assets/Dog.png");
        animalLabel = new JLabel(dogImage);
        animalLabel.setSize(dogImage.getIconWidth(), dogImage.getIconHeight());
    }

    @Override
    public void moveAnimalToRandomBush() {
        Random random = new Random();
        int randomBushIndex = random.nextInt(bushLabels.length);
        JLabel randomBush = bushLabels[randomBushIndex];

        int x = randomBush.getX() + randomBush.getWidth() / 1 - animalLabel.getWidth() / 2 - 17;
        int y = randomBush.getY() + randomBush.getHeight() / 14 - animalLabel.getHeight() / 9 - 19;

        animalLabel.setLocation(x, y);
    }
}
