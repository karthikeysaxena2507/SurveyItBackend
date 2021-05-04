package survey.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import survey.model.Option;

@Repository
public interface OptionRepository extends JpaRepository<Option, Long> {

}
