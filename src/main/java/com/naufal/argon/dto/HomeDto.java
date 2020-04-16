package com.naufal.argon.dto;

public class HomeDto {

    private Long id;

    private String name;

    private String email;

    private Long idKota;

    private String kota;
    
    private Long idProvinsi;
    
    private String nmProvinsi;

    public Long getIdProvinsi() {
		return idProvinsi;
	}

	public void setIdProvinsi(Long idProvinsi) {
		this.idProvinsi = idProvinsi;
	}

	public String getNmProvinsi() {
		return nmProvinsi;
	}

	public void setNmProvinsi(String nmProvinsi) {
		this.nmProvinsi = nmProvinsi;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getKota() {
        return kota;
    }

    public void setKota(String kota) {
        this.kota = kota;
    }

    public Long getIdKota() {
        return idKota;
    }

    public void setIdKota(Long idKota) {
        this.idKota = idKota;
    }

}