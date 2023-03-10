import java.awt.Color;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;

public class PhilosopherFrame {
    private JFrame frame;
    //main containers
    private JPanel control;
    private JScrollPane log;
    private JPanel philosopherView;
    //Control panel
    private JPanel playControls;
    private JButton playButton, resetButton, pauseButton;
    private JLabel ticksText;
    private JSpinner tickSpinner;
    private JPanel dinnerControls;
    private JButton semaphorButton, monitorButton;
    //Log pane
    private JTextArea logMessages;
    //Philosopher panel
    private ArrayList<PhilosopherPanel> philosophers;
    

    public PhilosopherFrame() {
        try {
            this.philosophers = new ArrayList<PhilosopherPanel>();

            philosophers.add(new PhilosopherPanel("Plato"));
            philosophers.add(new PhilosopherPanel("Socrates"));
            philosophers.add(new PhilosopherPanel("Aristotle"));
            philosophers.add(new PhilosopherPanel("Diogenese"));
            philosophers.add(new PhilosopherPanel("Epechurius"));
            
            initalize();
        } catch (Exception e) {
            System.out.println(e);
        }
        
    }

    private void initalize() {
        frameInitialize();

        controlPanelInitialize();
        this.frame.add(control, BorderLayout.SOUTH);

        logPaneInitialize();
        this.frame.add(log, BorderLayout.EAST);

        philosopherPanelInitialize();
        this.frame.add(this.philosopherView, BorderLayout.CENTER);

        this.frame.setVisible(true);

        for(int i = 0; i < 25; i++) {
            addLogMessage("message " + String.valueOf(i));
        }
    }

    private void frameInitialize() {
        this.frame = new JFrame();

        this.frame.setTitle("Dining Philosophers");
        this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.frame.setSize(700, 900);
        this.frame.setLocationRelativeTo(null); //null centers the frame on the screen
        this.frame.setLayout(new BorderLayout());
    }

    private void controlPanelInitialize() {
        this.control = new JPanel(new BorderLayout());
        this.control.setBackground(Color.LIGHT_GRAY);

        //Play Controls
        String pause= "???"; //\u23f8
        String play = "??????"; //\u23f5
        String reset = "???"; //\u23ee

        this.playControls = new JPanel();
        this.tickSpinner = new JSpinner();
        this.tickSpinner.setValue(5);
        this.playControls.setBackground(Color.LIGHT_GRAY);
        this.playButton = new JButton(play);
        this.pauseButton = new JButton(pause);
        this.resetButton = new JButton(reset);
        this.playControls.add(this.playButton);
        this.playControls.add(this.pauseButton);
        this.playControls.add(this.resetButton);
        this.ticksText = new JLabel("Ticks/s:");
        this.playControls.add(this.ticksText);
        this.playControls.add(this.tickSpinner);

        this.control.add(playControls, BorderLayout.NORTH);

        //Diner Controls
        this.dinnerControls = new JPanel();
        this.semaphorButton = new JButton("Semaphore Dinner");
        this.monitorButton = new JButton("Monitor Dinner");
        this.dinnerControls.setBackground(Color.LIGHT_GRAY);
        this.dinnerControls.add(this.semaphorButton);
        this.dinnerControls.add(this.monitorButton);

        this.control.add(this.dinnerControls, BorderLayout.SOUTH);
    }

    private void logPaneInitialize() {
        this.logMessages = new JTextArea("Philosopher Log");
        this.logMessages.setEditable(false);
        this.logMessages.setBackground(Color.DARK_GRAY);
        this.logMessages.setSelectedTextColor(Color.BLACK);
        this.logMessages.setForeground(Color.LIGHT_GRAY);
        this.logMessages.setBorder(BorderFactory.createCompoundBorder(
        this.logMessages.getBorder(), 
        BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        this.log = new JScrollPane(this.logMessages, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.log.setBackground(Color.LIGHT_GRAY);
        this.log.setBorder(null);
    }

    private void philosopherPanelInitialize() {
        this.philosopherView = new JPanel(new GridLayout(4, 5));
        int[] seats = {2, 5, 9, 16, 18};
        for(int i = 0; i < 20; i++) {
            for(int j = 0; j < seats.length; j++) {
                if(seats[j] == i) {
                    philosopherView.add(this.philosophers.get(j));
                    break;
                } else if(j == seats.length - 1) {
                    philosopherView.add(new JPanel());
                }
            }
        }
    }

    public void addLogMessage(String msg) {
        this.logMessages.append("\n" + msg);
    }
}
