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
                    sh 'mvn package'
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
    }
}