package com.swe7303.devops.service;

import com.swe7303.devops.model.Package;
import com.swe7303.devops.repository.PackagesRepository;
import com.swe7303.devops.service.impl.PackageServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PackageServiceTest {

    @Mock
    private PackagesRepository packagesRepository;

    @InjectMocks
    private PackageServiceImpl packageService;

    @Test
    void testGetAllPackages() {
        when(packagesRepository.findAll()).thenReturn(Arrays.asList(new Package(), new Package()));

        List<Package> packages = packageService.getAllPackages();

        assertEquals(2, packages.size());
    }

    @Test
    void testSavePackage() {
        Package pkg = new Package();
        pkg.setPname("Test Package");

        when(packagesRepository.save(pkg)).thenReturn(pkg);

        Package result = packageService.savePackage(pkg);

        assertNotNull(result);
        assertEquals("Test Package", result.getPname());
    }

    @Test
    void testGetPackageById() {
        Package pkg = new Package();
        pkg.setPid(1);

        when(packagesRepository.findById(1)).thenReturn(Optional.of(pkg));

        Package result = packageService.getPackageById(1);

        assertNotNull(result);
        assertEquals(1, result.getPid());
    }
}
