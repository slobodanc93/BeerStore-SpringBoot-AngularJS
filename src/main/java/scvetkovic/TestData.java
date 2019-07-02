package scvetkovic;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import scvetkovic.model.Pivara;
import scvetkovic.model.Pivo;
import scvetkovic.service.PivaraService;
import scvetkovic.service.PivoService;


@Component
public class TestData {

	@Autowired
	private PivaraService pivaraService;
	
	@Autowired
	private PivoService pivoService;
	
	@PostConstruct
	public void init() {
		
		
		Pivara p1 = new Pivara();
		p1.setNaziv("Dogma");
		p1.setDrzava("Srbija");
		p1.setPib("1234567");
		pivaraService.save(p1);
		
		Pivara p2 = new Pivara();
		p2.setNaziv("Crown");
		p2.setDrzava("Srbija");
		p2.setPib("157956");
		pivaraService.save(p2);
		
		
		Pivo pivo1 = new Pivo();
		pivo1.setNaziv("Hoptopod");
		pivo1.setVrsta("ALE");
		pivo1.setProcenatAlkohola(5.7);
		pivo1.setKolicinaStanje(10);
		pivo1.setIbu(11.0);
		pivo1.setPivara(p1);
		pivoService.save(pivo1);
		
		Pivo pivo2 = new Pivo();
		pivo2.setNaziv("Jaogidnika");
		pivo2.setVrsta("CRNO");
		pivo2.setProcenatAlkohola(5.9);
		pivo2.setKolicinaStanje(15);
		pivo2.setIbu(15.0);
		pivo2.setPivara(p2);
		pivoService.save(pivo2);
		
		Pivo pivo3 = new Pivo();
		pivo3.setNaziv("Smash");
		pivo3.setVrsta("PALE ALE");
		pivo3.setProcenatAlkohola(5.1);
		pivo3.setKolicinaStanje(5);
		pivo3.setIbu(9.0);
		pivo3.setPivara(p1);
		pivoService.save(pivo3);
		
	}
}