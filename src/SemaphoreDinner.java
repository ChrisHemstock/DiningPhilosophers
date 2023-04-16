import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class SemaphoreDinner extends Dinner{
    private Semaphore mutex = new Semaphore(1);
    private ArrayList<Semaphore> forks;

    public SemaphoreDinner(ArrayList<PhilosopherPanel> philosophersArray) {
        super(philosophersArray);
        this.setForks(this.getPhilosophersArray().size());
    }

    public void setForks(int count) {
        this.forks = new ArrayList<Semaphore>();
        for (int i = 0; i < count; i++) {
            this.getForks().add(new Semaphore(1));
        }
    }

    public ArrayList<Semaphore> getForks() {
        return this.forks;
    }

    @Override
    public void takeForks(int index) {
        try {
            mutex.acquire(); // enter critical region
            this.getPhilosophersArray().get(index).getPhilosopher().setStatus(PhilosopherStatus.HUNGRY);
            this.testForks(index);
            mutex.release();
            this.getForks().get(index).acquire();
        } catch (InterruptedException e) {
            System.out.println("takeForks() index: " + index + " status: " + this.getPhilosopherStatus(index).name());
            e.printStackTrace();
        }      
    }
          
    @Override  
    public void putForks(int index) {
        try {
            mutex.acquire();
            this.getPhilosophersArray().get(index).getPhilosopher().setStatus(PhilosopherStatus.THINKING);
            this.testForks(this.prevPhilosopher(index));
            this.testForks(this.nextPhilosopher(index));
            mutex.release();
        } catch (InterruptedException e) {
            System.out.println("putForks() index: " + index + " status: " + this.getPhilosopherStatus(index).name());
            e.printStackTrace();
        }
    }

    @Override
    public void testForks(int index) {
        if (
            this.getPhilosopherStatus(index) == PhilosopherStatus.HUNGRY && 
            this.getPhilosopherStatus(this.nextPhilosopher(index)) != PhilosopherStatus.EATING &&
            this.getPhilosopherStatus(this.prevPhilosopher(index)) != PhilosopherStatus.EATING
        ) {
            this.getPhilosophersArray().get(index).getPhilosopher().setStatus(PhilosopherStatus.EATING);
            this.getForks().get(index).release(); // I dont get why this is a release and not an aquire
        }
    }
}
