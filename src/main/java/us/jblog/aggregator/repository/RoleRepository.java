package us.jblog.aggregator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import us.jblog.aggregator.entity.Role;
import java.lang.String;
import java.util.List;

/**
 * 
 * @author lokesh
 * This User respository refers to User entity. 
 * Each repository will have its asociated repository interface.
 */
public interface RoleRepository extends JpaRepository<Role, Integer>{//Types refer to the object and the primary key reference
	
		Role findByName(String name);
}
