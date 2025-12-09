package com.swe7303.devops.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swe7303.devops.model.Package;

public interface PackagesRepository extends JpaRepository<Package, Integer> {
	

}
