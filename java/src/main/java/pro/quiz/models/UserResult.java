package pro.quiz.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;
import pro.quiz.serializers.UserDeserializer;
import pro.quiz.serializers.UserResultDeserializer;
import pro.quiz.serializers.UserResultSerializer;
import pro.quiz.serializers.UserSerializer;


@Data
@Entity
@Table(name = "UserResult")

@JsonSerialize(using = UserResultSerializer.class)
@JsonDeserialize(using = UserResultDeserializer.class)


public class UserResult {

	@Id 
	@GeneratedValue 
	private Long id;
	
	@NotNull
	private Float result;
	
	@ManyToOne
    @JoinColumn(name="subject_id")
	private Subject subject;
	
	
	@ManyToOne
    @JoinColumn(name="user_id")
	private User user;
	
	public UserResult() {}

	public UserResult(Long id, @NotNull Float result, Subject subject, User user) {
		super();
		this.id = id;
		this.result = result;
		this.subject = subject;
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Float getResult() {
		return result;
	}

	public void setResult(Float result) {
		this.result = result;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
	
}