package pro.quiz.services;

import java.util.List;

import pro.quiz.models.Question;
import pro.quiz.models.Subject;

public interface QuestionService {
	
	List<Question> getQuestionsBySubject(Subject subject);

}