package com.naufal.argon.Repository;

import com.naufal.argon.model.HomeModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HomeRepository extends JpaRepository<HomeModel, Long> {

}