import java.util.ArrayList;

public class MonitorDinner extends Dinner{

    public MonitorDinner(ArrayList<PhilosopherPanel> philosophersArray) {
        super(philosophersArray);
        //TODO Auto-generated constructor stub
    }

    @Override
    public void takeForks(int threadIndex) {
        System.out.println("Monitor Dinner");
    }

    @Override
    public void putForks(int threadIndex) {

    }

    @Override
    public void testForks(int threadIndex) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'testForks'");
    }
    
}
