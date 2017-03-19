/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
app.controller("UnderwayController", function ($scope, $http, $rootScope, stringService, $interval) {
    $http.get('underway', {
        //参数
    }).success(function (data) {
        for (var i = 0; i < data.length; i++) {
            if (data[i].firstTime == null) {//如果还没上菜则取现在时间减去开始时间，格式化为字符串
                var now = new Date();
                var nowMS = now.getTime();
                var waitTimeMS = nowMS - data[i].beginTime;//现在与开始时间之差是等待时间
                var waitTimeS = round(waitTimeMS / 1000);//转为秒数
                data[i].waitTimeS = Number(waitTimeS);
                data[i].waitTime = timeToString(waitTimeS);
            } else {
                var first = Number(data[i].firstTime);
                var begin = Number(data[i].beginTime);
                var waitTime = round((first - begin) / 1000);
                data[i].waitTime = timeToString(waitTime);
            }
            //将表示点菜的一个字符串转化为对象数组
            var reserve = stringService.convertString(data[i].reserve, data[i].fulfill);
            data[i].reserve = reserve;//预定的菜品数,将字符串转为对象数组
            var fulfill = "" != data[i].fulfill ? stringService.convertString(data[i].fulfill) : "";
            data[i].fulfill = fulfill;
            var beginDate = new Date();//将开始时间的毫秒数转为字符串
            var beginTime = Number(data[i].beginTime);
            beginDate.setTime(beginTime);
            data[i].beginTime = beginDate.toLocaleString();
        }
        $rootScope.indents = data;
        $scope.indents = data;
        //stomp相关，用于接受指定主题返回的消息
        stomp();
        //stomp相关结束
        $interval(function () {
            for (var i = 0; i < $scope.indents.length; i++) {
                if ($scope.indents[i].firstTime == null) {
                    var waitTimeS = Number($scope.indents[i].waitTimeS);
                    waitTimeS = waitTimeS + 1;
                    $scope.indents[i].waitTimeS = Number(waitTimeS);
                    $scope.indents[i].waitTime = timeToString(waitTimeS);
                }
            }
        }, 1000);
    });
    //对取消的支持
    $scope.cancel = function (id) {
        console.log(id);
        var indentStyle = {};
        indentStyle.id = id;
        indentStyle.style = 2;
        $http.post('/indent/style',
                $.param({id: indentStyle.id, style: indentStyle.style}),
                {headers: {'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8'}}
        ).success(function (data) {
            if (data == "success") {
                for (var i = 0; i < $rootScope.indents.length; i++) {
                    if ($rootScope.indents[i].id == id) {
                        $rootScope.indents.splice(i, 1);
                        $scope.indents.splice(i, 1);
                        break;
                    }
                }
            } else {
                alert("取消订单失败");
            }
        }).error(function (a) {
            alert("服务器发生错误");
        });
    };
});//正在进行控制器结束

