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
return this