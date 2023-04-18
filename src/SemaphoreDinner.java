import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class SemaphoreDinner extends Dinner{
    private Semaphore mutex = new Semaphore(1);
    private ArrayList<Semaphore> semPhilosophers;

    public SemaphoreDinner(ArrayList<PhilosopherPanel> philosophersArray) {
        super(philosophersArray);
        this.setSemPhilosophers(this.getPhilosophersArray().size());
    }

    public void setSemPhilosophers(int count) {
        this.semPhilosophers = new ArrayList<Semaphore>();
        for (int i = 0; i < count; i++) {
            this.getSemPhilosophers().add(new Semaphore(1));
        }
    }

    public ArrayList<Semaphore> getSemPhilosophers() {
        return this.semPhilosophers;
    }

    @Override
    public void takeForks(int index) {
        this.getPhilosophersArray().get(index).getPhilosopher().setStatus(PhilosopherStatus.HUNGRY);
        this.testForks(index);
        mutex.release();
        try {
            this.getSemPhilosophers().get(index).acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }    
    }
          
    @Override  
    public void putForks(int index) {
        this.getPhilosophersArray().get(index).getPhilosopher().setStatus(PhilosopherStatus.THINKING);
        this.testForks(this.prevPhilosopher(index));
        this.testForks(this.nextPhilosopher(index));
        mutex.release();
    }

    @Override
    public void testForks(int index) {
        if (
            this.getPhilosopherStatus(index) == PhilosopherStatus.HUNGRY && 
            this.getPhilosopherStatus(this.nextPhilosopher(index)) != PhilosopherStatus.EATING &&
            this.getPhilosopherStatus(this.prevPhilosopher(index)) != PhilosopherStatus.EATING
        ) {
            this.getPhilosophersArray().get(index).getPhilosopher().setStatus(PhilosopherStatus.EATING);
            this.getSemPhilosophers().get(index).release();
        }
    }

    @Override
    public void reset() {
        mutex.release();
        for (Semaphore semaphore : semPhilosophers) {
            semaphore.release();
        }
        for (PhilosopherPanel philosopherPanel : getPhilosophersArray()) {
            philosopherPanel.getPhilosopher().setStatus(PhilosopherStatus.THINKING);
        }
        getPhilosophersArray().get(0).clearLogMessages();
    }

    @Override
    public void setNextStatus(int philosopherIndex) {
        try {
            mutex.acquire();
            if(this.getPhilosopherStatus(philosopherIndex) == PhilosopherStatus.EATING) {
                this.putForks(philosopherIndex);
            } else {
                this.takeForks(philosopherIndex);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
