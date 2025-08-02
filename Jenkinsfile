#! usr/bin/env groovy
@Library('jenkins-shared-library')
def gv

pipeline{
    agent any
     parameters {
        string(name: 'BRANCH_NAME', defaultValue: 'main', description: 'Branch to build')
        choice(name: 'ENVIRONMENT', choices: ['dev', 'test', 'prod'], description: 'Deployment environment')
        choice(name: "VERSION", choices: ['1.1', '1.2', '1.3'], description: 'Application version')
        booleanParam(name: 'RUN_TESTS', defaultValue: true, description: 'Run tests during the build')
    }
    tools {
        maven 'maven-3.9'
       
    }
    stages{

        stage("initialize"){
            steps {
                script {
                  
                    gv = load 'script.groovy'
                }
            }
        }

        stage("test"){
            steps {
                script {
                    echo "Testing the application..."
    
                }
            }
        }
        stage("build jar"){
           
            steps {
                script {
                    buildMavenJar()
                   
                }
            }
        }
        stage("build and push"){
            steps {
                script {
                  
                    buildImage("192.168.64.8:8083/java-maven-app:jma-${params.VERSION}")
                    dockerLogin()
                    dockerPush("192.168.64.8:8083/java-maven-app:jma-${params.VERSION}")
                }
            }
        }

        stage("deploy"){
          
            steps {
                script {
                    gv.deployApp()
                }
            }
        }
    }
}