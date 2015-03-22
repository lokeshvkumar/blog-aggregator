package us.jblog.aggregator.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import us.jblog.aggregator.entity.Blog;
import us.jblog.aggregator.entity.Role;
import us.jblog.aggregator.entity.User;
import us.jblog.aggregator.repository.BlogRepository;
import us.jblog.aggregator.repository.ItemRepository;
import us.jblog.aggregator.repository.RoleRepository;
import us.jblog.aggregator.repository.UserRepository;

/**
 * Transactional added this service as there is 
 * form submission and other database submission methods invoked.
 * @author lokesh
 *
 */
@Service
@Transactional
public class UserService {

	@Autowired
	private UserRepository userRepo;
	@Autowired
	private BlogRepository blogRepo;
	@Autowired
	private ItemRepository itemRepo;
	@Autowired
	private RoleRepository roleRepo;
	
	public List<User> findAll(){
		return userRepo.findAll();
	}

	public Object findOne(int id) {
		return userRepo.findOne(id);
	}
	
	/**
	 *  STEP 25 
	 * is to add paging and sorting capabilities. This can be done by adding another arguement 
	 * Pageable to method findOneWithBlogs in user service
	 * @param model
	 * @param id
	 * @return
	 * Next step 26, refer usercontroller. regustation form added.
	 */
	public User findOneWithBlogs(int id) {
		User user = userRepo.findOne(id);
		List<Blog> blogs = blogRepo.findByUser(user);
		//for each blog find items
		for (Blog blog: blogs) {
			//set the items to each blog
			//we only get 1st 10 items for each blog ordered by publisheddate in descending order.
			blog.setItems(itemRepo.findByBlog(blog, new PageRequest(0, 10, Direction.DESC,
					"publishedDate")));
		}
		user.setBlogs(blogs);//set blogs list to the user
		return user;
	}
	/**
	 * added as part of step 27
	 * @param user
	 * step 28 is about securing the web application.
	 * Refer configuration changes.
	 */
	public void save(User user) {
		/*
		 * as part of step 35, before saving the user, enable the access to the application
		 * and encode the password.
		 * Also the user needs to have role user in order to be able to login to application.
		 * copy piece of code from initdbservice.
		 */
		BCryptPasswordEncoder encode = new BCryptPasswordEncoder();
		user.setPassword(encode.encode(user.getPassword()));
		user.setEnabled(true);
		//step 35, add user role to registered user.
		List<Role> roles = new ArrayList<Role>();
		roles.add(roleRepo.findByName("ROLE_USER"));
		user.setRoles(roles);
		userRepo.save(user);
		
		//step 36 is redirect. registration completion status, and refreshing the same page results in re-insert of form submitted.
		//solution? redirect!!
	}
	
	/**
	 * this method was added as part of step 37, to retrieve user details 
	 * based on user name obtained from principal object.
	 * Next add link to the classic.jsp and show it only if the user is authenticated
	 * @param name
	 * @return
	 */
	public User findOneWithBlogs(String name) {
		User user = userRepo.findByName(name);
		return findOneWithBlogs(user.getId());
	}

	public void remove(int id) {
		userRepo.delete(id);
	}

	public User findOne(String userName) {
		return userRepo.findByName(userName);
	}
}
