import java.util.Random;
 
enum PhilosopherStatus {
    EATING,
    THINKING,
    HUNGRY;
};
    
public class Philosopher implements Runnable {
    private String name;
    private PhilosopherStatus status;
    private int remainingTicks;
    private long msPerTick;
    private PhilosopherObserver observer;


    public Philosopher(String name) {
        setName(name);
        setStatus(PhilosopherStatus.THINKING);
        setRemainingTicks(new Random().nextInt(15));
        setMsPerTick(500);

    }

    public void setObserver(PhilosopherObserver observer) {
        this.observer = observer;
    }

    public PhilosopherStatus getStatus() {
        return this.status;
    }

    public void setStatus(PhilosopherStatus status) {
        if(observer != null) {
            observer.notifyStatusChange(status);
        }
        
        this.status = status;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRemainingTicks(int ticks) {
        this.remainingTicks = ticks;
    }


    public int getRemainingTicks() {
        return this.remainingTicks;
    }

    public void setNextStatus() {
        Random rand = new Random();
        if(this.status == PhilosopherStatus.EATING) {
            this.setStatus(PhilosopherStatus.THINKING);
            System.out.println("status changed to thinking");
            this.setRemainingTicks(rand.nextInt(15));
        } else if(this.status == PhilosopherStatus.THINKING) {
            System.out.println("status changed to hungry");
            this.setStatus(PhilosopherStatus.HUNGRY);
        } else {
            this.setStatus(PhilosopherStatus.EATING);
            System.out.println("status changed to eating");
            this.setRemainingTicks(rand.nextInt(15));
        }
    }

    //going to have to create a thread and pass a philosopher into the thread and then run philosopher.start()
    
    

    public void setMsPerTick(long ms) {
        this.msPerTick = ms;
    }

    public long getMsPerTick() {
        return this.msPerTick;
    }

    public void tick() {
        this.setRemainingTicks(this.getRemainingTicks() - 1);
        if(this.getRemainingTicks() <= 0) {
            this.setNextStatus();
        }
        
    }

    @Override
    public void run() {
        long before = System.currentTimeMillis();
        while(true) {
            long time = System.currentTimeMillis() - before;
            if(time > this.getMsPerTick()) {
                before = System.currentTimeMillis();
                tick();
            }
        }
    }
}
