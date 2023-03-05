package snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;


public class Snake extends JFrame implements ActionListener {
    private final int WIDTH = 500;
    private final int HEIGHT = 500;
    private final int DOT_SIZE = 10;
    private final int ALL_DOTS = 900;
    private final int RAND_POS = 29;
    private final int DELAY = 140;
    private final int[] x = new int[ALL_DOTS];
    private final int[] y = new int[ALL_DOTS];
    private int dots;
    private int apple_x;
    private int apple_y;
    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;
    private boolean inGame = true;
    private Timer timer;
    
    public Snake() {
        setTitle("贪吃蛇小游戏");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setLocationRelativeTo(null);
        addKeyListener(new TAdapter());
        setBackground(Color.BLACK);
        setFocusable(true);
        initGame();
    }
    private void initGame() {
        dots = 3;
        for (int i = 0; i < dots; i++) {
            x[i] = 50 - i * DOT_SIZE;
            y[i] = 50;
        }
        locateApple();
        timer = new Timer(DELAY, this);
        timer.start();
    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (inGame) {
            g.setColor(Color.RED);
            g.fillOval(apple_x, apple_y, DOT_SIZE, DOT_SIZE);
            for (int i = 0; i < dots; i++) {
                if (i == 0) {
                    g.setColor(Color.GREEN);
                } else {
                    g.setColor(Color.YELLOW);
                }
                g.fillRect(x[i], y[i], DOT_SIZE, DOT_SIZE);
            }
            Toolkit.getDefaultToolkit().sync();
        } else {
            gameOver(g);
        }
    }
    
    private void gameOver(Graphics g) {
        String msg = "游戏结束";
        Font font = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metrics = getFontMetrics(font);
        g.setColor(Color.WHITE);
        g.setFont(font);
        g.drawString(msg, (WIDTH - metrics.stringWidth(msg)) / 2, HEIGHT / 2);
    }
    
    private void checkApple() {
        if (x[0] == apple_x && y[0] == apple_y) {
            dots++;
            locateApple();
        }
    }
    
    private void move() {
        for (int i = dots; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        if (left) {
            x[0] -= DOT_SIZE;
        }
        if (right) {
            x[0] += DOT_SIZE;
        }
        if (up) {
            y[0] -= DOT_SIZE;
        }
        if (down) {
            y[0] += DOT_SIZE;
        }
    }
    
    private void checkCollision() {
        for (int i = dots; i > 0; i--) {
            if (i > 4 && x[0] == x[i] && y[0] == y[i]) {
            	inGame = false;
            	}
            }
            if (x[0] < 0 || x[0] >= WIDTH || y[0] < 0 || y[0] >= HEIGHT) {
            inGame = false;
            }
            if (!inGame) {
            timer.stop();
            }
    	}
    private void locateApple() {
	        Random rand = new Random();
	        apple_x = rand.nextInt(RAND_POS) * DOT_SIZE;
	        apple_y = rand.nextInt(RAND_POS) * DOT_SIZE;
	}

	    @Override
	public void actionPerformed(ActionEvent e) {
	    if (inGame) {
	            checkApple();
	            checkCollision();
	            move();
	    }
	        repaint();
	}

	private class TAdapter extends KeyAdapter {
	        @Override
	    public void keyPressed(KeyEvent e) {
	        int key = e.getKeyCode();
	        if (key == KeyEvent.VK_LEFT && !right) {
	            left = true;
	            up = false;
	            down = false;
	            }
	        if (key == KeyEvent.VK_RIGHT && !left) {
	            right = true;
	            up = false;
	            down = false;
	        }
	        if (key == KeyEvent.VK_UP && !down) {
	            up = true;
	            left = false;
	            right = false;
	        }
	        if (key == KeyEvent.VK_DOWN && !up) {
	            down = true;
	            left = false;
	            right = false;
	        }
	    }
	}

	public static void main(String[] args) {
	    EventQueue.invokeLater(() -> {
	        JFrame ex = new Snake();
	        ex.setVisible(true);
	    });
	}
}
