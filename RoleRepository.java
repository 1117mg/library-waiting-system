
package com.example.librarywaitingsystem.repository;

import com.example.librarywaitingsystem.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByName(String name);
    Role findByRole(Integer role);
}
