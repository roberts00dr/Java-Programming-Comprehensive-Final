package finalproject;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Keisuke Ueda on 2014/05/30.
 * ThreadRunner class implements each runner.
 */
public class ThreadRunner extends Thread
{
    private String name;
    private int rest;
    private int speed;
    private int location = 0;

    private ArrayList<Observer> observers = new ArrayList<Observer>();
    private Random random = new Random();


    /**
     * Constructor
     * @param Name the runner's name
     * @param RestPercentage the runner rests based on the percentage
     * @param RunnerSpeed the runner's speed
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
        for (Observer o : observers) {
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
