package com.tss.security.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tss.security.entity.Role;
import com.tss.security.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	Optional<User> findByUsername(String username);

	boolean existsByUsername(String username);

	List<User> findByRole(Role role);

	List<User> findByRoleRoleId(Integer roleId);

}
