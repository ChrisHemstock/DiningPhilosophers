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
    private JButton playButton, resetButton;
    private JLabel ticksText;
    private JSpinner tickSpinner;
    private JButton submitTicksButton;
    private JPanel dinnerControls;
    private JButton semaphorButton, monitorButton;
    //Log pane
    private JTextArea logMessages;
    //Philosopher panel
    private ArrayList<PhilosopherPanel> philosophers;
    private ArrayList<ForkPanel> forks;
    private Dinner dinner;
    

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
        final int inputWidth = 70;

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
        
        this.playButton = new JButton("Play/Pause");
        this.playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(philosophers.get(0).getThread().isAlive()) {
                    philosophers.get(0).getPhilosopher().switchPaused();
                } else {
                    System.out.println("Start");
                    getDinner().start();
                }
                
            }
        });

        this.resetButton = new JButton("Reset");
        this.resetButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                philosophers.get(0).getPhilosopher().setPaused(true);
                philosophers.get(0).getPhilosopher().getDinner().reset();
            }
            
        });
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
        this.playControls.add(this.resetButton);
        this.playControls.add(this.ticksText);
        this.playControls.add(this.tickSpinner);
        this.playControls.add(this.submitTicksButton);
        
        this.playControls.setBackground(Color.LIGHT_GRAY);

        this.control.add(playControls, BorderLayout.NORTH);

        //Diner Controls
        this.dinnerControls = new JPanel();

        this.semaphorButton = new JButton("Semaphore Dinner");
        semaphorButton.setBackground(Color.WHITE);
        semaphorButton.setForeground(Color.BLUE);
        this.semaphorButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                philosophers.get(0).getPhilosopher().setPaused(true);
                philosophers.get(0).getPhilosopher().getDinner().reset();
                setDinner(new SemaphoreDinner(philosophers));
                semaphorButton.setBackground(Color.WHITE);
                semaphorButton.setForeground(Color.BLUE);
                monitorButton.setBackground(Color.DARK_GRAY);
                monitorButton.setForeground(Color.GRAY);
            }
        });
        this.monitorButton = new JButton("Monitor Dinner");
        monitorButton.setBackground(Color.DARK_GRAY);
        monitorButton.setForeground(Color.GRAY);
        this.monitorButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                philosophers.get(0).getPhilosopher().setPaused(true);
                philosophers.get(0).getPhilosopher().getDinner().reset();
                setDinner(new MonitorDinner(philosophers));
                monitorButton.setBackground(Color.WHITE);
                monitorButton.setForeground(Color.BLUE);
                semaphorButton.setBackground(Color.DARK_GRAY);
                semaphorButton.setForeground(Color.GRAY);
            }
        });
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

    public void setDinner(Dinner dinner) {
        this.dinner = dinner;
        this.philosophers.get(0).setDinner(dinner);
    }

    public Dinner getDinner() {
        return this.dinner;
    }

    private void philosopherPanelInitialize() {
        //Create PhilosopherPanels and add them to the list
        try {
            this.philosophers = new ArrayList<PhilosopherPanel>();
            philosophers.add(new PhilosopherPanel("Plato", this.logMessages, 0));
            philosophers.add(new PhilosopherPanel("Socrates", this.logMessages, 1));
            philosophers.add(new PhilosopherPanel("Aristotle", this.logMessages, 2));
            philosophers.add(new PhilosopherPanel("Diogenes", this.logMessages, 3));
            philosophers.add(new PhilosopherPanel("Epicurus", this.logMessages, 4));
            
            setDinner(new SemaphoreDinner(philosophers));
            this.forks = new ArrayList<ForkPanel>();
            forks.add(new ForkPanel(1, philosophers.get(0).getPhilosopher(), philosophers.get(4).getPhilosopher()));
            forks.add(new ForkPanel(2, philosophers.get(1).getPhilosopher(), philosophers.get(0).getPhilosopher()));
            forks.add(new ForkPanel(4, philosophers.get(4).getPhilosopher(), philosophers.get(3).getPhilosopher()));
            forks.add(new ForkPanel(3, philosophers.get(2).getPhilosopher(), philosophers.get(1).getPhilosopher()));
            forks.add(new ForkPanel(5, philosophers.get(3).getPhilosopher(), philosophers.get(2).getPhilosopher()));
            
            philosophers.get(0).getPhilosopher().setLeftForkObserver(forks.get(1));
            philosophers.get(0).getPhilosopher().setRightForkObserver(forks.get(0));
            philosophers.get(1).getPhilosopher().setLeftForkObserver(forks.get(3));
            philosophers.get(1).getPhilosopher().setRightForkObserver(forks.get(1));
            philosophers.get(2).getPhilosopher().setLeftForkObserver(forks.get(4));
            philosophers.get(2).getPhilosopher().setRightForkObserver(forks.get(3));
            philosophers.get(3).getPhilosopher().setLeftForkObserver(forks.get(2));
            philosophers.get(3).getPhilosopher().setRightForkObserver(forks.get(4));
            philosophers.get(4).getPhilosopher().setLeftForkObserver(forks.get(0));
            philosophers.get(4).getPhilosopher().setRightForkObserver(forks.get(2));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        this.philosopherView = new JPanel(new GridLayout(4, 5));
        int[] seats = {2, 9, 18, 16, 5};
        int[] forkSpots = {1, 3, 10, 14, 17};
        for(int i = 0; i < 20; i++) {
            Boolean found = false;
            for(int j = 0; j < seats.length; j++) {
                if(seats[j] == i) {
                    philosopherView.add(this.philosophers.get(j));
                    found = true;
                    break;
                }
            }
            if(!found) {
                for(int j = 0; j < forkSpots.length; j++) {
                    if(forkSpots[j] == i) {
                        philosopherView.add(this.forks.get(j));
                        found = true;
                        break;
                    }
                }
            }
            if(!found) {
                philosopherView.add(new JPanel());
            }
        }
    }
}
