import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by keta on 2014/05/30.
 */
public class Reader {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    static int readInt() {
        int value = 0;

        try {
            value = Integer.parseInt(br.readLine());
        } catch (Exception e) {
            System.out.println("Please input int value.");
        }

        return value;
    }

    static int readInt(String str) {
        System.out.print(str);

        return readInt();
    }


    static double readDouble(String str) {
        double value = 0.0;

        while (true) {
            try {
                System.out.print(str);
                value = Double.parseDouble(br.readLine());
                break;
            } catch (Exception e) {
                System.out.println("Please input double value.");
            }
        }

        return value;
    }

    static String readString() {
        String value = "";

        try {
            value = br.readLine();
        } catch (IOException e) {
            System.out.println("Please input string value.");
        }

        return value;
    }

    static String readString(String str) {
        System.out.print(str);

        return readString();
    }


    static String ask(String question, String accept) {
        String input;

        while (true) {
            input = Reader.readString(question);
            if (accept.indexOf(input.charAt(0)) != -1)
                return input;
        }
    }
}