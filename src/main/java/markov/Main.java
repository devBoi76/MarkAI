package markov;

import javax.security.auth.login.LoginException;
import java.io.Console;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, LoginException {
        Console console = System.console();

        if (console == null) {
            System.out.println("Console is not available to current JVM process");
        }

        // BOT CODE STARTS

        Bot markAI = new Bot();
        String[] botArgs = {args[1]};
        Bot.main(botArgs);

        // BOT CODE ENDS
        /*
        markMessage input1 = new markMessage(console.readLine("Enter something: "));
        input1.getFrequency();
        input1.writeNextWords();

        Random random = new Random();

        String root = input1.splitWords[random.nextInt(input1.splitWords.length)];
        System.out.println("BEHOLD! Message: ");
        for(int i = 0; i < random.nextInt(10) + 20; i++){
            System.out.print(root + " ");
            Word branch = new Word(new Word(root).getBranch());

            root = branch.root;

        }
         */
    }

}
