package com.example.datanucleus.dao.stub;
import java.util.ArrayList;
import java.util.List;

import com.example.datanucleus.dao.*;
public class PictureDaoStub implements PictureDao{

	@Override
	public List<Picture> getPicture() {
		Picture p1 = new Picture("Photo de vacande de Boony");
		Picture p2 = new Picture("Profil tinder de Rémy");
		List<Picture> listPic = new ArrayList<Picture>();
		listPic.add(p1);
		listPic.add(p2);
		return listPic;
	}



}
