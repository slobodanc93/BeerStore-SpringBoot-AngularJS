package scvetkovic.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import scvetkovic.model.Kupovina;
import scvetkovic.model.Pivo;
import scvetkovic.repository.KupovinaRepository;
import scvetkovic.repository.PivoRepository;
import scvetkovic.service.KupovinaService;

@Service
public class JpaKupovinaServiceImpl implements KupovinaService {

	@Autowired
	private PivoRepository pivoRepository;
	
	@Autowired
	private KupovinaRepository kupovinaRepository;

	@Override
	public Kupovina kupi(Long pivoId) {
		if(pivoId == null) {
			throw new IllegalArgumentException("Id of the beer cannot be null!");
		}
		
		Pivo pivo = pivoRepository.findOne(pivoId);
		if(pivo == null) {
			throw new IllegalArgumentException("There is no beer with given id!");	
		}
		
		if(pivo.getKolicinaStanje() > 0) {
			Kupovina kupovina = new Kupovina();
			kupovina.setPivo(pivo);
			pivo.setKolicinaStanje(pivo.getKolicinaStanje()-1);
			pivoRepository.save(pivo);
			//kupovinaRepository.save(kupovina); //dupliracemo unos u tabelu glas ukoliko ovo uradimo
			return kupovina;
		}
		return null;
	} 
	
	
}
