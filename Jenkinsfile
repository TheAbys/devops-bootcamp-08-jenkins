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
        stage("increment version") {
            steps {
                script {
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
        stage("build app") {
            steps {
                script {
                    echo 'building the application...'
                    sh 'mvn clean package'
                }
            }
        }
        stage("build image") {
            steps {
                script {
                    buildImage(IMAGE_NAME)
                    dockerLogin()
                    dockerPush(IMAGE_NAME)
                }
            }
        }
        stage("commit version update") {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: "fbf62b63-a61f-4b72-98ca-f68cd0480b8f", passwordVariable: "PASS", usernameVariable: "USER")]) {
                        sh 'git config --global user.email "jenkins@example.com"'
                        sh 'git config --global user.name "jenkins"'

                        sh "git status"
                        sh "git branch"
                        sh "git config --list"

                        sh "git remote set-url origin https://${USER}:${PASS}@github.com/TheAbys/devops-bootcamp-08-jenkins.git"
                        sh 'git add .'
                        sh 'git commit -m "CI: version bump"'
                        sh 'git push origin HEAD:jenkins-shared-lib'
                    }
                }
            }
        }
    }
}