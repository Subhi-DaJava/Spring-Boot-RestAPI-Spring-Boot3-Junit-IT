package com.uyghurjava.restapi.survey;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(controllers = SurveyController.class)
class SurveyControllerTest {
	@MockBean
	private SurveyService surveyService;
	
	@Autowired
	private MockMvc mockMvc;
	private static String SPECIFIC_QUESTION_URL = "http://localhost:8080/surveys/Survey1/questions/Question1";
	private static String GENERIC_QUESTIONS_URL = "http://localhost:8080/surveys/Survey1/questions";
	
	// Mock -> surveyService.retrieveSpecificQuestion(surveyId, questionId)
	// Fire a request -> http://localhost:8080/surveys/Survey1/questions/question1 GET
	
	@Test
	void retrieveSpecificSurveyQuestion_404Scenario() throws Exception {
		
		RequestBuilder requestBuilder =
				MockMvcRequestBuilders.get(SPECIFIC_QUESTION_URL)
				.accept(MediaType.APPLICATION_JSON);
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		// System.out.println("Http Code : " + mvcResult.getResponse().getStatus()); // 404 if (questions == null)
		// System.out.println(mvcResult.getResponse().getContentAsString());
		
		assertEquals(404, mvcResult.getResponse().getStatus());
		
	}
	
	@Test
	void retrieveSpecificSurveyQuestion_BasicScenario() throws Exception {

		Question question = new Question("Question1", "Most Popular Cloud Platform Today",
				Arrays.asList("AWS", "Azure", "Google Cloud", "Oracle Cloud"), "AWS");
		String expectedResponse = """
				{"id":"Question1",
				"description":"Most Popular Cloud Platform Today",
				"options":["AWS","Azure","Google Cloud","Oracle Cloud"],"correctAnswer":"AWS"}
				""";

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(SPECIFIC_QUESTION_URL)
				.accept(MediaType.APPLICATION_JSON);
		
		when(surveyService.retrieveSpecificSurveyQuestion("Survey1", "Question1")).thenReturn(question);

		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

		// System.out.println("Http code: " + mvcResult.getResponse().getStatus()); // 200
		// System.out.println(mvcResult.getResponse().getContentAsString());

		MockHttpServletResponse response = mvcResult.getResponse();
		assertEquals(200, response.getStatus());
		JSONAssert.assertEquals(expectedResponse, response.getContentAsString(), true);
	}
	
	@Test
	void retrieveSpecificSurveyQuestion_BasicScenario_SomeProperties() throws Exception {

		Question question = new Question("Question1", "Most Popular Cloud Platform Today",
				Arrays.asList("AWS", "Azure", "Google Cloud", "Oracle Cloud"), "AWS");
		String expectedResponse = """
				{"id":"Question1",
				"description":"Most Popular Cloud Platform Today"
				}
				""";

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(SPECIFIC_QUESTION_URL)
				.accept(MediaType.APPLICATION_JSON);
		
		when(surveyService.retrieveSpecificSurveyQuestion("Survey1", "Question1")).thenReturn(question);

		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

		// System.out.println("Http code: " + mvcResult.getResponse().getStatus()); // 200
		// System.out.println(mvcResult.getResponse().getContentAsString());

		MockHttpServletResponse response = mvcResult.getResponse();
		assertEquals(200, response.getStatus());
		JSONAssert.assertEquals(expectedResponse, response.getContentAsString(), false);
	}
	
	@Test
	void retrieveAllSurveyQuestions_BasicScenario() throws Exception {
		
		Question question = new Question("Question1", "Most Popular Cloud Platform Today",
				Arrays.asList("AWS", "Azure", "Google Cloud", "Oracle Cloud"), "AWS");
		
		String expectedResponse = """
				[{"id":"Question1",
				"description":"Most Popular Cloud Platform Today",
				"options":["AWS","Azure","Google Cloud",
				"Oracle Cloud"],"correctAnswer":"AWS"}]
				""";
		
		List<Question> questions = new ArrayList<>(
				Arrays.asList(question));
		//Survey survey = new Survey("Survey1", "My Favorite Survey", "Description of the Survey", questions);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(GENERIC_QUESTIONS_URL)
				.accept(MediaType.APPLICATION_JSON);
		
		when(surveyService.retrieveAllSurveyQuestions(anyString())).thenReturn(questions);
		
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = mvcResult.getResponse();
		System.out.println(response.getContentAsString());
		
		assertEquals(200, response.getStatus());
		JSONAssert.assertEquals(expectedResponse, response.getContentAsString(), true);
	}
	
	@Test
	void retrieveAllSurveyQuestions_404Scenario() throws Exception {
		
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(GENERIC_QUESTIONS_URL)
				.accept(MediaType.APPLICATION_JSON);
		
		when(surveyService.retrieveAllSurveyQuestions(anyString())).thenReturn(null);
		
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = mvcResult.getResponse();
		assertEquals(404, response.getStatus());
	}
	
	// addNewSurveyQuestion
	// POST
	// RequestBody
	// URL: http://localhost:8080/surveys/Survey1/questions
	@Test
	void addNewSurveyQuestion_BasicScenario() throws Exception {
		
		String requestBody = """
			    {	
				"description": "Your Favorite Programming Language",
				"options": [
				"JAVA",
				"PYTHON",
				"JavaScript",
				"C#"
				],
				"correctAnswer": "JAVA"
				}
				""";
		
		when(surveyService.addNewSurveyQuestion(anyString(), any())).thenReturn("new_id"); // return question.getId()
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(GENERIC_QUESTIONS_URL)
				.accept(MediaType.APPLICATION_JSON)
				.content(requestBody)
				.contentType(MediaType.APPLICATION_JSON);
		
		
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = mvcResult.getResponse();
		// http://localhost:8080/surveys/Survey1/questions/new_id
		String locationHeader = response.getHeader("Location");
		
		//System.out.println(mvcResult.getResponse().getStatus());
		//System.out.println(locationHeader);
		
		assertEquals(201, response.getStatus());
		assertTrue(locationHeader.contains("surveys/Survey1/questions/new_id"));
	}
	
}
