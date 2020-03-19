package quickndirty.minisurveymonkey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.*;

@Controller
public class WebController {

    @Autowired
    private SurveyRepository surveyRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/survey/{id}")
    public String answerSurvey( Model model,
                                @PathVariable("id") Integer id,
                                @RequestParam("questionNumber") Optional<Integer> questionNumberParam) {

        if (surveyRepository.findById(id).isEmpty()){
            return "notFoundSurvey";
        }

        int questionNumber = questionNumberParam.orElse(0);

        Survey survey = surveyRepository.findById(id).get();
        List<Question> questions = surveyRepository.findById(id).get().getQuestions();
        model.addAttribute("questions", survey.getQuestions());
        model.addAttribute("surveyName", survey.getName());
        model.addAttribute("questionNumber", questionNumber);
        model.addAttribute("nextQuestionNumber", 1 + questionNumber);
        model.addAttribute("baseUrl", ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString());

        if (questionNumber + 1 > questions.size()){
            return "completeSurvey";
        }

        return "answerSurvey";
    }

    @GetMapping("/survey/{id}/responses")
    public String surveyResponses( Model model, @PathVariable("id") Integer id) {
        if (surveyRepository.findById(id).isEmpty()){
            return "notFoundSurvey";
        }
        Survey survey = surveyRepository.findById(id).get();
        Map<Integer, List> resultsMap= new HashMap();

        // Iterating through survey object, collecting responses, and appending results to each question
        for(int i = 0; i < survey.questions.size(); i++){
            Question questionObj = survey.questions.get(i);
            // Results for text based questions just returns a list of all responses given
            if(questionObj.type == QuestionType.TEXT){
                List<String> textResults = new ArrayList<>();
                for(int j = 0; j < questionObj.responses.size(); j++){
                    TextResponse textResp = (TextResponse) questionObj.responses.get(j);
                    textResults.add(textResp.getAnswer());
                }
                resultsMap.put(questionObj.getID(), textResults);
            } // Extending for question types on display goes here
        }
        model.addAttribute("questions", survey.getQuestions());
        model.addAttribute("results", resultsMap);
        model.addAttribute("name", survey.getName());
        return "surveyResponse";
    }

    @GetMapping("/create-survey")
    public String surveyForm(@AuthenticationPrincipal OAuth2User principal, Model model) {
        System.out.println(principal.getName());
        User user = userRepository.findByExternalID(principal.getName());
        System.out.println(user);
        model.addAttribute("userId", user.getID());
        return "createSurvey";
    }

    @GetMapping("/home")
    public String home(Model model) {
        return "home";
    }

    @GetMapping("/archive-surveys")
    public String userSurveys(@AuthenticationPrincipal OAuth2User principal, Model model) {
        User user = userRepository.findByExternalID(principal.getName());
        model.addAttribute("userId", user.getID());
        return "archiveSurveys";
    }
}