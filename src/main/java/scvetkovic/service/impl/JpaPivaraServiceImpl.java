package scvetkovic.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import scvetkovic.model.Pivara;
import scvetkovic.repository.PivaraRepository;
import scvetkovic.service.PivaraService;

@Service
@Transactional
public class JpaPivaraServiceImpl implements PivaraService {

	@Autowired
	private PivaraRepository pivaraRepository;
	
	@Override
	public Pivara findOne(Long id) {
		return pivaraRepository.findOne(id);
	}

	@Override
	public List<Pivara> findAll() {
		return pivaraRepository.findAll();
	}

	@Override
	public void save(Pivara pivara) {
		pivaraRepository.save(pivara);
	}

	@Override
	public void remove(Long id) {
		pivaraRepository.delete(id);
	}

}
