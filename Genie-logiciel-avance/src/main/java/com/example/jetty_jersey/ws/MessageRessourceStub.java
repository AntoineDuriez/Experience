package com.example.jetty_jersey.ws;

import java.util.ArrayList; 
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.example.datanucleus.dao.Message;
//JAVADOC
@Path("/message")
public class MessageRessourceStub {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getmessage")
	public List<Message> getMessage() {
		Message m1 = new Message("Salut", "message 1");
		Message m2 = new Message("Au revoir", "message 2");
		List<Message> listMessage = new ArrayList<Message>();
		listMessage.add(m1);
		listMessage.add(m2);
		return listMessage;
	}
}