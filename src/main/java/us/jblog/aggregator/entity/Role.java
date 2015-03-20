package us.jblog.aggregator.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Role
 *
 */
@Entity
public class Role implements Serializable {


	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private Integer roleId;
	private String name;
	
	@ManyToMany(mappedBy="roles")
	List<User> users;
	
	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getId() {
		return roleId;
	}

	public void setId(Integer id) {
		this.roleId = id;
	}

}
