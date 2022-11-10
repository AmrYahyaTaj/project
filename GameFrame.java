import javax.swing.*;

/**
 * Created by learn on 09/11/22.
 */
public class GameFrame extends JFrame {

    GameFrame(){

        GamePanel panel = new GamePanel();
        this.add(panel);
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);


    }
}
