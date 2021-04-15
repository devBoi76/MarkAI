package markov;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.Console;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Random;

public class Utilities {
    // Get no. of strings in string array
    static int getCount(String[]arr, String s) {
        int counter = 0;
        for (int j = 0; j < arr.length; j++) {
            // Check if string is on index, if yes increase counter
            if (s.equals(arr[j])) {
                counter++;
            }
        }
        return counter;
    }

    public static void processMessageForBot(MessageReceivedEvent inputEevent) throws IOException {
        Console console = System.console();

        if (console == null) {
            System.out.println("Console is not available to current JVM process");
            return;
        }

        Message inputDiscordMessage = inputEevent.getMessage();
        markMessage input1 = new markMessage(inputDiscordMessage.getContentRaw());
        MessageChannel channel = inputEevent.getChannel();
        System.out.println("Recieved Message: " + input1.message);

        if(!inputDiscordMessage.getAuthor().isBot() && input1.message.toLowerCase().contains("!markai")) {

            input1.getFrequency();
            //input1.writeNextWords();

            Random random = new Random();
            File wordsDir = new File("words/");
            FilenameFilter filter = new FilenameFilter() {
                public boolean accept(File wordsDir, String name) {
                    return !name.equals("next.json");
                }
            };
            File[] files = wordsDir.listFiles(filter);

            File file = files[random.nextInt(files.length)];

            String root = file.getName().replace("next.json", "");
            String outputMessage = "";
            for (int i = 0; i < random.nextInt(10) + 5; i++) {
                outputMessage += (root + " ");
                Word branch = new Word(new Word(root).getBranch());
                root = branch.root;


            }
            channel.sendMessage(outputMessage + " ").queue();
        }else if (!inputDiscordMessage.getAuthor().isBot() && !input1.message.toLowerCase().contains("!markai")){
            input1.getFrequency();
            input1.writeNextWords();
        }
    }
}
