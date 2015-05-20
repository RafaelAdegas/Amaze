package io.redshoes.amaze.dao.user;

import java.util.List;

import io.redshoes.amaze.dao.Dao;
import io.redshoes.amaze.entity.Establishment;
import io.redshoes.amaze.entity.User;

import org.springframework.security.core.userdetails.UserDetailsService;


public interface UserDao extends Dao<User, Long>, UserDetailsService
{
	/**
	 * Method used to retrieve all users from current establishment.
	 * @param establishment
	 * @return List of users
	 */
	List<User> findAllFromEstablishment(Establishment establishment);
	
	User findByName(String name);

}