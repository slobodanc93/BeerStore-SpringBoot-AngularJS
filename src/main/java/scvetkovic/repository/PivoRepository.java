package scvetkovic.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import scvetkovic.model.Pivo;

@Repository
public interface PivoRepository extends JpaRepository<Pivo, Long>  {
	
	Page<Pivo> findByPivaraId(Long pivaraId, Pageable pageRequest);

	@Query("SELECT p FROM Pivo p WHERE "
			+ "(:nazivPiva is NULL or p.naziv like :nazivPiva ) AND "
			+ "(:minIbu IS NULL or p.ibu >= :minIbu ) AND "
			+ "(:maxIbu IS NULL or p.ibu <= :maxIbu ) AND "
			+ "(:nazivPivare IS NULL or p.pivara.naziv like :nazivPivare) AND "
			+ "(:nestalo IS NULL or p.kolicinaStanje = :nestalo)"
			)
	Page<Pivo> pretraga (
			@Param("nazivPiva") String nazivPiva,
			@Param("minIbu") Double minIbu,
			@Param("maxIbu") Double maxIbu,
			@Param("nazivPivare") String nazivPivare,
			@Param("nestalo") Integer nestalo,
			Pageable pageRequest	
			);

	
}
