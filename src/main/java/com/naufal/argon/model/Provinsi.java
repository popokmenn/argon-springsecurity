package com.naufal.argon.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "T_PROVINSI")
public class Provinsi {
	
	@Id
	private Long id;
	
	private String nmProvinsi;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNmProvinsi() {
		return nmProvinsi;
	}

	public void setNmProvinsi(String nmProvinsi) {
		this.nmProvinsi = nmProvinsi;
	}
	
	

}
