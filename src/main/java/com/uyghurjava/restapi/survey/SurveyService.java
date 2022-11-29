package com.uyghurjava.restapi.survey;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class SurveyService {

	private static List<Survey> surveys = new ArrayList<>();

	static {
		Question question1 = new Question("Question1", "Most Popular Cloud Platform Today",
				Arrays.asList("AWS", "Azure", "Google Cloud", "Oracle Cloud"), "AWS");
		Question question2 = new Question("Question2", "Fastest Growing Cloud Platform",
				Arrays.asList("AWS", "Azure", "Google Cloud", "Oracle Cloud"), "Google Cloud");
		Question question3 = new Question("Question3", "Most Popular DevOps Tool",
				Arrays.asList("Kubernetes", "Docker", "Terraform", "Azure DevOps"), "Kubernetes");
		Question question4 = new Question("Question4", "Most Popular framework java",
				Arrays.asList("Jango", "Hibernate", "Spring", "Spring Boot"), "Spring Boot");
		Question question5 = new Question("Question5", "Most Popular Project Version Control",
				Arrays.asList("GitLab", "Git", "Linux", "GitFlow"), "Git");
		List<Question> questions = new ArrayList<>(
				Arrays.asList(question1, question2, question3, question4, question5));

		Survey survey = new Survey("Survey1", "My Favorite Survey", "Description of the Survey", questions);
		Survey survey1 = new Survey("Survey2", "My Favorite Survey", "Description of the Survey", questions);
		Survey survey2 = new Survey("Survey3", "My Favorite Survey", "Description of the Survey", new ArrayList<>());

		surveys.add(survey);
		surveys.add(survey1);
		surveys.add(survey2);

	}

	public List<Survey> retrieveAllSurveys() {

		return surveys;
	}

	public Survey retrieveSurveyById(String surveyId) {

		// Predicate<? super Survey> predicate = survey ->
		// survey.getId().equals(surveyId);
		// Optional<Survey> survey = surveys.stream().filter(predicate).findFirst();

		Optional<Survey> surveyOptional = surveys.stream().filter(survey -> survey.getId().equals(surveyId))
				.findFirst();

		if (surveyOptional.isEmpty()) {
			return null;
		}

		return surveyOptional.get();
	}

	public List<Question> retrieveAllSurveyQuestions(String surveyId) {
		Survey survey = retrieveSurveyById(surveyId);

		if (survey == null) {
			return null;
		}

		List<Question> questions = survey.getQuestions();

		if (questions.isEmpty()) {
			return new ArrayList<>();
		}

		return questions;
	}

	public Question retrieveSpecificSurveyQuestion(String surveyId, String questionId) {

		List<Question> questions = retrieveAllSurveyQuestions(surveyId);
		if (questions == null) {
			return null;
		}
		Optional<Question> question = questions.stream()
				.filter(questionsFilter -> questionsFilter.getId().equalsIgnoreCase(questionId)).findFirst();
		if (question.isEmpty()) {
			return null;
		}

		return question.get();
	}

	public void addNewSurveyQuestion(String surveyId, Question question) {
		List<Question> questions = retrieveAllSurveyQuestions(surveyId);
		if(questions == null) {
			throw new RuntimeException("This survey with id " + surveyId + " doesn't exit!");
		}
		questions.add(question);
	}

}
