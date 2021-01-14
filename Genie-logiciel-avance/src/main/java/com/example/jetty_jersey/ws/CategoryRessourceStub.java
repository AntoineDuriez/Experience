package com.example.jetty_jersey.ws;

import java.util.ArrayList; 
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.example.datanucleus.dao.*;
//Bouchon webservice Category
@Path("/category")
public class CategoryRessourceStub {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getcategory")
		public List<Category> getCategory() {
			Category cat1 = new Category("Musique pour chat", "pour les chats");
			Category cat2 = new Category("Pop", "pop");
			List<Category> listCat = new ArrayList<Category>();
			listCat.add(cat1);
			listCat.add(cat2);
			return listCat;
		}
}