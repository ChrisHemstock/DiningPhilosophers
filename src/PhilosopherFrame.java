import java.awt.Color;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.DecimalFormat;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;
import javax.swing.text.NumberFormatter;

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
    private JButton submitTicksButton;
    private JPanel dinnerControls;
    private JButton semaphorButton, monitorButton;
    //Log pane
    private JTextArea logMessages;
    //Philosopher panel
    private ArrayList<PhilosopherPanel> philosophers;
    

    public PhilosopherFrame() {
        frameInitialize();

        controlPanelInitialize();
        this.frame.add(control, BorderLayout.SOUTH);

        logPaneInitialize();
        this.frame.add(log, BorderLayout.EAST);

        philosopherPanelInitialize();
        this.frame.add(this.philosopherView, BorderLayout.CENTER);

        this.frame.setVisible(true);
    }

    private void frameInitialize() {
        this.frame = new JFrame();

        this.frame.setTitle("Dining Philosophers");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setSize(700, 900);
        this.frame.setLocationRelativeTo(null); //null centers the frame on the screen
        this.frame.setLayout(new BorderLayout());
    }

    private void tickSpinnerInitialize() {
        final double value = 1.75;
        final double minimum = 0.01;
        final double maximum = 50.00;
        final double stepSize = 0.25;
        final int inputWidth = 50;

        this.tickSpinner = new JSpinner();
        this.ticksText = new JLabel("Ticks/s:");

        // Set the model values
        SpinnerNumberModel model = new SpinnerNumberModel(value, minimum, maximum, stepSize); 
        this.tickSpinner.setModel(model);

        // Set the demensions of the input box
        Dimension prefSize = this.tickSpinner.getPreferredSize();
        prefSize = new Dimension(inputWidth, prefSize.height);
        this.tickSpinner.setPreferredSize(prefSize);

        // Allow inputs to only be in the form 0.00 with no characters
        JFormattedTextField txt = ((JSpinner.NumberEditor) this.tickSpinner.getEditor()).getTextField();
        ((NumberFormatter) txt.getFormatter()).setAllowsInvalid(false);
        NumberFormatter formatter = (NumberFormatter) txt.getFormatter();
        DecimalFormat decimalFormat = new DecimalFormat("0.00");         
        formatter.setFormat(decimalFormat);         
        formatter.setAllowsInvalid(false);
    }


    private void controlPanelInitialize() {
        this.control = new JPanel(new BorderLayout());
        this.control.setBackground(Color.LIGHT_GRAY);

        this.playControls = new JPanel();
        
        this.playButton = new JButton("Play");
        this.playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (PhilosopherPanel philosopher : philosophers) {
                    philosopher.startThread();
                }
            }
        });
        this.pauseButton = new JButton("Pause");
        this.resetButton = new JButton("Reset");
        this.tickSpinnerInitialize();
        this.submitTicksButton = new JButton("Apply Ticks");
        this.submitTicksButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                double ticksPerSec = (double) tickSpinner.getValue(); // t/s => 
                int msPerTick = (int) (1000 / ticksPerSec);
                System.out.println(ticksPerSec);
                System.out.println(msPerTick);

                for (PhilosopherPanel philosopher : philosophers) {
                    philosopher.getPhilosopher().setMsPerTick(msPerTick);
                }
            }
            
        });
        
        this.playControls.add(this.playButton);
        this.playControls.add(this.pauseButton);
        this.playControls.add(this.resetButton);
        this.playControls.add(this.ticksText);
        this.playControls.add(this.tickSpinner);
        this.playControls.add(this.submitTicksButton);
        
        this.playControls.setBackground(Color.LIGHT_GRAY);

        this.control.add(playControls, BorderLayout.NORTH);

        //Diner Controls
        this.dinnerControls = new JPanel();

        this.semaphorButton = new JButton("Semaphore Dinner");
        this.monitorButton = new JButton("Monitor Dinner");

        this.dinnerControls.add(this.semaphorButton);
        this.dinnerControls.add(this.monitorButton);
        
        this.dinnerControls.setBackground(Color.LIGHT_GRAY);

        this.control.add(this.dinnerControls, BorderLayout.SOUTH);
    }

    private void logPaneInitialize() {
        this.logMessages = new JTextArea("Philosopher Log       \n");
        this.logMessages.setEditable(false);
        this.logMessages.setBackground(Color.DARK_GRAY);
        this.logMessages.setSelectedTextColor(Color.BLACK);
        this.logMessages.setForeground(Color.LIGHT_GRAY);
        this.logMessages.setBorder(BorderFactory.createCompoundBorder(
            this.logMessages.getBorder(), 
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        this.log = new JScrollPane(this.logMessages, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.log.setBackground(Color.LIGHT_GRAY);
        this.log.setBorder(null);
    }

    private void philosopherPanelInitialize() {
        //Create PhilosopherPanels and add them to the list
        try {
            this.philosophers = new ArrayList<PhilosopherPanel>();
            philosophers.add(new PhilosopherPanel("Plato", this.logMessages));
            philosophers.add(new PhilosopherPanel("Socrates", this.logMessages));
            philosophers.add(new PhilosopherPanel("Aristotle", this.logMessages));
            philosophers.add(new PhilosopherPanel("Diogenes", this.logMessages));
            philosophers.add(new PhilosopherPanel("Epicurus", this.logMessages));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
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
}
