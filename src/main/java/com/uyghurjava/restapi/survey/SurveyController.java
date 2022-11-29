package com.uyghurjava.restapi.survey;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class SurveyController {
	private SurveyService surveyService;

	public SurveyController(SurveyService surveyService) {
		super();
		this.surveyService = surveyService;
	}

	// /surveys
	@RequestMapping("/surveys")
	public List<Survey> retrieveAllSurveys() {
		return surveyService.retrieveAllSurveys();
	}

	@RequestMapping("/surveys/{surveyId}")
	public Survey retrieveBySurvey(@PathVariable(name = "surveyId") String id) {
		Survey survey = surveyService.retrieveSurveyById(id);
		if (survey == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		return survey;
	}

	@RequestMapping("/surveys/{surveyId}/questions")
	public List<Question> retrieveAllSurveyQuestions(@PathVariable(name = "surveyId") String id) {
		List<Question> questions = surveyService.retrieveAllSurveyQuestions(id);

		if (questions == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

		return questions;
	}
	
	@RequestMapping(value = "/surveys/{surveyId}/questions", method = RequestMethod.POST)
	public void addNewSurveyQuestion(
			@PathVariable(name="surveyId") String surveyId,
			@RequestBody Question question) {
		surveyService.addNewSurveyQuestion(surveyId, question);
	}

	@RequestMapping("/surveys/{surveyId}/questions/{questionId}")
	public Question retrieveSpecificSurveyQuestion(@PathVariable(name = "surveyId") String surveyId,
			@PathVariable String questionId) {
		Question question = surveyService.retrieveSpecificSurveyQuestion(surveyId, questionId);
		if (question == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		return question;
	}

}
