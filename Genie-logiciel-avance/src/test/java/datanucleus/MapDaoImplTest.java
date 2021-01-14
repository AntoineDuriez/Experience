package datanucleus;

import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.junit.Assert;
import org.junit.Test;

import com.example.datanucleus.dao.Action;
import com.example.datanucleus.dao.ActionDao;
import com.example.datanucleus.dao.DAO;
import com.example.datanucleus.dao.Map;
import com.example.datanucleus.dao.MapDao;
import com.example.datanucleus.dao.dn.ActionDaoImpl;
import com.example.datanucleus.dao.dn.MapDaoImpl;

public class MapDaoImplTest {
	@Test
	public void test() {
		PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("Example");
		MapDao mapDao = new MapDaoImpl(pmf);

		//Assert.assertEquals(0, mapDao.getActions("user1").size());
		
		//mapDao.getActions("user2");
		//Map map = new Map("Test");
		/*
		action.setUsername("user1");
		action.setTitle("A title");
		action.setContent("A content");*/
		
		
		mapDao.addMap("Alderande",34,34);
		mapDao.addMap("Hosnian Prime",355,3554);
		mapDao.addMap("Coruscant",454, 345);
		
		List<Map> maps = mapDao.getMaps();
		for(Map m : maps) {
			System.out.println(m.getName());
		}
		
		/*for(Map m : maps) {
			mapDao.deleteMap(m);
		}*/
		
		
		//Assert.assertEquals(1, mapDao.getActions("user1").size());
		

		//DAO.getActionDao().getActions("user1");
		
		
		pmf.close();
	}
}
