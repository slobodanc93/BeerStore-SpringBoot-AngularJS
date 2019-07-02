package scvetkovic.support;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import scvetkovic.model.Pivara;
import scvetkovic.model.Pivo;
import scvetkovic.service.PivaraService;
import scvetkovic.service.PivoService;
import scvetkovic.web.dto.PivoDTO;

@Component
public class PivoDTOToPivo implements Converter<PivoDTO, Pivo> {

	@Autowired
	private PivoService pivoService;
	
	@Autowired
	private PivaraService pivaraService;
	
	@Override
	public Pivo convert(PivoDTO source) {
		Pivo target;
		Pivara pivara = pivaraService.findOne(source.getPivaraId());
		
		if(source.getId() == null) {
			target = new Pivo();
		} else {
			target = pivoService.findOne(source.getId());
		}
		
		target.setPivara(pivara);
		target.setNaziv(source.getNaziv());
		target.setIbu(source.getIbu());
		target.setKolicinaStanje(source.getKolicinaStanje());
		target.setProcenatAlkohola(source.getProcenatAlkohola());
		target.setVrsta(source.getVrsta());
	
		return target;
	}
	
	public List<Pivo> convert(List<PivoDTO> source) {	
		List<Pivo> target = new ArrayList<>();	
		for(PivoDTO p : source) {
			target.add(convert(p));
		}	
		return target;
	}

}
