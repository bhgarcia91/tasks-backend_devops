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
    }
}
