package scvetkovic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import scvetkovic.model.Kupovina;

@Repository
public interface KupovinaRepository  extends JpaRepository<Kupovina, Long>  {

}
