package org.devops

def getPomInfo(){
    def jarName = sh returnStdout: true, script: "cd target;ls *.jar"
    env.jarName = jarName - "\n"

    def pom = readMavenPom file: 'pom.xml'
    env.pomGroupId = pom.groupId
    env.pomArtifactId = pom.artifactId
    env.pomVersion = pom.version
    env.pomPackaging = pom.packaging
}

def pluginUpload(repoPotocol,repoHost,repoName,certId){
    getPomInfo()
    // jenkins插件上传
    nexusArtifactUploader artifacts: [[artifactId: pomArtifactId,classifier: "",file: fileName,type: pomPackaging]], 
        credentialsId: certId,
        groupId: pomGroupId,
        nexusUrl: repoHost,
        nexusVersion: "nexus3", 
        protocol: repoPotocol, 
        repository: repoName, 
        version: pomVersion
}

def mavenUpload(repoPotocol,repoHost,repoName,certId){

    // 原生命令上传
    def m2Home = tool "M2"
    String repoUrl = "${repoPotocol}://${repoHost}/repository/${repoName}"
    sh """
        ${m2Home}/bin/mvn deploy:deploy-file -Dmaven.test.skip=true \
        -DgroupId=${pomGroupId} \
        -DartifactId=${pomArtifactId} \
        -Dversion=${pomVersion} \
        -Dpackaging=${pomPackaging} \
        -Dfile=./target/${jarName} \
        -DrepositoryId=${repoName}\
        -Durl=${repoUrl}
    """
}

def upload(repoPotocol="http",repoHost,repoName,certId,type="plugin"){
    getPomInfo()
    println(type)
    switch(type) {
        case "plugin":
            println("Use plugin upload !")
            String fileName = "target/${jarName}"
            pluginUpload(repoPotocol,repoHost,repoName,certId)
            break;
        case "maven":
            println("Use mvn command upload !")
            mavenUpload(repoPotocol,repoHost,repoName,certId)
            break;
        default:
            error "Type error!"
            return false
    }
}