package com.naufal.argon.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.naufal.argon.model.Provinsi;

@Repository
public interface ProvinsiRepository extends JpaRepository<Provinsi, Long> {

    List<Provinsi> findByNmProvinsiIgnoreCaseContaining(String keywordProvinsi);

}
