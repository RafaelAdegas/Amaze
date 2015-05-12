package io.redshoes.amaze.dao.establishment;

import io.redshoes.amaze.dao.JpaDao;
import io.redshoes.amaze.entity.Establishment;


/**
 * JPA Implementation of a {@link EstablishmentDao}.
 * 
 * @author Philip W. Sorst <philip@sorst.net>
 */
public class JpaEstablishmentDao extends JpaDao<Establishment, Long> implements EstablishmentDao
{

	public JpaEstablishmentDao()
	{
		super(Establishment.class);
	}

}
