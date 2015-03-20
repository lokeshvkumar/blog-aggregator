package us.jblog.aggregator.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import us.jblog.aggregator.entity.Role;
import us.jblog.aggregator.entity.User;

import java.lang.String;
import java.util.List;

/**
 * 
 * @author lokesh
 * This User respository refers to User entity. 
 * Each repository will have its asociated repository interface.
 */
public interface UserRepository extends JpaRepository<User, Integer>{//Types refer to the object and the primary key reference
	
		User findByName(String name);
		List<User> findByRoles(List<Role> roles);
}
