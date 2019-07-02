package scvetkovic.support;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import scvetkovic.model.Kupovina;
import scvetkovic.web.dto.KupovinaDTO;

@Component
public class KupovinaToKupovinaDTO implements Converter<Kupovina, KupovinaDTO>  {

	@Override
	public KupovinaDTO convert(Kupovina source) {
		if(source==null){
			return null;
		}
		KupovinaDTO target = new KupovinaDTO();
		target.setId(source.getId());
		return target;
	}
	
	public List<KupovinaDTO> convert(List<Kupovina> source){
		List<KupovinaDTO> target = new ArrayList<>();	
		for(Kupovina k: source){
			target.add(convert(k));
		}	
		return target;
	}

}
