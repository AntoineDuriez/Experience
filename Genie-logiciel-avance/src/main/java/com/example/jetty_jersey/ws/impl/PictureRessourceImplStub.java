package com.example.jetty_jersey.ws.impl;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.example.datanucleus.dao.Picture;
import com.example.datanucleus.dao.stub.PictureDaoStub;
@Path("/pictureimpl")
public class PictureRessourceImplStub {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getpicture")
	public List<Picture> getPicture() {
		PictureDaoStub pds = new PictureDaoStub();
		return pds.getPicture();
	}
}
