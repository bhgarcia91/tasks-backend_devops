pipeline {
    agent any
    stages {
        stage ('Build Backend') {
            steps {
                bat 'mvn clean package -DskipTest=true'
            }
        }
        stage ('Build Test') {
            steps {
                bat 'mvn test'
            }
        }
        stage ('Sonar Analyses') {
            environment {
                scannerHome = tool 'SONAR_SCANNER'
            }
            steps {
                withSonarQubeEnv('SONAR_SCANNER'){
                    bat "${scannerHome}/bin/sonar-scanner -e -Dsonar.projectKey=DeployBack -Dsonar.host.url=http://localhost:9000 -Dsonar.login=06cd33aa90ade2c188a8683573a84bf8053852e9 -Dsonar.java.binaries=target -Dsonar.coverage.exclusions=**/mvn/**,**/scr/test/**,**Application.java"
                }
            }
        }
    }
}
