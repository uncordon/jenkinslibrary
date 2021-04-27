package org.devops

def gitlabHttpAPI(httpHost,crtId,action,url){
    withCredentials([string(credentialsId: crtId, variable: 'ACCESS_TOKEN')]) {
        httpRequest contentType: 'APPLICATION_JSON', 
            customHeaders: [[maskValue: true, name: 'PRIVATE-TOKEN', value: '${ACCESS_TOKEN}']], 
            httpMode: 'POST', 
            ignoreSslErrors: true, 
            responseHandle: 'NONE', 
            url: '${httpHost}/api/v4/${url}', 
            wrapAsMultipart: false
    }
}

def gitlabBuildStatus(httpHost,crtId,projectId,commitId,status){
    String url = "projects/${projectId}/statuses/${commitId}?state=${status}"
    gitlabHttpAPI(httpHost,crtId,'POST',url)
}

def getHttpHost(httpUrl){
    String protocol = httpUrl.split(':')[0]
    String host = httpUrl.split('/')[2]
    return "${protocol}://${host}"
}