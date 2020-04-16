package com.naufal.argon.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "T_KOTA")
public class Kota {

	@Id
	private Long idKota;
	
	private Long idProvinsi;

	private String kota;

	public Long getIdKota() {
		return idKota;
	}

	public void setIdKota(Long idKota) {
		this.idKota = idKota;
	}

	public Long getIdProvinsi() {
		return idProvinsi;
	}

	public void setIdProvinsi(Long idProvinsi) {
		this.idProvinsi = idProvinsi;
	}

	public String getKota() {
		return kota;
	}

	public void setKota(String kota) {
		this.kota = kota;
	}
	
	

}
