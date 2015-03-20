package us.jblog.aggregator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import us.jblog.aggregator.entity.Blog;
import us.jblog.aggregator.entity.User;
import java.util.List;

/**
 * 
 * @author lokesh
 * This User respository refers to User entity. 
 * Each repository will have its asociated repository interface.
 */
public interface BlogRepository extends JpaRepository<Blog, Integer>{//Types refer to the object and the primary key reference
		
		/**
		 * Spring data jpa will generate the required code to
		 * pull all blogs based on the user id specified.
		 * @param user
		 * @return
		 */
		List<Blog> findByUser(User user);
	
}
