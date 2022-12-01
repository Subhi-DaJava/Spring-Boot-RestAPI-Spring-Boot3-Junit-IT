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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT) // which port launch up for a request
public class SurveyControllerIT {
	@Autowired
	private TestRestTemplate template;

	// http://localhost:8080/surveys/Survey1/questions/question1
	String src = """
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

	private static String GENERIC_QUESTIONS_URL = "/surveys/Survey1/questions";
	
	private static String GENERIC_SURVEYS_URL = "/surveys";
	
	private static String SPECIFIC_SURVEY_URL = "/surveys/Survey1";
	

	// {"id":"Question1","description":"Most Popular Cloud Platform
	// Today","options":["AWS","Azure","Google Cloud","Oracle
	// Cloud"],"correctAnswer":"AWS"}
	// [Content-Type:"application/json", Transfer-Encoding:"chunked", Date:"Thu, 01
	// Dec 2022 16:42:12 GMT", Keep-Alive:"timeout=60", Connection:"keep-alive"]

	@Test
	void retrieveSpecificSurveyQuestion_BasicScenario() throws JSONException {
		ResponseEntity<String> responseEntity = template.getForEntity(SPECIFIC_QUESTION_URL, String.class); // String.class
																											// is
																											// response
																											// Entity
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
		ResponseEntity<String> responseEntity = template.getForEntity(SPECIFIC_QUESTION_URL, String.class); // String.class
																											// is
																											// response
																											// Entity
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
		ResponseEntity<String> responseEntity = template.getForEntity(SPECIFIC_QUESTION_URL, String.class); // String.class
																											// is
																											// response
																											// Entity
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
		assertEquals("application/json", responseEntity.getHeaders().get("Content-type").get(0));

		JSONAssert.assertEquals(expectedResponse, responseEntity.getBody(), false);
	}

	String strList = """
			{
				"id": "Question2",
				"description": "Fastest Growing Cloud Platform",
				"options": [
				"AWS",
				"Azure",
				"Google Cloud",
				"Oracle Cloud"
				],
				"correctAnswer": "Google Cloud"
				}
			""";

	// tests for list
	@Test
	void retrieveAllSurveyQuestions_basicScenario() throws JSONException {
		String responseExpected = """
				[
				{
				"id": "Question1",
				"description": "Most Popular Cloud Platform Today",
				"options": [
				"AWS",
				"Azure",
				"Google Cloud",
				"Oracle Cloud"
				],
				"correctAnswer": "AWS"
				},
				{
				"id": "Question2",
				"description": "Fastest Growing Cloud Platform",
				"options": [
				"AWS",
				"Azure",
				"Google Cloud",
				"Oracle Cloud"
				],
				"correctAnswer": "Google Cloud"
				},
				{
				"id": "Question3",
				"description": "Most Popular DevOps Tool",
				"options": [
				"Kubernetes",
				"Docker",
				"Terraform",
				"Azure DevOps"
				],
				"correctAnswer": "Kubernetes"
				},
				{
				"id": "Question4",
				"description": "Most Popular framework java",
				"options": [
				"Jango",
				"Hibernate",
				"Spring",
				"Spring Boot"
				],
				"correctAnswer": "Spring Boot"
				},
				{
				"id": "Question5",
				"description": "Most Popular Project Version Control",
				"options": [
				"GitLab",
				"Git",
				"Linux",
				"GitFlow"
				],
				"correctAnswer": "Git"
				}
				]
				""";
		ResponseEntity<String> response = template.getForEntity(GENERIC_QUESTIONS_URL, String.class);
		JSONAssert.assertEquals(responseExpected, response.getBody(), true);
	}
	
	@Test
	void retrieveAllSurveyQuestions_basicScenario_with_some_elements() throws JSONException {
		String responseExpected = """
				[
				{
				"id": "Question1"
				},
				{
				"id": "Question2"
				},
				{
				"id": "Question3"
				},
				{
				"id": "Question4"
				},
				{
				"id": "Question5"
				}
				]
				""";
		ResponseEntity<String> response = template.getForEntity(GENERIC_QUESTIONS_URL, String.class);
		
		assertTrue(response.getStatusCode().is2xxSuccessful());
		assertEquals("application/json", response.getHeaders().get("Content-Type").get(0));
		
		JSONAssert.assertEquals(responseExpected, response.getBody(), false);
		
	}
	
	// retrive all surveys

	@Test
	void retrieveAllSurveys_basicScenario() throws JSONException {
		String responseExpected = """
								[
				{
				"id": "Survey1",
				"title": "My Favorite Survey",
				"description": "Description of the Survey",
				"questions": [
				{
				"id": "Question1",
				"description": "Most Popular Cloud Platform Today",
				"options": [
				"AWS",
				"Azure",
				"Google Cloud",
				"Oracle Cloud"
				],
				"correctAnswer": "AWS"
				},
				{
				"id": "Question2",
				"description": "Fastest Growing Cloud Platform",
				"options": [
				"AWS",
				"Azure",
				"Google Cloud",
				"Oracle Cloud"
				],
				"correctAnswer": "Google Cloud"
				},
				{
				"id": "Question3",
				"description": "Most Popular DevOps Tool",
				"options": [
				"Kubernetes",
				"Docker",
				"Terraform",
				"Azure DevOps"
				],
				"correctAnswer": "Kubernetes"
				},
				{
				"id": "Question4",
				"description": "Most Popular framework java",
				"options": [
				"Jango",
				"Hibernate",
				"Spring",
				"Spring Boot"
				],
				"correctAnswer": "Spring Boot"
				},
				{
				"id": "Question5",
				"description": "Most Popular Project Version Control",
				"options": [
				"GitLab",
				"Git",
				"Linux",
				"GitFlow"
				],
				"correctAnswer": "Git"
				}
				]
				},
				{
				"id": "Survey2",
				"title": "My Favorite Survey",
				"description": "Description of the Survey",
				"questions": [
				{
				"id": "Question1",
				"description": "Most Popular Cloud Platform Today",
				"options": [
				"AWS",
				"Azure",
				"Google Cloud",
				"Oracle Cloud"
				],
				"correctAnswer": "AWS"
				},
				{
				"id": "Question2",
				"description": "Fastest Growing Cloud Platform",
				"options": [
				"AWS",
				"Azure",
				"Google Cloud",
				"Oracle Cloud"
				],
				"correctAnswer": "Google Cloud"
				},
				{
				"id": "Question3",
				"description": "Most Popular DevOps Tool",
				"options": [
				"Kubernetes",
				"Docker",
				"Terraform",
				"Azure DevOps"
				],
				"correctAnswer": "Kubernetes"
				},
				{
				"id": "Question4",
				"description": "Most Popular framework java",
				"options": [
				"Jango",
				"Hibernate",
				"Spring",
				"Spring Boot"
				],
				"correctAnswer": "Spring Boot"
				},
				{
				"id": "Question5",
				"description": "Most Popular Project Version Control",
				"options": [
				"GitLab",
				"Git",
				"Linux",
				"GitFlow"
				],
				"correctAnswer": "Git"
				}
				]
				},
				{
				"id": "Survey3",
				"title": "My Favorite Survey",
				"description": "Description of the Survey",
				"questions": []
				}
				]
				""";
		ResponseEntity<String> response = template.getForEntity(GENERIC_SURVEYS_URL, String.class);
		JSONAssert.assertEquals(responseExpected, response.getBody(), true);
	}
	
	@Test
	void retrieveAllSurveys_Some_Properties() throws JSONException {
		String responseExpected = """
			[
				{
				"id": "Survey1"
				},
				{
				"id": "Survey2"
				},
				{
				"id": "Survey3"
				}
			]
				""";
		ResponseEntity<String> response = template.getForEntity(GENERIC_SURVEYS_URL, String.class);
		assertTrue(response.getStatusCode().is2xxSuccessful());

		JSONAssert.assertEquals(responseExpected, response.getBody(), false);
	}
	
	
	
	
	@Test
	void retrieveSpecificSurvey_BasicScenario() throws JSONException {
		ResponseEntity<String> responseEntity = template.getForEntity(SPECIFIC_SURVEY_URL, String.class); 

		String responseExpected = """
				{
				"id": "Survey1",
				"title": "My Favorite Survey",
				"description": "Description of the Survey",
				"questions": [
				{
				"id": "Question1",
				"description": "Most Popular Cloud Platform Today",
				"options": [
				"AWS",
				"Azure",
				"Google Cloud",
				"Oracle Cloud"
				],
				"correctAnswer": "AWS"
				},
				{
				"id": "Question2",
				"description": "Fastest Growing Cloud Platform",
				"options": [
				"AWS",
				"Azure",
				"Google Cloud",
				"Oracle Cloud"
				],
				"correctAnswer": "Google Cloud"
				},
				{
				"id": "Question3",
				"description": "Most Popular DevOps Tool",
				"options": [
				"Kubernetes",
				"Docker",
				"Terraform",
				"Azure DevOps"
				],
				"correctAnswer": "Kubernetes"
				},
				{
				"id": "Question4",
				"description": "Most Popular framework java",
				"options": [
				"Jango",
				"Hibernate",
				"Spring",
				"Spring Boot"
				],
				"correctAnswer": "Spring Boot"
				},
				{
				"id": "Question5",
				"description": "Most Popular Project Version Control",
				"options": [
				"GitLab",
				"Git",
				"Linux",
				"GitFlow"
				],
				"correctAnswer": "Git"
				}
				]
				}
				""";
		JSONAssert.assertEquals(responseExpected, responseEntity.getBody(), true);

	}
	
	@Test
	void retrieveSpecificSurvey_Some_Properties() throws JSONException {
		ResponseEntity<String> responseEntity = template.getForEntity(SPECIFIC_SURVEY_URL, String.class); 

		String responseExpected = """
								{
								"id": "Survey1",
								"questions": [
								{"id": "Question1"},
								{"id": "Question2"},
								{"id": "Question3"},
								{"id": "Question4"},
								{"id": "Question5"}
								]
								}
				""";
		JSONAssert.assertEquals(responseExpected, responseEntity.getBody(), false);
		assertTrue(responseEntity.getStatusCode().is2xxSuccessful());

	}
	
	
	// Post Methods
	
	// /surveys/{surveyId}/questions
	// RequestBody: Content-Type: Application/Json
	// 201 
	// Location: http://localhost:8080/surveys/{surveyId}/questions/564646464
	@Test
	void addNewSurveyQuestion_basiceScenario() {
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
		
		HttpHeaders headers = new HttpHeaders();
		//headers.add("Content-Type", "application/json");
		headers.add("Content-Type", "application/json");
		HttpEntity<String> httpEntity = new HttpEntity<String>(requestBody, headers);
	
		ResponseEntity<String> responseEntity 
		= template.exchange(
				GENERIC_QUESTIONS_URL,
				HttpMethod.POST,
				httpEntity,
				String.class);
		
		//System.out.println(responseEntity.getHeaders());
		//System.out.println(responseEntity.getBody());
		// [Location:"http://localhost:51120/surveys/Survey1/questions/362054959",
		// Content-Length:"0", Date:"Thu, 01 Dec 2022 20:07:57 GMT", Keep-Alive:"timeout=60", Connection:"keep-alive"]
		
		assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
		String locationHeader = responseEntity.getHeaders().get("Location").get(0);
		assertTrue(locationHeader.contains("/surveys/Survey1/questions"));
		
		// delete request for eliminate the side effect, order of the tests
		
		template.delete(locationHeader);
	}
	
	
	
	
	
	
	
}