package markov;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

public class Bot extends ListenerAdapter
{
    public static void main(String[] args) throws LoginException
    {
        if (Main.token.equals("NULL")) {
            System.out.println("You have to provide a token in the config file!");
            System.exit(1);
        }

        JDABuilder.createLight(Main.token, GatewayIntent.GUILD_MESSAGES, GatewayIntent.DIRECT_MESSAGES)
                .addEventListeners(new Bot())
                .setActivity(Activity.of(Activity.ActivityType.WATCHING, Main.status))
                .build();
    }

    public void replyToMessage(MessageChannel channel) throws IOException {

        Random random = new Random();

        File file = new File("words/BEGIN.json");

        ObjectMapper mapper = new ObjectMapper();
        // Array of possible 1st words and their values
        HashMap<String, Integer> begins = new HashMap<>();
        // Read them
        begins = mapper.readValue(file, begins.getClass());
        // Randomise them based on a weighed average
        String root = Utilities.weighedRandom(begins);
        // Make the output message an empty string
        String outputMessage = "";
        // Randomise message length
        for (int i = 0; i < random.nextInt(Main.lengthVariation) + Main.minMessageLength; i++) {
            outputMessage += (root + " ");
            // DEBUG System.out.print(root + " ");
            Word branch = new Word(new Word(root).getBranch());
            root = branch.root;
        }
        channel.sendMessage(outputMessage + " ").queue();
    }

    public void learnMessage(MarkMessage inputMessage) throws IOException {
        inputMessage.splitWords = inputMessage.formatWords(); // Format and split the words
        inputMessage.writeNextWords();
        inputMessage.saveFirstWord();
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event)
    {
        Message inputDiscordMessage = event.getMessage();
        MarkMessage inputMessage = new MarkMessage(inputDiscordMessage.getContentRaw());
        MessageChannel inputChannel = event.getChannel();
        String[] formattedMessage = {};
        try {
             formattedMessage = inputMessage.formatWords();
        } catch (IOException e) {
            e.printStackTrace();
        }
        MarkMessage formattedMarkMessage = new MarkMessage(formattedMessage);
        System.out.println("Recieved Message: " + inputMessage.message);
        try {
            if (!inputDiscordMessage.getAuthor().isBot() && inputMessage.message.toLowerCase().contains(Main.triggerPhrase)) {
                replyToMessage(inputChannel);
            }
            // Now that's a long statement!
            if (!inputDiscordMessage.getAuthor().isBot()
                    && !inputMessage.message.toLowerCase().contains(Main.triggerPhrase)
                    && (formattedMessage.length > 2)
                    && formattedMessage.length < Main.maxMessageLength) {

                learnMessage(formattedMarkMessage);
            } else{
                return;
            }
        }    catch(IOException e){
            e.printStackTrace();
        }
    }
}

