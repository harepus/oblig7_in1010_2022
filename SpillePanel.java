import java.awt.*;

import java.awt.event.*;

import javax.swing.*;

import java.util.Random;


/**
 * GamePanel
 */
public class SpillePanel extends JPanel implements ActionListener {
    static final int SKJERM_BREDDE = 600;
    static final int SKJERM_HOYDE = 600;
    static final int ENHET_STOERRELSE = 50;
    static final int SPILL_ENHETER = (SKJERM_BREDDE*SKJERM_HOYDE)/(ENHET_STOERRELSE*ENHET_STOERRELSE);
    /**
     *2000 millisekunder HUSK HUSK
     */

    static final int DELAY = 500;

    /**
     * Arrayene i dette tilfellet vil holde x-koordinatene, samt y-koordinatene.
     */
    final int x[] = new int[SPILL_ENHETER];
    final int y[] = new int[SPILL_ENHETER];


    int slangeStoerrelse = 4;
    int skatterFunnet;

    /**
     * X- og Y-koordinatene til skatten som plasseres tilfeldig utover rutenettet.
     */
    int skattX;
    int skattY;

    char retning = 'H';
    boolean kjorer = false;
    Timer timer;
    Random random;

    SpillePanel() {
        random = new Random();
        this.setPreferredSize(new Dimension(SKJERM_BREDDE, SKJERM_HOYDE));
        this.setBackground(Color.blue);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startSpill();

    }

    public void startSpill() {
        nySkatt();
        kjorer = true;
        timer = new Timer(DELAY,this);
        timer.start();
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        tegn(g);
    }

    public void tegn(Graphics g) {
        if (kjorer) {
            for (int i = 0; i < SKJERM_HOYDE / ENHET_STOERRELSE; i++) {

                g.drawLine(i * ENHET_STOERRELSE, 0, i * ENHET_STOERRELSE, SKJERM_HOYDE);

                g.drawLine(0, i * ENHET_STOERRELSE, SKJERM_BREDDE, i * ENHET_STOERRELSE);

            }
        }

        g.setColor(Color.yellow);
        g.fillRect(skattX, skattY, ENHET_STOERRELSE, ENHET_STOERRELSE);

        for(int i = 0; i<slangeStoerrelse; i++){
            if(i == 0){
                g.setColor(Color.magenta);
                g.fillRect(x[i], y[i], ENHET_STOERRELSE, ENHET_STOERRELSE);
            }
            else{
                g.setColor(Color.pink);
                g.fillRect(x[i], y[i], ENHET_STOERRELSE, ENHET_STOERRELSE);
            }
        }
    }

    public void nySkatt() {

        skattX = random.nextInt(SKJERM_BREDDE/ENHET_STOERRELSE)*ENHET_STOERRELSE;
        skattY = random.nextInt(SKJERM_HOYDE/ENHET_STOERRELSE)*ENHET_STOERRELSE;

    }

    public void bevege(){
        for(int i = slangeStoerrelse; i>0; i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        switch(retning){
            //Opp
            case 'O':
                y[0] = y[0] - ENHET_STOERRELSE;
                break;
                //NED
            case 'N':
                y[0] = y[0] + ENHET_STOERRELSE;
                break;
                //VENSTRE
            case 'V':
                x[0] = x[0] - ENHET_STOERRELSE;
                break;
                //HOYRE
                case 'H':
                x[0] = x[0] + ENHET_STOERRELSE;
                break;

        }
    }

    public void sjekkSkatt() {

    }

    public void sjekkKol() {

    }

    public void gameOver(Graphics g) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(kjorer) {
            bevege();
            sjekkSkatt();
            sjekkKol();
        }
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {

        }
    }


}
