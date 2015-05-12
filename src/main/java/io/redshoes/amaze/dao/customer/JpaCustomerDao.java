package io.redshoes.amaze.dao.customer;


import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.transaction.annotation.Transactional;

import io.redshoes.amaze.dao.JpaDao;
import io.redshoes.amaze.entity.Customer;


/**
 * JPA Implementation of a {@link CustomerDao}.
 * 
 * @author Philip W. Sorst <philip@sorst.net>
 */
public class JpaCustomerDao extends JpaDao<Customer, Long> implements CustomerDao
{

	public JpaCustomerDao()
	{
		super(Customer.class);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Customer> findAll()
	{
		final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Customer> criteriaQuery = builder.createQuery(Customer.class);

		Root<Customer> root = criteriaQuery.from(Customer.class);
		criteriaQuery.orderBy(builder.desc(root.get("name")));

		TypedQuery<Customer> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
		return typedQuery.getResultList();
	}

}
