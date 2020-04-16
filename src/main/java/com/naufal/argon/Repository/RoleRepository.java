package com.naufal.argon.Repository;

import java.util.Collection;

import com.naufal.argon.model.Role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {

    public Collection<Role> findAllByUserRoleIdUserId(Long idUser);

}