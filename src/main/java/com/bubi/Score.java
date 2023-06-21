package com.bubi;

import javax.swing.*;
import java.awt.*;

public class Score extends JLabel {
    private int score;

    public Score(String text, int x, int y) {
        super(text);
        score = 0;
        setBounds(x, y, 400, 30);
        setForeground(Color.WHITE);
        setFont(new Font("Arial", Font.BOLD, 20));
    }

    public void incrementScore() {
        score++;
        setText("Score: " + score);
    }

    public int getScore() {
        return score;
    }

    public void resetScore() {
        score = 0;
        setText("Score: " + score);
    }
}
