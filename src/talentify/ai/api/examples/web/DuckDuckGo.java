package talentify.ai.api.examples.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONException;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class DuckDuckGo {
	public ApiAIResponse searchApache(String q) throws IOException, JSONException {
		String url = "http://api.duckduckgo.com/?q=" + q + "&format=json";
		String output = "";
		try {
			HttpClient client = HttpClientBuilder.create().build();
			HttpGet request = new HttpGet(url);
			HttpResponse response = client.execute(request);

			int responseCode = response.getStatusLine().getStatusCode();

			System.out.println("**GET** request Url: " + request.getURI());
			System.out.println("Response Code: " + responseCode);
			System.out.println("Content:-\n");
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

			String line = "";
			while ((line = rd.readLine()) != null) {
				output += line;
			}

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (UnsupportedOperationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String text = getTextFromDuckJson(output);

		ApiAIResponse aiResponse = new ApiAIResponse();
		JsonArray contextOut = new JsonArray();
		aiResponse.setContextOut(contextOut);
		aiResponse.setDisplayText(text);
		aiResponse.setSpeech(text);
		aiResponse.setSource("DuckDuckGo");
		return aiResponse;
	}

	private String getTextFromDuckJson(String output) {
		JsonParser jsonParser = new JsonParser();
		JsonObject response = jsonParser.parse(output).getAsJsonObject();
		String Abstract = response.get("Abstract").getAsString();
		if(!Abstract.equalsIgnoreCase("")) {
			return Abstract;
		}
		JsonArray RelatedTopics = response.get("RelatedTopics").getAsJsonArray();
		if (RelatedTopics.size() > 0) {
			JsonObject asJsonObject = RelatedTopics.get(0).getAsJsonObject();
			return asJsonObject.get("Text").getAsString();
		} else {
			return null;
		}
	}
}
