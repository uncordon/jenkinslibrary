package org.devops

def sonarJava(sonarServe,username,password,projectName,projectDescription,projectPath){
    String SONAR_SCAN_CLI_HOME = "/usr/local/sonar-scanner-cli"
    String sonarDate = sh returnStdout:true,script:"date +%Y%m%d%H%M%S"
    sonarDate = sonarDate - '\n'
    
    sh """
        ${SONAR_SCAN_CLI_HOME}/bin/sonar-scanner  -Dsonar.host.url=${sonarServe}  \
        -Dsonar.login=${username} \
        -Dsonar.password=${password} \
        -Dsonar.ws.timeout=30 \
        -Dsonar.projectKey=${projectName}  \
        -Dsonar.projectName=${projectName} \
        -Dsonar.projectVersion=${sonarDate} \
        -Dsonar.projectDescription=${projectDescription}  \
        -Dsonar.links.homepage=http://www.baidu.com \
        -Dsonar.sources=${projectPath} \
        -Dsonar.sourceEncoding=UTF-8 \
        -Dsonar.java.binaries=target/classes \
        -Dsonar.java.test.binaries=target/test-classes \
        -Dsonar.java.surefire.report=target/surefire-reports
    """
}
