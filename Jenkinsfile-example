def gv

pipeline {
    agent any
    parameters {
        string(name: 'BRANCH_NAME', defaultValue: 'main', description: 'Branch to build')
        choice(name: 'ENVIRONMENT', choices: ['dev', 'test', 'prod'], description: 'Deployment environment')
        choice(name: "VERSION", choices: ['1.0', '2.0', '3.0'], description: 'Application version')
        booleanParam(name: 'RUN_TESTS', defaultValue: true, description: 'Run tests during the build')
    }
    tools {
        // jdk 'jdk11'
        maven 'maven-3.9' //names defined in Jenkins global tool configuration
        // gradle 'gradle6'
    }
    environment {
        MY_ENV_VAR = "some_value"
        SERVER_CREDENTIALS = credentials('nexus-docker-repo') // need credentials and credentials binding plugin installed
    }
    stages {
        stage("init") {
            steps {
                script {
                    gv = load "script.groovy"
                }
            }
        }
        stage("build jar") {
            steps {
                script {
                    echo "building jar"
                    gv.buildJar()
                }
            }
        }
        stage("build image") {
            steps {
                script {
                    echo "building image"
                    gv.buildImage()
                }
            }
        }
        stage("test") {
            when {
                expression { params.RUN_TESTS }
            }
            steps {
                script {
                    // echo "running tests"
                    gv.runTests()
                }
            }
        }
    
        stage("deploy") {
            input {
                message "Select the environment to deploy to"
                ok "Deploy" // This is a button that confirms the user's selection and proceeds with the deployment
                parameters {
                    choice(name: 'ONE', choices: ['dev', 'staging', 'prod'], description: 'Deployment environment')
                    choice(name: 'TWO', choices: ['dev', 'staging', 'prod'], description: 'Deployment environment')
                }
            }
           
            steps {
                script {
                    env.ENV = input(
                        id: 'userInput', 
                        message: 'Please select the environment for deployment:',
                        parameters: [
                            choice(name: 'ENVIRONMENT', choices: ['dev', 'test', 'prod'], description: 'Deployment environment')
                        ]
                    )
                    echo "Selected environment from inline user input: ${env.ENV}"
                    // echo "deploying"
                    // echo "deploying with: ${SERVER_CREDENTIALS}"  
                    gv.deployApp()
                    echo "Deploying to ${ONE}"
                    echo "Deploying to ${TWO}"
                }
            }
        }
    }   
}