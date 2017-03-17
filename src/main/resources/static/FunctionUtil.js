/* global $scope, $rootScope */

//参数为等待时间的秒数,进行取整
function timeToString(wait) {
    var waitTime = new String();
    if (wait >= 60 && wait < 3600) {
        var minute = round(wait/60);
        var second = wait%60;;
        waitTime = minute + "分钟" + second + "秒";
    } else if (wait < 60) {
        waitTime = wait + "秒";
    } else if (wait >= 3600) {
        var hour = round(wait / 3600);
        var minute = round(wait % 3600/60);
        var second = wait % 3600 % 60;
        waitTime = hour + "小时" + minute + "分钟" + second + "秒";
    }
    return waitTime;
}
//由于Math.round函数是四舍五入的，所以不符合需求，需要向下取整的方法
function round(number){
    var num=Number(number);
    var numRound=Math.round(num);
    if(numRound>num){
        return numRound-1;
    }else{
        return numRound;
    }
}
//连接stomp
function connectStomp() {
    var stompClient = null;
    var socket = new SockJS('/server');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        stompClient.subscribe('/topic/add', function (response) {//订阅消息

        });
    });
}
