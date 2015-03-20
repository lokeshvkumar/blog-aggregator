package us.jblog.aggregator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import us.jblog.aggregator.entity.Blog;
import us.jblog.aggregator.entity.User;
import us.jblog.aggregator.repository.BlogRepository;
import us.jblog.aggregator.repository.UserRepository;

/**
 * This Service was added as part of step 38, in order to allow for blog submission from
 * the user account page.
 * Finally user-detail page needs to be updated to encompass the whole modal under form tag.
 * @author lokesh
 *
 */
@Service
public class BlogSerive {
	
	@Autowired
	private BlogRepository blogRepository;
	
	@Autowired
	private UserRepository userRepository;

	public void save(Blog blog, String name) {
		User user = userRepository.findByName(name);
		blog.setUser(user);
		blogRepository.save(blog);
	}
	/*
	 * This method was added as part of stpe 40, in order to be 
	 * able to allow for deletion of blog.
	 * We can handle "s org.springframework.orm.jpa.JpaSystemException: org.hibernate.exception.ConstraintViolationException:"
	 * better by looking up all items for this blog, deleting them then removing the blog OR
	 * modify the Blog entity and change cascadetype to remove on blog.items instance variable.
	 */
	public void remove(int id) {
		blogRepository.delete(id);
	}
	
	/*
	 * added for step 41, in order to ensure one user does not delete others blogs
	 */
	public Blog findOne(int id) {
		return blogRepository.findOne(id);
	}
	
	/*
	 * added for step 41, in order to ensure one user does not delete others blogs
	 */
	@PreAuthorize(value = "#blog.user.name == authentication.name or hasRole('ROLE_ADMIN')")
	public void delete(@P("blog") Blog blog) {
		blogRepository.delete(blog);
	}

}
