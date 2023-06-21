package com.bubi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class MainFrame extends JFrame {

    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel3;
    private GameGUI gameGUI;
    private BufferedImage[] images;
    public static int state;
    public static final int READY = 0;
    public static final int START = 1;
    public static final int OVER = 2;
//    private static final int FPS = 10;

    public MainFrame() {
        setFrame();
        setVisible(true);
        run();
    }
    
    public final void setFrame() {
        state = READY;
        images = new BufferedImage[10];
        setTitle("Start");
        setSize(Constant.width, Constant.height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Panel 1 (Mulai.png)
        panel1 = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                images[0] = GameUtil.loadBufferedImage("Assets/Mulai.png");
                g.drawImage(images[0], 0, 0, getWidth(), getHeight(), this);
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
                images[1] = GameUtil.loadBufferedImage("Assets/Peraturan.png");
                g.drawImage(images[1], 0, 0, getWidth(), getHeight(), this);
            }
        };
        panel2.setLayout(null);
        panel2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                state = START;
                showPanel3();
                setTitle("BUBI");
            }
        });

        // Panel 3 (GameGUI)
        panel3 = new JPanel();
        panel3.setLayout(new BorderLayout());
        panel3.setPreferredSize(new Dimension(Constant.width, Constant.height)); // Mengatur ukuran panel 3

        gameGUI = new GameGUI();
        gameGUI.setBounds(Constant.framex, Constant.framey, Constant.width, Constant.height); // Mengatur ukuran dan posisi GameGUI di dalam panel3
        panel3.add(gameGUI);

        // Tampilkan panel pertama saat aplikasi dijalankan
        add(panel1);
/*     

*/
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
        panel3.setVisible(true);
        getContentPane().revalidate();
        getContentPane().repaint();
    }
    
    public static void setGame(int state) {
        MainFrame.state = state;
    }
    
    public void run() {
        new Thread(() -> {
            while (state == START) {
                    gameGUI.initialize();
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                }
            }
        }).start();
    }
}