package finalproject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Keisuke Ueda on 2014/05/30.
 * This is a class which helps to read something from stdin.
 */
@SuppressWarnings("SameParameterValue")
public class Reader {
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    static public int readInt(String str)
    {
        int value = 0;
        boolean exitFlag = false;

        //noinspection PointlessBooleanExpression
        while( exitFlag == false )
        {
            System.out.print(str);
            try {
                value = Integer.parseInt(br.readLine());
                exitFlag = true;
            } catch (Exception e) {
                System.out.println("Please input int value.");
            }
        }

        return value;
    }


// --Commented out by Inspection START (2014/06/13 12:50):
//    static public double readDouble(String str) {
//        double value;
//
//        while (true) {
//            try {
//                System.out.print(str);
//                value = Double.parseDouble(br.readLine());
//                break;
//            } catch (Exception e) {
//                System.out.println("Please input double value.");
//            }
//        }
//
//        return value;
//    }
// --Commented out by Inspection STOP (2014/06/13 12:50)

    private static String readString() {
        String value = "";

        try {
            value = br.readLine();
        } catch (IOException e) {
            System.out.println("Please input string value.");
        }

        return value;
    }

    static public String readString(String str) {
        System.out.print(str);

        return readString();
    }


// --Commented out by Inspection START (2014/06/13 12:50):
//    static public String ask(String question, String accept) {
//        String input;
//
//        while (true) {
//            input = Reader.readString(question);
//            if (accept.indexOf(input.charAt(0)) != -1)
//                return input;
//        }
//    }
// --Commented out by Inspection STOP (2014/06/13 12:50)
}