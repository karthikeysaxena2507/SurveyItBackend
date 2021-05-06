package survey.payload;

import java.util.*;

public class NewSurveyRequest {
	private String author;
	private String content;
	private List<NewOption> options = new ArrayList<NewOption>();
	
	public NewSurveyRequest() {
		
	}
	
	public NewSurveyRequest(String author, String content, List<NewOption> options) {
		super();
		this.author = author;
		this.content = content;
		this.options = options;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public List<NewOption> getOptions() {
		return options;
	}
	public void setOptions(List<NewOption> options) {
		this.options = options;
	}
	
	
}
