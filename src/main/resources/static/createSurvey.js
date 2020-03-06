let surveyHref = ""
let surveyId = ""
let questions = []
textInputForm =`
    <br>
    <div id="questionInputBlock">
     <label for="textQuestionPrompt">Question:</label><br>
     <textarea id="textQuestionPrompt" name="questionPrompt" rows="2" cols="80"></textarea><br>
     <button type="button" id="addTextQuestion">Add Question</button>
    </div>`

$( document ).ready(function() {
    if(questions.length == 0)
    $("#submitSurvey").prop('disabled', true)
});

$(".inputSelect").change(function() {
  inputType = $(".inputSelect").val()
  $('#questionInput').empty()
  if(inputType =='TEXT'){
     $('#questionInput').append(textInputForm)
  }
});

$("#createSurveyForm").submit(function(e){
    e.preventDefault()
    surveyName = $('#surveyName').val()
    createSurveyRequest(surveyName).then(function(result){
        surveyHref = result._links.self.href
        surveyId=result.id
        promises = []
        for(var i = 0; i < questions.length; i++){
         promises.push(addQuestionToSurvey(questions[i].prompt, questions[i].inputType))
        }
        Promise.all(promises)
         .then(() => {
            surveyMessage = 'Survey can be filled out at: '+ window.location.origin + '/survey/' + surveyId
            $('#surveyName').val('')
            $('#textQuestionPrompt').val('')
            surveyHref = ""
            surveyId = ""
            questions = []
            $('#questionTBody').empty()
            console.log('SurveyUrl')
            alert(surveyMessage)
         })
         .catch((e) => {
        });
    })
});

$("#createSurveyForm").on('click', '#addTextQuestion', function () {
     prompt = $('#textQuestionPrompt').val()
     inputType =  $(".inputSelect").val()
     questions.push({'prompt': prompt, 'inputType': inputType})
     addNewQuestion(prompt, inputType)
});

function addNewQuestion(questionPrompt, inputType){
    $("#submitSurvey").prop('disabled', false)
    questionRow = "<tr><td>" + questions.length + "</td><td>" + questionPrompt + "</td><td>" + inputType + "</td></tr>"
    $('#questionTBody').append(questionRow)
}

function createSurveyRequest(surveyName){
 return new Promise((resolve, reject) => {
    apiUrl = window.location.origin + '/api/survey'
    $.ajax({
      url: apiUrl,
      type: 'POST',
      contentType: "application/json",
      data: JSON.stringify({name: surveyName}),
      success: function(data) {
        resolve(data)
      },
      error: function(error) {
        reject(error)
      },
    })
  })
}

function addQuestionToSurvey(questionPrompt, inputType){
 return new Promise((resolve, reject) => {
    apiUrl = window.location.origin + '/api/question'
    $.ajax({
      url: apiUrl,
      type: 'POST',
      contentType: "application/json",
      data: JSON.stringify({ survey: surveyHref, type: inputType, prompt: questionPrompt }),
      success: function(data) {5
        resolve(data)
      },
      error: function(error) {
        console.log(error)
        reject(error)
      },
    })
  })
}
