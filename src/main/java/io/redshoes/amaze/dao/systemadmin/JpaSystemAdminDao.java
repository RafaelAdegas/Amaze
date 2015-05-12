package io.redshoes.amaze.dao.systemadmin;


import io.redshoes.amaze.dao.JpaDao;
import io.redshoes.amaze.entity.SystemAdmin;


/**
 * JPA Implementation of a {@link SystemAdminDao}.
 * 
 * @author Philip W. Sorst <philip@sorst.net>
 */
public class JpaSystemAdminDao extends JpaDao<SystemAdmin, Integer> implements SystemAdminDao
{

	public JpaSystemAdminDao()
	{
		super(SystemAdmin.class);
	}

}
