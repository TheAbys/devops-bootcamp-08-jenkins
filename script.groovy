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
    withCredentials([aws(credentialsId: "aws-credentials")]){
        sh 'docker build -t 561656302811.dkr.ecr.eu-central-1.amazonaws.com/k0938261-training:latest .'
        sh "aws ecr get-login-password --region eu-central-1 | docker login --username AWS --password-stdin 561656302811.dkr.ecr.eu-central-1.amazonaws.com"
        sh 'docker push 561656302811.dkr.ecr.eu-central-1.amazonaws.com/k0938261-training:latest'
    }
}
return this