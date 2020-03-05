# Mini-SurveyMonkey - Group QuicknDirty

[![Build Status](https://travis-ci.com/austinjturner/survey-monkey-SYSC4806.svg?branch=master)](https://travis-ci.com/austinjturner/survey-monkey-SYSC4806)  
[Open App 🔗](https://survey-monkey-sysc4806.herokuapp.com/)

This project contains the implementation of a web-app platform to create and respond to surveys, similar to [SurveyMonkey](https://www.surveymonkey.com/).
The platform backend is written in Java using the [Spring Boot](https://spring.io/projects/spring-boot) framework and data is stored in a 
persistent PostgreSQL database. 

This project is continuously integrated via [Travis CI](https://travis-ci.org/) and deployed on [Heroku](https://dashboard.heroku.com/apps).

## State of the Project
The project is now being tested via Travis CI and deployed to Heroku.

The first milestone will allow users to create a survey with only text-based questions.
The issues currently pending for this milestone can be summarized as:

1. Setup an initial Spring Boot system and integrate with Travis CI and Heroku.
2. Establish the data models needed for milestone 1
3. Implement the UI for milestone 1


## Plan for the Next Sprint
The next sprint will add the 2 additional questions types: Multiple choice and range input. The creator of the survey
should also be able to view some of the result data (maybe number based, rather than the complete graphical displays).

## Database Schema
![DB Schema Diagram](src/main/resources/static/db_schema.PNG?raw=true "DB Schema Diagram")