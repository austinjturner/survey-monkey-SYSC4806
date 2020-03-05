package quickndirty.minisurveymonkey;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {
    @GetMapping("/create-survey")
    public String surveyForm(Model model) {
        return "createSurvey.html";
    }

    @GetMapping("/add-text-question")
    public String textQuestionForm(Model model) {
        return "addTextQuestion.html";
    }

    @GetMapping("/create-survey-basic")
    public String surveyBasicForm(Model model) {
        return "createBasicSurvey.html";
    }
}