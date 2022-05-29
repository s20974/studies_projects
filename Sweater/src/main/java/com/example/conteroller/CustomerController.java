package com.example.conteroller;

import com.example.domain.Customer;
import com.example.domain.Role;
import com.example.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/customer")
@PreAuthorize("hasAuthority('ADMIN')")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping
    public String customerList(Model model){
        model.addAttribute("customers", customerRepository.findAll());

        return "customerList";
    }

    @GetMapping("{customer}")
    public String customerEditForm(@PathVariable Customer customer, Model model){
        model.addAttribute("customer", customer);
        model.addAttribute("roles", Role.values());
        return "customerEdit";
    }

    @PostMapping
    public String customerSave(
            @RequestParam String username,
            @RequestParam Map<String, String> form,
            @RequestParam("customerId") Customer customer) {

        customer.setUsername(username);

        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());

        customer.getRoles().clear();

        for (String key : form.keySet()) {
            if(roles.contains(key)){
                customer.getRoles().add(Role.valueOf(key));
            }
        }

        customerRepository.save(customer);

        return "redirect:/customer";
    }
}
