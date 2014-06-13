package finalproject;

import datasource.DataSource;
import datasource.DerbyDB;
import datasource.TextFile;
import datasource.XML;

import java.util.ArrayList;
import java.util.Iterator;

public class Final implements Observer
{
    private ArrayList rank = new ArrayList();

    /**
     * the main function which just execute run() method.
     *
     * @param args is not used.
     */
    public static void main(String[] args) {
        new Final().run();
    }

    /**
     * The main routine.
     * Shows welcome message.
     * Declares an ArrayList object to contain ThreadRunner objects.
     * Shows menu.
     * Processes menu.
     * Then, execute race.
     *
     * @return is null when this program is going to end.
     */
    private boolean mainRoutine()
    {
        System.out.println("Welcome to the Marathon Race Runner Program");

        ArrayList runners = null;

        boolean continueFlag = false;
        int choice;

        while( continueFlag == false )
        {
            DataSource source;
            showMenu();
            choice = Reader.readInt("Enter your choice: ");

            switch( choice )
            {
                case 1:
                    source = new DerbyDB();
                    runners = source.getRunners();
                    continueFlag = true;
                    break;
                case 2:
                    try {
                        source = new XML();
                        runners = source.getRunners();
                        continueFlag = true;
                    } catch (Exception ignored) {

                    }
                    break;
                case 3:
                    try {
                        source = new TextFile();
                        runners = source.getRunners();
                        continueFlag = true;
                    } catch (Exception ignored) {

                    }
                    break;
                case 4:
                    runners = new ArrayList<ThreadRunner>();
                    runners.add(new ThreadRunner("Tortoise", 0, 10));
                    runners.add(new ThreadRunner("Hare", 90, 100));
                    continueFlag = true;
                    break;
                case 5:
                    return true;
                default:
                    System.out.println("Please input a number between 1 and 5.");
                    break;
            }
        }

        if (runners.size() == 0)
        {
            System.err.println("There is no runners!");

        } else {
            executeRace(runners);
        }

        return false;
    }


    /**
     * Runs ThreadRunner objects
     * and shows the result.
     *
     * @param runners is an ArrayList which contains ThreadRunner objects.
     */
    private void executeRace(ArrayList<ThreadRunner> runners) {
        if (runners != null) {
            Iterator it = runners.iterator();
            while (it.hasNext()) {
                ThreadRunner r = (ThreadRunner) it.next();
                r.addObserver(this);
                r.start();
            }

            try {
                it = runners.iterator();
                while (it.hasNext()) {
                    ThreadRunner r = (ThreadRunner) it.next();
                    r.join();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            it = rank.iterator();
            if (it.hasNext()) {
                System.out.println("The race is over! The " + it.next() + " is the winner.");
                System.out.println("");
            }

            while (it.hasNext()) {
                System.out.println(it.next() + ": You beat me fair and square.");
            }
        }
    }

    /**
     * show menu
     */
    private void showMenu() {
        System.out.println("Select your data source:");
        System.out.println("");
        System.out.println("1. Derby database");
        System.out.println("2. XML file");
        System.out.println("3. Text file");
        System.out.println("4. Default two runners");
        System.out.println("5. Exit");
        System.out.println("");
    }

    /**
     * run mainRoutine
     * do loop until mainRoutine() returns true.
     */
    public void run()
    {
        while( true )
        {
            rank.clear();
            boolean flag = mainRoutine();
            if( flag == true )
                break;
            else {
                System.out.println("Press any key to continue . . .");
                Reader.readString("");
            }
        }


        System.out.println("Thank you for using my Marathon Race Program");
    }


    @Override
    /**
     * Observer pattern.
     */
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
