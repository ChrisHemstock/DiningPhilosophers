import java.util.Random;
 
enum PhilosopherStatus {
    EATING,
    THINKING,
    HUNGRY;
};
    
public class Philosopher implements Runnable {
    private String name;
    private PhilosopherStatus status;
    private int index;
    private int remainingTicks;
    private long msPerTick;
    private PhilosopherObserver observer;
    private PhilosopherObserver leftForkObserver;
    private PhilosopherObserver rightForkObserver;
    private static Dinner dinner;


    public Philosopher(String name, int index) {
        setName(name);
        setStatus(PhilosopherStatus.THINKING);
        setRemainingTicks(new Random().nextInt(15));
        setMsPerTick(500);
        setIndex(index);
    }

    public void setObserver(PhilosopherObserver observer) {
        this.observer = observer;
    }

    public void setRightForkObserver(PhilosopherObserver observer) {
        this.rightForkObserver = observer;
    }

    public void setLeftForkObserver(PhilosopherObserver observer) {
        this.leftForkObserver = observer;
    }

    public void setDinner(Dinner dinner) {
        Philosopher.dinner = dinner;
    }

    public Dinner getDinner() {
        return Philosopher.dinner;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return this.index;
    }

    public PhilosopherStatus getStatus() {
        return this.status;
    }

    public void setStatus(PhilosopherStatus status) {
        this.status = status;
        if(this.status == PhilosopherStatus.EATING || this.status == PhilosopherStatus.THINKING) {
            Random rand = new Random();
            this.setRemainingTicks(rand.nextInt(15));
        }
        if(observer != null) {
            observer.notifyStatusChange(status);
        }
        if(leftForkObserver != null) {
            leftForkObserver.notifyStatusChange(status);
        }
        if(rightForkObserver != null) {
            rightForkObserver.notifyStatusChange(status);
        }
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
        if(this.status == PhilosopherStatus.EATING) {
            this.getDinner().putForks(this.getIndex());
        } else {
            this.getDinner().takeForks(this.getIndex());
        }
    }

    public void setMsPerTick(long ms) {
        this.msPerTick = ms;
    }

    public long getMsPerTick() {
        return this.msPerTick;
    }

    public void tick() {
        System.out.println(this.getName() + " " + this.getRemainingTicks());
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
            String check = time + ": " + this.getMsPerTick(); //even though this does nothing it is necessary to prevent getMsPerTick() from being cached
            if(time > this.getMsPerTick()) {
                before = System.currentTimeMillis();
                tick();
            }
        }
    }
}
