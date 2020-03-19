# Mini-SurveyMonkey - Group QuicknDirty

[![Build Status](https://travis-ci.com/austinjturner/survey-monkey-SYSC4806.svg?branch=master)](https://travis-ci.com/austinjturner/survey-monkey-SYSC4806)  
[Open App 🔗](https://survey-monkey-sysc4806.herokuapp.com/)

This project contains the implementation of a web-app platform to create and respond to surveys, similar to [SurveyMonkey](https://www.surveymonkey.com/).
The platform backend is written in Java using the [Spring Boot](https://spring.io/projects/spring-boot) framework and data is stored in a 
persistent PostgreSQL database. 

This project is continuously integrated via [Travis CI](https://travis-ci.org/) and deployed on [Heroku](https://dashboard.heroku.com/apps).

## State of the Project

- The project is now being tested via Travis CI and deployed to Heroku.
- The project uses PostgreSQL to persistently store entities
- Users can log in via Google or Github
- Users can create a named survey with multiple questions
- Users can respond to the questions in an existing survey
- Question types currently supported:
  - Text
  - Multiple choice
  - Number range
- The creator of a survey can view the results of surveys they have created

## Plan for the Next Sprint
- Enable feature toggle
- Improve UI graphics for viewing results

## Database Schema
### ORM Diagram
This diagram represents the ER model which the ORM is trying map to the schema
![DB Schema Diagram](src/images/orm_diagram.png?raw=true "DB Schema Diagram")

### Schema Diagram
This diagram is a representation of the actual schema in the PostgreSQL database
![DB Schema Diagram](src/images/schema_diagram.png?raw=true "DB Schema Diagram")