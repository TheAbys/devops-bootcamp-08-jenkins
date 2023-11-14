pipeline {
    agent any
    tools {
        // does not work for every tool, just for maven gradle and jdk
        // maven-3.9 is the name of the installation within Jenkins
        maven "maven-3.9"
    }
    environment {
        NEW_VERSION = '1.3.0'
        SERVER_CREDENTIALS = credentials('server-credentials')
    }
    parameters {
        string(name: 'VERSION', defaultValue: '', description: 'version to deploy on prod')
        choice(name: 'VERSION', choices: ['1.1.0', '1.2.0', '1.3.0'], description: '')
        booleanParam(name: 'executeTests', defaultValue: true, description: '')
    }
    stages {

        stage("build") {
            /*when {
                expression {
                    // environmental variable from Jenkins
                    BRANCH_NAME == 'dev' && CODE_CHANGES == true
                }
                // will just execute if the current branch is 'dev'
                // and there were changes in the code
            }*/
            steps {
                echo 'building the application...'
                echo "building version ${NEW_VERSION}" //variable is interpreted
                //echo 'building version ${NEW_VERSION}' //variable is not interpreted
            }
        }

        stage("test") {
            /*when {
                expression {
                    // environmental variable from Jenkins
                    BRANCH_NAME == 'dev' 
                }
                // will just execute if the current branch is 'dev'
            }*/
            when {
                expression {
                    params.executeTests
                }
            }
            steps {
                echo 'testing the application...'
            }
        }

        stage("deploy") {
            steps {
                echo 'deploying the application...'

                // using on environmental level
                echo "deploying version ${params.VERSIOn}"

                // using just in this current step
                // basically extracts the credentials in to variables USER and PWD
                withCredentials([
                    usernamePassword(credentials: 'server-credentials', usernameVariable: USER, passwordVariable: PWD)
                ]) {
                    sh "some script ${USER} ${PWD}"
                }
            }
        }

        //post {
            /*always {
                // whatever happens, success,failure,... this will always execute
            }*/
            /*success {
                // will happen on success
            }*/
            /*failure {
                // will happen on failure
            }*/
        //}
    }
}