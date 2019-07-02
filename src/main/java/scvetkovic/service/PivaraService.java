package scvetkovic.service;

import java.util.List;

import scvetkovic.model.Pivara;

public interface PivaraService {
	
	Pivara findOne(Long id);
	List<Pivara> findAll();
	void save(Pivara pivara);
	void remove(Long id);

}
