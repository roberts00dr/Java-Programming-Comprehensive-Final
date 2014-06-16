package datasource;

import finalproject.Reader;
import finalproject.ThreadRunner;

import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by Keisuke Ueda on 2014/05/31.
 * This class reads a text file to create ThreadRunner objects.
 */
@SuppressWarnings("UnusedParameters")
public class TextFile implements DataSource
{
    private BufferedReader bufferedReader = null;

    /**
     * Constructor
     *
     * @throws Exception is thrown when the specified file is not found.
     */
    public TextFile() throws Exception {
        String filename = Reader.readString("Enter text file name: ");
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
        } catch (FileNotFoundException e) {
            System.out.println("File: " + filename + " is not found.");
            System.out.println("");
        }

        if (bufferedReader == null) {
            throw new Exception();
        }
    }

// --Commented out by Inspection START (2014/06/13 12:50):
//    /**
//     * Lazy test
//     *
//     * @param i makes no sense.
//     */
//    @SuppressWarnings("This is a constructor for lazy test.")
//    public TextFile(int i) {
//        String input = "";
//        if (parse(input) != null)
//            System.err.println(input + " should return null but is not null.");
//
//        input = "Taro";
//        if (parse(input) != null)
//            System.err.println(input + " should return null but is not null.");
//
//        input = "Taro Jiro";
//        if (parse(input) != null)
//            System.err.println(input + " should return null but is not null.");
//
//        input = "Taro Jiro Sabro";
//        if (parse(input) != null)
//            System.err.println(input + " should return null but is not null.");
//
//        input = "Taro 10 Sabro";
//        if (parse(input) != null)
//            System.err.println(input + " should return null but is not null.");
//
//        input = "Taro Jiro 10";
//        if (parse(input) != null)
//            System.err.println(input + " should return null but is not null.");
//
//        input = "Taro 20 10";
//        if (parse(input) == null)
//            System.err.println(input + " should not return null but is null.");
//
//        input = "Taro 20 10 30";
//        if (parse(input) == null)
//            System.err.println(input + " should not return null but is null.");
//
//        input = "Taro Jiro Sabro shiro";
//        if (parse(input) != null)
//            System.err.println(input + " should return null but is not null.");
//    }
// --Commented out by Inspection STOP (2014/06/13 12:50)

    /**
     * Get ArrayList object which contains ThreadRunner objects.
     */
    @Override
    public ArrayList<ThreadRunner> getRunners() {
        ArrayList<ThreadRunner> runners = new ArrayList<ThreadRunner>();

        try {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                ThreadRunner runner = parse(line);
                if (runner != null)
                    runners.add(runner);
            }
        } catch (IOException e) {
            System.err.println("An error has occured when reading a line from the input text file.");
        }

        return runners;
    }

    /**
     * This method creates a ThreadRunner instance from a line of text;
     *
     * @param line is a line from the input text file.
     */
    private ThreadRunner parse(String line) {
        String seperator = "/ \t";
        StringTokenizer st = new StringTokenizer(line, seperator);
        if (st.countTokens() >= 3) {
            String name = st.nextToken();
            String s = st.nextToken();
            String r = st.nextToken();

            try {
                int speed = Integer.parseInt(s);
                int rest = Integer.parseInt(r);

                try {
                    return new ThreadRunner(name, rest, speed);
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            } catch (NumberFormatException e) {
                System.out.println("NumberFormatException is raised.");
                System.out.println("line: " + line);
            }
        }

        // otherwise, returns null.
        return null;
    }
}
