package org.devops


def sonarJava(sonarServer,projectName,projectDescription,projectPath){
    /*
    发起sonarscan扫描
    */
    def servers = ["test":"sonarqube-test","dev":"sonarqube-dev","pro":"sonarqube-pro"]

    withSonarQubeEnv(servers[sonarServer]) {
        // This expands the evironment variables SONAR_CONFIG_NAME, SONAR_HOST_URL, SONAR_AUTH_TOKEN that can be used by any script.
        // https://docs.sonarqube.org/latest/analysis/scan/sonarscanner-for-jenkins
        
        String SONAR_SCAN_CLI_HOME = "/usr/local/sonar-scanner-cli"
        String sonarDate = sh returnStdout:true,script:"date +%Y%m%d%H%M%S"
        sonarDate = sonarDate - '\n'
        
        sh """
            ${SONAR_SCAN_CLI_HOME}/bin/sonar-scanner \
            -X \
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

    /*
    插件获取状态
    */
    
    timeout(time: 5, unit: 'MINUTES') {
        def qg = waitForQualityGate()
        if (qg.status != 'OK') {
            error "Pipeline aborted due to quality gate failure: ${qg.status}"
        }
    }
}

/*
// 请求HTTP的URL进行拼接
def getSonarStatus(httpHost,projectName,crtId){
    String url  = "project_branches/list?project=${projectName}"
    String apiUrl = "${httpHost}/api/${url}"

    response = sonarHttpRequest(apiUrl,crtId)
    println response
}

// 发起GET请求
def sonarHttpRequest(apiUrl,crtId){
    res = httpRequest authentication: crtId, 
        contentType: 'APPLICATION_JSON', 
        ignoreSslErrors: true, 
        responseHandle: 'NONE', 
        url: apiUrl, 
        wrapAsMultipart: false

    return res
}

*/