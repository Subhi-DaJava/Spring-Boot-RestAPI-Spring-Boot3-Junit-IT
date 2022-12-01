package com.uyghurjava.restapi.survey;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment =  WebEnvironment.RANDOM_PORT) // which port launch up for a request 
public class SurveyControllerIT {
	@Autowired
	private TestRestTemplate template;
	
	// http://localhost:8080/surveys/Survey1/questions/question1
	String src =
			"""
			"id": "Question1",
			"description": "Most Popular Cloud Platform Today",
			"options": [
			"AWS",
			"Azure",
			"Google Cloud",
			"Oracle Cloud"
			],
			"correctAnswer": "AWS"
						""";
	// Text Blocks """ all here """ java15
	
	// any port webEnvironment
	private static String SPECIFIC_QUESTION_URL = "/surveys/Survey1/questions/question1";
	
	
	
	// {"id":"Question1","description":"Most Popular Cloud Platform Today","options":["AWS","Azure","Google Cloud","Oracle Cloud"],"correctAnswer":"AWS"}
	// [Content-Type:"application/json", Transfer-Encoding:"chunked", Date:"Thu, 01 Dec 2022 16:42:12 GMT", Keep-Alive:"timeout=60", Connection:"keep-alive"]
	
	@Test
	void retrieveSpecificSurveyQuestion_BasicScenario() throws JSONException {
		ResponseEntity<String> responseEntity =
				template.getForEntity(SPECIFIC_QUESTION_URL, String.class); // String.class is response Entity
		// System.out.println(responseEntity.getBody());
		// System.out.println(responseEntity.getHeaders());
		
		String expectedResponse = """
				{"id":"Question1",
				"description":"Most Popular Cloud Platform Today",
				"options":["AWS","Azure","Google Cloud","Oracle Cloud"],
				"correctAnswer":"AWS"}
				""";
		
		// assertEquals(expectedResponse.trim(), responseEntity.getBody());
		JSONAssert.assertEquals(expectedResponse, responseEntity.getBody(), true);
		
	}
	
	@Test
	void retrieveSpecificSurveyQuestion_BasicScenario_Some_Properties() throws JSONException {
		ResponseEntity<String> responseEntity =
				template.getForEntity(SPECIFIC_QUESTION_URL, String.class); // String.class is response Entity
		// System.out.println(responseEntity.getBody());
		// System.out.println(responseEntity.getHeaders());
		
		String expectedResponse = """
				{"id":"Question1",
				"description":"Most Popular Cloud Platform Today",
				"correctAnswer":"AWS"}
				""";
		
		// assertEquals(expectedResponse.trim(), responseEntity.getBody());
		JSONAssert.assertEquals(expectedResponse, responseEntity.getBody(), false);
	}
	
	@Test
	void retrieveSpecificSurveyQuestion_BasicScenario_ContentType_StatusOfResponse() throws JSONException {
		ResponseEntity<String> responseEntity =
				template.getForEntity(SPECIFIC_QUESTION_URL, String.class); // String.class is response Entity
		// System.out.println(responseEntity.getBody());
		// System.out.println(responseEntity.getHeaders());
		
		String expectedResponse = """
				{"id":"Question1",
				"description":"Most Popular Cloud Platform Today",
				"correctAnswer":"AWS"}
				""";
		
		// Status of Response is it 200 ?
		assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
		// Content-type: "application/json"
		assertEquals("application/json",responseEntity.getHeaders().get("Content-type").get(0));
		
		
		
		
		JSONAssert.assertEquals(expectedResponse, responseEntity.getBody(), false);
	}
	
	
}
