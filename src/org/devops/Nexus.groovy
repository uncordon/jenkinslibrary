package org.devops

def getPomInfo(){
    def pom = readMavenPom file: 'pom.xml'
    env.pomGroupId = pom.groupId
    env.pomArtifactId = pom.artifactId
    env.pomVersion = pom.version
    env.pomPackaging = pom.packaging
    env.fileName = "target/${jarName}"
}

def pluginUpload(repoPotocol,repoHost,repoName,certId){
    println("Use plugin upload !")
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
    println("Use mvn command upload !")
    // 原生命令上传
    def m2Home = tool "M2"
    String repoUrl = "${repoPotocol}://${repoHost}/repository/${repoName}"
    sh """
        ${m2Home}/bin/mvn deploy:deploy-file -Dmaven.test.skip=true \
        -DgroupId=${pomGroupId} \
        -DartifactId=${pomArtifactId} \
        -Dversion=${pomVersion} \
        -Dpackaging=${pomPackaging} \
        -Dfile=${filename} \
        -DrepositoryId=${repoName}\
        -Durl=${repoUrl}
    """
}

def artifactUpdate(repoPotocol,repoHost,repoName,certId,repoSnapshotUrl,artifactUrl){
    sh "wget ${artifactUrl}"
    
    // 获取pomInfo
    // [, com, mycompany, app, my-app, 1.0-SNAPSHOT, my-app-1.0-20210430.082831-6.jar]
    pomInfo = artifactUrl.minus(repoSnapshotUrl).split("/").toList()

    // 获取pomGroupId
    env.pomGroupId = pomInfo[0..2].join(".")

    // 获取pomArtifactId
    env.pomArtifactId = pomInfo[3]

    // 获取pomVersion
    env.pomVersion = pomInfo[4].replace("SNAPSHOT","RELEASE")

    // 获取jarName
    // my-app-1.0-20210430.082831-6.jar
    jarName = pomInfo[-1]

    // 获取pomPackaging
    // jar
    env.pomPackaging = jarName.split("\\.")[-1]

    // 设置新的jarName
    env.fileName = "${pomArtifactId}-${pomVersion}.${pomPackaging}"

    // jar重命名
    sh "mv ${jarName} ${fileName}"

    // 上传制品
    nexus.pluginUpload(repoPotocol,repoHost,repoName,certId)
}

def upload(repoPotocol="http",repoHost,repoName,certId,type="plugin"){
    def jarName = sh returnStdout: true, script: "cd target;ls *.jar"
    env.jarName = jarName - "\n"

    getPomInfo()
    println(type)
    switch(type) {
        case "plugin":
            pluginUpload(repoPotocol,repoHost,repoName,certId)
            break;
        case "maven":
            mavenUpload(repoPotocol,repoHost,repoName,certId)
            break;
        default:
            error "Type error!"
            return false
    }
}