package com.demo.bbq.business.tableplacement.infrastructure.repository.database;

import com.demo.bbq.business.tableplacement.infrastructure.repository.database.entity.MenuOrderEntity;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuOrderRepository extends CrudRepository<MenuOrderEntity, Long> {

  MenuOrderEntity save(MenuOrderEntity menuOrder);

  Optional<MenuOrderEntity> findByProductCode(String productCode);

}
