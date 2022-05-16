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
    //storrelse på hver unit, eller hver rute i dette tilfellet.
    static final int ENHET_STOERRELSE = 50;
    static final int SPILL_ENHETER = (SKJERM_BREDDE * SKJERM_HOYDE) / (ENHET_STOERRELSE * ENHET_STOERRELSE);

    JFrame frame;
    JTextField textfield;
    JButton[] bevegelse = new JButton[5];
    JButton opp, ned, venstre, hoyre, quit, slutt;
    JPanel panel;

    Font minFont = new Font("Comic Sans", Font.ITALIC,30);

    char operator;

    /**
     * erstatt 500ms med 2000ms HUSK HUSK
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
     * X- og Y-koordinatene til skatten som skal plasseres tilfeldig utover rutenettet.
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
        timer = new Timer(DELAY, this);
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
        } else {
            gameOver(g);
        }


        g.setColor(Color.cyan);
        g.setFont(new Font("Comic Sans", Font.ITALIC, 35));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Lengde: " + slangeStoerrelse, (SKJERM_BREDDE - metrics.stringWidth("Lengde: " + slangeStoerrelse)) / 2, g.getFont().getSize());


        g.setColor(Color.orange);
        g.fillRect(skattX, skattY, ENHET_STOERRELSE, ENHET_STOERRELSE);


        for (int i = 0; i < slangeStoerrelse; i++) {
            if (i == 0) {
                g.setColor(Color.magenta);
                g.fillRect(x[i], y[i], ENHET_STOERRELSE, ENHET_STOERRELSE);
            } else {
                g.setColor(Color.pink);
                g.fillRect(x[i], y[i], ENHET_STOERRELSE, ENHET_STOERRELSE);
            }
        }
    }

    public void nySkatt() {
        skattX = random.nextInt(SKJERM_BREDDE / ENHET_STOERRELSE) * ENHET_STOERRELSE;
        skattY = random.nextInt(SKJERM_HOYDE / ENHET_STOERRELSE) * ENHET_STOERRELSE;
    }

    public void spreSkatter() {
        for (int skatt = 0; skatt < 10; skatt++) {
            nySkatt();
        }
    }

    public void bevege() {
        for (int i = slangeStoerrelse; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        switch (retning) {
            //Opp
            case 'O' -> y[0] = y[0] - ENHET_STOERRELSE;

            //NED
            case 'N' -> y[0] = y[0] + ENHET_STOERRELSE;

            //VENSTRE
            case 'V' -> x[0] = x[0] - ENHET_STOERRELSE;

            //HOYRE
            case 'H' -> x[0] = x[0] + ENHET_STOERRELSE;
        }
    }

    public void sjekkSkatt() {
        if ((x[0] == skattX) && (y[0] == skattY)) {
            slangeStoerrelse++;
            skatterFunnet++;
            nySkatt();
        }
    }

    public void sjekkKol() {
        /**
         * Kollisjon dersom hodet skulle kollidert med en del av kroppen
         */
        for (int i = slangeStoerrelse; i > 0; i--) {
            if ((x[0] == x[i]) && (y[0] == y[i])) {
                kjorer = false;
            }
        }

        /**
         * Kollisjon dersom hodet skulle truffet VENSTRE siden av mappet (?) idk
         */
        if (x[0] < 0) {
            kjorer = false;
        }

        /**
         * Kollisjon dersom hodet skulle truffet HOYSTRE siden av mappet (?) idk
         */
        if (x[0] > SKJERM_BREDDE - ENHET_STOERRELSE) {
            kjorer = false;
        }

        //Kollisjon overst

        if (y[0] < 0) {
            kjorer = false;
        }

        //Kollisjon nederst

        if (y[0] > SKJERM_HOYDE - ENHET_STOERRELSE) {
            kjorer = false;
        }

        /**
         * mulig dette er waste lol
         */
        if (!kjorer) {
            timer.stop();
        }
    }

    public void gameOver(Graphics g) {


        g.setColor(Color.WHITE);
        g.setFont(new Font("Comic Sans", Font.ITALIC, 60));
        FontMetrics fMetrics = getFontMetrics(g.getFont());
        g.drawString("You died lol xd !", (SKJERM_BREDDE - fMetrics.stringWidth("You died lol xd !")) / 2, SKJERM_HOYDE / 2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (kjorer) {
            bevege();
            sjekkSkatt();
            sjekkKol();
        }
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_A:
                case KeyEvent.VK_LEFT:
                    if (retning != 'H') {
                        retning = 'V';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                case KeyEvent.VK_D:
                    if (retning != 'V') {
                        retning = 'H';
                    }
                    break;
                case KeyEvent.VK_UP:
                case KeyEvent.VK_W:
                    if (retning != 'N') {
                        retning = 'O';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                case KeyEvent.VK_S:
                    if (retning != 'O') {
                        retning = 'N';
                    }
                    break;
            }
        }
    }


}
