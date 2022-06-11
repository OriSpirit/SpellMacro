package com.spiritlight.spellmacro;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonWriter;

import java.io.*;

public class ConfigSpirit {
    public static void getConfig() throws IOException {
        File config = new File("config/RMT.json");
        if (config.exists()) {
            JsonParser parser = new JsonParser();
            JsonObject jsonObject = (JsonObject)parser.parse(new FileReader("config/SpellMacro.json"));
            MainMod.macroList.put(0, new SpellSequence(String.valueOf(jsonObject.get("spell1")).replace("\"", "")));
            MainMod.macroList.put(1, new SpellSequence(String.valueOf(jsonObject.get("spell2")).replace("\"", "")));
            MainMod.macroList.put(2, new SpellSequence(String.valueOf(jsonObject.get("spell3")).replace("\"", "")));
            MainMod.macroList.put(3, new SpellSequence(String.valueOf(jsonObject.get("spell4")).replace("\"", "")));
        } else {
            writeConfig();
        }
    }

    public static void writeConfig() throws IOException {
        JsonWriter writer = new JsonWriter(new BufferedWriter(new FileWriter("config/SpellMacro.json")));
        writer.beginObject();
        for(int i=1; i<5; i++) {
            SpellSequence seq = MainMod.macroList.get(i - 1);
            writer.name("spell" + i).value(seq == null ? "" : seq.getRawSequence());
        }
        writer.endObject();
        writer.close();
    }
}