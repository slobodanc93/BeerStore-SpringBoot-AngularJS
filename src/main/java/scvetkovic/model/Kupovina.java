package scvetkovic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table
public class Kupovina {
	
	@Column
	@Id
	@GeneratedValue
	private Long id;
	@ManyToOne(fetch=FetchType.EAGER)
	private Pivo pivo;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Pivo getPivo() {
		return pivo;
	}
	public void setPivo(Pivo pivo) {
		this.pivo = pivo;
		if(pivo != null & !pivo.getKupovine().contains(this)) {
			pivo.getKupovine().add(this);
		}
	}
	
	


}
