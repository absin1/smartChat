package talentify.ai.api.examples.web;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.JsonArray;

public class Bing {
	public ApiAIResponse search(String q) throws IOException {
		ApiAIResponse aiResponse = new ApiAIResponse();
		String url = "https://www.bing.com/search?q=" + q;
		System.out.println("Connecting to URL >>" + url);
		Document document = Jsoup.connect(url).timeout(4000).userAgent("Mozilla/17.0").followRedirects(true)
				.maxBodySize(1024 * 1024 * 3).get();
		Elements elementsByClass = document.getElementsByClass("rwrl_padref");
		String response = "";
		for (Element element : elementsByClass) {
			response += element.text();
		}
		if (response.length() < 2) {
			Elements rightHand = document.getElementsByClass("b_lBottom b_snippet");
			for (Element element : rightHand) {
				response += element.text();
			}
		}
		System.err.println(response);
		try {
			response = response.replaceAll("\\(" + ".*" + "\\)", "");
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println("Bing search string shortening failed!");
		}
		try {
			response = response.replaceAll("\\/" + ".*" + "\\/", "");
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println("Bing search string shortening failed!");
		}
		JsonArray contextOut = new JsonArray();
		aiResponse.setContextOut(contextOut);
		aiResponse.setDisplayText(response);
		aiResponse.setSpeech(response);
		aiResponse.setSource("Bing");
		return aiResponse;
	}
}
