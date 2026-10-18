package tn.esprit.tic.cce.autoloc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.tic.cce.autoloc.domain.Agence;

public interface AgenceRepository extends JpaRepository<Agence, Long> {
}