package com.my.ms.le.service;

import com.my.ms.le.dto.CustomerDTO;

public interface AccountsService {
	
	void createAccount(CustomerDTO customerDTO);
	CustomerDTO getCustomerByMobileNumber(String mobileNumber);
	boolean updateAccount(CustomerDTO customerDTO);
	boolean deleteAccount(String mobileNumber);
}
