package io.redshoes.amaze.dao.beacon;


import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.transaction.annotation.Transactional;

import io.redshoes.amaze.dao.JpaDao;
import io.redshoes.amaze.entity.ActivityData;
import io.redshoes.amaze.entity.Beacon;
import io.redshoes.amaze.entity.Establishment;


/**
 * JPA Implementation of a {@link BeaconDao}.
 * 
 * @author Philip W. Sorst <philip@sorst.net>
 */
public class JpaBeaconDao extends JpaDao<Beacon, Long> implements BeaconDao
{

	public JpaBeaconDao()
	{
		super(Beacon.class);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Beacon> findAll()
	{
		final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Beacon> criteriaQuery = builder.createQuery(Beacon.class);

		Root<Beacon> root = criteriaQuery.from(Beacon.class);
		criteriaQuery.orderBy(builder.desc(root.get("major")));

		TypedQuery<Beacon> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
		return typedQuery.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<Beacon> findAllFromEstablishment(Establishment establishment) {
		
		Query query = this.getEntityManager().createQuery("select b from Beacon b where b.establishment = :establishment").setParameter("establishment", establishment);

		return (List<Beacon>)query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<Beacon> findAllFromActivity(Establishment establishment, ActivityData activityData) {
		Query query = this.getEntityManager().createQuery("select b from Beacon where b.establishment = :establishment and b.activityData = :activityData")
				.setParameter("establishment", establishment)
				.setParameter("activityData", activityData);
		
		return (List<Beacon>)query.getResultList();
	}

}
