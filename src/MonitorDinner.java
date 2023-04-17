import java.util.ArrayList;

public class MonitorDinner extends Dinner{

    public MonitorDinner(ArrayList<PhilosopherPanel> philosophersArray) {
        super(philosophersArray);
        //TODO Auto-generated constructor stub
    }

    @Override
    public synchronized void takeForks(int index) {
        System.out.println("Monitor Dinner");
        this.getPhilosophersArray().get(index).getPhilosopher().setStatus(PhilosopherStatus.HUNGRY);
        this.testForks(index);
        if (this.getPhilosopherStatus(index) == PhilosopherStatus.HUNGRY) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public synchronized void putForks(int index) {
        this.getPhilosophersArray().get(index).getPhilosopher().setStatus(PhilosopherStatus.THINKING);
        this.testForks(this.prevPhilosopher(index));
        this.testForks(this.nextPhilosopher(index));
    }

    @Override
    public synchronized void testForks(int index) {
        if (
            this.getPhilosopherStatus(index) == PhilosopherStatus.HUNGRY && 
            this.getPhilosopherStatus(this.nextPhilosopher(index)) != PhilosopherStatus.EATING &&
            this.getPhilosopherStatus(this.prevPhilosopher(index)) != PhilosopherStatus.EATING
        ) {
            this.getPhilosophersArray().get(index).getPhilosopher().setStatus(PhilosopherStatus.EATING);
            this.notify();
        }
    }
}
