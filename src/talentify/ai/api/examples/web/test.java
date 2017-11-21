package talentify.ai.api.examples.web;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONException;

public class test {
	public static void main(String[] args) throws IOException, JSONException {
		// System.err.println(new TalentifyTask().getTaskExtraShort("777"));
		// testGoogle();
		// testBing();
		// testYahoo();
		// testDuckDuckGo();
		// checkWolframShort();
		// checkRegEx();
		checkOntologicalResponse();
		// checkUltimateFallBack();
	}

	private static void checkUltimateFallBack() {
		ApiAIResponse ultimateFallbackResponse = new SimpleChatServlet().getUltimateFallbackResponse();
		System.err.println(ultimateFallbackResponse.getSource() + " >> " + ultimateFallbackResponse.getSpeech());
	}

	private static void checkOntologicalResponse() {
		try {
			ApiAIResponse ontologicalResponse = new SimpleChatServlet()
					.getOntologicalResponse("how+far+is+delhi+from+jaipur");
			System.err.println(ontologicalResponse.getSource() + " >> " + ontologicalResponse.getSpeech());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void checkWolframShort() throws IOException {
		ArrayList<String> q = new ArrayList<>();
		q.add("how+far+is+chennai+from+bangalore");
		for (String string : q) {
			ApiAIResponse wolframResponse = new WolframAlpha().getShortAnswer(string);
			System.out.println(string + " >> " + wolframResponse.getSpeech() + "\n");
		}
	}

	private static void testYahoo() throws IOException {
		ArrayList<String> q = new ArrayList<>();
		q.add("who+is+modi");
		for (String string : q) {
			ApiAIResponse googleSearchResponse = new Yahoo().search(string);
			System.out.println(string + " >> " + googleSearchResponse.getSpeech() + "\n");
		}
	}

	private static void testDuckDuckGo() throws IOException, JSONException {
		ArrayList<String> q = new ArrayList<>();
		q.add("who+is+modi");
		for (String string : q) {
			ApiAIResponse googleSearchResponse = new DuckDuckGo().searchApache(string);
			System.out.println(string + " >> " + googleSearchResponse.getSpeech() + "\n");
		}
	}

	private static void checkRegEx() {
		String response = "Gandalf/??ænd??lf/ is a fictional character and one of the main protagonists in J. R. R. Tolkien's novels The Hobbit and The Lord of the Rings. He is a wizard, member of the Istari order, as well as leader of the Fellowship of the Ring and the army of the West.";
		try {
			response = response.replaceAll("\\/" + ".*" + "\\/", "");
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println("Google search string shortening failed!");
		}
		System.out.println(response);
	}

	private static void testGoogle() throws IOException {
		ArrayList<String> q = new ArrayList<>();
		q.add("how+does+internet+work");
		q.add("who+is+alaudin+khilji");
		q.add("how+does+hibernate+work");
		q.add("what+is+java");
		q.add("when+was+buddha+born");
		q.add("who+is+christopher+columbus");
		q.add("who+is+gandalf");
		for (String string : q) {
			ApiAIResponse googleSearchResponse = new Google().search(string);
			System.out.println(string + " >> " + googleSearchResponse.getSpeech() + "\n");
		}
	}

	private static void testBing() throws IOException {
		ArrayList<String> q = new ArrayList<>();
		q.add("how+does+internet+work");
		q.add("who+is+alaudin+khilji");
		q.add("how+does+hibernate+work");
		q.add("what+is+java");
		q.add("when+was+buddha+born");
		q.add("who+is+christopher+columbus");
		q.add("who+is+gandalf");
		for (String string : q) {
			ApiAIResponse googleSearchResponse = new Bing().search(string);
			System.out.println(string + " >> " + googleSearchResponse.getSpeech() + "\n");
		}
	}
}
