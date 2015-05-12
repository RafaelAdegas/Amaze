package io.redshoes.amaze.dao.user;

import io.redshoes.amaze.dao.Dao;
import io.redshoes.amaze.entity.User;

import org.springframework.security.core.userdetails.UserDetailsService;


public interface UserDao extends Dao<User, Long>, UserDetailsService
{

	User findByName(String name);

}