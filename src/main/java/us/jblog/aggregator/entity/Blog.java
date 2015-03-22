package us.jblog.aggregator.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.URL;

/**
 * Entity implementation class for Entity: Blog
 *
 */
@Entity
public class Blog implements Serializable {


	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Integer id;
	
	//step 42, server side validation
	@Size(min=3,message="Name should be atleast 3 characters long!")
	private String name;
	
	//step 42, server side validation
	/** This will throw exception, hence needs to be shown in a nice way on controller side.
	 * org.springframework.web.util.NestedServletException: Request processing failed; nested exception is javax.validation.ConstraintViolationException: Validation failed for classes [us.jblog.aggregator.entity.User] during persist time for groups [javax.validation.groups.Default, ]
List of constraint violations:[
	ConstraintViolationImpl{interpolatedMessage='Name must be minimum 3 characters long!', propertyPath=name, rootBeanClass=class us.jblog.aggregator.entity.User, messageTemplate='Name must be minimum 3 characters long!'}
]
	 */
	@Size(min=3, message="URL must be minimum 3 characters long!")
	@URL(message="Invalid URL!")
	
	@Column(length = 1000)
	private String url;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	
	/**
	 * cascade type added to ensure cascade delete on blogs.
	 * cascade added for step 40. As a result itmes will be removed first, then the blog.
	 */
	@OneToMany(mappedBy = "blog", cascade=CascadeType.REMOVE)
	private List<Item> items;
	
	public String getUrl() {
		return url;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
