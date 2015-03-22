package us.jblog.aggregator.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

import us.jblog.aggregator.annotation.UserNameConstraint;

/*
 * Entity given a new table name since user is a reserved keyword in some cases
 * and might not work in some databases.
 * Also security.xml needs to be modified to point to the new 
 * user table.
 * 
 */
@Entity
@Table(name = "app_user")
public class User {

	@Id
	@GeneratedValue
	private Integer id;
	
	//step 42, server side validation
	@Size(min=1, message="Name must be minimum 3 characters long!")
	@Column(unique = true)//added as part of step 44. Added annotations to prevent same username creation.
	@UserNameConstraint(message = "Such User already exists!")
	private String name;
	
	//step 42, server side validation
	@Size(min=10, message="Invalid Email size!")
	@Email(message="Invalid Email!")
	private String email;
	
	//step 42, server side validation. Same changes on blogs too.
	@Size(min=5, message="Password needs to be atleast 6 characters long!")
	private String password;
	/*
	 * for step 35, additional column added in order to 
	 * accomodate enabled flag. Registered user will be able to login.
	 * 
	 */
	private boolean enabled;
	
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@ManyToMany
	@JoinTable(name="join_user_role", 
	joinColumns = {@JoinColumn(name="id")},
	inverseJoinColumns={@JoinColumn(name="roleId")})
	private List<Role> roles;
	
	
	public List<Blog> getBlogs() {
		return blogs;
	}

	public void setBlogs(List<Blog> blogs) {
		this.blogs = blogs;
	}
	
	/*
	 * This mapping of blogs user to blogs is by default
	 * lazy. i.e. , fetch=FetchType.LAZY. Either use fetch=FetchType.EAGER
	 * or have script handle the loading of all blogs for users.
	 */
	@OneToMany(mappedBy="user", cascade=CascadeType.REMOVE )
	private List<Blog> blogs;
	

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
