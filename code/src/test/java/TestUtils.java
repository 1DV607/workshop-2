import java.io.File;
import java.io.FileReader;

import javax.json.Json;
import javax.json.JsonValue;
import javax.json.JsonString;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonArray;
import javax.json.JsonReader;
import javax.json.JsonException;

public class TestUtils {

    public static JsonObject readJsonObjectFromFile(String path) {
        try (JsonReader jr = Json.createReader(new FileReader(new File(path)))) {
            return jr.readObject(); 
        } catch (Exception ex) {
            throw new JsonException("Error reading from JSON file. Message: " + ex.getMessage());
        }
    }

    public static JsonArray readJsonArrayFromFile(String path) {
        try (JsonReader jr = Json.createReader(new FileReader(new File(path)))) {
            return jr.readArray(); 
        } catch (Exception ex) {
            throw new JsonException("Error reading from JSON file. Message: " + ex.getMessage());
        }
    }

    public static boolean jsonObjectsEqual(JsonObject obj1, JsonObject obj2) {
        if (obj1.keySet().size() != obj2.keySet().size()) {
            return false; 
        }

        for (String key : obj1.keySet()) {
            if ( !(obj2.keySet().contains(key)) ) {
                return false;
            }

            return jsonValuesEqual(obj1.get(key), obj2.get(key));
        }

        return true;
    }

    public static boolean jsonArraysEqual(JsonArray arr1, JsonArray arr2) {
        if (arr1.size() != arr2.size()) {
            return false; 
        }

        for (JsonValue val1 : arr1) {
            if ( !(arr2.contains(val1))) {
                return false;
            }

            return jsonValuesEqual(val1, arr2.get(arr2.indexOf(val1)));
        }

        return true;
    }

    private static boolean jsonValuesEqual(JsonValue val1, JsonValue val2) {
        if (val1.getValueType() != val2.getValueType()) {
            return false;
        }

        switch (val1.getValueType()) {
            case OBJECT: {
                return jsonObjectsEqual((JsonObject)val1, (JsonObject)val2);
            }

            case ARRAY: {
                return jsonArraysEqual((JsonArray)val1, (JsonArray)val2);
            }

            case STRING: {
                return ((JsonString)val1).equals((JsonString)val2);
            }

            case NUMBER: {
                return (JsonNumber)val1 == (JsonNumber)val2;
            }
                
            default: {
                return true;
            }
        }
    }
}
