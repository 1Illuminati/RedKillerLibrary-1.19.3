package org.redkiller.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.apache.commons.codec.binary.Base64;
import org.jline.utils.InputStreamReader;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;
import java.util.UUID;

public class Mojang {
    private static final JsonParser parser = new JsonParser();

    public static String getSkinUrl(String uuid){
        String json = getContent("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid);
        JsonObject o = parser.parse(json).getAsJsonObject();
        String jsonBase64 = o.get("properties").getAsJsonArray().get(0).getAsJsonObject().get("value").getAsString();

        o = parser.parse(new String(Base64.decodeBase64(jsonBase64))).getAsJsonObject();
        return o.get("textures").getAsJsonObject().get("SKIN").getAsJsonObject().get("url").getAsString();
    }

    public static GameProfile getGameProfile(String texture, String displayName) {
        String urlTexture = "http://textures.minecraft.net/texture/" + texture;
        GameProfile profile = new GameProfile(UUID.randomUUID(), displayName);
        byte[] encodedData = Base64.encodeBase64(String.format("{textures:{SKIN:{url:\"%s\"}}}", urlTexture).getBytes());
        profile.getProperties().put("textures", new Property("textures", new String(encodedData)));

        return profile;
    }

    public static String spiltUUIDbyString(String texture) {
        StringBuilder builder = new StringBuilder(texture);
        for(int index = 1; index < builder.length(); index++) {
            if(index % 4 == 0) {
                builder.setCharAt(index, '-');
            }
        }

        return builder.toString();
    }

    public static String getContent(String link){
        BufferedReader br = null;
        try {
            URL url = new URL(link);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();

            br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            return br.readLine();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try { br.close(); } catch (IOException ignore) {}
            }
        }
        return null;
    }
}
