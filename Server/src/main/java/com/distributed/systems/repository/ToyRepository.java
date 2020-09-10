package com.distributed.systems.repository;

import com.distributed.systems.model.Toy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToyRepository extends JpaRepository<Toy, String> {

}
