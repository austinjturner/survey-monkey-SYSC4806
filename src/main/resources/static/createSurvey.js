let surveyID = "http://localhost:8080/survey/3"
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
  console.log($(".inputSelect").val())
  $('#questionInput').empty()
  if(inputType =='TEXT'){
     $('#questionInput').append(textInputForm)
  }
});

$("#createSurveyForm").submit(function(e){
    e.preventDefault()
    surveyName = $('#surveyName').val()
    createSurveyRequest(surveyName).then(function(result){
        surveyID = result._links.self.href
        console.log(surveyID)
    })
});

$("#createSurveyForm").on('click', '#addTextQuestion', function () {
     prompt = $('#textQuestionPrompt').val()
     inputType =  $(".inputSelect").val()
     questions.push({'prompt': prompt, 'inputType': inputType})
     addNewQuestion(prompt, inputType)
     $('#textQuestionPrompt').val('')
     console.log(questions)
     addQuestionToSurvey(prompt, inputType).then(function(result){
         console.log(result)
     })
});

function addNewQuestion(questionPrompt, inputType){
    $("#submitSurvey").prop('disabled', false)
    questionRow = "<tr><td>" + questions.length + "</td><td>" + questionPrompt + "</td><td>" + inputType + "</td></tr>"
    $('#questionTable').append(questionRow)
}

function createSurveyRequest(surveyName){
 return new Promise((resolve, reject) => {
    apiUrl = window.location.origin + '/survey'
    $.ajax({
      url: apiUrl,
      type: 'POST',
      contentType: "application/json",
      data: JSON.stringify({name: surveyName}),
      success: function(data) {
        console.log("Post Survey ", data)
        resolve(data)
      },
      error: function(error) {
        console.log(error)
        reject(error)
      },
    })
  })
}

function addQuestionToSurvey(questionPrompt, inputType){
 return new Promise((resolve, reject) => {
    console.log(JSON.stringify({ survey: surveyID, type: inputType, prompt: questionPrompt }))
    apiUrl = window.location.origin + '/question'
    $.ajax({
      url: apiUrl,
      type: 'POST',
      contentType: "application/json",
      data: JSON.stringify({ survey: surveyID, type: inputType, prompt: questionPrompt }),
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
