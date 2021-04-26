package org.devops

def saltDeploy(hosts,command){
    sh "salt -t 10s -L ${hosts} ${command}"
}

def ansibleDeploy(hosts,command,options=""){
    if("${options}"== ""){
        sh "ansible ${hosts} -m ${command}"
    }else{
        sh "ansible ${hosts} -m ${command} -a ${options}"
    }
}