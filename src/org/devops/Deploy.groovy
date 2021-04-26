package org.devops

def saltDeploy(hosts,command){
    sh "salt ${hosts} ${command}"
}

def ansibleDeploy(){
    sh "ansible all -m ping"
}