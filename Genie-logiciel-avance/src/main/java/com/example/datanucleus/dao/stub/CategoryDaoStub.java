package com.example.datanucleus.dao.stub;
import java.util.ArrayList;
import java.util.List;

import com.example.datanucleus.dao.*;
public class CategoryDaoStub implements CategoryDao{

	@Override
	public List<Category> getCategory() {
		Category cat1 = new Category("Musique pour chat", "pour les chats");
		Category cat2 = new Category("Pop", "pop");
		List<Category> listCat = new ArrayList<Category>();
		listCat.add(cat1);
		listCat.add(cat2);
		return listCat;
	}
}
