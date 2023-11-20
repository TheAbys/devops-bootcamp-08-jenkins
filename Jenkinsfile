pipeline {
    agent any
    tools {
        maven "maven-3.9"
    }
    stages {
        stage("build jar") {
            steps {
                script {
                    echo "building the application..."
                    sh "mvn package"
                }
            }
        }
        stage("build image") {
            steps {
                script {
                    echo "building the docker image..."
                    withCredentials([usernamePassword(credentialsId: "nexus-docker-repo", passwordVariable: "PASS", usernameVariable: "USER")]){
                        sh 'docker build -t 64.226.110.153:8083/1.2 .'
                        sh 'echo $PASS | docker login -u $USER --password-stdin 64.226.110.153:8083'
                        sh 'docker push 64.226.110.153:8083/1.2'
                    }
                }
            }
        }
        stage("deploy") {
            steps {
                script {
                    echo "deploying the application..."
                }
            }
        }
    }
}