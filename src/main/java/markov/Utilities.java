package markov;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.Console;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Utilities {
    // Get no. of strings in string array
    static int getCount(String[] arr, String s) {
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
        input1.formatWords();

        if (!inputDiscordMessage.getAuthor().isBot() && input1.message.toLowerCase().contains("markai")) {

            input1.getFrequency();
            //input1.writeNextWords(); Don't learn from bot mentions
            Random random = new Random();
            File wordsDir = new File("words/");
            FilenameFilter filter = new FilenameFilter() {
                public boolean accept(File wordsDir, String name) {
                    return !name.equals("next.json");
                }
            };

            File file = new File("words/BEGIN.json");

            String root; //file.getName().replace("next.json", "");
            ObjectMapper mapper = new ObjectMapper();
            // Array of possible 1st wo rds and their values
            HashMap<String, Integer> begins = new HashMap<>();
            // Read them
            begins = mapper.readValue(file, begins.getClass());
            // Randomise them based on a weighed average
            root = weighedRandom(begins);
            // Make the output message an empty string
            String outputMessage = "";
            // Randomise message length
            for (int i = 0; i < random.nextInt(20) + 10; i++) {
                outputMessage += (root + " ");
                System.out.print(root + " ");
                Word branch = new Word(new Word(root).getBranch());
                root = branch.root;
                // Continue message if many options
                if (branch.branchFreq.values().size() > 3 && i > 1 && i < 20) {
                    i--;
                }


            }
            channel.sendMessage(outputMessage + " ").queue();
        } else if (!inputDiscordMessage.getAuthor().isBot() && !input1.message.toLowerCase().contains("markai") && input1.splitWords.length > 2) {
            input1.getFrequency();
            input1.writeNextWords();
            input1.saveFirstWord();
        }
    }
        // Get a weighed random value from HashMap of String:Integer
        public static String weighedRandom(HashMap<String, Integer> input) {
            Random random = new Random();
            // Array of only values fron the HashMap
            Integer[] valueArray = input.values().toArray(new Integer[input.values().size()]);
            // All keys (nextwords) fron the HashMap
            ArrayList<String> l = new ArrayList<String>(input.keySet());
            // same as l, but each nextword is repeated the no. of times it appeared after root word
            ArrayList<String> lrepeated = new ArrayList<>();
            for (int i = 0; i < l.size(); i++) {
                for (int j = 0; j < valueArray[i]; j++) {
                    lrepeated.add(l.get(i));
                }
            }
            return l.get(random.nextInt(l.size()));
        }
    }

