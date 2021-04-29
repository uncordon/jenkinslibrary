package org.devops

def gitlabHttpRequest(apiUrl,crtId,action){
    /*
    对API进行请求
    */
    withCredentials([string(credentialsId: crtId, variable: 'ACCESS_TOKEN')]) {
        res = httpRequest contentType: 'APPLICATION_JSON', 
                customHeaders: [[name: 'PRIVATE-TOKEN', value: ACCESS_TOKEN]], 
                httpMode: action, 
                ignoreSslErrors: true, 
                responseHandle: 'NONE', 
                url: apiUrl, 
                wrapAsMultipart: false
    }
    return res
}

def gitlabBuildStatus(httpUrl,crtId,projectId,commitSha,status){
    // 拼接完整API请求URL地址
    String apiHost = getApiHost(httpUrl)
    String url = "projects/${projectId}/statuses/${commitSha}?state=${status}"
    String apiUrl = "${apiHost}/api/v4/${url}"

    // 发起HTTP请求
    String response = gitlabHttpRequest(apiUrl,crtId,'POST')
    return response
}

def getApiHost(httpUrl){
    /*
    获取API主机地址
    */
    String protocol = httpUrl.split(':')[0]
    String apiHost = httpUrl.split('/')[2]
    return "${protocol}://${apiHost}"
}