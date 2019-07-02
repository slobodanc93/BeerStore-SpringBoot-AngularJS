package scvetkovic.support;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import scvetkovic.model.Pivara;
import scvetkovic.web.dto.PivaraDTO;

@Component
public class PivaraToPivaraDTO implements Converter<Pivara, PivaraDTO>  {

	@Override
	public PivaraDTO convert(Pivara source) {
		if(source==null){
			return null;
		}
		PivaraDTO target = new PivaraDTO();
		target.setId(source.getId());
		target.setNaziv(source.getNaziv());
		target.setDrzava(source.getDrzava());
		target.setPib(source.getPib());
		return target;
	}
	
	public List<PivaraDTO> convert(List<Pivara> source) {
		List<PivaraDTO> target = new ArrayList<>();
		for(Pivara p : source) {
			target.add(convert(p));
		}
		return target;
	}


}
