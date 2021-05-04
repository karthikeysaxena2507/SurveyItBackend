package survey.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import survey.model.Voter;

@Repository
public interface VoterRepository extends JpaRepository<Voter, Long> {

}
