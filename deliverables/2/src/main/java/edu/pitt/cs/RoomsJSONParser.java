package edu.pitt.cs;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

class RoomsJSONParser {

    void storeToFile(String filePath, ArrayList<Room> rooms) throws JsonIOException, IOException {
        FileWriter writer = new FileWriter(filePath);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        gson.toJson(rooms, writer);
        writer.flush();
    }

    ArrayList<Room> loadFromFile(String filePath) throws FileNotFoundException {
        FileReader reader = new FileReader(filePath);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        ArrayList<Room> rooms = gson.fromJson(reader, new TypeToken<ArrayList<Room>>(){}.getType());
        return rooms;
    }
}