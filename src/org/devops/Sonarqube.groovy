package org.devops

class Sonarqube(){
    String sonarServe
    String username
    String password
    String sonarDate = sh returnStdout:true,script:"date +%s"
    sonarDate = sonarDate - "\n"

    def sonarJava(projectName,projectDescription,projectPath){
        sh """
        sonar-scanner  -Dsonar.host.url=${sonarServe}  \
            -Dsonar.projectKey=${projectName}  \
            -Dsonar.projectName=${projectName} \
            -Dsonar.projectVersion=${sonarDate} \
            -Dsonar.login=${username} \
            -Dsonar.password=${password} \
            -Dsonar.ws.timeout=30 \
            -Dsonar.projectDescription=${projectDescription}  \
            -Dsonar.links.homepage=http://www.baidu.com \
            -Dsonar.sources={projectPath} \
            -Dsonar.sourceEncoding=UTF-8 \
            -Dsonar.java.binaries=target/classes \
            -Dsonar.java.test.binaries=target/test-classes \
            -Dsonar.java.surefire.report=target/surefire-reports
        """
    }
}
