package pro.quiz.services;

import java.util.List;

import pro.quiz.models.Question;
import pro.quiz.models.Subject;
import pro.quiz.models.User;

public interface QuestionService {
	
	List<Question> getQuestionsBySubject(Subject subject);
	Question createQuestion(Question question);
	Question getQuestionById(Long id);
	String deleteQuestion(Long id);
	Question updateQuestion(Question question);
	
}
