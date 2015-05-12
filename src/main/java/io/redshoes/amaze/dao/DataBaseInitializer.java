package io.redshoes.amaze.dao;

import io.redshoes.amaze.dao.activitydata.ActivityDataDao;
import io.redshoes.amaze.dao.beacon.BeaconDao;
import io.redshoes.amaze.dao.customer.CustomerDao;
import io.redshoes.amaze.dao.establishment.EstablishmentDao;
import io.redshoes.amaze.dao.user.UserDao;
import io.redshoes.amaze.entity.ActivityData;
import io.redshoes.amaze.entity.Beacon;
import io.redshoes.amaze.entity.Customer;
import io.redshoes.amaze.entity.Establishment;
import io.redshoes.amaze.entity.User;

import org.springframework.security.crypto.password.PasswordEncoder;


/**
 * Initialize the database with some test entries.
 * 
 * @author Philip W. Sorst <philip@sorst.net>
 */
public class DataBaseInitializer
{

	private UserDao userDao;

	private PasswordEncoder passwordEncoder;
	
	private BeaconDao beaconDao;
	
	private CustomerDao customerDao;
	
	private EstablishmentDao establishmentDao;
	
	private ActivityDataDao activityDao;


	protected DataBaseInitializer()
	{
		/* Default constructor for reflection instantiation */
	}


	public DataBaseInitializer(UserDao userDao, PasswordEncoder passwordEncoder, BeaconDao beaconDao, CustomerDao customerDao, EstablishmentDao establishmentDao, ActivityDataDao activityDao)
	{
		this.userDao = userDao;
		this.passwordEncoder = passwordEncoder;
		this.beaconDao = beaconDao;
		this.customerDao = customerDao;
		this.establishmentDao = establishmentDao;
		this.activityDao = activityDao;
	}


	public void initDataBase()
	{
		User userUser = new User("user", this.passwordEncoder.encode("user"));
		userUser.addRole("user");
		this.userDao.save(userUser);

		User adminUser = new User("admin", this.passwordEncoder.encode("admin"));
		adminUser.addRole("user");
		adminUser.addRole("admin");
		adminUser = this.userDao.save(adminUser);
		
		Establishment establishment = new Establishment();
		establishment.setCode("Establishment CODE");
		establishment.setFormalName("FORMAL NAME hue hue");
		establishment.setLongName("LONG NAME haha");
		establishment.setShortName("SHORT NAME");
		establishment = this.establishmentDao.save(establishment);
		
		adminUser.setEstablishment(establishment);
		adminUser = this.userDao.save(adminUser);

		for (int i = 0; i < 5; i++) {			
			Beacon beacon = new Beacon();
			beacon.setMajor("major " + i);
			beacon.setMinor("minor "+ i);
			beacon.setUuid("uuid " + i);
			beacon.setEstablishment(establishment);
			beacon.setLocale("proximity to "+i);
			beacon.setName("BRIEF name"+i);
			beacon.setDescription("BRIEF DESCRIPTION OF BEACON "+i);
			
			Customer customer = new Customer();
			customer.setLogin("login " + i);
			customer.setName("name " + i);
			customer.setPassword("password " + i);
			
			this.customerDao.save(customer);
			
			ActivityData ac = new ActivityData();
			ac.setActivityType("activity type "+ i);
			ac.setCode("activity CODE "+i);
			ac.setImageUrl("activity URL "+i);
			ac.setText("TEXT TO SHOW ACTIVITY");
			
			beacon.setActivityData(ac);
			this.beaconDao.save(beacon);
		}
	}

}