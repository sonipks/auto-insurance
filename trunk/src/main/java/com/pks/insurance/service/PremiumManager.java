package com.pks.insurance.service;

import java.util.Map;

import com.pks.insurance.dao.UserDAO;

public interface PremiumManager {

	Map<String, Object> getQuote(String ssn);

	void setUserDAO(UserDAO userDAO);

}
