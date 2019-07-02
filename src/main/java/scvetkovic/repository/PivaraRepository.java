package scvetkovic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import scvetkovic.model.Pivara;

@Repository
public interface PivaraRepository extends JpaRepository<Pivara, Long> {

}
