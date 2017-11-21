package talentify.ai.api.examples.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import com.google.gson.JsonArray;

public class WolframAlpha {
	private static String appID = "AEAHY2-WAVT8RPXXY";

	public ApiAIResponse getShortAnswer(String string) throws IOException {
		String url = "http://api.wolframalpha.com/v1/result?appid=" + appID + "&i=" + string;
		URL wolfram = new URL(url);
		HttpURLConnection wolframConnection = (HttpURLConnection) wolfram.openConnection();
		if (wolframConnection.getResponseCode() == 501) {
			return null;
		}
		BufferedReader responseReader = new BufferedReader(new InputStreamReader(wolframConnection.getInputStream()));
		String answer = "";
		String line;
		while ((line = responseReader.readLine()) != null) {
			answer += line;
		}
		if (answer.equalsIgnoreCase("")) {
			return null;
		}
		JsonArray contextOut = new JsonArray();
		ApiAIResponse aiResponse = new ApiAIResponse();
		aiResponse.setContextOut(contextOut);
		aiResponse.setDisplayText(answer);
		aiResponse.setSpeech(answer);
		aiResponse.setSource("Wolframalpha");
		return aiResponse;
	}

}
