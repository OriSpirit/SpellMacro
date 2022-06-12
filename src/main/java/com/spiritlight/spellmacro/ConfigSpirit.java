package com.spiritlight.spellmacro;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonWriter;

import java.io.*;

public class ConfigSpirit {
    public static void getConfig() throws IOException {
        File config = new File("config/SpellMacro.json");
        if (config.exists()) {
            JsonParser parser = new JsonParser();
            JsonObject jsonObject = (JsonObject)parser.parse(new FileReader("config/SpellMacro.json"));
            for(int i=1; i<5; i++) {
                MainMod.macroList.put(i-1, new SpellSequence(String.valueOf(jsonObject.get("spell" + i)).replace("\"", "")));
                try {
                    // if not defined, auto assume 50
                    MainMod.macroList.get(i - 1).setCastDelay(jsonObject.get("delay" + i).getAsInt());
                } catch (NullPointerException ignored) {}
            }
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
            writer.name("delay" + i).value(seq == null ? 50 : seq.getCastDelay());
        }
        writer.endObject();
        writer.close();
    }
}