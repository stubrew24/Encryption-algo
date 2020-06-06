package encryptdecrypt;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.*;

public class Main {

    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws FileNotFoundException {

        String mode = "enc";
        int key = 0;
        String data = "";
        File in = null;
        File out = null;
        String alg = "shift";

        for (int i = 0; i < args.length; i += 2) {
            switch(args[i]) {
                case "-mode":
                    mode = args[i + 1];
                    break;
                case "-key":
                    key = Integer.parseInt(args[i + 1]);
                    break;
                case "-data":
                    data = args[i + 1];
                    break;
                case "-in":
                    in = new File(args[i + 1]);
                    break;
                case "-out":
                    out = new File(args[i + 1]);
                    break;
                case "-alg":
                    alg = args[i + 1];
                    break;
                default:
                    break;
            }
        }

        if (in != null) {
            try {
                Scanner fileRead = new Scanner(in);
                while (fileRead.hasNext()) {
                    data += fileRead.nextLine();
                }
            } catch (Exception e) {
                System.out.println("File not found");
            }
        }

        String output;

        if (alg.equals("shift")){
            output = shift(data, mode.equals("enc") ? key : -key);
        } else {
            output = unicode(data, mode.equals("enc") ? key : -key);
        }

        System.out.println(output);

        if (out != null) {
            try {
                FileWriter writer = new FileWriter(out);
                writer.write(output);
                writer.close();
            } catch (Exception e) {
                System.out.println("File not found");
            }
        }
    }

    public static String unicode(String input, int key){
        int length = input.length();

        char[] chars = new char[length];

        for (int i = 0; i < length; i++) {
            chars[i] = flipChar(input.charAt(i), key);
        }

        return new String(chars);
    }

    public static String shift(String input, int key){
        int length = input.length();

        char[] chars = new char[length];

        for (int i = 0; i < length; i++) {
            chars[i] = shiftChar(input.charAt(i), key);
        }

        return new String(chars);
    }

    public static char shiftChar (char letter, int key){
        // 65 - 90  // 97 - 122
        if (letter <  65 || letter > 122 || (letter > 90 && letter < 97)) {
            return letter;
        }

        if (letter >= 65 && letter <= 90 ) {
            letter += key;
            if (letter > 90) {
                letter -= 26;
            }
            if (letter < 65) {
                letter += 26;
            }
        }

        if (letter >= 97 && letter <= 122 ) {
            letter += key;
            if (letter > 122) {
                letter -= 26;
            }
            if (letter < 97) {
                letter += 26;
            }
        }
        return letter;
    }

    public static char flipChar (char letter, int key){
        int num = letter + key;
        return (char) num;
    }
}
