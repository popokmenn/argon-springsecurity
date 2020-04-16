package com.naufal.argon.Repository;

import com.naufal.argon.model.Biodata;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BiodataRepository extends JpaRepository<Biodata, Long> {

}