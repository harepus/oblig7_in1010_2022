import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * GameFrame
 */
public class SpilleRamme extends JFrame implements ActionListener {

    //JFrame frame = new JFrame("Snakegame");
    JButton opp, ned, venstre, hoyre, quit;

    SpilleRamme(){
        SpillePanel spillePanel = new SpillePanel();
        this.setTitle("Det beste SnakeGamet i verden");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setResizable(false);
        //this.pack();
        this.setSize(600, 800);

        opp = new JButton();
        opp.setText("Opp");
        opp.setSize(50,50);
        opp.setLocation(50,150);
        opp.addActionListener(this);

        ned = new JButton();
        ned.setText("Ned");
        ned.setSize(50,50);
        ned.setLocation(50,200);
        ned.addActionListener(this);

        venstre = new JButton();
        venstre.setText("Venstre");
        venstre.setSize(50,50);
        venstre.setLocation(0,200);
        venstre.addActionListener(this);

        hoyre = new JButton();
        hoyre.setText("Hoyre");
        hoyre.setSize(50,50);
        hoyre.setLocation(75,200);
        hoyre.addActionListener(this);

        quit = new JButton();
        quit.setText("Quit");
        quit.setSize(50,50);
        quit.setLocation(500,800);
        quit.addActionListener(this);


        this.add(opp);this.add(ned);this.add(venstre);this.add(hoyre);this.add(quit);
        this.add(spillePanel);



        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
