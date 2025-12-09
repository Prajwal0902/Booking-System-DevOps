package com.swe7303.devops.service;
import com.swe7303.devops.model.Package;
import java.util.List;

public interface PackageService {
	 	List<Package> getAllPackages();   // to list all packages
	    
	    Package savePackage(Package pkg); // to save/create package
	    
	    Package getPackageById(Integer pid); // for editing
	    
	    void deletePackage(Integer pid);    // to delete package
}
