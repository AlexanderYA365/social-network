package com.getjavajob.training.yakovleva.repository;

import com.getjavajob.training.yakovleva.common.Group;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GroupRepository extends CrudRepository<Group, Integer> {

    @Query("SELECT g FROM Group g")
    List<Group> findAll();

}