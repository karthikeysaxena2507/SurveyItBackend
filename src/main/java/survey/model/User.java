package survey.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.sun.istack.NotNull;

/**
 * @Entity annotation shows that the given class will be used as an entity
 * @Table annotation specifies the name of the table to be created for the entity in the database
 */
@Entity
@Table(name = "users")
public class User {
	
	/**
	 * @GeneratedValue annotation provides a way to generate primary keys in spring boot
	 * @Id annotation tells that id is the unique identifier of the object
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	/**
	 * @Column specifies the column name to be created in the database
	 */
	@Column(name = "username")
	@NotNull
	private String username;
	
	@Column(name = "email")
	@NotNull
	private String email;
	
	@Column(name = "password")
	@NotNull
	private String password;
	
	/**
	 * Default Constructor
	 */
	public User() {
		
	}

	/**
	 * Parameterized Constructor
	 * @param username
	 * @param email
	 * @param password
	 */
	public User(String username, String email, String password) {
		super();
		this.username = username;
		this.email = email;
		this.password = password;
	}

	/*
	 * The Getters and Setters (encapsulation)
	 */
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
