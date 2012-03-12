package wavefancy;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * This class is used to return Hibernate session
 * @author icorner
 *
 */
public class HibernateUtil {
	
	private static SessionFactory sessionFactory;
	
	static{
		sessionFactory = new Configuration().configure().buildSessionFactory();
	}
	
	public static final ThreadLocal<Session> session = new ThreadLocal<Session>();
	
	/**
	 * return Hibernate session, please remember to close this session when you will not use it.
	 * @return
	 */
	public static Session getSession(){
		Session s = session.get();
		if (s == null) {
			s = sessionFactory.openSession();
		}
		return s;
	}
	
	public static void closeSession(){
		Session s = session.get();
		session.set(null);
		if (s != null) {
			s.close();
		}
	}
	
	/**
	 * get the current session, don't need to close it when you don't want to use it.
	 * @return
	 */
	public static Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}
}
