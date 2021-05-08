package markov;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static markov.Utilities.getCount;

public class MarkMessage {
    String message; // The message as a String
    String[] splitWords; // As 1 String per word (split by spaces)
    HashMap<String, Integer> frequency = new HashMap<>(); // Frequency of words in message
    public MarkMessage(String messageIn){
        this.message = messageIn;
    }



    // Get word frequency in message
    public void getFrequency() throws IOException {
        // Makes sure the splitWords isn't empty
        this.splitWords = formatWords(this.message);
        ArrayList<String> distinctWords = new ArrayList<>();
        // Put words in distinctWords if they don't repeat
        for(int i = 0; i < splitWords.length; i++){
            if(!distinctWords.contains(splitWords[i])){
                distinctWords.add(splitWords[i]);
            }
        }
        for(int i = 0; i < splitWords.length; i++) {
            frequency.putIfAbsent(distinctWords.get(distinctWords.indexOf(splitWords[i])).toString(), getCount(splitWords, splitWords[i]));
        }
    }
    // Print debug info about the message
    public void printInfo(){
        System.out.println("Message: " + message);
        System.out.println("Word Freq: " + frequency.toString());
    }
    // For each word in the message, increment the no. nextword appeared after word by the times it did
    void writeNextWords() throws IOException {
        for(int i = 0; i < this.splitWords.length - 1; i++){
            Word word = new Word(this.splitWords[i]);
            word.addNextWordToJSON(this.splitWords[i+1]);
        }

    }

    // Save the frequency of the 1st words appearing in messages
    void saveFirstWord() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        File f = new File("words/BEGIN.json");
        if(!f.exists()){f.createNewFile();}
        HashMap<String, Integer> beginnings = new HashMap<>();

        if(f.length() != 0) {
            beginnings = mapper.readValue(f, beginnings.getClass());
            if(!beginnings.containsKey(splitWords[0])){
                beginnings.put(splitWords[0], 1);
            }else {
                beginnings.replace(splitWords[0], beginnings.get(splitWords[0]) + 1);
            }
        }else{
            beginnings.put(splitWords[0], 1);
        }
        mapper.writeValue(f, beginnings);
    }

    // Split the words in message to splitWords
    public String[] formatWords(String input) throws IOException {
        input.replaceAll("\\.", " .");
        input.replaceAll("[*]", "");
        input.replaceAll("~~", "");
        input.replaceAll("`", "");
        input.replaceAll("/", "");

        for(int i = 0; i < Main.filter.size(); i++){
            input.replaceAll(Main.filter.get(i), "");
        }

        String[] output = message.trim().split("\\s+");
        return output;
    }

}
