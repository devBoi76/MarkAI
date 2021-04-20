package markov;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Utilities {
    // Get no. of strings in string array
    static int getCount(String[] arr, String s) {
        int counter = 0;
        for (String value : arr) {
            // Check if string is on index, if yes increase counter
            if (s.equals(value)) {
                counter++;
            }
        }
        return counter;
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
            return lrepeated.get(random.nextInt(lrepeated.size()));
        }

        public static String loadConfig(String setting) throws IOException {
            ObjectMapper mapper = new ObjectMapper();
            File f = new File("config/CONFIG");
            HashMap<String, String> settingsMap = new HashMap<>();
            if(!f.exists()){
                f.createNewFile();
                settingsMap.put("Token", "NULL");
                settingsMap.put("MinMessageLength", "10");
                settingsMap.put("LengthVariation", "20");
                settingsMap.put("Status", "Beep Boop");
                settingsMap.put("TriggerPhrase", "markai");
                settingsMap.put("MaxMSGLength", "75");
                mapper.writeValue(f, settingsMap);
                System.out.println("Created config file with default settings");
            }
            settingsMap = mapper.readValue(f, settingsMap.getClass());
            return settingsMap.get(setting);

        }
    }

