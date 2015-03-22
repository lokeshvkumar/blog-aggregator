package us.jblog.aggregator.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import us.jblog.aggregator.entity.Item;
import us.jblog.aggregator.entity.Blog;

import java.util.List;

/**
 * 
 * @author lokesh
 * This User respository refers to User entity. 
 * Each repository will have its asociated repository interface.
 */
public interface ItemRepository extends JpaRepository<Item, Integer>{//Types refer to the object and the primary key reference
	
		List<Item> findByBlog(Blog blog, Pageable pageable);
		
		Item findByBlogAndLink(Blog blog, String link);
	
}
