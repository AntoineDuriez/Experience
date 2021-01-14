package datanucleus;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;

import org.junit.Test;

import com.example.datanucleus.dao.Map;
import com.example.datanucleus.dao.MapDao;
import com.example.datanucleus.dao.Marker;
import com.example.datanucleus.dao.MarkerDao;
import com.example.datanucleus.dao.dn.MapDaoImpl;
import com.example.datanucleus.dao.dn.MarkerDaoImpl;

public class MarkerDaoImplTest {
	@Test
	public void test() {
		PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("Example");
		MarkerDao markerDao = new MarkerDaoImpl(pmf);

		markerDao.addMarker("lenom", "desc", 2.5644, 45.566);
		markerDao.addMarker("BOONY", "Le boboon BOONY", 384, 48);
		
		List<Marker> markers = markerDao.getMarker();
		for(Marker m : markers) {
			System.out.println(m.getName());
		}
		markerDao.deleteMarker("BOONY");
		markers = markerDao.getMarker();
		for(Marker m : markers) {
			System.out.println(m.getName());
		}
		pmf.close();
	}
}
