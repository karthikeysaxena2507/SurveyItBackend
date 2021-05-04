package survey.model;

import java.util.*;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "surveys")
public class Survey {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "content", nullable = false)
	private String content;
	
	@Column(name = "author", nullable = false)
	private String author;
	
	@Column(name = "category", nullable = false)
	private String category;
	
	@OneToMany(mappedBy = "survey")
	private Set<Option> options = new HashSet<Option>();
	
	public Survey() {
		
	}

	public Survey(String content, String author, String category, Set<Option> options) {
		super();
		this.content = content;
		this.author = author;
		this.category = category;
		this.options = options;
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

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Set<Option> getOptions() {
		return options;
	}

	public void setOptions(Set<Option> options) {
		this.options = options;
	}
	
}
