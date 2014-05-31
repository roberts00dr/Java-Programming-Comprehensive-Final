public class Final implements Observer
{
    private boolean exitFlag = false;

    public static void main(String[] args) {
        new Final();
    }

    Final()
    {
        ThreadRunner Tortoise = new ThreadRunner("Tortoise", 0, 10);
        ThreadRunner Hare = new ThreadRunner("Hare", 90, 100);

        Tortoise.addObserver(this);
        Hare.addObserver(this);

        Hare.start();
        Tortoise.start();

        try {
            Hare.join();
            Tortoise.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
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
            exitFlag = true;
            System.out.println(name + ": I finished!");
        }
    }
}
