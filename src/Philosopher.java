public class Philosopher {
    private String name;
    private PhilosopherStatus status;

    private enum PhilosopherStatus {
        EATING,
        THINKING,
        HUNGRY;
    };

    public Philosopher(String name) {
        setName(name);
        setStatus(PhilosopherStatus.THINKING);
    }

    public PhilosopherStatus getStatus() {
        return this.status;
    }

    public void setStatus(PhilosopherStatus status) {
        this.status = status;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
