package com.example.jetty_jersey.ws.impl;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.example.datanucleus.dao.Message;
import com.example.datanucleus.dao.stub.MessageDaoStub;
@Path("messageimpl")
public class MessageRessourceImplStub {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getmessage")
	public List<Message> getMessage() {
		MessageDaoStub mds = new MessageDaoStub();
		return mds.getMessage();
	}
}
