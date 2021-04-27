package org.devops

def gitlabHttpAPI(httpHost,crtId,action,url){
    withCredentials([string(credentialsId: crtId, variable: 'ACCESS_TOKEN')]) {
        res = httpRequest contentType: 'APPLICATION_JSON', 
                customHeaders: [[maskValue: true, name: 'PRIVATE-TOKEN', value: '${ACCESS_TOKEN}']], 
                httpMode: 'POST', 
                ignoreSslErrors: true, 
                responseHandle: 'NONE', 
                url: '${httpHost}/api/v4/${url}', 
                wrapAsMultipart: false
    }
    return res
}

def gitlabBuildStatus(httpHost,crtId,projectId,commitId,status){
    String url = "projects/${projectId}/statuses/${commitId}?state=${status}"
    response = gitlabHttpAPI(httpHost,crtId,'POST',url)
    println(response)
    return response
}

def getHttpHost(httpUrl){
    String protocol = httpUrl.split(':')[0]
    String host = httpUrl.split('/')[2]
    return "${protocol}://${host}"
}