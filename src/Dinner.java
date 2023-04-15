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

    private int nextPhilosopher(int philosopherIndex) {
        try {
            this.getPhilosophersArray().get(philosopherIndex + 1);
            return philosopherIndex + 1;
        } catch (Exception e) {
            return 0;
        }
    }

    private int prevPhilosopher(int philosopherIndex) {
        try {
            this.getPhilosophersArray().get(philosopherIndex - 1);
            return philosopherIndex - 1;
        } catch (Exception e) {
            return this.getPhilosophersArray().size() - 1;
        }
    }

    private Enum<PhilosopherStatus> getPhilosopherStatus(int philosopherIndex) {
        return this.getPhilosophersArray().get(philosopherIndex).getPhilosopher().getStatus();
    }

    public void start() {
        for (PhilosopherPanel philosopher : philosophersArray) {
            philosopher.getThread().start();
        }
    }


    public Boolean testForks(int threadIndex) {
        if (
            this.getPhilosopherStatus(threadIndex) == PhilosopherStatus.HUNGRY && 
            this.getPhilosopherStatus(this.nextPhilosopher(threadIndex)) != PhilosopherStatus.EATING &&
            this.getPhilosopherStatus(this.prevPhilosopher(threadIndex)) != PhilosopherStatus.EATING
        ) {
            return true;
        }
        return false;
    }
    public abstract void takeForks(int threadIndex);

    public abstract void putForks(int threadIndex);

}
