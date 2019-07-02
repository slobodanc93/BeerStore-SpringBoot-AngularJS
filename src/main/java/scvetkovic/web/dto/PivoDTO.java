package scvetkovic.web.dto;

public class PivoDTO {
	

	private Long id;
	private String naziv;
	private String vrsta;
	private Double procenatAlkohola;
	private Double ibu;
	private Integer kolicinaStanje = 0;
	private Long pivaraId;
	private String pivaraNaziv;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNaziv() {
		return naziv;
	}
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	public String getVrsta() {
		return vrsta;
	}
	public void setVrsta(String vrsta) {
		this.vrsta = vrsta;
	}
	public Double getProcenatAlkohola() {
		return procenatAlkohola;
	}
	public void setProcenatAlkohola(Double procenatAlkohola) {
		this.procenatAlkohola = procenatAlkohola;
	}
	public Double getIbu() {
		return ibu;
	}
	public void setIbu(Double ibu) {
		this.ibu = ibu;
	}
	public Integer getKolicinaStanje() {
		return kolicinaStanje;
	}
	public void setKolicinaStanje(Integer kolicinaStanje) {
		this.kolicinaStanje = kolicinaStanje;
	}
	public Long getPivaraId() {
		return pivaraId;
	}
	public void setPivaraId(Long pivaraId) {
		this.pivaraId = pivaraId;
	}
	public String getPivaraNaziv() {
		return pivaraNaziv;
	}
	public void setPivaraNaziv(String pivaraNaziv) {
		this.pivaraNaziv = pivaraNaziv;
	}
	
}
