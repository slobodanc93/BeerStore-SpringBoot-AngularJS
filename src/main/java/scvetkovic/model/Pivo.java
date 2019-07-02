package scvetkovic.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table
public class Pivo {
	
	@Id
	@GeneratedValue
	@Column
	private Long id;
	@Column
	private String naziv;
	@Column
	private String vrsta;
	@Column
	private Double procenatAlkohola;
	@Column
	private Double ibu;
	@Column
	private Integer kolicinaStanje = 0;
	@ManyToOne(fetch = FetchType.EAGER)
	private Pivara pivara;
	@OneToMany(mappedBy = "pivo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Kupovina> kupovine = new ArrayList<>();
	
	
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
	public Pivara getPivara() {
		return pivara;
	}
	public void setPivara(Pivara pivara) {
		this.pivara = pivara;
		if (pivara != null && !pivara.getPiva().contains(this)) {
			pivara.getPiva().add(this);
		}
	}
	public List<Kupovina> getKupovine() {
		return kupovine;
	}
	public void setKupovine(List<Kupovina> kupovine) {
		this.kupovine = kupovine;
	}
	public void addKupovina(Kupovina kupovina) {
		this.kupovine.add(kupovina);
		if(!this.equals(kupovina.getPivo())) {
			kupovina.setPivo(this);
		}
	}
	
	

}
