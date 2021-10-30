package api_tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class json_to_string {

    public String chamaUrl(String url) throws IOException {
      URL url_api = new URL(url);
      BufferedReader bufferedReader = new BufferedReader(
        new InputStreamReader(url_api.openStream()));

      String retornoJson;

      StringBuilder builder = new StringBuilder();
      while ((retornoJson = bufferedReader.readLine()) != null)
        builder.append(retornoJson);

      bufferedReader.close();

      return builder.toString();
    }
}