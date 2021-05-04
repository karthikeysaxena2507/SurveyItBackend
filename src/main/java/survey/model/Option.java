package survey.model;

import java.util.*;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "options")
public class Option {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "content", nullable = false)
	private String content;
	
	@Column(name = "numberOfVotes", nullable = false)
	private long numberOfVotes;
	
	@ManyToOne
	@JoinColumn(name = "survey_id", nullable = false)
	private Survey survey;
	
	@OneToMany(mappedBy = "option")
	private Set<Voter> voters = new HashSet<Voter>();
	
	public Option() {
		
	}
	
	public Option(String content, long numberOfVotes, Survey survey, Set<Voter> voters) {
		super();
		this.content = content;
		this.numberOfVotes = numberOfVotes;
		this.survey = survey;
		this.voters = voters;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public long getNumberOfVotes() {
		return numberOfVotes;
	}

	public void setNumberOfVotes(long numberOfVotes) {
		this.numberOfVotes = numberOfVotes;
	}

	public Survey getSurvey() {
		return survey;
	}

	public void setSurvey(Survey survey) {
		this.survey = survey;
	}

	public Set<Voter> getVoters() {
		return voters;
	}

	public void setVoters(Set<Voter> voters) {
		this.voters = voters;
	}
}
