package quickndirty.minisurveymonkey;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;

@RestController
public class QuestionController {

	@Autowired
	QuestionRepository questionRepository;
	
	@Autowired
	ResponseRepository responseRepository;

	@GetMapping("/question")
	public Iterable<Question> question() {
		return questionRepository.findAll();
	}

	@GetMapping("/question/{id}")
	public Question getQuestionById(@PathVariable("id") int id){
		Optional<Question> question = questionRepository.findById(id);
		if (question.isPresent()){
			return question.get();
		} else {
			throw new ResourceNotFoundException();
		}
	}
	
	@PostMapping("/question/{id}")
	public Question answerQuestion(@PathVariable("id") int id, @RequestBody Response r){
		Optional<Question> question = questionRepository.findById(id);
		Question q;
		if (question.isPresent()){
			q = question.get();
		} else {
			throw new ResourceNotFoundException();
		}
		
		q.addResponse(r);
		r.setQuestion(q);
		questionRepository.save(q);
		responseRepository.save(r);
		return q;
	}
}
