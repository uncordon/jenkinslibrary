package org.devops

def ansibleDeploy(){
    sh "ansible all -m ping"
}