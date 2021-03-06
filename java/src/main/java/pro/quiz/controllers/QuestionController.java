package pro.quiz.controllers;

import java.util.ArrayList;
import java.util.List;

import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pro.quiz.models.Answer;
import pro.quiz.models.Question;
import pro.quiz.models.Subject;
import pro.quiz.models.User;
import pro.quiz.repositories.AnswerRepository;
import pro.quiz.services.AnswerService;
import pro.quiz.services.QuestionService;
import pro.quiz.services.SubjectService;
import pro.quiz.services.impl.SubjectServiceImpl.Result;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {

private final QuestionService questionService;
private final SubjectService subjectService;
private final AnswerService answerService;	
private final AnswerRepository answerRepository;

	public QuestionController(QuestionService questionService, SubjectService subjectService,
			AnswerService answerService, AnswerRepository answerRepository) {
		this.questionService=questionService;
		this.subjectService=subjectService;
		this.answerService=answerService;
		this.answerRepository=answerRepository;
}
	

	@GetMapping("demo/{id}")
	ResponseEntity getQuestionsBySubject(@PathVariable int id) throws AuthenticationException
	{
		Long idl=Long.valueOf(id);
		Subject subject=this.subjectService.getSubjectById(idl);
		
		if(!subject.getCourse().equals("demoweb")&&!subject.getCourse().equals("demojava")) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("brak dostepu");
		
		} else {
		List<Question> questions=this.questionService.getQuestionsBySubject(subject);
		return  ResponseEntity.status(HttpStatus.OK).body(questions);
	}
	 
}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/{id}")
	ResponseEntity getQuestionById(@PathVariable Long id) 
	{
		
		Question question=this.questionService.getQuestionById(id);
		return  ResponseEntity.status(HttpStatus.OK).body(question);
	 
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/create")
	ResponseEntity createQuestion(@RequestBody Question question) {
		//Subject subject = this.subjectService.getSubjectById(question.getSubject().getId());
		//question.setSubject(subject);
		
		for (Answer answer:question.getAnswers() ) {
			answer.setQuestion(question);
			System.out.println(answer.getText());
		}Question newQuestion = this.questionService.createQuestion(question);
		//Question newestQuestion = this.questionService.getQuestionById(newQuestion.getId());
		return ResponseEntity.status(HttpStatus.OK).body(newQuestion);
	}
	
	@PostMapping("/demo/result")
	ResponseEntity checkAnswersForDemo(@RequestBody List<Question> questions) throws AuthenticationException{
	//	Question question=questionRepository.getQuestionById(questions.get(0).getId());
		String course=questions.get(0).getSubject().getCourse();
		System.out.println(course);
		//course="demojava";
		
	//questions.get(0).setSubject(question.getSubject());
	//questions.get(1).setSubject(question.getSubject());
	//questions.get(2).setSubject(question.getSubject());
	//questions.get(3).setSubject(question.getSubject());
	
		if(!course.equals("demoweb")&&!course.equals("demojava")) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("brak dostepu");
		
		} else {
			Result result = this.subjectService.checkAnswersForDemo(questions);
			return ResponseEntity.status(HttpStatus.OK).body(result);
		}
	}
	
	
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/update")
	ResponseEntity updateQuestion(@RequestBody Question question) {
		
		for (Answer answer:question.getAnswers() ) {
			answer.setQuestion(question);
		}
		
			Question myQuestion = this.questionService.updateQuestion(question);
			return ResponseEntity.status(HttpStatus.OK).body(myQuestion);
		
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	ResponseEntity deleteQuestionById(@PathVariable Long id)  
	{
		
		Question question=this.questionService.getQuestionById(id);
		String response=new String();
		if (question!=null) {
		List <Answer> answers=this.answerRepository.getAnswersByQuestion(question);

		
		
		for(Answer a:answers) {
			this.answerService.deleteAnswer(a.getId());
		};
		
		
		response=this.questionService.deleteQuestion(id);
		return  ResponseEntity.status(HttpStatus.OK).body(response);
		}
		else return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("nie znaleziono");
	}

	
}
