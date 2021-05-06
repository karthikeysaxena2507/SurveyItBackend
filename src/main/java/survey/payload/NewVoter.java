package survey.payload;

public class NewVoter {
	
	private String name;
	
	private Long optionId;
	
	private Long surveyId;
	
	public NewVoter() {
		
	}

	public NewVoter(String name, Long optionId, Long surveyId) {
		super();
		this.name = name;
		this.optionId = optionId;
		this.surveyId = surveyId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getOptionId() {
		return optionId;
	}

	public void setOptionId(Long optionId) {
		this.optionId = optionId;
	}

	public Long getSurveyId() {
		return surveyId;
	}

	public void setSurveyId(Long surveyId) {
		this.surveyId = surveyId;
	}
	
}
