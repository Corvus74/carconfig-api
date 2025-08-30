package com.computacenter.carconfig.repository.pool;

import com.computacenter.carconfig.entities.base.SpecialEquipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpecialEquipmentRepository extends JpaRepository<SpecialEquipment, Long>{
    /** Retrieve a special equipment item by its orderNumber when active (not deleted). */
    @Query("select s from SpecialEquipment s where s.orderNumber = ?1 and (upper(s.deleteFlag) = 'N' or s.deleteFlag is null)")
    Optional<SpecialEquipment> findByOrderNumberAndNotDeleted(String orderNumber);

    /** Returns active special equipment items matching the productId. */
    @Query("select s from SpecialEquipment s where s.productId = ?1 and (upper(s.deleteFlag) = 'N' or s.deleteFlag is null)")
    List<SpecialEquipment> findBySpecialEquipmentByProductIdAndNotDeleted(String productId);
}
