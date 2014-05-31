import java.util.ArrayList;
import java.util.Iterator;

public class Final implements Observer
{
    private ArrayList rank = new ArrayList();

    public static void main(String[] args) {
        new Final();
    }

    Final()
    {
        ArrayList runners = new ArrayList();

        runners.add(new ThreadRunner("Tortoise", 0, 10));
        runners.add(new ThreadRunner("Hare", 90, 100));
        runners.add(new ThreadRunner("Dog", 40, 50));
        runners.add(new ThreadRunner("Cat", 75, 30));

        Iterator it = runners.iterator();
        while( it.hasNext() ) {
            ThreadRunner r = (ThreadRunner)it.next();
            r.addObserver(this);
            r.start();
        }

        try {
            it = runners.iterator();
            while( it.hasNext() ) {
                ThreadRunner r = (ThreadRunner)it.next();
                r.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        it = rank.iterator();
        if( it.hasNext() )
        {
            System.out.println("The race is over! The " + it.next() + " is the winner.");
            System.out.println("");
        }

        while( it.hasNext() )
        {
            System.out.println(it.next() + ": You beat me fair and square.");
        }


    }

    @Override
    public void update(ThreadRunner t)
    {
        String name = t.getRunnerName();
        int location = t.getLocation();

        System.out.println(name + " : " + location);

        if( location >= 1000 )
        {
            System.out.println(name + ": I finished!");
            rank.add(name);
        }
    }
}
