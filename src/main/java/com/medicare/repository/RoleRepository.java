package com.medicare.repository;

import org.springframework.data.repository.CrudRepository;

import com.medicare.entity.Roles;

public interface RoleRepository extends CrudRepository<Roles, Integer> {

}
