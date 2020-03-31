let surveyHref = "";
let surveyId = "";
let questions = [];

// Input HTML for creating a text based question
textInputForm =`
    <br>
    <div id="questionInputBlock">
     <label for="textQuestionPrompt">Question:</label><br>
     <textarea id="textQuestionPrompt" name="questionPrompt" rows="2" cols="80"></textarea><br>
     <button type="button" id="addTextQuestion">Add Question</button>
    </div>`;
	
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
    </div>`;
	
	
//Input HTML for creating a MC based question
MCInputForm =`
    <br>
    <div id="questionInputBlock">
     <label for="MCQuestionPrompt">Question:</label><br>
     <textarea id="MCQuestionPrompt" name="questionPrompt" rows="2" cols="80"></textarea><br>
	 <hr>
	 <div id="choiceList"></div>
	 <div class="input-group mb-3">
         <button class="btn btn-primary" type="button" onclick="addChoices(1)">Add another choice</button>
      </div>
    </div>
    <hr>
    <button type="button" id="addMCQuestion">Add Question</button>`;

MCChoiceHTML = `
	 <div class="input-group mb-3 choice-group">
        <input type="text" class="form-control" placeholder="Enter an answer choice" aria-label="choice">
        <div class="input-group-append">
           <button class="btn btn-outline-danger" type="button" onclick="$(this).closest('.choice-group').remove()">
                Remove
            </button>
        </div>
     </div>`;


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
      addChoices(3);
  }
});



$("#createSurveyForm").submit(function(e){
    e.preventDefault();
    const surveyName = $('#surveyName').val();

    // run make sure we have the minimum inputs
    if (!surveyName){
        $('#surveyCompletedModal').modal('toggle');
        alert("Please enter a survey name.");
        return;
    }

    if (questions.length === 0){
        $('#surveyCompletedModal').modal('toggle');
        alert("Please enter at least one question.");
        return;
    }

    // load some form data from the page
    const creator = $('#surveyCreator').val();
    let surveyLink = window.location.origin + '/survey/...';

    // set link to temporary text while the real id is loaded
    $('#surveyCompletedLinkText').val(surveyLink);

    // reset values right away to prevent any duplicate surveys being created
    $('#surveyName').val('');
    $('#textQuestionPrompt').val('');
    $('#questionTBody').empty();

    // Create initial survey with name parameter so that questions can be added to an existing survey
    createSurveyRequest(surveyName, creator).then(async function(result){
        surveyHref = result._links.self.href;
        surveyId=result.id;

        surveyLink = window.location.origin + '/survey/' + surveyId;
        $('#surveyCompletedLinkText').val(surveyLink);

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

        // Set the onclick listener for copying the survey link and remove any old listeners
        $("#surveyCompletedCopyBtn").off("click");
        $('#surveyCompletedCopyBtn').click(e => {
            $('#surveyCompletedLinkText').select();
            document.execCommand("copy");
            window.getSelection().removeAllRanges();
        });

        // set the onclick listener for opening the survey link and remove any old listeners
        $("#surveyCompletedLinkBtn").off("click");
        $('#surveyCompletedLinkBtn').click(e => {
            window.open(surveyLink);
        });

        // set the onclick listener for opening the survey responses and remove any old listeners
        $("#surveyCompleteResponsesButton").off("click");
        $('#surveyCompleteResponsesButton').click(e => {
            window.open(surveyLink + '/responses');
        });

        // reset page data
        questions = [];
        surveyHref = "";
        surveyId = "";
    })
});

// Button listener to add question to evaluation to array for submission later
$("#createSurveyForm").on('click', '#addTextQuestion', function () {
     prompt = $('#textQuestionPrompt').val()
     inputType =  $(".inputSelect").val()
     questions.push({'prompt': prompt, 'inputType': inputType})
     addNewQuestion(prompt, inputType)

    // clear inputs
    $('#textQuestionPrompt').val('');
});

// Button listener to add range question to evaluation to array for submission later
$("#createSurveyForm").on('click', '#addRangeQuestion', function () {
     prompt = $('#rangeQuestionPrompt').val()
     inputType =  $(".inputSelect").val()
	 min = $('#rangeQuestionMin').val()
	 max = $('#rangeQuestionMax').val()
	 
	if(min >= max){
		alert("Maximum must be above Minimum");
		return;
	}else{
		questions.push({'prompt': prompt, 'inputType': inputType, 'min' : min, 'max': max})
		addNewQuestion(prompt, inputType)
	}


    // clear inputs
    $('#rangeQuestionPrompt').val('');
    $('#rangeQuestionMin').val('');
    $('#rangeQuestionMax').val('');
});

// Button listener to add MC question to evaluation to array for submission later
$("#createSurveyForm").on('click', '#addMCQuestion', function () {

    // load the choices from the page into the array
    let divs = $('#choiceList').children();
    let choices = [];
    for (div of divs){
        choices.push($(div).find('input').val())
    }

    if (choices.length < 2){
        alert("Please enter at least 2 choices.");
        return;
    }

    // add questions to the survey
    let prompt = $('#MCQuestionPrompt').val();
    let inputType =  $(".inputSelect").val();
    questions.push({'prompt': prompt, 'inputType': inputType, 'choices' : choices});
    addNewQuestion(prompt, inputType);

    // clear inputs
    $('#MCQuestionPrompt').val('');
    $('.choice-group').remove();
    addChoices(3);
});

// Adds question to display on table
function addNewQuestion(questionPrompt, inputType){
    $("#submitSurvey").prop('disabled', false)
    questionRow = "<tr><td>" + questions.length + "</td><td>" + questionPrompt + "</td><td>" + inputType + "</td></tr>"
    $('#questionTBody').append(questionRow)
}

function addChoices(count){
    for (let i = 0; i < count; i++){
        $('#choiceList').append(MCChoiceHTML);
    }
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
