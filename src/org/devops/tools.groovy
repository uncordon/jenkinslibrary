package org.devops

def PrintMsg(msg,color){
    colors = [
        'red': "\033[40;31m >>>>>>>>>>>>>>>>>>>> ${msg} <<<<<<<<<<<<<<<<<<<< \033[0m",
        'blue': "\033[40;34m >>>>>>>>>>>>>>>>>>>> ${msg} <<<<<<<<<<<<<<<<<<<< \033[0m",
        'green': "\033[40;32m >>>>>>>>>>>>>>>>>>>> ${msg} <<<<<<<<<<<<<<<<<<<< \033[0m",
    ]
    ansiColor('xterm'){
        println(colors[color])
    }
}