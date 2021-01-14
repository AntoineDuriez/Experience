package com.example.jetty_jersey.ws;

import java.util.ArrayList; 
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.example.datanucleus.dao.Picture;
//JAVADOC
@Path("/picture")
public class PictureRessourceStub {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getpicture")
	public List<Picture> getPicture() {
		Picture p1 = new Picture("Photo de vacance de Boony");
		Picture p2 = new Picture("Profil tinder de Rémy");
		List<Picture> listPic = new ArrayList<Picture>();
		listPic.add(p1);
		listPic.add(p2);
		return listPic;
	}
}
