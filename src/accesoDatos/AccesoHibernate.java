package accesoDatos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import logicaRefrescos.Deposito;
import logicaRefrescos.Dispensador;

public class AccesoHibernate implements I_Acceso_Datos {
	Session s;
	private SessionFactory sessionFactory;
	private Deposito deposito;
	private Dispensador dispensador;

	public AccesoHibernate() {
		sessionFactory = new Configuration().configure().buildSessionFactory();
		s = sessionFactory.openSession();
	}

	@Override
	public HashMap<Integer, Deposito> obtenerDepositos() {

		HashMap<Integer, Deposito> depositosCreados = new HashMap<Integer, Deposito>();

		Query q = s.createQuery("select e from Deposito e");
		List results = q.list();

		Iterator depositosIterator = results.iterator();

		while (depositosIterator.hasNext()) {
			Deposito deposito = (Deposito) depositosIterator.next();
			
			depositosCreados.put(deposito.getValor(), deposito);
		}

		return depositosCreados;
	}

	@Override
	public HashMap<String, Dispensador> obtenerDispensadores() {
		HashMap<String, Dispensador> dispensadoresCreados = new HashMap<String, Dispensador>();

		Query q = s.createQuery("select c from Dispensador c");
		List results = q.list();

		Iterator depositosIterator = results.iterator();

		while (depositosIterator.hasNext()) {
			Dispensador dispensador = (Dispensador) depositosIterator.next();
			// deposito = new Deposito(deposito.getNombreMoneda(), deposito.getValor(),
			// deposito.getCantidad());
			
			dispensadoresCreados.put(dispensador.getClave(), dispensador);
		}

		return dispensadoresCreados;
	}

	@Override
	public boolean guardarDepositos(HashMap<Integer, Deposito> depositos) {
		s.beginTransaction();

		for (Map.Entry<Integer, Deposito> entry : depositos.entrySet()) {
			
			deposito = entry.getValue();
			s.update(deposito);
			
		}
	
		s.getTransaction().commit();
		boolean todoOK = true;

		return todoOK;
	}

	@Override
	public boolean guardarDispensadores(HashMap<String, Dispensador> dispensadores) {
		
		s.beginTransaction();
		for (Map.Entry<String, Dispensador> entry : dispensadores.entrySet()) {
			
			dispensador = entry.getValue();
			s.update(dispensador);
			
		}
		s.getTransaction().commit();

		boolean todoOK = true;

		return todoOK;
	}

}
