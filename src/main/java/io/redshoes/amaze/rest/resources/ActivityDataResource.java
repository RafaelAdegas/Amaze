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
import io.redshoes.amaze.entity.ActivityData;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Path("/activitydata")
public class ActivityDataResource extends GenericResource<ActivityData>
{
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ActivityDataDao activityDataDao;
	
	@Autowired
	private ObjectMapper mapper;
	

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String list() throws JsonGenerationException, JsonMappingException, IOException 
	{
		this.logger.info("list()");
		
		ObjectWriter viewWriter;
		if (super.isAdmin()) {
			viewWriter = this.mapper.writerWithView(JsonViews.Admin.class);
		} else {
			viewWriter = this.mapper.writerWithView(JsonViews.User.class);
		}
		List<ActivityData> allActivities = this.activityDataDao.findAll();
		
		return viewWriter.writeValueAsString(allActivities);
	}


	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{idActivityData}")
	public ActivityData read(@PathParam("idActivityData") Long idActivityData)
	{
		this.logger.info("read(idActivityData)");

		ActivityData activityData = this.activityDataDao.find(idActivityData);
		if (activityData == null) {
			throw new WebApplicationException(404);
		}
		return activityData;
	}
	
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public ActivityData create(ActivityData activityData)
	{
		this.logger.info("create(): " + activityData);

		return this.activityDataDao.save(activityData);
	}


	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{idActivityData}")
	public ActivityData update(@PathParam("idActivityData") Long idActivityData, ActivityData activityData)
	{
		this.logger.info("update(): " + activityData);

		return this.activityDataDao.save(activityData);
	}


	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{idActivityData}")
	public void delete(@PathParam("idActivityData") Long idActivityData)
	{
		this.logger.info("delete(idActivityData)");

		this.activityDataDao.delete(idActivityData);
	}

}