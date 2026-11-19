
import java.util.Scanner;

public class Pb2 {

    static String reverseEachWord(String sentence) {

        String[] words = sentence.split(" ");

        String result = "";

        for (int i = 0; i < words.length; i++) {

            StringBuilder sb = new StringBuilder();

            for (int j = words[i].length() - 1; j >= 0; j--) {
                sb.append(words[i].charAt(j));
            }

            result = result + sb.toString();

            if (i < words.length - 1) {
                result = result + " ";
            }
        }

        return result;
    }

    public static void main(String[] args) {

        try (Scanner sc = new Scanner(System.in)) {

            System.out.print("Enter a sentence: ");
            String sentence = sc.nextLine();

            String reversed = reverseEachWord(sentence);

            System.out.println("Reversed Sentence: " + reversed);
        }
    }
}

