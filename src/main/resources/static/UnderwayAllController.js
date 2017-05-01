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
                data[i].waitTimeS=waitTime;
            }
            //将表示点菜的一个字符串转化为对象数组
            var reserve = stringService.convertString(data[i].reserve, data[i].fulfill);
            data[i].reserve = reserve;//预定的菜品数,将字符串转为对象数组
            var beginDate = new Date();//将开始时间的毫秒数转为字符串
            var beginTime = Number(data[i].beginTime);
            beginDate.setTime(beginTime);
            data[i].beginTime = beginDate.toLocaleString();
        }
        $rootScope.indents = data;
        $scope.indents = data;
        $interval(function () {
            for (var i = 0; i < $scope.indents.length; i++) {
                //有上菜且，上菜数量为0时，会发生某种问题
                if ($scope.indents[i].firstTime == null || $scope.indents[i].fulfillNumber == 0) {
                    var waitTimeS = Number($scope.indents[i].waitTimeS);
                    waitTimeS = waitTimeS + 1;
                    $scope.indents[i].waitTimeS = Number(waitTimeS);
                    $scope.indents[i].waitTime = timeToString(waitTimeS);
                }
            }
        }, 1000);
    });
    //stomp相关，用于接受指定主题返回的消息
    stompAddIndent($http,$scope,$rootScope,stringService,$interval);
    //stomp相关结束
    //对取消的支持
    $scope.cancel = function (id) {
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
app.controller('UnderwayDetailsController', function ($rootScope, $stateParams, $scope, $http, $state) {
    var id = Number($stateParams.id);
    var indents = $rootScope.indents;
    for (var i = 0; i < indents.length; i++) {
        if (indents[i].id == id) {
            $scope.indent = indents[i];
            $scope.reserve = indents[i].reserve;//取出菜单赋值给scope
            break;
        }
    }
    /**
     * 用于查找菜单，返回订单菜单的价格,id为被更改的id
     */
    $scope.getPrice = function(id) {
        var value = Number(document.getElementById(id).value);
        for (var z = 0; z < $scope.reserve.length; z++) {
            if (id == $scope.reserve[z].id) {
                $scope.reserve[z].count = value;
                break;
            }
        }
        var price = Number(0);
        for (var i = 0; i < $scope.reserve.length; i++) {
            for (var y = 0; y < $rootScope.findMenu.length; y++) {
                if ($scope.reserve[i].name == $rootScope.findMenu[y].name) {
                    //通过count统计订单的价格
                    price = price + $scope.reserve[i].count * $rootScope.findMenu[y].price;
                    break;
                }
            }
        }
        $scope.indent.price = price;
    };
    //若web端更改上菜，判断是否是第一次上菜，若是，则更改firstTime
    function updateFirstTime() {
        var copyReserve = [];
        copyReserve = $scope.reserve;
        for (var i = 0; i < $scope.reserve.length; i++) {
            var value = Number(document.getElementById($scope.reserve[i].id).value);
            if ($scope.reserve[i].number != 0) {
                return;
            }
            if ($scope.reserve[i].id == id) {
                $scope.reserve[i].number = value;
            }
            if (i == $scope.reserve.length - 1) {
                //若已经到了最后一个，则更改第一次上菜时间
                $scope.indent.firstTime = new Date().getTime();
            }
        }
    }
    //直接使用scope中的值传递
    $scope.update = function () {
        var names = [];
        var counts = [];
        var numbers = [];
        for (var i = 0; i < $scope.indent.reserve.length; i++) {
            var name = String($scope.reserve[i].name);
            var count = String($scope.reserve[i].count);
            var number = String($scope.reserve[i].number);
            names.push(name);
            counts.push(count);
            numbers.push(number);
        }
        updateFirstTime();
        //count订菜，number上菜
        var param = {"id": $scope.indent.id, "name": names, "count": counts, "number": numbers, "table": $scope.indent.tableId,
            "reminderNumber": $scope.indent.reminderNumber, "price": $scope.indent.price, "time": $scope.indent.firstTime};
        $http.post('/indent/update', $.param(param), {headers: {'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8'}})
                .success(function (data) {
                    if (data == "success") {
                        $state.go('/underway');
                        alert("修改成功");
                    } else {
                        $state.go('/underway');
                        alert("修改失败");
                    }
                }).error(function(){
                    $state.go('/underway');
                });
        $state.go('/underway');
    };
    //利用id不同来实现用jquery控制spinner，reserve中对象的属性有count,number,name,id
});
