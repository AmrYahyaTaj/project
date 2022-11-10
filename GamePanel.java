import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;



/**
 * Created by learn on 09/11/22.
 */
public class GamePanel extends JPanel implements ActionListener {

    static final int SCREEN_WIDTH = 650;
    static final int SCREEN_HEIGHT = 650;
    static final int UNIT_SIZE = 20;
    static final int GAME_UNIT = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
    static final int DELAY = 75;
    final int x[] = new int [GAME_UNIT];
    final int y[] = new int [GAME_UNIT];
    int bodyparts = 6;
    int appleEaten;
    int applex;
    int appley;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;



    GamePanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        StartGame();

    }







    public void StartGame(){
        newApple();
        running = true;
        timer = new Timer(DELAY,this);
        timer.start();

    }







    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);

    }
    public void draw(Graphics g){
        if (running) {
            for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
                g.drawLine(0, i * UNIT_SIZE, SCREEN_HEIGHT, i * UNIT_SIZE);

            }
            g.setColor(Color.red);
            g.fillOval(applex, appley, UNIT_SIZE, UNIT_SIZE);

            for (int i = 0; i < bodyparts; i++) {
                if (i == 0) {
                    g.setColor(Color.GREEN);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                } else {
                    g.setColor(new Color(45, 200, 0));
                   // g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);

                }
            }
            g.setColor(Color.red);
            g.setFont(new Font("IN FREE",Font.BOLD,35));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("score: "+appleEaten, (SCREEN_WIDTH - metrics.stringWidth("score: "+appleEaten))/2,g.getFont().getSize() );
        }
        else {
            gameOver(g);
        }
      }








    public void newApple(){
        applex = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        appley = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;

    }







    public void move(){
        for (int i = bodyparts;i>0;i--){
            x[i] = x[i-1];
            y[i] = y[i-1];

        }
        switch (direction){
            case 'U':
                y[0] = y[0] -  UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] +  UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] -  UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] +  UNIT_SIZE;
                break;

        }

    }







    public void checkApple(){
        if ((x[0]==applex)&&(y[0]==appley)){
            bodyparts++;
            appleEaten++;
            newApple();
        }


    }







    public void checkCollisions(){
        //check if the head collides with body
        for ( int i = bodyparts;i>0;i--){
            if ((x[0] == x[i]) && (y[0] == y[i])){
                running = false;
            }
        }
        //check if the head touches left border
        if (x[0] < 0 ){
            running = false;
        }
        //check if the head touches right border
        if (x[0] > SCREEN_WIDTH ) {
            running = false;
        }
        //check if the head touches top border
        if (y[0] < 0 ) {
            running = false;
        }
        //check if the head touches bottom border
        if (y[0] > SCREEN_HEIGHT ) {
            running = false;
        }
        if (!running){
            timer.stop();
        }

    }







    public void gameOver(Graphics g){
        //score
        g.setColor(Color.red);
        g.setFont(new Font("IN FREE",Font.BOLD,35));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("score: "+appleEaten, (SCREEN_WIDTH - metrics1.stringWidth("score: "+appleEaten))/2,g.getFont().getSize() );
        // over the game
        g.setColor(Color.red);
        g.setFont(new Font("IN FREE",Font.BOLD,75));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREEN_WIDTH - metrics2.stringWidth("Game Over"))/2,SCREEN_HEIGHT/2);

    }

    @Override







    public void actionPerformed(ActionEvent e) {

        if (running){
            move();
            checkApple();
            checkCollisions();
        }
        repaint();

    }








    public class MyKeyAdapter extends KeyAdapter{
        //@Override
        public void keyPressed(KeyEvent e){
            switch (e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    if (direction != 'R'){
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != 'L'){
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (direction != 'D'){
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != 'U'){
                        direction = 'D';
                    }
                    break;
            }

        }
    }
}
