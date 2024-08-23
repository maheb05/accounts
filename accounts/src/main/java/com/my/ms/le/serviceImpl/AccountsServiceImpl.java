package com.my.ms.le.serviceImpl;

import java.util.Optional;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.my.ms.le.constants.AccountsConstants;
import com.my.ms.le.dto.AccountsDTO;
import com.my.ms.le.dto.CustomerDTO;
import com.my.ms.le.entity.Accounts;
import com.my.ms.le.entity.Customer;
import com.my.ms.le.exception.CustomerAlreadyExistException;
import com.my.ms.le.exception.ResourceNotFoundException;
import com.my.ms.le.mapper.AccountsMapper;
import com.my.ms.le.mapper.CustomerMapper;
import com.my.ms.le.repository.AccountsRepository;
import com.my.ms.le.repository.CustomerRepository;
import com.my.ms.le.service.AccountsService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AccountsServiceImpl implements AccountsService {
	
	private AccountsRepository accountsRepository;
	private CustomerRepository customerRepository;

	@Override
	public void createAccount(CustomerDTO customerDTO) {
		
		Customer customer = CustomerMapper.mapToCustomer(customerDTO, new Customer());
		Optional<Customer> mobileNumber = customerRepository.findByMobileNumber(customer.getMobileNumber());
		if(mobileNumber.isPresent()) {
			throw new CustomerAlreadyExistException("A Customer with this "+customer.getMobileNumber()+" mobile number has already present ");
		}
//		customer.setCreatedAt(LocalDateTime.now());
//		customer.setCreatedBy("Anonymus");
		Customer savedCustomer = customerRepository.save(customer);
		accountsRepository.save(createNewAccount(savedCustomer));
		
	}
	
	private Accounts createNewAccount(Customer customer) {
		
		Accounts newAccount = new Accounts();
		newAccount.setCustomerId(customer.getCustomerId());
		long randomAccountNumber = 1000000000L + new Random().nextInt(900000000);
		
		newAccount.setAccountNumber(randomAccountNumber);
		newAccount.setAccountType(AccountsConstants.SAVINGS);
		newAccount.setBranchAddress(AccountsConstants.ADDRESS);
//		newAccount.setCreatedAt(LocalDateTime.now());
//		newAccount.setCreatedBy("Anonymus");
		
		return newAccount;
	}

	@Override
	public CustomerDTO getCustomerByMobileNumber(String mobileNumber) {
		Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
				() -> new ResourceNotFoundException("Customer", "Mobile Number", mobileNumber) );
		
		Accounts account = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
				() -> new ResourceNotFoundException("Account", "Customer Id", customer.getCustomerId().toString()));
		
		CustomerDTO customerDTO = CustomerMapper.mapToCustomerDTO(customer, new CustomerDTO());
		customerDTO.setAccountsDetails(AccountsMapper.mapToAccountsDTO(account, new AccountsDTO()));
		
		return customerDTO;
	}

	@Override
	public boolean updateAccount(CustomerDTO customerDTO) {
		
		boolean isUpdated = false;
		AccountsDTO accountsDetails = customerDTO.getAccountsDetails();
		if(accountsDetails != null) {
			Accounts account=  accountsRepository.findById(accountsDetails.getAccountNumber()).orElseThrow( () ->
					new ResourceNotFoundException("Account", "Account Number", accountsDetails.getAccountNumber().toString()));
		
		AccountsMapper.mapToAccounts(accountsDetails, account);
		account = accountsRepository.save(account);
		
		Long customerId = account.getCustomerId();
		Customer customer = customerRepository.findById(customerId).orElseThrow(
				() -> new ResourceNotFoundException("Customer", "Customer ID", customerId.toString()));
		
		CustomerMapper.mapToCustomer(customerDTO, customer);
		customerRepository.save(customer);
		isUpdated = true;
		}
		
		return isUpdated;
	}

	@Override
	public boolean deleteAccount(String mobileNumber) {
		
		Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
				() -> new ResourceNotFoundException("Customer"," mobileNumber",mobileNumber));
		
		accountsRepository.deleteByCustomerId(customer.getCustomerId());
		customerRepository.deleteById(customer.getCustomerId());
		return true;
	}
	
	

}
