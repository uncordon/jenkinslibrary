package org.devops

def email(status,toUser){
    emailext body: '111', 
    subject: 'Jenkins-项目构建信息', 
    to: toUser
}