package survey.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import survey.model.Survey;

@Repository
public interface SurveyRepository extends JpaRepository<Survey, Long> {

}
