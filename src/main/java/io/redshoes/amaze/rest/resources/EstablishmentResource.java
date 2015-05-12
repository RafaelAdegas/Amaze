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
import io.redshoes.amaze.dao.establishment.EstablishmentDao;
import io.redshoes.amaze.entity.Establishment;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@Path("/establishments")
public class EstablishmentResource
{
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private EstablishmentDao establishmentDao;
	
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
		List<Establishment> allEstablishments = this.establishmentDao.findAll();
		
		return viewWriter.writeValueAsString(allEstablishments);
	}
	

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Establishment read(@PathParam("id") Long id)
	{
		this.logger.info("read(id)");

		Establishment establishment = this.establishmentDao.find(id);
		if (establishment == null) {
			throw new WebApplicationException(404);
		}
		return establishment;
	}
	
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Establishment create(Establishment establishment)
	{
		this.logger.info("create(): " + establishment);

		return this.establishmentDao.save(establishment);
	}


	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Establishment update(@PathParam("id") Long id, Establishment establishment)
	{
		this.logger.info("update(): " + establishment);

		return this.establishmentDao.save(establishment);
	}


	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{idEstablishment}")
	public void delete(@PathParam("idEstablishment") Long id)
	{
		this.logger.info("delete(id)");

		this.establishmentDao.delete(id);
	}
	
	
	private boolean isAdmin()
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Object principal = authentication.getPrincipal();
		if (principal instanceof String && ((String) principal).equals("anonymousUser")) {
			return false;
		}
		UserDetails userDetails = (UserDetails) principal;

		for (GrantedAuthority authority : userDetails.getAuthorities()) {
			if (authority.toString().equals("admin")) {
				return true;
			}
		}

		return false;
	}
}