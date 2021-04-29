package org.devops

// 发起请求
def sonarHttpRequest(apiUrl,crtId,action){
    res = httpRequest authentication: crtId, 
        contentType: 'APPLICATION_JSON', 
        ignoreSslErrors: true, 
        httpMode: action, 
        responseHandle: 'NONE', 
        url: apiUrl, 
        wrapAsMultipart: false
    return res
}


// 获取扫描状态
def getSonarStatus(apiHost,projectName,crtId){
    String apiUrl  = "${apiHost}/api/project_branches/list?project=${projectName}"

    response = sonarHttpRequest(apiUrl,crtId,"GET")
    responseJSON = readJSON text: """${response.content}"""

    return responseJSON.branches[0].status.qualityGateStatus
}


// 查找项目
def searchProject(apiHost,projectName,crtId){
    String apiUrl  = "${apiHost}/api/projects/search?projects=${projectName}"

    response = sonarHttpRequest(apiUrl,crtId,"GET")
    responseJSON = readJSON text: """${response.content}"""
    
    result = responseJSON.paging.total.toString()
    if(result == "0"){
        return false
    }
    return responseJSON
}

// 创建项目
def createProject(apiHost,projectName,projectKey,crtId){
    String apiUrl  = "${apiHost}/api/projects/create?name=${projectName}&projects=${projectKey}"

    response = sonarHttpRequest(apiUrl,crtId,"GET")
    responseJSON = readJSON text: """${response.content}"""

    return responseJSON
}