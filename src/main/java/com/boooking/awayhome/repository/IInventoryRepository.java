package com.boooking.awayhome.repository;

import com.boooking.awayhome.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IInventoryRepository  extends JpaRepository<Inventory,Long> {
}
