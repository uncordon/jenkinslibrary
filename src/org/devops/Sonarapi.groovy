package org.devops

// 发起请求
def sonarHttpRequest(apiUrl,crtId,action){
    println(apiUrl)
    
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
def getSonarStatus(apiHost,crtId,projectName){
    String apiUrl  = "${apiHost}/api/project_branches/list?project=${projectName}"

    response = sonarHttpRequest(apiUrl,crtId,"GET")
    responseJSON = readJSON text: """${response.content}"""
    println(responseJSON)
    
    return responseJSON["branches"][0]["status"]["qualityGateStatus"]
}


// 查找项目
def searchProject(apiHost,crtId,projectName){
    String apiUrl  = "${apiHost}/api/projects/search?projects=${projectName}"

    response = sonarHttpRequest(apiUrl,crtId,"GET")
    responseJSON = readJSON text: """${response.content}"""
    println(responseJSON)
    result = responseJSON["paging"]["total"].toString()
    if(result == "0"){
        return false
    }
    return responseJSON
}

// 创建项目
def createProject(apiHost,crtId,projectName,projectKey){
    String apiUrl  = "${apiHost}/api/projects/create?name=${projectName}&project=${projectKey}"

    response = sonarHttpRequest(apiUrl,crtId,"POST")
    responseJSON = readJSON text: """${response.content}"""
    println(responseJSON)
    return responseJSON
}

//  关联质量配置
def configQualityProfile(apiHost,crtId,language,projectKey,qualityProfile){
    String apiUrl  = "${apiHost}/api/qualityprofiles/add_project?language=${language}&project=${projectKey}&qualityProfile=${qualityProfile}"

    response = sonarHttpRequest(apiUrl,crtId,"POST")
    responseJSON = readJSON text: """${response.content}"""
    println(responseJSON)
    return responseJSON
}