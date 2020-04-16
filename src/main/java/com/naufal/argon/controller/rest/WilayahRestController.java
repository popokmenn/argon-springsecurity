package com.naufal.argon.controller.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.naufal.argon.Repository.KotaRepository;
import com.naufal.argon.Repository.ProvinsiRepository;
import com.naufal.argon.model.Kota;
import com.naufal.argon.model.Provinsi;

@RestController
public class WilayahRestController {

	@Autowired
	KotaRepository kotaRepository;

	@Autowired
	ProvinsiRepository provinsiRepository;

	@GetMapping(value = "/kota/all")
	public List<Kota> getAllKota() {
		return kotaRepository.findAll();
	}

	@GetMapping(value = "/provinsi/all")
	public List<Provinsi> getAllProvinsi() {
		return provinsiRepository.findAll();
	}

	@GetMapping(value = "/kota/{idProvinsi}")
	public List<Kota> getKotaByIdProvinsi(@PathVariable(value = "idProvinsi") Long idProvinsi) {
		return kotaRepository.findByIdProvinsi(idProvinsi);
	}

	@GetMapping(value = "/provinsi/search")
	public List<Provinsi> searchProvinsi(HttpServletRequest req) {
		String keyword = req.getParameter("term");
		return provinsiRepository.findByNmProvinsiIgnoreCaseContaining(keyword);
	}

}
