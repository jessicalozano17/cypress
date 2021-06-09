pipeline {

    agent none

    stages {
        stage('Build') {agent{
                    label 'jenkins-slave-mvn-jdk11'
                }
            steps {
                sh 'mvn -X -gs ./settings.xml install'
            }
        }
        stage('Test') {agent{
                    label 'jenkins-slave-mvn-jdk11'
                }
            steps {
                sh 'mvn -X -gs ./settings.xml install'
            }
        }
    }
}