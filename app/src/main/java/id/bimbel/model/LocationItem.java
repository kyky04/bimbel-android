package id.bimbel.model;

import java.io.Serializable;

public class LocationItem implements Serializable {
	private String id;
	private String alamat;
	private String kecamatan;
	private String jenisKursus;
	private String namaLembaga;
	private Double latitude;
	private Double longitude;

	public void setJenisKursus(String jenisKursus){
		this.jenisKursus = jenisKursus;
	}

	public String getJenisKursus(){
		return jenisKursus;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public void setKecamatan(String kecamatan){
		this.kecamatan = kecamatan;
	}

	public String getKecamatan(){
		return kecamatan;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setNamaLembaga(String namaLembaga){
		this.namaLembaga = namaLembaga;
	}

	public String getNamaLembaga(){
		return namaLembaga;
	}

	public void setAlamat(String alamat){
		this.alamat = alamat;
	}

	public String getAlamat(){
		return alamat;
	}

	public Double getLongitude() {
		return longitude;
	}

	@Override
	public String toString() {
		return "LocationItem{" +
				"id='" + id + '\'' +
				", alamat='" + alamat + '\'' +
				", kecamatan='" + kecamatan + '\'' +
				", jenisKursus='" + jenisKursus + '\'' +
				", namaLembaga='" + namaLembaga + '\'' +
				", latitude='" + latitude + '\'' +
				", longitude='" + longitude + '\'' +
				'}';
	}
}
