package talentify.ai.api.examples.web;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.JsonArray;

public class Yahoo {
	public ApiAIResponse search(String q) throws IOException {
		ApiAIResponse aiResponse = new ApiAIResponse();
		String url = "https://in.search.yahoo.com/search?p=" + q;
		System.out.println("Connecting to URL >>" + url);
		try {
			Connection.Response response = Jsoup.connect(url).userAgent(
					"Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21 (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21")
					.timeout(10000).execute();
			int statusCode = response.statusCode();
			if (statusCode == 503) {
				return null;
			}
		} catch (IOException e) {
			System.out.println("io - " + e);
		}
		Document document = Jsoup.connect(url).timeout(40000).userAgent("Mozilla/17.0").followRedirects(true)
				.maxBodySize(1024 * 1024 * 3).get();
		Elements elementsByClass = document.getElementsByClass("searchRightTop");
		String response = "";
		for (Element element : elementsByClass) {
			response += element.text();
		}
		if (response.length() < 2) {
			Elements rightHand = document.getElementsByClass("compText");
			for (Element element : rightHand) {
				response += element.text();
			}
		}
		System.err.println(response);
		try {
			response = response.replaceAll("\\(" + ".*" + "\\)", "");
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println("Yahoo search string shortening failed!");
		}
		try {
			response = response.replaceAll("\\/" + ".*" + "\\/", "");
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println("Yahoo search string shortening failed!");
		}
		JsonArray contextOut = new JsonArray();
		aiResponse.setContextOut(contextOut);
		aiResponse.setDisplayText(response);
		aiResponse.setSpeech(response);
		aiResponse.setSource("Yahoo");
		return aiResponse;
	}
}
