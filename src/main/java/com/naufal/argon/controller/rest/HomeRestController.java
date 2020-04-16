package com.naufal.argon.controller.rest;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.naufal.argon.Repository.HomeRepository;
import com.naufal.argon.Repository.KotaRepository;
import com.naufal.argon.dto.HomeDto;
import com.naufal.argon.model.HomeModel;
import com.naufal.argon.model.Kota;

@RestController
public class HomeRestController {

	@Autowired
	private HomeRepository homeRepository;

	@Autowired
	private KotaRepository homeDetailRepository;

	@RequestMapping(value = "/get-all", method = RequestMethod.GET)
	public List<HomeDto> getAllBiodata() {
		List<HomeModel> homeModels = homeRepository.findAll();
		List<HomeDto> biodataDtos = new ArrayList<>();

		for (int i = 0; i < homeModels.size(); i++) {
			HomeDto homeDto = new HomeDto();
			homeDto.setId(homeModels.get(i).getId());
			homeDto.setEmail(homeModels.get(i).getEmail());
			homeDto.setName(homeModels.get(i).getName());
			homeDto.setKota(homeModels.get(i).getKota().getKota());
			homeDto.setIdKota(homeModels.get(i).getKota().getIdKota());
			homeDto.setNmProvinsi(homeModels.get(i).getProvinsi().getNmProvinsi());
			homeDto.setIdProvinsi(homeModels.get(i).getProvinsi().getId());
			biodataDtos.add(homeDto);
		}

		return biodataDtos;
	}

	@GetMapping(value = "/get-all-kota")
	private List<Kota> getAllKota() {
		List<Kota> detailModels = homeDetailRepository.findAll();
		return detailModels;
	}

	@GetMapping(value = "/api/{idHome}")
	private HomeDto getById(@PathVariable(value = "idHome") Long id) {
		HomeModel homeModel = homeRepository.findById(id).get();
		HomeDto dto = new HomeDto();
		dto.setEmail(homeModel.getEmail());
		dto.setId(homeModel.getId());
		dto.setName(homeModel.getName());
		dto.setIdKota(homeModel.getKota().getIdKota());
		dto.setKota(homeModel.getKota().getKota());
		dto.setIdProvinsi(homeModel.getProvinsi().getId());
		dto.setNmProvinsi(homeModel.getProvinsi().getNmProvinsi());

		return dto;
	}

	@PostMapping(value = "/save")
	public HomeModel saveAndUpdateHome(@RequestBody HomeModel homeModel) {
		homeRepository.save(homeModel);
		return homeModel;
	}

	@PutMapping(value = "/api/{idHome}")
	private HomeModel edit(@PathVariable(value = "idHome") Long id, @Valid @RequestBody HomeModel homeModel) {
		return homeRepository.save(homeModel);
	}

	@DeleteMapping(value = "/api/{idHome}")
	private void delete(@PathVariable("idHome") Long id) {
		homeRepository.deleteById(id);
	}

	/*
	 * private Date dateFromLocalDate(String myDate) { final String myPattern =
	 * "dd/MM/yyyy"; DateTimeFormatter dateTimeFormatter =
	 * DateTimeFormatter.ofPattern(myPattern); LocalDate localDate =
	 * LocalDate.parse(myDate, dateTimeFormatter);
	 * 
	 * return java.sql.Date.valueOf(localDate); }
	 */

	// private HomeDto convertToDto(HomeModel home) {
	// HomeDto homeDto = modelMapper.map(home, HomeDto.class);
	// HomeDetailModel homeDetailDto = modelMapper.map(home.getDetailModel(),
	// HomeDetailModel.class);
	// BeanUtils.copyProperties(homeDetailDto, homeDto);

	// return homeDto;
	// }

}
