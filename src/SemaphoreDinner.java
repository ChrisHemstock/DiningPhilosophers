import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class SemaphoreDinner extends Dinner{
    private ArrayList<PhilosopherPanel> philosophersArray;
    private Semaphore takeForksS = new Semaphore(1);
    private ArrayList<Semaphore> forks;

    public SemaphoreDinner(ArrayList<PhilosopherPanel> philosophersArray) {
        this.philosophersArray = philosophersArray;
        for(int i = 0; i < philosophersArray.size(); i++) {
            forks.add(new Semaphore(1));
        }
    }

    // public void tick() {
    //     int index = 0;
    //     for (PhilosopherPanel philosopherPanel : philosophersArray) {
    //         Philosopher philosopher = philosopherPanel.getPhilosopher();

    //         philosopher.passTime();
    //         if(philosopher.getStatus() == PhilosopherStatus.HUNGRY) {
    //             this.takeForks(index);
    //         }
    //         index++;
    //     }
    // }

    public void philosopher(int i) {
        while (true) { 
            //Think think()
            //take both forks takeForks(i)
            //eat eat()
            //put both forks down putForks(i)
        } 
    }
    private void takeForks(int index) {
        try {
            takeForksS.acquire(); // enter critical region
            if(this.checkForks(index)) {
                
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // make philosopher hungry
        // attempt to accuire two 2 forks
        takeForksS.release();// leave critical region
        // block if the two forks were not aquired
        
    }

    private Boolean checkForks(int index) {
        Philosopher left;
        try {
            left = philosophersArray.get(index - 1).getPhilosopher();
        } catch (Exception e) {
            left = philosophersArray.get(philosophersArray.size() - 1).getPhilosopher();
        }

        Philosopher right;
        try {
            right = philosophersArray.get(index + 1).getPhilosopher();
        } catch (Exception e) {
            right = philosophersArray.get(0).getPhilosopher();
        }

        if(left.getStatus() != PhilosopherStatus.EATING || right.getStatus() != PhilosopherStatus.EATING) {
            return false;
        }
        return true;
    }

        
          
         
    private void putForks(int i) {
        // enter critical region
        // make philosopher thinking
        // **check if left and right neighbor can eat 
        // exit critical region
    }
}
