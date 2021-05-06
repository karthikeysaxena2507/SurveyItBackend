package survey.payload;

public class NewOption {
	
	private String content;

	public NewOption() {
		
	}
	
	public NewOption(String content) {
		super();
		this.content = content;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
