package markov;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.IOException;

import static java.lang.Integer.parseInt;

public class Main {
    static String token;
    static Integer minMessageLength;
    static Integer lengthVariation;
    static String status;
    static String triggerPhrase;
    static Integer maxMessageLength;

    public static void main(String[] args) throws LoginException, IOException {
        // Create Folders
        new File("words").mkdirs();
        new File("config").mkdirs();

        System.out.println("Token: " + Utilities.loadConfig("Token"));
        token = Utilities.loadConfig("Token");

        System.out.println("MinMessageLength: " + Utilities.loadConfig("MinMessageLength"));
        minMessageLength = parseInt(Utilities.loadConfig("MinMessageLength"));

        System.out.println("LengthVariation: " + Utilities.loadConfig("LengthVariation"));
        lengthVariation = parseInt(Utilities.loadConfig("LengthVariation"));

        System.out.println("Status: " + Utilities.loadConfig("Status"));
        status = Utilities.loadConfig("Status");

        System.out.println("Trigger Phrase: " + Utilities.loadConfig("TriggerPhrase"));
        triggerPhrase = Utilities.loadConfig("TriggerPhrase");

        System.out.println("MaxMSGLength: " + Utilities.loadConfig("MaxMSGLength"));
        maxMessageLength = parseInt(Utilities.loadConfig("MaxMSGLength"));

        String[] botArgs = {token, status, triggerPhrase};
        Bot.main(botArgs);
    }
}
