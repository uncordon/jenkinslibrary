package org.devops

def PrintMsg(msg,color){
    colors = [
        'red': "\033[47;31m >>>>>>>>>>>>>>>>>>>> ${msg} <<<<<<<<<<<<<<<<<<<< \033[0m",
        'blue': "\033[47;34m >>>>>>>>>>>>>>>>>>>> ${msg} <<<<<<<<<<<<<<<<<<<< \033[0m",
        'green': "\033[32m >>>>>>>>>>>>>>>>>>>> ${msg} <<<<<<<<<<<<<<<<<<<< \033[0m",
    ]
    ansiColor('xterm'){
        println(colors[color])
    }
}