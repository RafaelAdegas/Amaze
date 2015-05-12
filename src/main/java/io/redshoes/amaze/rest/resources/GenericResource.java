package io.redshoes.amaze.rest.resources;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.ws.rs.WebApplicationException;

import io.redshoes.amaze.dao.user.UserDao;
import io.redshoes.amaze.entity.Establishment;
import io.redshoes.amaze.entity.User;
import io.redshoes.amaze.transfer.UserTransfer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.sun.jersey.core.util.Base64;

public class GenericResource<T> {
	
	@Autowired
	private UserDao userDao;
	
	
	public Establishment getCurrentEstablishment() {
		
		UserResource userResource = new UserResource();
		UserTransfer userTransf = userResource.getUser();
		User user = userDao.findByName(userTransf.getName());
		
		return user.getEstablishment();
	}
	
	public boolean isAdmin()
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
	
	public String saveDataUriToFile(String dataUri) throws IOException {
		String encodingPrefix = "data:image/png;base64,";
		int contentStartIndex = dataUri.indexOf(encodingPrefix) + encodingPrefix.length();
		
		byte[] imageData = Base64.decode(dataUri.substring(contentStartIndex));
		
		BufferedImage inputImage = ImageIO.read(new ByteArrayInputStream(imageData));
		
		String fileLocation = inputImage.getProperty("name")+".jpg";
		String path = System.getProperty("catalina.base") + "\\wtpwebapps\\amaze-webapp\\images\\"+fileLocation;
		File outputImage = new File(path);
		ImageIO.write(inputImage, "jpg", outputImage);
		
		return fileLocation;
	}
	
}
