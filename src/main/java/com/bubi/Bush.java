package com.bubi;

import java.awt.Graphics;
import javax.swing.*;

public class Bush {
    private JLabel bushLabel;

    public Bush(int x, int y) {
        ImageIcon bushImage = new ImageIcon("Assets/bush.png");
        bushLabel = new JLabel(bushImage);
        bushLabel.setBounds(x, y, bushImage.getIconWidth(), bushImage.getIconHeight());
    }

    public JLabel getBushLabel() {
        return bushLabel;
    }
    
    public void draw(Graphics g) {
        
    }
}
