package org.devops

def saltDeploy(hosts,command){
    sh "salt -L ${hosts} ${command}"
}

def ansibleDeploy(hosts,command,options=""){
    if("${options}"== ""){
        sh "ansible ${hosts} -m ${command}"
    }else{
        sh "ansible ${hosts} -m ${command} -a ${options}"
    }
}