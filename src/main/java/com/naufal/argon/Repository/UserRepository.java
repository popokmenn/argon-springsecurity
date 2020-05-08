package com.naufal.argon.Repository;

import java.util.List;

import com.naufal.argon.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    User findByUsernameAndPassword(String username, String password);

    User findByUsername(String username);

    List<User> findAllByUsernameIgnoreCaseContainingAndBiodataFullnameIgnoreCaseContainingAndUserRoleRoleNameIgnoreCaseContaining(
            String username, String fullname, String roleName);

    List<User> findAllByUserRoleRoleName(String roleName);
}
