#! usr/bin/env groovy
@Library('jenkins-shared-library')_
pipeline{
    agent any
    stages{
        stage("test"){
            steps {
                script {
                    echo "Testing the application..."
    
                }
            }
        }
        stage("build"){
           
            steps {
                script {
                    buildMavenJar()
                   
                }
            }
        }

        stage("deploy"){
          
            steps {
                script {
                   buildImage("192.168.64.8:8083/java-maven-app:jma-3.0")
                }
            }
        }
    }
}