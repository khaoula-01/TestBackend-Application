package com.tawtechs.testbackend.Repository;

import com.tawtechs.testbackend.Entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}