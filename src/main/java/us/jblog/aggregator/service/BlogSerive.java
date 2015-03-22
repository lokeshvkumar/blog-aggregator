package us.jblog.aggregator.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import us.jblog.aggregator.entity.Blog;
import us.jblog.aggregator.entity.Item;
import us.jblog.aggregator.entity.User;
import us.jblog.aggregator.exception.RssException;
import us.jblog.aggregator.repository.BlogRepository;
import us.jblog.aggregator.repository.ItemRepository;
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
	
	
	
	/*
	 * new method added, to allow for saving items based on the URL provided.
	 * The user provides url, then click on save wil ensure rss service is invoked to download
	 * all items, unmarshall them and save them to database.
	 */
	
	@Autowired
	RssService rssService;
	
	@Autowired
	private ItemRepository itemRepository;
	
	public void saveItems(Blog blog){
		List<Item> items;
		try{
			items = rssService.getItems(blog.getUrl());
			for(Item item: items){
				Item savedItem = itemRepository.findByBlogAndLink(blog, item.getLink());
				if(savedItem == null){
					item.setBlog(blog);
					itemRepository.save(item);
				}
			}
		}catch(RssException e){
			e.printStackTrace();
		}
	}

	/**
	 * step 46, added a method to be able to find all blogs and 
	 * reload. Annotated this method as Scheduled and provided a 1 hour
	 * delay between its executions.
	 * 1 hour = 60*60*1000
	 * @param blog
	 * @param name
	 */
	
	@Scheduled(fixedDelay=3600000)
	public void reloadBlogs(){
		
		List<Blog> blogs = blogRepository.findAll();
		for(Blog blog: blogs){
			saveItems(blog);
		}
		
	}
	
	public void save(Blog blog, String name) {
		User user = userRepository.findByName(name);
		blog.setUser(user);
		blogRepository.save(blog);
		saveItems(blog);
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
