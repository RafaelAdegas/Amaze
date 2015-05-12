package io.redshoes.amaze.dao.beacon;

import java.util.List;

import io.redshoes.amaze.dao.Dao;
import io.redshoes.amaze.entity.ActivityData;
import io.redshoes.amaze.entity.Beacon;
import io.redshoes.amaze.entity.Establishment;


/**
 * Definition of a Data Access Object that can perform CRUD Operations for {@link NewsEntry}s.
 * 
 * @author Philip W. Sorst <philip@sorst.net>
 */
public interface BeaconDao extends Dao<Beacon, Long>
{
	/**
	 * Method used to retrieve all beacons from current establishment.
	 * @param establishment
	 * @return List of beacons
	 */
	List<Beacon> findAllFromEstablishment(Establishment establishment);
	
	/**
	 * Method used to retrieve all beacons from current establishment and some activity.
	 * @param establishment
	 * @param activityData
	 * @return List of beacons
	 */
	List<Beacon> findAllFromActivity(Establishment establishment, ActivityData activityData);
}