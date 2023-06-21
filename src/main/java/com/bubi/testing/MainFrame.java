package com.bubi.testing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainFrame extends JFrame {

    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel3;
    private GameGUI gameGUI;

    public MainFrame() {
        setTitle("Start");
        setSize(1280,860);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Panel 1 (Mulai.png)
        panel1 = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon imageIcon = new ImageIcon("Assets/Mulai.png");
                Image image = imageIcon.getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };
        panel1.setLayout(null);
        panel1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showPanel2();
                setTitle("Peraturan permainan");
            }
        });

        // Panel 2 (Peraturan.png)
        panel2 = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon imageIcon = new ImageIcon("Assets/Peraturan.png");
                Image image = imageIcon.getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };
        panel2.setLayout(null);
        panel2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showPanel3();
                setTitle("Game Frame");
            }
        });

        // Panel 3 (GameGUI)
        panel3 = new JPanel();
        panel3.setLayout(new BorderLayout());
        panel3.setPreferredSize(new Dimension(1280,860)); // Mengatur ukuran panel 3
        gameGUI = new GameGUI();
        panel3.add(gameGUI.getContentPane(), BorderLayout.CENTER);

        // Tampilkan panel pertama saat aplikasi dijalankan
        add(panel1);

        setVisible(true);
    }

    private void showPanel2() {
        getContentPane().removeAll();
        add(panel2);
        getContentPane().revalidate();
        getContentPane().repaint();
    }

    private void showPanel3() {
        getContentPane().removeAll();
        add(panel3);
        getContentPane().revalidate();
        getContentPane().repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainFrame::new);
    }
}
