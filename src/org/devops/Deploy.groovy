package org.devops

def saltDeploy(hosts,command){
    sh "salt -L ${hosts} ${command}"
}

def ansibleDeploy(){
    sh "ansible all -m ping"
}