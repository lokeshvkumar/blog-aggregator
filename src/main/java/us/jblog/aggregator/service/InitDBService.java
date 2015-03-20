package us.jblog.aggregator.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import us.jblog.aggregator.entity.Blog;
import us.jblog.aggregator.entity.Item;
import us.jblog.aggregator.entity.Role;
import us.jblog.aggregator.entity.User;
import us.jblog.aggregator.repository.BlogRepository;
import us.jblog.aggregator.repository.ItemRepository;
import us.jblog.aggregator.repository.RoleRepository;
import us.jblog.aggregator.repository.UserRepository;

@Transactional
@Service
public class InitDBService {
	
	//19 step, after creating repository interfaces
	/**
	 * To work with the entity, use its corresponding repository 
	 * interfaces.
	 * All these respositories are autowired, as spring 
	 * injects there instances automatically.
	 * Under the hood, spring inserts an instance of some class it generates, 
	 * which implements these interfaces.
	 * 
	 */
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private BlogRepository blogRepository;
	@Autowired
	private ItemRepository itemRepository;
	@Autowired
	private UserRepository userRepository;
	
	 @Autowired
	 private DataSource blogDataSource;
	/**
	 * Method annotated with PostConstruct get executed once spring context 
	 * gets created during deployment and start up.
	 */
	@PostConstruct
	public void init(){
		//create a role as regular user
		Role roleUser = new Role();
		roleUser.setName("ROLE_USER");
		roleRepository.save(roleUser);//save the role
		
		//create a role as admin
		Role roleAdmin = new Role();
		roleAdmin.setName("ROLE_ADMIN");
		roleRepository.save(roleAdmin);//save the role
		
		//create a user as admin
		User userAdmin = new User();
		userAdmin.setName("admin");
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		userAdmin.setPassword(encoder.encode("admin"));
		userAdmin.setEnabled(true);//this line changed to allow admin be able to login as part of step 35
		List<Role> roles = new ArrayList<Role>();
		roles.add(roleAdmin);
		roles.add(roleUser);
		//add two roles to this user
		userAdmin.setRoles(roles);
		userRepository.save(userAdmin);//save the user
		
		//create a blog
		Blog blog = new Blog();
		blog.setName("Video Blog");
		blog.setUrl("http://feeds.feedsburner.com/javavids?format=xml");
		blog.setUser(userAdmin);
		blogRepository.save(blog);
		
		Item item = new Item();
		item.setBlog(blog);
		item.setTitle("First Blog Item");
		item.setPublishedDate(new Date());
		item.setLink("https://javavids.com");
		itemRepository.save(item);
		
		Item item2 = new Item();
		item2.setBlog(blog);
		item2.setTitle("First Blog Item");
		item2.setPublishedDate(new Date());
		item2.setLink("https://javavids.com");
		itemRepository.save(item2);
	}//end method
	
	
}
