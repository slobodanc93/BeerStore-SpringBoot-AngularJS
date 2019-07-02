package scvetkovic.service;

import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;

import scvetkovic.model.Pivo;

public interface PivoService {
	
	Pivo findOne(Long id);
	Page<Pivo> findAll(int pageNum);
	void save(Pivo pivo);
	Pivo remove(Long id);
	
	Page<Pivo> findByPivaraId(int pageNum, Long pivaraId);
	Page<Pivo> pretraga(
			@Param("nazivPiva") String nazivPiva,
			@Param("minIbu") Double minIbu,
			@Param("maxIbu") Double maxIbu,
			@Param("nazivPivare") String nazivPivare,
			@Param("nestalo") Integer nestalo,
			int pageNum);

}
