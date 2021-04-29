package org.devops

// 获取扫描状态
def getSonarStatus(apiHost,projectName,crtId){
    String url  = "project_branches/list?project=${projectName}"

    response = sonarHttpRequest(apiHost,url,crtId,"GET")
    responseJSON = readJSON text: """${response.content}"""

    return responseJSON.branches[0].status.qualityGateStatus
}

// 发起请求
def sonarHttpRequest(apiHost,url,crtId,action){
    String apiUrl = "${apiHost}/api/${url}"

    res = httpRequest authentication: crtId, 
        contentType: 'APPLICATION_JSON', 
        ignoreSslErrors: true, 
        httpMode: action, 
        responseHandle: 'NONE', 
        url: apiUrl, 
        wrapAsMultipart: false
    return res
}

// 查找项目
def searchProject(apiHost,projectName,crtId){
    String url  = "projects/search?projects=${projectName}"

    response = sonarHttpRequest(apiHost,url,crtId,"GET")
    responseJSON = readJSON text: """${response.content}"""
    println(responseJSON)
    /*
    result = paging.total.toString()
    if(result == "0"){
        return false
    }
    */
    return responseJSON
}