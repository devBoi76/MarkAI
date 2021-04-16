package markov;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

import static markov.Utilities.getCount;

public class markMessage {
    String message; // The message as a String
    String[] splitWords; // As 1 String per word (split by spaces)
    HashMap<String, Integer> frequency = new HashMap(); // Frequency of words in message
    public markMessage(String messageIn){
        this.message = messageIn;
    }



    // Get word frequency in message
    public void getFrequency(){
        // Makes sure the splitWords isn't empty
        splitWordsGet();
        LinkedList distinctWords = new LinkedList();
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
            Word temp = new Word(this.splitWords[i]);
            temp.addNextWordToJSON(this.splitWords[i+1]);
        }

    }
    // Split the words in message to splitWords
    void splitWordsGet(){
        message.replaceAll(".", " .");
        message.replaceAll("[*]", "");
        message.replaceAll("~~", "");
        message.replaceAll("`", "");
        splitWords = message.trim().split("\\s+");
    }

}
