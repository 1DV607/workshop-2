package io;


import javax.json.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Write and Read from "The Happy Pirate.json" located at user.home directory
 * Transform JSON-String to and from JsonArray.
 */
public class Dao {

    private File jsonFile;
    private JsonWriter writer;
    private JsonReader reader;


    public Dao() {
        initializeJsonFile();
        initializeReader();
        initializeWriter();
    }

    /**
     * Save JsonArray with members information at file path
     * @param members - Members information as JsonObject
     * @return      successful - true
     *              unsuccessful - false
     */
    public boolean save(JsonArray members) {
        try {
            writer.writeArray(members);
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * Loads information from file and transform to an JsonArray
     * @return members as an JsonArray
     */
    public JsonArray load() {
        JsonArray members;
        try {
            members = reader.readArray();
        }
        catch (Exception e) {
            return null;
        }

        return members;
    }

    /**
     * Initializes the file "The Happy Pirate.json" at the user.home directory
     */
    private void initializeJsonFile() {
        try {
            jsonFile = new File(System.getProperty("user.home")+"The Happy Pirate.json");

        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Creates an JsonReader with "The Happy Pirate.json" as fileStream
     */
    private void initializeReader() {
        try {
            FileInputStream fileStream = new FileInputStream(jsonFile);
            reader = Json.createReader(fileStream);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Creates an JsonWriter with "The Happy Pirate.json" as fileStream
     */
    private void initializeWriter() {
       try {
           FileOutputStream fileStream = new FileOutputStream(jsonFile);
           writer = Json.createWriter(fileStream);
       }
       catch (Exception e) {
           System.out.println(e.getMessage());
       }
    }
}
