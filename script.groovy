def buildApp() {
    echo 'building the application...'
}
def testApp() {
    echo 'testing the application...'
}
def deployApp() {
    echo 'deploying the application...'

    // no extra configuration required to have params.VERSION accessable here
    echo "deploying version ${params.VERSION}"
}
def buildJar() {
    echo "building the application..."
    sh "mvn package"
}
def buildImage() {
    echo "building the docker image..."
    withCredentials([usernamePassword(credentialsId: "nexus-docker-repo", passwordVariable: "PASS", usernameVariable: "USER")]){
        sh 'docker build -t 64.226.110.153:8083/java-maven-app:1.2 .'
        sh 'echo $PASS | docker login -u $USER --password-stdin 64.226.110.153:8083'
        sh 'docker push 64.226.110.153:8083/java-maven-app:1.2'
    }
}
return this