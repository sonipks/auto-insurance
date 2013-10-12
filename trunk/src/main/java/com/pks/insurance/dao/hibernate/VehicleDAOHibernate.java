/**
 * 
 */
package com.pks.insurance.dao.hibernate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import com.pks.insurance.dao.VehicleDAO;
import com.pks.insurance.domain.Vehicle;

/**
 * Implementation class for VehicleDAO.
 * 
 * @author Pankaj Soni
 * 
 */
public class VehicleDAOHibernate extends HibernateDaoSupport implements
		VehicleDAO {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(VehicleDAOHibernate.class);

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.pks.insurance.dao.VehicleDAO#saveVehicle(com.pks.insurance.domain.Vehicle)
	 */
	@Override
	@Transactional
	public final void saveVehicle(final Vehicle vehicle) {
		getHibernateTemplate().saveOrUpdate(vehicle);
	}

}
