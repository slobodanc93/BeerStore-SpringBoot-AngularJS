package scvetkovic.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import scvetkovic.model.Pivo;
import scvetkovic.repository.PivoRepository;
import scvetkovic.service.PivoService;

@Service
@Transactional
public class JpaPivoServiceImpl implements PivoService {
	
	private int itemsPerPage = 5;
	
	@Autowired
	private PivoRepository pivoRepository;
	
	@Override
	public Pivo findOne(Long id) {
		return pivoRepository.findOne(id);
	}

	@Override
	public Page<Pivo> findAll(int pageNum) {
		return pivoRepository.findAll(new PageRequest(pageNum, itemsPerPage));
	}

	@Override
	public void save(Pivo pivo) {
		pivoRepository.save(pivo);
		
	}

	@Override
	public Pivo remove(Long id) {
		Pivo pivo = pivoRepository.findOne(id);
		if(pivo != null) {
			pivoRepository.delete(pivo);
		}
		return pivo;
	}

	@Override
	public Page<Pivo> pretraga(String nazivPiva, Double minIbu, Double maxIbu, String nazivPivare, Integer nestalo, int pageNum) {
		if(nazivPiva != null) {
			nazivPiva = "%" + nazivPiva + "%";
		}
		if(nazivPivare != null) {
			nazivPivare = "%" + nazivPivare + "%";
		}
		return pivoRepository.pretraga(nazivPiva, minIbu, maxIbu, nazivPivare, nestalo, new PageRequest(pageNum, itemsPerPage));
	}

	@Override
	public Page<Pivo> findByPivaraId(int pageNum, Long pivaraId) {
		return pivoRepository.findByPivaraId(pivaraId, new PageRequest(pageNum, itemsPerPage));
	}


}
