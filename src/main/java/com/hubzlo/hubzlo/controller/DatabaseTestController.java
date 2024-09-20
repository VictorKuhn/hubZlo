package com.hubzlo.hubzlo.controller;

import com.hubzlo.hubzlo.model.TestEntity;
import com.hubzlo.hubzlo.repository.TestRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DatabaseTestController {

    private final TestRepository testRepository;

    public DatabaseTestController(TestRepository testRepository) {
        this.testRepository = testRepository;
    }

    @PostMapping("/api/test/db/add")
    public String addTestEntity(@RequestParam String name) {
        TestEntity entity = new TestEntity(name);
        testRepository.save(entity);
        return "TestEntity added with name: " + name;
    }

    @GetMapping("/api/test/db/all")
    public List<TestEntity> getAllEntities() {
        return testRepository.findAll();
    }
}
