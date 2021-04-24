package org.devops

def PrintMsg(msg,color){
    colors = [
        'red': "\033[31m >>>>>>>>>>>>>>>>>>>> ${msg} <<<<<<<<<<<<<<<<<<<< \033[0m",
        'blue': "\033[34m >>>>>>>>>>>>>>>>>>>> ${msg} <<<<<<<<<<<<<<<<<<<< \033[0m",
        'green': "\033[32m >>>>>>>>>>>>>>>>>>>> ${msg} <<<<<<<<<<<<<<<<<<<< \033[0m",
        'darkgreen': "[1;32m >>>>>>>>>>>>>>>>>>>> ${msg} <<<<<<<<<<<<<<<<<<<< [m",
    ]
    ansiColor('xterm'){
        println(colors[color])
    }
}