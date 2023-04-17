import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;

public class PhilosopherPanel extends JPanel implements PhilosopherObserver {
    private Philosopher philosopher;
    private Thread thread;
    private JLabel pictureLabel;
    private JLabel nameLabel;
    private JLabel statusLabel;
    private static JTextArea log;


    

    public PhilosopherPanel(String name, JTextArea log, int index) throws IOException {
        super(new BorderLayout());
        this.philosopher = new Philosopher(name, index);
        this.philosopher.setObserver(this);
        setPictureLabel();
        setNameLabel();
        setStatusLabel();
        setLog(log);
        this.thread = new Thread(this.philosopher);
    }

    public JTextArea getLog() {
        return log;
    }

    public void setLog(JTextArea log) {
        PhilosopherPanel.log = log;
    }

    public void setDinner(Dinner dinner) {
        getPhilosopher().setDinner(dinner);
    }

    private void setPictureLabel() {
        PhilosopherStatus status = this.philosopher.getStatus();
        BufferedImage image;
        
        try {
            switch(status) {
                case EATING:
                    image = ImageIO.read(new File("img/PhilosopherEating.png"));
                    break;
                case HUNGRY:
                    image = ImageIO.read(new File("img/PhilosopherHungry.png"));
                    break;
                case THINKING:
                    image = ImageIO.read(new File("img/PhilosopherThinking.png"));
                    break;
                default:
                    throw new IOException();
            }
            this.pictureLabel = new JLabel(new ImageIcon(image), SwingConstants.CENTER);
            this.add(this.pictureLabel, BorderLayout.CENTER);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public Philosopher getPhilosopher() {
        return this.philosopher;
    }

    public void setNameLabel() {
        this.nameLabel = new JLabel(this.philosopher.getName(), SwingConstants.CENTER);

        this.add(this.nameLabel, BorderLayout.NORTH);
    }

    public void setStatusLabel() {
        String status = this.philosopher.getStatus().name();
        String name = this.philosopher.getName();

        this.addLogMessage(name + ": " + status);

        this.statusLabel = new JLabel(status, SwingConstants.CENTER);
        this.add(this.statusLabel, BorderLayout.SOUTH);
    }

    public void startThread() {
        this.thread.start();
    }

    public void pauseThread() {
        this.thread.interrupt();
    }

    public void resetThread() {

        this.philosopher.setStatus(PhilosopherStatus.THINKING);

    }

    public Thread getThread() {
        return this.thread;
    }

    @Override
    public void notifyStatusChange(PhilosopherStatus status) {
        BorderLayout layout = (BorderLayout)this.getLayout();
        try {

            this.remove(layout.getLayoutComponent(BorderLayout.CENTER));
            setPictureLabel();
            this.validate();
        } catch (NullPointerException e) {
            setPictureLabel();
            this.validate();
        } 

        try {
            this.remove(layout.getLayoutComponent(BorderLayout.SOUTH));
            setStatusLabel();
            this.validate();
        } catch (NullPointerException e) {
            setStatusLabel();
            this.validate();
        }
    }

    public void addLogMessage(String msg) {
        try {
            this.getLog().append(msg + "\n");
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public void clearLogMessages() {
        try {
            this.getLog().setText("Philosopher Log       \n");;
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
