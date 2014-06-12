package finalproject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * Created by keta on 2014/05/30.
 */
public class ThreadRunner extends Thread
{
    private String name;
    private int rest;
    private int speed;
    private int location = 0;

    private ArrayList observers = new ArrayList();
    private Random random = new Random();


    /**
     * Constructor
     * @param Name
     * @param RestPercentage
     * @param RunnerSpeed
     */
    public ThreadRunner( String Name, int RestPercentage, int RunnerSpeed )
    {
        name = Name;
        rest = RestPercentage;
        speed = RunnerSpeed;
    }


    public void run()
    {
        while( location < 1000 )
        {
            int r = random.nextInt(100);
            if( r < rest )
            {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            location += speed;
            notifyObservers();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void addObserver( Observer observer )
    {
        observers.add(observer);
    }

    public void notifyObservers()
    {
        Iterator it = observers.iterator();
        while( it.hasNext() )
        {
            Observer o = (Observer)it.next();
            o.update(this);
        }
    }

    public int getLocation()
    {
        return location;
    }

    public String getRunnerName()
    {
        return name;
    }
}
