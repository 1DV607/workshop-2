package io;

import javax.json.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Scanner;

/**
 * Write and Read from "The Happy Pirate.json" located at user.home directory
 * Transform JSON-String to and from JsonArray.
 */
public class Dao {

    private File jsonFile;
    private JsonWriter writer;
    private JsonReader reader;
    private JsonParser parser;

    public Dao() {
        initializeJsonFile();
    }

    public Dao(File file) {
        parser = new JsonParser();
        try {
            jsonFile = file;
        }
        catch (Exception e) {
            System.out.println(e.getMessage().toString());
        }
    }

    /**
     * Save JsonArray with members information at file path
     * @param members - Members information as JsonObject
     * @return      successful - true
     *              unsuccessful - false
     */
    public boolean save(List<Member> members) {
        try {
            initializeWriter();
            JsonArray jMembers = parser.membersToJson(members);
            writer.writeArray(jMembers);
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
    public List<Member> load() {
        JsonArray jMembers;
        ArrayList<Member> members;

        try {
            initializeReader();
            jMembers = reader.readArray();
            members = parser.jsonToMembers(jMembers);
        } catch (Exception e) {
            return Json.createArrayBuilder().build();
        }

        return members;
    }

    /**
     * Initializes the file "The Happy Pirate.json" at the user.home directory
     */
    private void initializeJsonFile() {
        try {
            jsonFile = new File(System.getProperty("user.home") + "/Documents/The_Happy_Pirate.json");

        }
        catch (Exception e) {
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
           System.out.println(e.getMessage().toString());
       }
    }

}
