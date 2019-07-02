package scvetkovic.support;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import scvetkovic.model.Pivo;
import scvetkovic.web.dto.PivoDTO;

@Component
public class PivoToPivoDTO  implements Converter<Pivo, PivoDTO> {

	@Override
	public PivoDTO convert(Pivo source) {
		if(source==null){
			return null;
		}
		PivoDTO target = new PivoDTO();
		target.setId(source.getId());
		target.setNaziv(source.getNaziv());
		target.setKolicinaStanje(source.getKolicinaStanje());
		target.setProcenatAlkohola(source.getProcenatAlkohola());
		target.setVrsta(source.getVrsta());
		target.setIbu(source.getIbu());
		target.setPivaraId(source.getPivara().getId());
		target.setPivaraNaziv(source.getPivara().getNaziv());
		return target;
	}
	
public List<PivoDTO> convert(List<Pivo> source) {
		List<PivoDTO> target = new ArrayList<>();	
		for(Pivo p : source) {
			target.add(convert(p));
		}
		return target;
	}


}
