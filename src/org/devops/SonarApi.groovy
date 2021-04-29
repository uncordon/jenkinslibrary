package org.devops

// 请求HTTP的URL进行拼接
def getSonarStatus(httpHost,projectName,crtId){
    String url  = "project_branches/list?project=${projectName}"
    String apiUrl = "${httpHost}/api/${url}"

    response = sonarHttpRequest(apiUrl,crtId)
    responseJSON = readJSON text: """${response.content}"""
    
    return responseJSON.branches[0].status.qualityGateStatus
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