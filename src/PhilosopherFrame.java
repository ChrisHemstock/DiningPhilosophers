import java.awt.Color;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;

public class PhilosopherFrame {
    private JFrame frame;

    public PhilosopherFrame() {
        this.frame = new JFrame();
        initalize();
    }

    private void initalize() {
        frame.setTitle("Dining Philosophers");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(500, 480);
        frame.setLocationRelativeTo(null); //Null centers the frame on the screen
        frame.setLayout(new BorderLayout());
        
        //Control Panel
        JPanel control = new JPanel(new BorderLayout());
        control.setBackground(Color.GREEN);

        JPanel playControls = new JPanel();
        JSpinner ticks = new JSpinner();
        ticks.setValue(5);
        playControls.setBackground(Color.RED);
        playControls.add(new JButton("Play"));
        playControls.add(new JButton("Stop"));
        playControls.add(new JButton("Pause"));
        playControls.add(new JTextArea("Ticks/s:"));
        playControls.add(ticks);
        control.add(playControls, BorderLayout.NORTH);

        JPanel dinnerControls = new JPanel();
        dinnerControls.setBackground(Color.BLUE);
        dinnerControls.add(new JButton("Semaphore Dinner"));
        dinnerControls.add(new JButton("Monitor Dinner"));
        control.add(dinnerControls, BorderLayout.SOUTH);
        
        frame.add(control, BorderLayout.SOUTH);

        //Log Panel
        
        
        JTextArea logMessages = new JTextArea("Philosopher Log");
        logMessages.setEditable(false);
        JScrollPane log = new JScrollPane(logMessages, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        log.setBackground(Color.DARK_GRAY);

        frame.add(log, BorderLayout.EAST);

        frame.setVisible(true);
        for(int i = 0; i < 25; i++) {
            logMessages.append("\nmessage " + String.valueOf(i));
        }
        
    }
}
