package com.pks.insurance.dao.hibernate;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import com.pks.insurance.dao.UserDAO;
import com.pks.insurance.domain.User;
import com.pks.insurance.domain.Vehicle;

/**
 * Implementation class for RegistrationDAO
 * 
 * @author Pankaj Soni
 */

public class UserDAOHibernate extends HibernateDaoSupport implements UserDAO {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(UserDAOHibernate.class);

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.pks.insurance.dao.UserDAO#saveUser(com.pks.insurance.domain.User)
	 */
	@Override
	@Transactional
	public final void saveUser(User user) {
		Vehicle vehicle = new Vehicle();
		vehicle.setSsn(user.getSsn());
		user.setVehicle(vehicle);
		getHibernateTemplate().saveOrUpdate(user);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.pks.insurance.dao.UserDAO#isUser(com.pks.insurance.domain.User)
	 */
	@Override
	public final boolean isUser(final User user) {
		if (getUser(user.getSsn()) != null)
			return true;
		else
			return false;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.pks.insurance.dao.UserDAO#updateUser(com.pks.insurance.domain.User)
	 */
	@Override
	@Transactional
	public final void updateUser(User user) {
		user.setVehicle((Vehicle) getHibernateTemplate().load(Vehicle.class,
				user.getSsn()));
		getHibernateTemplate().merge(user);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.pks.insurance.dao.UserDAO#getUser(java.lang.String)
	 */
	@Override
	public final User getUser(final String ssn) {
		return (User) getHibernateTemplate().get(User.class, ssn);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.pks.insurance.dao.UserDAO#getUsers()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public final List<User> getUsers() {
		return getHibernateTemplate().find("from User");
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.pks.insurance.dao.UserDAO#getUsers(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public final List<User> getUsers(final String ssn) {
		return getHibernateTemplate().find("from User where ssn like ?",
				"%" + ssn + "%");
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.pks.insurance.dao.UserDAO#removeUser(java.lang.String)
	 */
	@Override
	@Transactional
	public final void removeUser(final String ssn) {
		Object user = getHibernateTemplate().load(User.class, ssn);
		if (user != null)
			getHibernateTemplate().delete(user);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.pks.insurance.dao.UserDAO#authenticateUser(com.pks.insurance.domain.User)
	 */
	@Override
	public final int authenticateUser(final User user) {
		final String sql = "from User where ssn=? AND passwd=?";
		@SuppressWarnings("rawtypes")
		List result = getHibernateTemplate().find(sql,
				new String[] { user.getSsn(), user.getPasswd() });
		if (result.size() == 0) {
			LOGGER.warn("INVALID_USER: " + result);
			return INVALID_USER;
		} else {
			if ("adminadmin".equals(user.getSsn())) {
				LOGGER.warn("VALID_ADMIN: " + result);
				return VALID_ADMIN;
			}
			LOGGER.warn("VALID_USER: " + result);
			return VALID_USER;
		}
	}
}
