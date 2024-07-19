

package com.sentrifugo.performanceManagement.repository;
import com.sentrifugo.performanceManagement.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface VendorRepository extends JpaRepository<Vendor,Long> {
    @Query("SELECT v.vendorName FROM Vendor v")
    List<String> findAllVendorNames();

//    @Query("SELECT v.vendorId FROM Vendor v")
//    List<String> findAllVendorId();
    @Query(value = "SELECT VendorId AS VendorId, VendorName as VendorName  from vendor_data vdt ", nativeQuery = true)
    List<Map<String, Object>> findAllVendorId();
    boolean existsByVendorId(String vid);
}
