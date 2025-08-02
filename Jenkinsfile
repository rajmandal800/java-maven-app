#! usr/bin/env groovy
@Library('jenkins-shared-library')
def gv

pipeline{
    agent any
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
                  
                    buildImage("192.168.64.8:8083/java-maven-app:jma-3.0")
                    dockerLogin()
                    dockerPush("192.168.64.8:8083/java-maven-app:jma-3.0")
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