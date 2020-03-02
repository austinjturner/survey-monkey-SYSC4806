package quickndirty.minisurveymonkey;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SurveyController {

	@Autowired
	QuestionRepository questionRepository;
	
	@Autowired
	SurveyRepository surveyRepository;
	
	@GetMapping("/survey")
	public Iterable<Survey> survey() {
		return surveyRepository.findAll();
	}
	
	@PostMapping("/survey")
    public Survey addSurvey(@RequestBody String name) {
		Survey s = new Survey(name);
        s = surveyRepository.save(s);
        return s;
    }
	
	@PostMapping("/survey/{id}")
    public Survey addQuestion(@PathVariable("id") int id, @RequestBody Question q) {
		Optional<Survey> survey = surveyRepository.findById(id);
		Survey s;
		if (survey.isPresent()){
			s = survey.get();
		} else {
			throw new ResourceNotFoundException();
		}
		
		s.addQuestion(q);
		q.setSurvey(s);
		questionRepository.save(q);
		surveyRepository.save(s);
		return s;
    }
}
