package com.atlas.scheduler.gateway.repository;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity implements Serializable {

	private static final long serialVersionUID = -7937447843609868349L;

	@Id
	@Column(name="user_id")
	private String userId;

	@Column(name="first_name")
	private String firstName;
	@Column(name="last_name")
	private String lastName;

	@Column(name="password")
	private String encryptedPassword;
	
	@ManyToMany
	@JoinTable(	name = "user_roles", 
				joinColumns = @JoinColumn(name = "user_id"), 
				inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Roles> roles = new HashSet<>();

}
