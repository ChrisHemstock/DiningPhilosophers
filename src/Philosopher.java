enum PhilosopherStatus {
    EATING,
    THINKING,
    HUNGRY;
};
    
public class Philosopher {
    private String name;
    //Top 5 names
    //1) Aristotle
    //2) Diogenes
    //3) Plato
    //4) Socrates
    //5) Epicurius
    private PhilosopherStatus status;

    

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
