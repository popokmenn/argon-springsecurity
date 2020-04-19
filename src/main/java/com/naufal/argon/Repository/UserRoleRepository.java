package com.naufal.argon.Repository;

import com.naufal.argon.model.UserRole;
import com.naufal.argon.model.UserRoleId;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleId> {

    void deleteAllByIdUserId(Long idUser);

}