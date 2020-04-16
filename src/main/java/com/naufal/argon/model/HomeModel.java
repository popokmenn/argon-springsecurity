package com.naufal.argon.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "T_HOME")
public class HomeModel {

	@Id
	@Column(name = "ID_HOME")
	@GeneratedValue
	private Long id;

	@Column(name = "NAME_HOME")
	private String name;

	@Column(name = "EMAIL_HOME")
	private String email;

	@ManyToOne
	private Kota kota;
	
	@ManyToOne
	private Provinsi provinsi;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Kota getKota() {
		return kota;
	}

	public void setKota(Kota kota) {
		this.kota = kota;
	}

	public Provinsi getProvinsi() {
		return provinsi;
	}

	public void setProvinsi(Provinsi provinsi) {
		this.provinsi = provinsi;
	}
	
	

}
