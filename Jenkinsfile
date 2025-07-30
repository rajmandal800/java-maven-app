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
        SERVER_CREDENTIALS = credentials('nexus-docker-repo')
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
                    //gv.buildJar()
                }
            }
        }
        stage("build image") {
            steps {
                script {
                    echo "building image"
                    //gv.buildImage()
                }
            }
        }
        stage("test") {
            when {
                expression { params.RUN_TESTS }
            }
            steps {
                script {
                    echo "running tests"
                    //gv.runTests()
                }
            }
        }
    
        stage("deploy") {
            steps {
                script {
                    echo "deploying"
                    echo "deploying with: ${SERVER_CREDENTIALS}"

                    // or use cedentials using withCredentials wrapper
                    withCredentials([usernamePassword(credentialsId: 'nexus-docker-repo', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
                        echo "Username: ${USER}"
                        echo "Password: ${PASS}"
                    }
                    echo "deplying version: ${params.VERSION} to environment: ${params.ENVIRONMENT}"
                    //gv.deployApp()
                }
            }
        }
    }   
}