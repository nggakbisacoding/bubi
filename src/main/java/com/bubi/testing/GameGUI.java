package com.bubi.testing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GameGUI extends JFrame {
    private JLabel backgroundLabel;
    private JLabel characterLabel;
    private JLabel timeLabel;

    private JLabel[] bushLabels;
    private JLabel pigLabel;
    private JLabel dogLabel;

    private int characterX, characterY;
    private boolean isClicked;
    private boolean pigVisible;
    private boolean dogVisible;
    private boolean[] bushLocked;
    private int scorePlayer1;
    private int scorePlayer2;
    private int livesPlayer1;
    private int livesPlayer2;
    private int currentPlayer;
    private Timer gameTimer;
    private int timeRemaining;


    public GameGUI() {
        characterX = 200;
        characterY = 350;
        isClicked = false;
        pigVisible = false;
        dogVisible = false;
        scorePlayer1 = 0;
        scorePlayer2 = 0;
        livesPlayer1 = 3;
        livesPlayer2 = 3;
        currentPlayer = 1;

        ImageIcon bgImage = new ImageIcon("Assets/bg.png");
        backgroundLabel = new JLabel(bgImage);
        backgroundLabel.setBounds(0, 0, 1280, 860);
        add(backgroundLabel);

        ImageIcon characterImage1 = new ImageIcon("Assets/kapak1.png");
        ImageIcon characterImage2 = new ImageIcon("Assets/kapak2.png");
        characterLabel = new JLabel(characterImage1);
        characterLabel.setBounds(characterX, characterY, 100, 100);
        backgroundLabel.add(characterLabel);

        characterLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (isClicked) {
                    characterLabel.setIcon(characterImage1);
                    isClicked = false;
                } else {
                    characterLabel.setIcon(characterImage2);
                    isClicked = true;

                    Timer timer = new Timer(100, (ActionEvent e1) -> {
                        characterLabel.setIcon(characterImage1);
                        isClicked = false;
                    });
                    timer.setRepeats(false);
                    timer.start();
                }

                if (!pigVisible) {
                    pigVisible = true;
                    movePigToRandomBush();
                }

                if (!dogVisible) {
                    dogVisible = true;
                    moveDogToRandomBush();
                }
            }
        });

        backgroundLabel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int mouseX = e.getX();
                int mouseY = e.getY();
                double sensitivity = 1; // Increase or decrease the sensitivity factor as desired

                characterX = mouseX - characterLabel.getWidth() / 2;
                characterY = mouseY - characterLabel.getHeight() / 2;
                characterX *= sensitivity;
                characterY *= sensitivity;
                characterLabel.setLocation(characterX, characterY);
            }
        });

        bushLabels = new JLabel[7];
        int[] bushXCoordinates = {27, 370, 690, 1040, 540, 400, 670};
        int[] bushYCoordinates = {420, 500, 540, 540, 60, 250, 210};

        ImageIcon bushImage = new ImageIcon("Assets/bush.png");
        for (int i = 0; i < bushLabels.length; i++) {
            bushLabels[i] = new JLabel(bushImage);
            bushLabels[i].setBounds(bushXCoordinates[i], bushYCoordinates[i], bushImage.getIconWidth(), bushImage.getIconHeight());
            backgroundLabel.add(bushLabels[i]);
        }

        ImageIcon pigImage = new ImageIcon("Assets/pig.png");
        pigLabel = new JLabel(pigImage);
        pigLabel.setBounds(getRandomXCoordinate(), getRandomYCoordinate() - pigImage.getIconHeight() / 2, pigImage.getIconWidth(), pigImage.getIconHeight());
        pigLabel.setVisible(false);
        backgroundLabel.add(pigLabel);

        ImageIcon dogImage = new ImageIcon("Assets/dog.png");
        dogLabel = new JLabel(dogImage);
        dogLabel.setBounds(getRandomXCoordinate(), getRandomYCoordinate() - dogImage.getIconHeight() / 2, dogImage.getIconWidth(), dogImage.getIconHeight());
        dogLabel.setVisible(false);
        backgroundLabel.add(dogLabel);

        bushLocked = new boolean[bushLabels.length];
        for (int i = 0; i < bushLocked.length; i++) {
            bushLocked[i] = false;
        }

        timeLabel = new JLabel("Player 1: Score: 0 | Lives: 3 | Time: 30s");
        timeLabel.setForeground(Color.WHITE);
        timeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        timeLabel.setBounds(10, 10, 400, 30);
        backgroundLabel.add(timeLabel);

        setFocusable(true);
        requestFocusInWindow();
        setVisible(true);

        startGame();
    }

    private int getRandomXCoordinate() {
        return (int) (Math.random() * 800) + 200;
    }

    private int getRandomYCoordinate() {
        return (int) (Math.random() * 400) + 200;
    }

    private void movePigToRandomBush() {
        Random random = new Random();

        Timer pigTimer = new Timer(random.nextInt(1200) + 350, (ActionEvent e) -> {
            int randomBushIndex = random.nextInt(bushLabels.length);
            if (bushLocked[randomBushIndex]) {
                movePigToRandomBush();
                return;
            }
            JLabel randomBushLabel = bushLabels[randomBushIndex];
            int x1 = randomBushLabel.getX() + randomBushLabel.getWidth() / 1 - pigLabel.getWidth() / 2 - 14;
            int y1 = randomBushLabel.getY() + randomBushLabel.getHeight() / 6 - pigLabel.getHeight() / 6 - 19;
            pigLabel.setVisible(false);
            pigLabel.setLocation(x1, y1);
            pigLabel.setVisible(true);
            bushLocked[randomBushIndex] = true;
            Timer pigHideTimer = new Timer(random.nextInt(1200) + 350, (ActionEvent e1) -> {
                pigLabel.setVisible(false);
                bushLocked[randomBushIndex] = false;
                movePigToRandomBush();
            });
            pigHideTimer.setRepeats(false);
            pigHideTimer.start();
        });

        pigTimer.setRepeats(false);
        pigTimer.start();

        pigLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handlePigClick();
            }
        });
    }

    private void handlePigClick() {
        if (currentPlayer == 1) {
            scorePlayer1 += 1;
            timeLabel.setText("Player 1: Score: " + scorePlayer1 + " | Lives: " + livesPlayer1 + " | Time: " + (gameTimer.getDelay() / 1000) + "s");
        } else {
            scorePlayer2 += 1;
            timeLabel.setText("Player 2: Score: " + scorePlayer2 + " | Lives: " + livesPlayer2 + " | Time: " + (gameTimer.getDelay() / 1000) + "s");
        }
        timeLabel.repaint();
    }

    private void moveDogToRandomBush() {
        Random random = new Random();

        Timer dogTimer = new Timer(random.nextInt(1400) + 400, (ActionEvent e) -> {
            int randomBushIndex = random.nextInt(bushLabels.length);
            if (bushLocked[randomBushIndex]) {
                moveDogToRandomBush();
                return;
            }
            JLabel randomBushLabel = bushLabels[randomBushIndex];
            int x1 = randomBushLabel.getX() + randomBushLabel.getWidth() / 1 - dogLabel.getWidth() / 2 - 17;
            int y1 = randomBushLabel.getY() + randomBushLabel.getHeight() / 14 - dogLabel.getHeight() / 9 - 19;
            dogLabel.setVisible(false);
            dogLabel.setLocation(x1, y1);
            dogLabel.setVisible(true);
            bushLocked[randomBushIndex] = true;
            Timer dogHideTimer = new Timer(random.nextInt(1400) + 400, (ActionEvent e1) -> {
                dogLabel.setVisible(false);
                bushLocked[randomBushIndex] = false;
                moveDogToRandomBush();
            });
            dogHideTimer.setRepeats(false);
            dogHideTimer.start();
        });

        dogTimer.setRepeats(false);
        dogTimer.start();

        dogLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleDogClick();
            }
        });
    }

    private void handleDogClick() {
        if (currentPlayer == 1) {
            if (livesPlayer1 > 0) {
                livesPlayer1 -= 1;
            }
            timeLabel.setText("Player 1: Score: " + scorePlayer1 + " | Lives: " + livesPlayer1 + " | Time: " + (gameTimer.getDelay() / 1000) + "s");
        } else {
            if (livesPlayer2 > 0) {
                livesPlayer2 -= 1;
            }
            timeLabel.setText("Player 2: Score: " + scorePlayer2 + " | Lives: " + livesPlayer2 + " | Time: " + (gameTimer.getDelay() / 1000) + "s");
        }
        if (livesPlayer1 == 0) {
            switchPlayer();
        } else if(livesPlayer2 == 0) {
            endTurn();
        }
        timeLabel.repaint();
    }

    private void endTurn() {
        gameTimer.stop();

        if (livesPlayer1 <= 0 && livesPlayer2 <= 0) {
            JOptionPane.showMessageDialog(null, "Game Over!\nPlayer 1 Score: " + scorePlayer1 + "\nPlayer 2 Score: " + scorePlayer2);
            resetGame();
        } else if (livesPlayer1 <= 0) {
            JOptionPane.showMessageDialog(null, "Game Over!\nPlayer 2 Wins!\nPlayer 1 Score: " + scorePlayer1 + "\nPlayer 2 Score: " + scorePlayer2);
            resetGame();
        } else if (livesPlayer2 <= 0) {
            JOptionPane.showMessageDialog(null, "Game Over!\nPlayer 1 Wins!\nPlayer 1 Score: " + scorePlayer1 + "\nPlayer 2 Score: " + scorePlayer2);
            resetGame();
        } else {
            switchPlayer();
        }
    }

    private void switchPlayer() {
        if (currentPlayer == 1) {
            currentPlayer = 2;
        } else {
            currentPlayer = 1;
        }

        scorePlayer1 = 0;
        scorePlayer2 = 0;
        timeRemaining = 30;

        timeLabel.setText("Player " + currentPlayer + ": Score: " + getCurrentScore() + " | Lives: " + getCurrentLives() + " | Time: " + timeRemaining + "s");

        gameTimer.start();
    }

    private int getCurrentScore() {
        return (currentPlayer == 1) ? scorePlayer1 : scorePlayer2;
    }

    private int getCurrentLives() {
        return (currentPlayer == 1) ? livesPlayer1 : livesPlayer2;
    }

    private void startGame() {
        currentPlayer = 1;
        scorePlayer1 = 0;
        scorePlayer2 = 0;
        livesPlayer1 = 3;
        livesPlayer2 = 3;
        timeRemaining = 30;

        timeLabel.setText("Player 1: Score: " + scorePlayer1 + " | Lives: " + livesPlayer1 + " | Time: " + timeRemaining + "s");

        gameTimer = new Timer(1000, (ActionEvent e) -> {
            timeRemaining--;
            if (timeRemaining == 0) {
                gameTimer.stop();
                endTurn();
            } else {
                timeLabel.setText("Player " + currentPlayer + ": Score: " + getCurrentScore() + " | Lives: " + getCurrentLives() + " | Time: " + timeRemaining + "s");
            }
        });

        gameTimer.setRepeats(true);
        gameTimer.start();
    }

    private void resetGame() {
        int result = JOptionPane.showConfirmDialog(null, "Do you want to play again?", "Play Again?", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            startGame();
        } else {
            String imagePath;
            if (scorePlayer1 > scorePlayer2) {
                imagePath = "Assets/Player1Wins.png";
            } else if (scorePlayer2 > scorePlayer1) {
                imagePath = "Assets/Player2Wins.png";
            } else {
                imagePath = "Assets/PlayerDraw.png";
            }

            ImageIcon winnerImage = new ImageIcon(imagePath);
            JLabel winnerLabel = new JLabel(winnerImage);

            JFrame winnerFrame = new JFrame("Game Over");
            backgroundLabel = new JLabel(winnerImage);
            backgroundLabel.setBounds(0, 0, 1280, 860);
            winnerFrame.getContentPane().add(winnerLabel);
            winnerFrame.setSize(winnerImage.getIconWidth(), winnerImage.getIconHeight());
            winnerFrame.setLocationRelativeTo(null);
            winnerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            winnerFrame.setVisible(true);
            int exit = JOptionPane.showConfirmDialog(null, "Do you want to exit game?", "Exit?", JOptionPane.YES_NO_OPTION);
            if (exit == JOptionPane.YES_OPTION) {
                winnerFrame.dispose();
            } else {
                startGame();
            }
        }
    }
}