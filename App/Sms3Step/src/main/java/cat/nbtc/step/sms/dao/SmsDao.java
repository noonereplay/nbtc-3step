package cat.nbtc.step.sms.dao;



import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cat.nbtc.step.sms.dto.Sms;

@Repository
public class SmsDao {
	
	private static final Logger logger = LoggerFactory.getLogger(SmsDao.class);

	@Autowired
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	
	@Transactional("txManager")
	public Sms getSmsById(Integer messageId){
		Sms sms = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			sms = session.get(Sms.class, messageId);
		} catch (Exception e) {
			logger.error("Error Get transId {} ",messageId,e);
		}
		return sms;
	}
	
	@Transactional(value="txManager",readOnly=false)
	public void save(Sms sms){
		try {
			Session session = sessionFactory.getCurrentSession();
			session.saveOrUpdate(sms);
		} catch (Exception e) {
			logger.error("Error update Sms {} ",sms,e);
		}
	}
}
