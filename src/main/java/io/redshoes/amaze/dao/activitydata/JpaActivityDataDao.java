package io.redshoes.amaze.dao.activitydata;


import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

import io.redshoes.amaze.dao.JpaDao;
import io.redshoes.amaze.entity.ActivityData;


/**
 * JPA Implementation of a {@link ActivityDataDao}.
 * 
 * @author Philip W. Sorst <philip@sorst.net>
 */
public class JpaActivityDataDao extends JpaDao<ActivityData, Long> implements ActivityDataDao
{

	public JpaActivityDataDao()
	{
		super(ActivityData.class);
	}
	
	@Override
	@Transactional(readOnly = true)
	public ActivityData find(Long idActivityData) {
		
		Query query = this.getEntityManager().createQuery("select a from ActivityData JOIN FETCH where a.idActivityData = :idActivityData").setParameter("idActivityData", idActivityData);

		return (ActivityData) query.getSingleResult();
	}

}
