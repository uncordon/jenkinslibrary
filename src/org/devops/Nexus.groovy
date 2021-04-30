package org.devops

def base(){
    def pomInfo = [:]
    def jarName = sh returnStdout: true, script: "cd target;ls *.jar"
    jarName = jarName - "\n"
    def pom = readMavenPom file: 'pom.xml'

    pomInfo['jarName'] = jarName
    pomInfo['pomGroupId'] = pom.groupId
    pomInfo['pomArtifactId'] = pom.artifactId
    pomInfo['pomVersion'] = pom.version
    pomInfo['pomPackaging'] = pom.packaging

    return pomInfo
}

def mavenUpload(repoPotocol='http',repoHost,repoName,certId){
    def pomInfo  = base()

    /*
    // 原生命令上传
    def m2Home = tool "M2"
    sh """
        ${m2Home}/bin/mvn deploy:deploy-file -Dmaven.test.skip=true \
        -DgroupId=${pomGroupId} \
        -DartifactId=${pomArtifactId} \
        -Dversion=${pomVersion} \
        -Dpackaging=${pomPackaging} \
        -Dfile=./target/${jarName} \
        -DrepositoryId=${serverId}\
        -Durl=https://maven.ibumobile.com/repository/demo-hosted
    """
    */
    
    
    // jenkins插件上传
    String fileName = "target/${pomInfo.jarName}"
    nexusArtifactUploader artifacts:[[artifactId:"${pomInfo.pomArtifactId}",classifier:"",file:"${fileName}",type:"${pomInfo.pomPackaging}"]], 
        credentialsId: certId,
        groupId: "${pomInfo.pomGroupId}",
        nexusUrl: repoHost,
        nexusVersion: "nexus3", 
        protocol: repoPotocol, 
        repository: repoName, 
        version: "${pomInfo.pomVersion}"
}