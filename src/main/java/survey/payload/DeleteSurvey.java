package survey.payload;

public class DeleteSurvey {
	private String username;
	
	private Long surveyId;
	
	public DeleteSurvey() {
		
	}

	public DeleteSurvey(String username, Long surveyId) {
		super();
		this.username = username;
		this.surveyId = surveyId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Long getSurveyId() {
		return surveyId;
	}

	public void setSurveyId(Long surveyId) {
		this.surveyId = surveyId;
	}
	
	
}
