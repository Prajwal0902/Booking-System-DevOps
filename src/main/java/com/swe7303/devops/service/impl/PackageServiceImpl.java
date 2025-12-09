package com.swe7303.devops.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swe7303.devops.model.Package;
import com.swe7303.devops.repository.PackagesRepository;
import com.swe7303.devops.service.PackageService;

@Service
public class PackageServiceImpl implements PackageService {

    @Autowired
    private PackagesRepository packageRepository;

    @Override
    public List<Package> getAllPackages() {
        return packageRepository.findAll();
    }

    @Override
    public Package savePackage(Package pkg) {
        return packageRepository.save(pkg);
    }

    @Override
    public Package getPackageById(Integer id) {
        return packageRepository.findById(id).orElse(null);
    }

    @Override
    public void deletePackage(Integer id) {
        packageRepository.deleteById(id);
    }
}
