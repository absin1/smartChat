package talentify.ai.api.examples.web;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import ai.api.AIServiceException;
import ai.api.model.AIResponse;
import ai.api.web.AIServiceServlet;

/**
 * Servlet implementation class AIServiceServlet
 */
@WebServlet(urlPatterns = { "/simple-ai" }, initParams = {
		@WebInitParam(name = SimpleChatServlet.PARAM_API_AI_KEY, value = "a376f705d56142adb4a8af5f55f163ae") })
public class SimpleChatServlet extends AIServiceServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// printparams(request);
		HttpSession session = request.getSession();
		String istarUserID = "777";
		try {
			istarUserID = request.getParameter("istarUserID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			AIResponse aiResponse = request(request.getParameter("query"), session);

			response.setContentType("text/plain");
			String speech = aiResponse.getResult().getFulfillment().getSpeech();
			if (aiResponse.getResult().getMetadata().getIntentName().equalsIgnoreCase("talentify.agent.task")) {
				String taskExtraShort = new TalentifyTask().getTaskExtraShort(istarUserID);
				speech += "\n" + taskExtraShort;
			}
			response.getWriter().append(speech);
		} catch (AIServiceException e) {
			e.printStackTrace();
		}
	}

	public String stringify(AIResponse aiResponse) {
		Gson gson = new Gson();
		String json = gson.toJson(aiResponse);
		// System.err.println(json);
		return json;
	}

	private void printparams(HttpServletRequest request) {
		System.err.println("Printing parameters on the backend server.");
		Map<String, String[]> parameterMap = request.getParameterMap();
		for (String iterable_element : parameterMap.keySet()) {
			System.err.println(iterable_element + "  >>  " + parameterMap.get(iterable_element));
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}