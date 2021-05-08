package markov;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class MarkMessage {
    String message; // The message as a String
    String[] splitWords; // As 1 String per word (split by spaces)
    HashMap<String, Integer> frequency = new HashMap<>(); // Frequency of words in message
    public MarkMessage(String messageIn){
        this.message = messageIn;
    }

    public MarkMessage(String[] messageIn) {
        this.message = "";
        for (int i = 0; i < messageIn.length; i++) {
            this.message += messageIn[i];
            this.message += " ";
        }
    }

            // For each word in the message, increment the no. nextword appeared after word by the times it did
            void writeNextWords() throws IOException {
                for(int i = 0; i < this.splitWords.length - 1; i++){
                    Word word = new Word(Utilities.formatWords(this.message)[i]);
                    word.addNextWordToJSON(Utilities.formatWords(this.message)[i+1]);
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



}
