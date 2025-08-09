#! usr/bin/env groovy
// @Library('jenkins-shared-library') // used to load the global shared library configured in Jenkins dashboard

library identifier: 'jenkins-shared-library@main', retriever: modernSCM(
    [$class: 'GitSCMSource',
     remote: 'https://github.com/rajmandal800/jenkins-shared-library.git',
     credentialsId: 'de951385-ce45-4fd3-8457-61ec0f3422d4'
    ]
)

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
        stage("increment version"){
            steps {
                script{
                    echo 'incrementing app version...'
                    sh 'mvn build-helper:parse-version versions:set \
                        -DnewVersion=\\\${parsedVersion.majorVersion}.\\\${parsedVersion.minorVersion}.\\\${parsedVersion.nextIncrementalVersion} \
                        versions:commit'
                    def matcher = readFile('pom.xml') =~ '<version>(.+)</version>'
                    def version = matcher[0][1]
                    env.IMAGE_NAME = "$version-$BUILD_NUMBER"
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
                  
                    buildImage("192.168.64.8:8083/java-maven-app:${IMAGE_NAME}")
                    dockerLogin("192.168.64.8:8083","nexus-docker-repo")
                    dockerPush("192.168.64.8:8083/java-maven-app:${IMAGE_NAME}")
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

         stage("commit version update"){
            steps {
                script{

            //     withCredentials([usernamePassword(credentialsId:'de951385-ce45-4fd3-8457-61ec0f3422d4', passwordVariable: 'PASS', usernameVariable: 'USER')]){
            //     sh 'git config --global user.email "jenkins@mobili.com"' // doing this if we don't configure it jenkins will complain for emtpy metadata
            //     sh 'git config --global user.name "jenkins"'

            //     def REPO_URL = "https://${USER}:${PASS}@github.com/rajmandal800/java-maven-app.git"
            //     sh 'git status'
            //     sh 'git branch'
            //     sh 'git config --list'

            //     sh "git remote set-url origin ${REPO_URL}"
            //     sh 'git add .'
            //     sh 'git commit -m "ci: version bump"'
            //     sh 'git push origin HEAD:jenkins-job'

            // }

                //Installed ssh agent plugin and created credential for ssh
                // Use Ignore Committer Strategy plugin and add jenkins user email to not build if committer is jenkins
                //Needed because of webtrigger loop when jenkins updates version and push to the SCM.
                sshagent(credentials: ['github_ssh_rajmandal800']) {
                    sh 'git config --global user.email "jenkins@mobili.com"'
                    sh 'git config --global user.name "jenkins"'

                    // Ensure remote URL is SSH
                    sh 'git remote set-url origin git@github.com:rajmandal800/java-maven-app.git'

                    sh 'git status'
                    sh 'git branch'
                    sh 'git config --list'

                    sh 'git add .'
                    sh 'git commit -m "ci: version bump" || echo "No changes to commit"'
                    sh 'git push origin HEAD:jenkins-shared-lib-demo'
                }

                
                } 
            }
         }
    
    
    }


}
