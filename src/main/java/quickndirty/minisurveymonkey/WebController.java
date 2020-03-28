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
import org.togglz.core.manager.FeatureManager;

import java.util.*;

@Controller
public class WebController {

    @Autowired
    private FeatureManager featureManager;

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

        if(survey.getClosed()) {
            return "surveyClosed";
        }
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
        Map<Integer, Map> resultsMap= new HashMap();
        // Iterating through survey object, collecting responses, and appending results to each question
        for(int i = 0; i < survey.questions.size(); i++){
            Question questionObj = survey.questions.get(i);
            // Results for text based questions just returns a list of all responses given
            if(questionObj.type == QuestionType.TEXT){
                HashMap<Integer, String> textResults = new HashMap<Integer, String>();
                for(int j = 0; j < questionObj.responses.size(); j++) {
                    TextResponse textResp = (TextResponse) questionObj.responses.get(j);
                    textResults.put(j, textResp.getAnswer());
                }
                resultsMap.put(questionObj.getID(), textResults);
            } // Extending for question types on display goes here
           else if(questionObj.type == QuestionType.NUMBER) {
                List<Integer> rangeResults = new ArrayList<>();
                for(int k = 0; k < questionObj.responses.size(); k++) {
                    RangeResponse rangeResp = (RangeResponse) questionObj.responses.get(k);
                    rangeResults.add(rangeResp.getAnswer());
                }
                HashMap<Integer, Integer> rangeAppearances = new HashMap<Integer, Integer>();
                for(int n: rangeResults) {
                    if(rangeAppearances.containsKey(n)) {
                        int counter = rangeAppearances.get(n);
                        counter = counter+1;
                        rangeAppearances.put(n, counter);
                    } else {
                        rangeAppearances.put(n, 1);
                    }
                }
                resultsMap.put(questionObj.getID(), rangeAppearances);
            }
           else if(questionObj.type == QuestionType.MC) {
                List<String> mcResults = new ArrayList<>();
                for(int k = 0; k < questionObj.responses.size(); k++) {
                    MultipleChoiceResponse mcResp = (MultipleChoiceResponse) questionObj.responses.get(k);
                    mcResults.add(mcResp.getAnswer());
                }
                HashMap<String, Integer> mcAppearances = new HashMap<String, Integer>();
                for(String n: mcResults) {
                    if(mcAppearances.containsKey(n)) {
                        int counter = mcAppearances.get(n);
                        counter = counter+1;
                        mcAppearances.put(n, counter);
                    } else {
                        mcAppearances.put(n, 1);
                    }
                }
                // If no response chose an option, set occurrences to 0
                MultipleChoiceQuestion question = (MultipleChoiceQuestion) questionObj;
                for(String m: question.getChoices()) {
                    if(!mcAppearances.containsKey(m)) {
                        mcAppearances.put(m, 0);
                    }
                }
                resultsMap.put(questionObj.getID(), mcAppearances);
            }
        }
        model.addAttribute("questions", survey.getQuestions());
        model.addAttribute("results", resultsMap);
        model.addAttribute("name", survey.getName());
        if (featureManager.isActive(ApplicationFeatures.GRAPHICAL_RESPONSES)) {
            model.addAttribute("featureVar", "GRAPHICAL_RESPONSES");
        }
        else{
            model.addAttribute("featureVar", "BASIC_RESPONSES");
        }
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
        model.addAttribute("user", user);
        return "archive";
    }
}