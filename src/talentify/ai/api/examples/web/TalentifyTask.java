package talentify.ai.api.examples.web;
//This class will take care of the talentify specific requests like

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class TalentifyTask {
	public static void main(String[] args) throws IOException {
		System.err.println(new TalentifyTask().getTaskExtraShort("777"));

	}

	public ApiAIResponse getTaskResponse(JsonObject result, String istarUserID) throws IOException {
		if (istarUserID == null)
			istarUserID = "777";
		ApiAIResponse aiResponse = new ApiAIResponse();
		JsonArray contextOut = new JsonArray();
		String displayText = "Showing you a blah blah list of tasks";
		JsonObject data = new JsonObject();
		data.addProperty("talentifyAction", "ShowTasks");
		data.add("tasks", new TalentifyTask().getTasks(istarUserID));
		String speech = "Now showing you a list of tasks. Please choose!";
		aiResponse.setContextOut(contextOut);
		aiResponse.setData(data);
		aiResponse.setDisplayText(displayText);
		aiResponse.setSpeech(speech);
		return aiResponse;
	}

	public JsonArray getTasks(String istarUserID) throws IOException {
		String url = "http://business.talentify.in/t2c/user/" + istarUserID + "/complex";
		// String json = getJSON(url, 5);
		System.err.println(url);
		String json = new Utils().getHTTP(url);
		JsonParser jsonParser = new JsonParser();
		JsonObject complexObject = jsonParser.parse(json).getAsJsonObject();
		JsonArray tasks = complexObject.get("tasks").getAsJsonArray();
		return tasks;
	}

	public String getJSON(String url, int timeout) {
		HttpURLConnection c = null;
		try {
			URL u = new URL(url);
			c = (HttpURLConnection) u.openConnection();
			c.setRequestMethod("GET");
			// c.setRequestProperty("Content-length", "0");
			c.setUseCaches(false);
			c.setAllowUserInteraction(false);
			c.setConnectTimeout(timeout);
			c.setReadTimeout(timeout);
			c.connect();
			int status = c.getResponseCode();

			switch (status) {
			case 200:
			case 201:
				BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
				StringBuilder sb = new StringBuilder();
				String line;
				while ((line = br.readLine()) != null) {
					sb.append(line + "\n");
				}
				br.close();
				return sb.toString();
			}

		} catch (MalformedURLException ex) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
		} finally {
			if (c != null) {
				try {
					c.disconnect();
				} catch (Exception ex) {
					Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
				}
			}
		}
		return null;
	}

	public String getTaskExtraShort(String istarUserID) throws IOException {
		if (istarUserID == null) {
			return "NPE";
		}
		JsonArray tasks = getTasks(istarUserID);
		String taskExtraShort = "You have a total of " + tasks.size() + " tasks.";
		return taskExtraShort;
	}

	public JsonArray getTaskButtons(String istarUserID) throws IOException {
		JsonArray buttons = new JsonArray();
		JsonArray tasks = getTasks(istarUserID);
		for (JsonElement taskJsonObject : tasks) {
			JsonObject asJsonObject = taskJsonObject.getAsJsonObject();
			JsonObject button = new JsonObject();
			switch (asJsonObject.get("itemType").getAsString()) {
			case "LESSON_PRESENTATION":
				button.addProperty("displayText", asJsonObject.get("title").getAsString());
				button.addProperty("action", "openTask");
				button.addProperty("itemID", asJsonObject.get("itemId").getAsString());
				button.addProperty("taskID", asJsonObject.get("id").getAsString());
				button.addProperty("itemType", asJsonObject.get("itemType").getAsString());
				buttons.add(button);
				break;
			case "ASSESSMENT":
			case "LESSON_ASSESSMENT":
				button.addProperty("displayText", asJsonObject.get("title").getAsString());
				button.addProperty("action", "openTask");
				button.addProperty("itemID", asJsonObject.get("itemId").getAsString());
				button.addProperty("taskID", asJsonObject.get("id").getAsString());
				button.addProperty("itemType", asJsonObject.get("itemType").getAsString());
				buttons.add(button);
				break;
			case "LESSON_INTERACTIVE":
				button.addProperty("displayText", asJsonObject.get("title").getAsString());
				button.addProperty("action", "openTask");
				button.addProperty("itemID", asJsonObject.get("itemId").getAsString());
				button.addProperty("taskID", asJsonObject.get("id").getAsString());
				button.addProperty("itemType", asJsonObject.get("itemType").getAsString());
				buttons.add(button);
				break;
			case "LESSON_VIDEO":
				button.addProperty("displayText", asJsonObject.get("title").getAsString());
				button.addProperty("action", "openTask");
				button.addProperty("itemID", asJsonObject.get("itemId").getAsString());
				button.addProperty("taskID", asJsonObject.get("id").getAsString());
				button.addProperty("itemType", asJsonObject.get("itemType").getAsString());
				buttons.add(button);
				break;
			default:
				break;
			}
		}
		return buttons;
	}
}
