package io.redshoes.amaze.rest.resources;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import io.redshoes.amaze.JsonViews;
import io.redshoes.amaze.dao.user.UserDao;
import io.redshoes.amaze.entity.Beacon;
import io.redshoes.amaze.entity.User;
import io.redshoes.amaze.rest.TokenUtils;
import io.redshoes.amaze.transfer.TokenTransfer;
import io.redshoes.amaze.transfer.UserTransfer;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;


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

		List<User> allUsers = this.userDao.findAllFromEstablishment(super.getCurrentEstablishment());
		
		return viewWriter.writeValueAsString(allUsers);
	}

}