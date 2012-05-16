package com.school.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.security.core.userdetails.UserDetails;

import com.school.util.OtherUtils;

@Entity
@Table(name = "user")
@XmlRootElement
public class User extends BaseEntity implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(unique = true, name = "username", nullable = false)
	private String username;
	@Column(name = "password", nullable = false)
	private String password;
	@Column(name = "name", nullable = false)
	private String name;
	@Column(name = "email", nullable = false)
	private String email;
	@Column(columnDefinition = "tinyint")
	private boolean active = true;

	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	private List<UserAuthority> authorities;

	public User() {
		super();
	}

	public boolean userHasRole(String role) {
		if (authorities != null) {
			for (UserAuthority authority : authorities) {
				if (authority.getAuthority().equals(role)) {
					return true;
				}
			}
		}
		return false;
	}

	public User(String username, String password, String name, String email) {
		super();
		this.username = username;
		this.password = password;
		this.name = name;
		this.email = email;
	}

	private void init() {
		authorities = new ArrayList<>();
	}

	public void addAuthorities(List<? extends UserAuthority> listAuthorities) {
		if (authorities == null) {
			init();
		}
		authorities.addAll(listAuthorities);
	}

	public void addAuthority(UserAuthority authority) {
		if (authorities == null) {
			init();
		}
		authorities.add(authority);
	}

	@SuppressWarnings("unchecked")
	public void setAuthorities(List<? extends UserAuthority> authorities) {
		this.authorities = (List<UserAuthority>) authorities;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public List<? extends UserAuthority> getAuthorities() {
		if (authorities == null) {
			init();
		}
		return authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return active;
	}

	@Override
	public boolean isAccountNonLocked() {
		return active;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return active;
	}

	@Override
	public boolean isEnabled() {
		return active;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		if (password != null) {
			password = OtherUtils.hashPassword(password);
		}
		this.password = password;
	}

	public boolean isValidData() {
		// TODO Auto-generated method stub
		return true;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isActive() {
		return active;
	}
	

}
