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
    private JTextArea log;


    

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
        this.log = log;
    }

    public void setDinner(Dinner dinner) {
        getPhilosopher().setDinner(dinner);
    }

    private void setPictureLabel() throws IOException {
        PhilosopherStatus status = this.philosopher.getStatus();
        BufferedImage image;
        
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
        
    }

    public void resetThread() {

    }

    public Thread getThread() {
        return this.thread;
    }

    @Override
    public void notifyStatusChange(PhilosopherStatus status) {
        try {
            BorderLayout layout = (BorderLayout)this.getLayout();

            this.remove(layout.getLayoutComponent(BorderLayout.CENTER));
            setPictureLabel();
            this.validate();

            this.remove(layout.getLayoutComponent(BorderLayout.SOUTH));
            setStatusLabel();
            this.validate();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void addLogMessage(String msg) {
        try {
            this.getLog().append(msg + "\n");
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
