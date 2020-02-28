# Mini-SurveyMonkey - Group QuicknDirty
This project contains the implementation of a web-app platform to create and repsond to surveys, similar to [SurveyMonkey](https://www.surveymonkey.com/).
The platform backend is written in Java using the [Spring Boot](https://spring.io/projects/spring-boot) framework and data is stored in a 
persistent PostgreSQL database. 

This project is continuously integerated via [Travis CI](https://travis-ci.org/) and deployed on [Heroku](https://dashboard.heroku.com/apps).

## State of the Project
The project is currently in its eariest starting phase. 

**There is currently no CI setup, and the project is not deployed on Heroku**

The first milestone will allow users to create a survey with only text-based questions.
The issues currently pending for this milestone can be summarized as:

1. Setup an initial Spring Boot system and integrate with Travis CI and Heroku.
2. Establish the data models needed for milestone 1
3. Implement the UI for milestone 1


## Plan for the Next Sprint
The next sprint will add the 2 additional questions types: Multiple choice and range input. The creator of the survey
should also be able to view some of the result data (maybe number based, rather than the complete graphical displays).

## Database Schema
TODO
