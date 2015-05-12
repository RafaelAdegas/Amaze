package io.redshoes.amaze.rest.resources;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import io.redshoes.amaze.JsonViews;
import io.redshoes.amaze.dao.activitydata.ActivityDataDao;
import io.redshoes.amaze.dao.beacon.BeaconDao;
import io.redshoes.amaze.dao.user.UserDao;
import io.redshoes.amaze.entity.Beacon;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
@Path("/beacons")
public class BeaconResource extends GenericResource<Beacon>
{
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private BeaconDao beaconDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private ActivityDataDao activityDao;
	
	@Autowired
	private ObjectMapper mapper;
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String list() throws JsonGenerationException, JsonMappingException, IOException 
	{
		this.logger.info("list()");
		
		ObjectWriter viewWriter;
		if (this.isAdmin()) {
			viewWriter = this.mapper.writerWithView(JsonViews.Admin.class);
		} else {
			viewWriter = this.mapper.writerWithView(JsonViews.User.class);
		}
		List<Beacon> allBeacons = this.beaconDao.findAllFromEstablishment(super.getCurrentEstablishment());
		
		return viewWriter.writeValueAsString(allBeacons);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{idBeacon}")
	public Beacon read(@PathParam("idBeacon") Long idBeacon)
	{
		this.logger.info("read(idBeacon)");

		Beacon beacon = this.beaconDao.find(idBeacon);
		if (beacon == null) {
			throw new WebApplicationException(404);
		}
		return beacon;
	}
	
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Beacon create(Beacon beacon) throws IOException
	{
		this.logger.info("create(): " + beacon);

		beacon.setEstablishment(super.getCurrentEstablishment());
		
		beacon.getActivityData().setImageUrl(super.saveDataUriToFile(beacon.getActivityData().getImageUrl()));
		System.out.println(beacon.getActivityData().getImageUrl());

		return this.beaconDao.save(beacon);
	}


	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{idBeacon}")
	public Beacon update(@PathParam("idBeacon") Long idBeacon, Beacon beacon) throws IOException
	{
		this.logger.info("update(): " + beacon);
		
		beacon.setEstablishment(super.getCurrentEstablishment());
		beacon.getActivityData().setImageUrl(super.saveDataUriToFile(beacon.getActivityData().getImageUrl()));
		System.out.println(beacon.getActivityData().getImageUrl());

		return this.beaconDao.save(beacon);
	}


	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{idBeacon}")
	public void delete(@PathParam("idBeacon") Long idBeacon)
	{
		this.logger.info("delete(idBeacon)");

		this.beaconDao.delete(idBeacon);
	}

}