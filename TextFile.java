import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by keta on 2014/05/31.
 */
public class TextFile extends DataSource
{
    private static String seperator = "/ \t";

    public ArrayList getRunners()
    {
        ArrayList runners = new ArrayList();
        BufferedReader bufferedReader = null;

        while( bufferedReader == null )
        {
            String filename = Reader.readString("Enter text file name: ");
            try
            {
                bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream( filename )));
            }
            catch (FileNotFoundException e)
            {
                System.out.println("File: " + filename + " is not found. Please enter again.");
            }
        }

        String line;
        try {
            while((line = bufferedReader.readLine()) != null )
            {
                StringTokenizer st = new StringTokenizer(line, seperator);
                if( st.countTokens() >= 3 )
                {
                    String name = st.nextToken();
                    String s = st.nextToken();
                    String r = st.nextToken();

                    try
                    {
                        int speed = Integer.parseInt(s);
                        int rest = Integer.parseInt(r);

                        ThreadRunner runner = new ThreadRunner(name, rest, speed);
                        runners.add(runner);
                    }
                    catch(NumberFormatException e)
                    {
                        System.out.println("NumberFormatException is raised.");
                        System.out.println("line: " + line);
                    }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return runners;
    }
}
