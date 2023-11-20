#!/user/bin/env groovy
library identifier: "jenkins-shared-library@starting-code", retriever: modernSCM([
    $class: "GitSCMSource",
    remote: "git@github.com:TheAbys/devops-bootcamp-08-jenkins-shared-library.git",
    credentialsId: "github-key-credentials"
])
def gv

pipeline {
    agent any
    tools {
        maven "maven-3.9"
    }
    stages {
        stage("init") {
            steps {
                script {
                    gv = load "script.groovy"
                }
            }
        }
        stage("test") {
            steps {
                script {
                    echo "testing the application..."
                    echo "Executing pipeline for branch $BRANCH_NAME"
                }
            }
        }
        stage("build jar") {
            steps {
                script {
                    buildJar()
                }
            }
        }
        stage("build image") {
            steps {
                script {
                    buildImage 'java-maven-app:2.0'
                    dockerLogin()
                    dockerPush 'java-maven-app:2.0'
                }
            }
        }
        stage("deploy") {
            when {
                expression {
                    BRANCH_NAME == "master"
                }
            }
            steps {
                script {
                    echo "deploying the application..."
                }
            }
        }
    }
}