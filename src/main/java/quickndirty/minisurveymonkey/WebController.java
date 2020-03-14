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
import quickndirty.minisurveymonkey.Question;
import quickndirty.minisurveymonkey.Survey;
import quickndirty.minisurveymonkey.SurveyRepository;

import java.util.List;
import java.util.Optional;

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
        model.addAttribute("name", survey.getName());
        model.addAttribute("questionNumber", questionNumber);
        model.addAttribute("nextQuestionNumber", 1 + questionNumber);
        model.addAttribute("baseUrl", ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString());

        if (questionNumber + 1 > questions.size()){
            return "completeSurvey";
        }

        return "answerSurvey";
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
        return "home.html";
    }

    @GetMapping("/archive-surveys")
    public String userSurveys(@AuthenticationPrincipal OAuth2User principal, Model model) {
        User user = userRepository.findByExternalID(principal.getName());
        model.addAttribute("userId", user.getID());
        return "archiveSurveys";
    }



}