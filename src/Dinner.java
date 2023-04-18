import java.util.ArrayList;

public abstract class Dinner {
    private ArrayList<PhilosopherPanel> philosophersArray;

    public ArrayList<PhilosopherPanel> getPhilosophersArray() {
        return this.philosophersArray;
    }

    public Dinner(ArrayList<PhilosopherPanel> philosophersArray) {
        this.setPhilosophersArray(philosophersArray);
    }
    
    public void setPhilosophersArray(ArrayList<PhilosopherPanel> philosophersArray) {
        this.philosophersArray = philosophersArray;
    }

    public int nextPhilosopher(int philosopherIndex) {
        try {
            this.getPhilosophersArray().get(philosopherIndex + 1);
            return philosopherIndex + 1;
        } catch (Exception e) {
            return 0;
        }
    }

    public int prevPhilosopher(int philosopherIndex) {
        try {
            this.getPhilosophersArray().get(philosopherIndex - 1);
            return philosopherIndex - 1;
        } catch (Exception e) {
            return this.getPhilosophersArray().size() - 1;
        }
    }

    public Enum<PhilosopherStatus> getPhilosopherStatus(int philosopherIndex) {
        return this.getPhilosophersArray().get(philosopherIndex).getPhilosopher().getStatus();
    }

    public void start() {
        for (PhilosopherPanel philosopher : philosophersArray) {
            System.out.println("Thread start");
            philosopher.getThread().start();
        }
    }

    public abstract void setNextStatus(int philosopherIndex);

    public abstract void testForks(int threadIndex);
    
    public abstract void takeForks(int threadIndex);

    public abstract void putForks(int threadIndex);

    public abstract void reset();

}
