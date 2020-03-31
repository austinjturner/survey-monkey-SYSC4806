let surveyHref = ""
let surveyId = ""
let questions = []
let choices = []

// Input HTML for creating a text based question
textInputForm =`
    <br>
    <div id="questionInputBlock">
     <label for="textQuestionPrompt">Question:</label><br>
     <textarea id="textQuestionPrompt" name="questionPrompt" rows="2" cols="80"></textarea><br>
     <button type="button" id="addTextQuestion">Add Question</button>
    </div>`
	
//Input HTML for creating a Range based question
rangeInputForm =`
    <br>
    <div id="questionInputBlock">
     <label for="rangeQuestionPrompt">Question:</label><br>
     <textarea id="rangeQuestionPrompt" name="questionPrompt" rows="2" cols="80"></textarea><br>
	 <label for="rangeQuestionMin">Range Minimum:</label><br>
	 <input type="number" id="rangeQuestionMin" name="questionMin"><br>
	 <label for="rangeQuestionMax">Range Maximum:</label><br>
	 <input type="number" id="rangeQuestionMax" name="questionMax"> <br>
     <button type="button" id="addRangeQuestion">Add Question</button>
    </div>`
	
	
//Input HTML for creating a MC based question
MCInputForm =`
    <br>
    <div id="questionInputBlock">
     <label for="MCQuestionPrompt">Question:</label><br>
     <textarea id="MCQuestionPrompt" name="questionPrompt" rows="2" cols="80"></textarea><br>
	 <label for="MCChoicePrompt">Choice:</label><br>
     <textarea id="MCChoice" name="MCChoice" rows="2" cols="80"></textarea><br>
	 <button type="button" id="addChoice">Add Choice</button>
	 <button type="button" id="deleteChoices">Delete All Choices</button>
	 <button type="button" id="addMCQuestion">Add Question</button>
	 
    </div>`



$( document ).ready(function() {
    if(questions.length == 0)
    $("#submitSurvey").prop('disabled', true)
});

// Removes old input, adds new input option in the form based on what question type you wish to create
$(".inputSelect").change(function() {
  inputType = $(".inputSelect").val()
  $('#questionInput').empty()
  if(inputType =='TEXT'){
     $('#questionInput').append(textInputForm)
  }else if(inputType == 'NUMBER'){
	 $('#questionInput').append(rangeInputForm)
  }else if(inputType == 'MC'){
	 $('#questionInput').append(MCInputForm)
  }
});



$("#createSurveyForm").submit(function(e){
    e.preventDefault()
    const surveyName = $('#surveyName').val();
    const creator = $('#surveyCreator').val();
    // Create initial survey with name parameter so that questions can be added to an existing survey
    createSurveyRequest(surveyName, creator).then(async function(result){
        surveyHref = result._links.self.href;
        surveyId=result.id;
        // Create a promise for each question that needs to be created through API
        for(let i = 0; i < questions.length; i++){
            if(questions[i].inputType === 'TEXT'){
                await addQuestionToSurvey(questions[i].prompt, questions[i].inputType);
            }else if(questions[i].inputType === 'NUMBER'){
                await addRangeQuestionToSurvey(questions[i].prompt, questions[i].inputType, questions[i].min, questions[i].max);
            }else if(questions[i].inputType === 'MC') {
                await addMCQuestionToSurvey(questions[i].prompt, questions[i].inputType, questions[i].choices);
            }
        }

        const surveyMessage = 'Survey can be filled out at: '+ window.location.origin + '/survey/' + surveyId;
        $('#surveyName').val('');
        $('#textQuestionPrompt').val('');
        surveyHref = "";
        surveyId = "";
        questions = [];
        $('#questionTBody').empty();
        console.log(surveyMessage);
        alert(surveyMessage)
    })
});

// Button listener to add question to evaluation to array for submission later
$("#createSurveyForm").on('click', '#addTextQuestion', function () {
     prompt = $('#textQuestionPrompt').val()
     inputType =  $(".inputSelect").val()
     questions.push({'prompt': prompt, 'inputType': inputType})
     addNewQuestion(prompt, inputType)
});

// Button listener to add range question to evaluation to array for submission later
$("#createSurveyForm").on('click', '#addRangeQuestion', function () {
     prompt = $('#rangeQuestionPrompt').val()
     inputType =  $(".inputSelect").val()
	 min = $('#rangeQuestionMin').val()
	 max = $('#rangeQuestionMax').val()
	 
	if(min >= max){
		alert("Maximum must be above Minimum");
	}else{
		questions.push({'prompt': prompt, 'inputType': inputType, 'min' : min, 'max': max})
		addNewQuestion(prompt, inputType)
	}
	 
});

// Button listener to add MC question to evaluation to array for submission later
$("#createSurveyForm").on('click', '#addMCQuestion', function () {
     prompt = $('#MCQuestionPrompt').val()
     inputType =  $(".inputSelect").val()
     questions.push({'prompt': prompt, 'inputType': inputType, 'choices' : choices})
	 choices = []
     addNewQuestion(prompt, inputType)
});

// Button listener to add choice for current question to array for submission later
$("#createSurveyForm").on('click', '#addChoice', function () {
     c = $('#MCChoice').val()
     choices.push(c)
	 alert("Choice added: "+c);
     $('#MCChoice').val('');
	 
});

// Button listener to delete currently saved choices
$("#createSurveyForm").on('click', '#deleteChoices', function () {
     choices = []
	 alert("Choices deleted");
});



// Adds question to display on table
function addNewQuestion(questionPrompt, inputType){
    $("#submitSurvey").prop('disabled', false)
    questionRow = "<tr><td>" + questions.length + "</td><td>" + questionPrompt + "</td><td>" + inputType + "</td></tr>"
    $('#questionTBody').append(questionRow)
}



function createSurveyRequest(surveyName, creator){
 return new Promise((resolve, reject) => {
    apiUrl = window.location.origin + '/api/survey'
    $.ajax({
      url: apiUrl,
      type: 'POST',
      contentType: "application/json",
      data: JSON.stringify({
          name: surveyName,
          creator: window.location.origin + creator,
      }),
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
      success: function(data) {
        resolve(data)
      },
      error: function(error) {
        console.log(error)
        reject(error)
      },
    })
  })
}

function addRangeQuestionToSurvey(questionPrompt, inputType, questionMin, questionMax){
 return new Promise((resolve, reject) => {
    apiUrl = window.location.origin + '/api/question'
    $.ajax({
      url: apiUrl,
      type: 'POST',
      contentType: "application/json",
      data: JSON.stringify({ survey: surveyHref, type: inputType, prompt: questionPrompt , min: questionMin , max: questionMax}),
      success: function(data) {
        resolve(data)
      },
      error: function(error) {
        console.log(error)
        reject(error)
      },
    })
  })
}

function addMCQuestionToSurvey(questionPrompt, inputType, choices){
 return new Promise((resolve, reject) => {
    apiUrl = window.location.origin + '/api/question'
    $.ajax({
      url: apiUrl,
      type: 'POST',
      contentType: "application/json",
      data: JSON.stringify({ survey: surveyHref, type: inputType, prompt: questionPrompt , choices: choices}),
      success: function(data) {
        resolve(data)
      },
      error: function(error) {
        console.log(error)
        reject(error)
      },
    })
  })
}
