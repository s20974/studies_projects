package com.example.service;

import com.example.domain.Customer;
import com.example.domain.Role;
import com.example.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.UUID;

@Service
public class CustomerService implements UserDetailsService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private MailSender mailSender;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return customerRepository.findByUsername(username);
    }

    public  boolean addUser(Customer customer){
        Customer userFromDb = customerRepository.findByUsername(customer.getUsername());

        if(userFromDb != null){
            return false;
        }

        customer.setActive(true);
        customer.setRoles(Collections.singleton(Role.USER));
        customer.setActivationCode(UUID.randomUUID().toString());

        customerRepository.save(customer);

        if(!StringUtils.isEmpty(customer.getEmail())){
            String message = String.format(
                    "Hello, %s!\n" +
                            "Welcome to Sweater, Please, visit next link: http://localhost:8080/activate/%s",
                    customer.getUsername(),
                    customer.getActivationCode()
            );

            mailSender.send(customer.getEmail(), "Activation code", message);
        }

        return true;
    }

    public boolean activateUser(String code) {
        Customer customer = customerRepository.findByActivationCode(code);

        if(customer == null){
            return false;
        }

        customer.setActivationCode(null);

        customerRepository.save(customer);

        return true;
    }
}
