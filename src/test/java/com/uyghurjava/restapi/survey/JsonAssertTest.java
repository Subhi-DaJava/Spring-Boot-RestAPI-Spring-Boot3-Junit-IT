package com.uyghurjava.restapi.survey;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
// JSON Assert Framework
class JsonAssertTest {

	@Test
	void jsonAssert_learningBasics() throws JSONException {
		
		String expectedResponse = """
				{"id":"Question1","description":"Most Popular Cloud Platform Today","options":["AWS","Azure","Google Cloud","Oracle Cloud"],"correctAnswer":"AWS"}
				""";
		
		String actualResponse = """
				{"id":"Question1","description":"Most Popular Cloud Platform Today","options":["AWS","Azure","Google Cloud","Oracle Cloud"],"correctAnswer":"AWS"}
				""";
		
		JSONAssert.assertEquals(expectedResponse, actualResponse, true);
	}
	
	@Test
	void jsonAssert_learningBasics_with_white_spaces() throws JSONException {
		
		String expectedResponse = """
				{"id":"Question1","description":"Most Popular Cloud Platform Today","options":["AWS","Azure","Google Cloud","Oracle Cloud"],"correctAnswer":"AWS"}
				""";
		
		String actualResponse = """
				     { "id":"Question1",
				"description":"Most Popular Cloud Platform Today",
				"options": ["AWS","Azure","Google Cloud","Oracle Cloud"],
				"correctAnswer":"AWS" }
				""";
		
		JSONAssert.assertEquals(expectedResponse, actualResponse, true);
	}
	
	@Test
	void jsonAssert_learningBasics_with_Field_Error() throws JSONException {
		
		String expectedResponse = """
				{"id":"Question",
				"description":"Most Popular Cloud Platform Today",
				"options":["AWS","Azure","Google Cloud","Oracle"],
				"correctAnswer":"AWS"}
				""";
		
		String actualResponse = """
				     { "id":"Question1",
				"description":"Most Popular Cloud Platform Today",
				"options": ["AWS","Azure","Google Cloud","Oracle Cloud"],
				"correctAnswer":"AWS" }
				""";
		
		JSONAssert.assertNotEquals(expectedResponse, actualResponse, true);
	}
	
	@Test
	void jsonAssert_learningBasics_with_Some_Properties() throws JSONException {
		
		String expectedResponse = """
				{"id":"Question1",
				"description":"Most Popular Cloud Platform Today",
				"correctAnswer":"AWS"}
				""";
		
		String actualResponse = """
				     { "id":"Question1",
				"description":"Most Popular Cloud Platform Today",
				"options": ["AWS","Azure","Google Cloud","Oracle Cloud"],
				"correctAnswer":"AWS" }
				""";
		
		JSONAssert.assertEquals(expectedResponse, actualResponse, false);
	}

}
