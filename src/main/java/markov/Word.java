package markov;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Word {
    String root; // The word
    File f; // File of word
    HashMap<String, Integer> branchFreq = new HashMap<>(); // Nextword:Times Appeared

    public Word(String rootIn) throws IOException {
        this.root = rootIn;
        // Make sure that each word has its own file,
        f = new File("words/" + root + "next.json");
        if(!f.exists()){
            f.createNewFile();
        }
    }
    // Get next word based on the current one
    public String getBranch() throws IOException {
        f = new File("words/" + root + "next.json");

        HashMap<String, Integer> temp = new HashMap(); // Temporary copy of branchFreq for this function
        ObjectMapper mapper = new ObjectMapper();
        // Make sure the file isn't empty
        if(!(f.length() == 0)) {
            temp = mapper.readValue(f, branchFreq.getClass());
            return Utilities.weighedRandom(temp);
        }else {
            return ""; // If word's file is empty (no nextwords in it), return a period
        }
    }
    // Opens the word's file, and adds a String:Integer pair of a word and how many times it appeared
    public void addNextWordToJSON(String nextword) throws IOException {
        switch (nextword){
            case("."):
                System.out.println("can't write " + nextword);
                return;
        }
        if(nextword.toLowerCase().startsWith("<") && nextword.toLowerCase().endsWith(">")){
            System.out.println("Can't write discord stuff " + nextword);
            return;
        }

        f = new File("words/" + root + "next.json");
        ObjectMapper mapper = new ObjectMapper();
        // If file isn't empty
        if(!(f.length() == 0)) {
            // If file isn't empty
            branchFreq = mapper.readValue(f, branchFreq.getClass());
            branchFreq.putIfAbsent(nextword, 1); // Make sure we have the next word
            branchFreq.replace(nextword, branchFreq.get(nextword)+1); // increment
        }else{
            // If file is empty
            branchFreq.put(nextword, 1); //Add the word
        }
        // Write the new HashMap to the file
        mapper.writeValue(f, branchFreq);
    }
}