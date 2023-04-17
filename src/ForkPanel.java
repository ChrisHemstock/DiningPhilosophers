import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class ForkPanel extends JPanel implements PhilosopherObserver {
    private JLabel pictureLabel;
    private int forkNumber;
    private Philosopher leftPhilosopher;
    private Philosopher rightPhilosopher;

    public ForkPanel(int forkNumber, Philosopher leftPhilosopher, Philosopher rightPhilosopher) {
        super(new BorderLayout());
        this.setForkNumber(forkNumber);
        this.setImage();
        this.setLeftPhilosopher(leftPhilosopher);
        this.setRightPhilosopher(rightPhilosopher);
        this.getLeftPhilosopher().setLeftForkObserver(this);
        this.getRightPhilosopher().setRightForkObserver(this);
    }

    public Philosopher getLeftPhilosopher() {
        return this.leftPhilosopher;
    }
    
    public void setLeftPhilosopher(Philosopher leftPhilosopher) {
        this.leftPhilosopher = leftPhilosopher;
    }

    public Philosopher getRightPhilosopher() {
        return this.rightPhilosopher;
    }

    public void setRightPhilosopher(Philosopher rightPhilosopher) {
        this.rightPhilosopher = rightPhilosopher;
    }

    public int getForkNumber() {
        return this.forkNumber;
    }

    public void setForkNumber(int forkNumber) {
        this.forkNumber = forkNumber;
    }

    public Boolean checkTaken() {
        if(
            this.getLeftPhilosopher().getStatus() != PhilosopherStatus.EATING && 
            this.getRightPhilosopher().getStatus() != PhilosopherStatus.EATING
        ) {
            return false;
        }
        return true;
    }

    public void setImage() {
        BufferedImage image;
        try {
            image = ImageIO.read(new File("img/Fork " + getForkNumber() + ".png"));
            this.pictureLabel = new JLabel(new ImageIcon(image), SwingConstants.CENTER);
            this.add(this.pictureLabel, BorderLayout.CENTER);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void notifyStatusChange(PhilosopherStatus status) {
        if(this.checkTaken()) {
            this.setVisible(false);;
        } else {
            this.setVisible(true);
        }
    }
}
