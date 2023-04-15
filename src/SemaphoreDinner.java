import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class SemaphoreDinner extends Dinner{
    private Semaphore mutex = new Semaphore(1);
    private ArrayList<Semaphore> forks;

    public SemaphoreDinner(ArrayList<PhilosopherPanel> philosophersArray) {
        super(philosophersArray);
    }

    public void setForks(int count) {
        this.forks = new ArrayList<Semaphore>();
        for (int i = 0; i < this.getPhilosophersArray().size(); i++) {
            this.getForks().add(new Semaphore(1));
        }
    }

    public ArrayList<Semaphore> getForks() {
        return this.forks;
    }

    public void takeForks(int index) {
        System.out.println("Semaphore Dinner");
        // try {
        //     takeForksS.acquire(); // enter critical region
        //     if(this.checkForks(index)) {
                
        //     }
        // } catch (InterruptedException e) {
        //     e.printStackTrace();
        // }
        
        // // make philosopher hungry
        // // attempt to accuire two 2 forks
        // takeForksS.release();// leave critical region
        // // block if the two forks were not aquired
        
    }
          
         
    public void putForks(int i) {
        // enter critical region
        // make philosopher thinking
        // **check if left and right neighbor can eat 
        // exit critical region
    }
}
