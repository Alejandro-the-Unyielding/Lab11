package it.unibo.oop.reactivegui02;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import it.unibo.oop.JFrameUtil;

import java.io.Serial;

/**
 * Second example of reactive GUI.
 */
@SuppressWarnings("PMD.AvoidPrintStackTrace")
public final class ConcurrentGUI extends JFrame {

    @Serial
    private static final long serialVersionUID = 1L;
    private final JLabel display = new JLabel();

    public ConcurrentGUI(){
        super();
        
        JFrameUtil.dimensionJFrame(this);

        final JPanel panel = new JPanel();

        panel.add(display);

        final JButton up = new JButton("UP");
        
        final JButton down = new JButton("DOWN");

        final JButton stop = new JButton("STOP");

        panel.add(up);  panel.add(down);  panel.add(stop);

        this.getContentPane().add(panel);

        this.setVisible(true);

        final Agent agent = new Agent();
        
        up.addActionListener(_ -> agent.countUp());

        down.addActionListener(_ -> agent.countDown());

        stop.addActionListener(_ -> {
            agent.stopCounting();
            stop.setEnabled(false);
            up.setEnabled(false);
            down.setEnabled(false);
        });

        new Thread(agent).start();

        }

        private final class Agent implements Runnable {
            private volatile boolean stop;
            private volatile boolean up = true;
            private int count;


            @Override
            public void run(){
                while(!stop){
                    try {
                        count += (up ? 1 : -1);



                        final var toShow = Integer.toString(count);
                        SwingUtilities.invokeLater(() -> display.setText(toShow));
                        Thread.sleep(100);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            public void stopCounting(){
                this.stop = true;
            }

            public void countUp(){
                this.up=true;
            }

            public void countDown(){
                this.up=false;
            }

        }


    }

