package io.redshoes.amaze.rest.resources;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.redshoes.amaze.JsonViews;
import io.redshoes.amaze.dao.user.UserDao;
import io.redshoes.amaze.entity.User;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * Used to get all other users from the system
 * @author Adegas
 *
 */
@Component
@Path("/users")
public class UsersResource extends GenericResource<User>
{
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ObjectMapper mapper;
	
	@Autowired
	private UserDao userDao;


	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String list() throws JsonGenerationException, JsonMappingException, IOException 
	{
		this.logger.info("list()");
		
		ObjectWriter viewWriter;
		viewWriter = this.mapper.writerWithView(JsonViews.Admin.class);

		List<User> allUsers = this.userDao.findAllFromEstablishment(super.getCurrentUserAccount().getEstablishment());
		
		return viewWriter.writeValueAsString(allUsers);
	}
	
	@GET
	@Path("/getaccess/")
	public User createPageAccessSet()
	{
		return super.getCurrentUserAccount();
	}

}