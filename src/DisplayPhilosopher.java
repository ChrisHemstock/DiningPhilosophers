import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class DisplayPhilosopher {
    private Philosopher philosopher;
    private BufferedImage image;

    public DisplayPhilosopher(String name) throws IOException {
        this.philosopher = new Philosopher(name);
        setImage();
    }

    private void setImage() throws IOException {
        PhilosopherStatus status = this.philosopher.getStatus();

        switch(status) {
            case EATING:
                this.image = ImageIO.read(new File("img/PhilosopherEating.png"));
                break;
            case HUNGRY:
                this.image = ImageIO.read(new File("img/PhilosopherHungry.png"));
                break;
            case THINKING:
                this.image = ImageIO.read(new File("img/PhilosopherThinking.png"));
                break;
            default:
                throw new IOException();
        }
    }

    public BufferedImage getImage() {
        return this.image;
    }

    public Philosopher getPhilosopher() {
        return this.philosopher;
    }
}
