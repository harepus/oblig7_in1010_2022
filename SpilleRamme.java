import javax.swing.*;

/**
 * GameFrame
 */
public class SpilleRamme extends JFrame {

    SpilleRamme(){
        this.add(new SpillePanel());
        this.setTitle("Det beste SnakeGamet i verden");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
