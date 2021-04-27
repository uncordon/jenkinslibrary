package org.devops

def gitlabHttpAPI(httpHost,crtId,action,url){
    withCredentials([string(credentialsId: crtId, variable: 'ACCESS_TOKEN')]) {
        res = httpRequest contentType: 'APPLICATION_JSON', 
                customHeaders: [[name: 'PRIVATE-TOKEN', value: '${ACCESS_TOKEN}']], 
                httpMode: action, 
                ignoreSslErrors: true, 
                responseHandle: 'NONE', 
                url: '${httpHost}/api/v4/${url}', 
                wrapAsMultipart: false
    }
    return res
}

def gitlabBuildStatus(httpUrl,crtId,projectId,commitSha,status){
    String httpHost = getHttpHost(httpUrl)
    String url = "projects/${projectId}/statuses/${commitSha}?state=${status}"
    println(url)
    String response = gitlabHttpAPI(httpHost,crtId,'POST',url)
    println(response)
    return response
}

def getHttpHost(httpUrl){
    String protocol = httpUrl.split(':')[0]
    String host = httpUrl.split('/')[2]
    return "${protocol}://${host}"
}