package org.devops

//构建
def build(btype,bshell){
    def buildTools = ["mvn":"M2","ant":"ANT","gradle":"GRADLE","npm":"NODE"]

    buildHome = tool buildTools[btype]

    sh "${buildHome}/bin/${btype} ${bshell}"
}
