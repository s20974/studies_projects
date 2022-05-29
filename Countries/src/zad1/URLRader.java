package zad1;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.io.IOException;
import java.io.*;
import org.json.*;

public class URLRader {

    public JSONArray render() throws IOException{
        URL url = new URL("https://restcountries.eu/rest/v2/all");

        URLConnection con = url.openConnection();
        InputStream is = con.getInputStream();

        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        String line = null;

        StringBuffer json = new StringBuffer();

        while ((line = br.readLine()) != null) {
            json.append(line);
        }

        JSONArray jsonArray = new  JSONArray(json.toString());

        return jsonArray;
    }
}
