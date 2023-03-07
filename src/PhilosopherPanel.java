import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;

public class PhilosopherPanel extends JPanel{
    private Philosopher philosopher;
    
    private JLabel pictureLabel;
    private JLabel nameLabel;
    private JLabel statusLabel;

    public PhilosopherPanel(String name) throws IOException {
        super(new BorderLayout());
        this.philosopher = new Philosopher(name);
        setPictureLabel();
        setNameLabel();
        setStatusLabel();
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
        this.statusLabel = new JLabel(this.philosopher.getStatus().name(), SwingConstants.CENTER);
        this.add(this.statusLabel, BorderLayout.SOUTH);
    }
}
