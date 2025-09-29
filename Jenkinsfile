pipeline {
    agent any
    stages {
        stage ('Build Backend') {
            steps {
                bat 'mvn clean package -DskipTests'
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
                // abre módulos para evitar o InaccessibleObjectException no JDK 21
                SONAR_SCANNER_OPTS = '--add-opens=java.base/java.lang=ALL-UNNAMED'
            }
            steps {
                withSonarQubeEnv('SONAR_SCANNER'){
                    bat """
                      set "JAVA_HOME=%JAVA21%"
                      set "PATH=%JAVA_HOME%\\bin;%PATH%"
                      java -version
                      "%scannerHome%\\bin\\sonar-scanner" -e ^
                        -Dsonar.projectKey=DeployBack ^
                        -Dsonar.host.url=http://localhost:9000 ^
                        -Dsonar.login=06cd33aa90ade2c188a8683573a84bf8053852e9 ^
                        -Dsonar.java.binaries=target/classes ^
                        -Dsonar.coverage.exclusions=**/mvn/**,**/src/test/**,**/*Application.java
                    """
                }
            }
        }
        stage ('Quality Gate') {
            steps {
                sleep(5)
                timeout(time: 5, unit: 'MINUTES'){
                    waitForQualityGate abortPipeline:true
                }
            }
        }
        stage ('Deploy Backend') {
            steps {
                   deploy adapters: [tomcat9(alternativeDeploymentContext: '', credentialsId: 'TomcatLogin', path: '', url: 'http://localhost:8001/')], contextPath: 'tasks-backend', war: 'target/tasks-backend.war'
                }
        }
         stage ('API Test') {
            steps {
                dir('api test') {
                git 'https://github.com/bhgarcia91/tasks-api-tests'
                bat 'mvn test'
                }
            }
        }
        stage ('Deploy FrontEnd') {
            steps {
                dir('frontend') {
                git 'https://github.com/bhgarcia91/tasks-frontend_devops'
                bat 'mvn clean package'
                deploy adapters: [tomcat9(alternativeDeploymentContext: '', credentialsId: 'TomcatLogin', path: '', url: 'http://localhost:8001/')], contextPath: 'tasks', war: 'target/tasks.war'
                }
            }
        }
        stage ('Functional Test') {
            steps {
                dir('functional test') {
                git 'https://github.com/bhgarcia91/Task-functional-tests'
                bat 'mvn test'
                }
            }
        }
        stage ('Deploy prod'){
            steps {
                bat 'docker-compose build'
                bat 'docker-compose up -d'
            }
        }
        stage ('Health Check'){
            steps {
                sleep(3)
                dir('functional test'){
                    bat 'mvn verify -DskipTests'
                }
            }
        }
    }
    post {
        always {
            junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml, api test/target/surefire-reports/*.xml, functional test/target/surefire-reports/*.xml, '
        }
    }
}