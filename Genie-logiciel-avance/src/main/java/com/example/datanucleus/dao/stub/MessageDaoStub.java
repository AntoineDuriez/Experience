package com.example.datanucleus.dao.stub;

import java.util.ArrayList;
import java.util.List;

import com.example.datanucleus.dao.*;

public class MessageDaoStub implements MessageDao{

	@Override
	public List<Message> getMessage() {
		Message m1 = new Message("Salut", "message 1");
		Message m2 = new Message("Au revoir", "message 2");
		List<Message> listMessage = new ArrayList<Message>();
		listMessage.add(m1);
		listMessage.add(m2);
		return listMessage;
	}

}
