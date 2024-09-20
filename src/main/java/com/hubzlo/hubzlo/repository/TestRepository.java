package com.hubzlo.hubzlo.repository;

import com.hubzlo.hubzlo.model.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<TestEntity, Long> {
}
