app.controller('UserController', function ($scope, $rootScope, $http) {
    $scope.users = [];
    $rootScope.users = [];
    $scope.addName = String();
    $scope.addUsername = String();
    $scope.addPassword = String();
    $http.get('/users', {}).success(function (data) {
        if (data[0].name == "404") {
            alert("服务器错误，读取数据失败");
        } else {
            $scope.users = data;
            $rootScope.users = data;
            //对用户数据类型进行转换
            for (var i = 0; i < $scope.users.length; i++) {
                $scope.users[i].styleString = String(convertStyle($scope.users[i].style));
                $rootScope.users[i].styleString = $scope.users[i].styleString;
            }
        }
    }).error(function () {
        alert("网络连接错误，获取数据失败");
    });
    //标记用户类型
    $scope.checked = function (sign) {
        var style = String(sign);
        $scope.addStyle = style == "0" ? "0" : "1";
    };
    var userModel={};
    $scope.addUser = function () {
        if ($scope.addStyle == "1") {
            userModel = {"name": $scope.addName, "username": $scope.addUsername, "password": $scope.addPassword, "style": 1};
        } else if ($scope.addStyle == "0") {
            userModel = {"name": $scope.addName, "username": $scope.addUsername, "password": $scope.addPassword, "style": 0};
        }
        $http.post('/user/add', $.param(userModel), {headers: {'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8'}})
                .success(function (data) {
                    if (data == "success") {
                        $http.get('/users', {}).success(function (data) {
                            if (data[0].name == 404) {
                                alert("服务器获取数据失败");
                            } else {
                                $scope.users = data;
                                $rootScope.users = data;
                            }
                        }).error(function () {
                            alert("获取数据失败");
                        });
                    } else {
                        alert("添加用户信息失败，服务器存储数据异常");
                    }
                }).error(function () {
            alert("添加用户信息失败，网络连接异常");
            });
        $("#modal-demo").modal("hide");
    };
    $scope.deleteUser = function (id) {
        var param = String(id);
        param = "/user/delete/" + param;
        $http.get(param, {}).success(function (data) {
            if ("success" == data) {
                for (var i = 0; i < $scope.users.length; i++) {
                    if ($scope.users[i].id == id) {
                        $scope.users.splice(i, 1);
                        $rootScope.users.splice(i, 1);
                        break;
                    }
                }
            } else {
                alert("删除失败");
            }
        }).error(function (a) {
            alert("服务器发生错误");
        });
    };
});
    app.controller('UserDetailsController', function ($scope, $rootScope, $http, $stateParams,$state) {
        $scope.showDetail = false;
        var id = Number($stateParams.id);
        for (var i = 0; i < $rootScope.users.length; i++) {
            if ($rootScope.users[i].id == id) {
                $scope.user = $rootScope.users[i];
                break;
            }
        }
        //获取签到信息
        var param=String(id);
        param="/signs/"+param;
        $http.get(param,{}).success(function (data) {
            angular.forEach(data, function (value, key) {
               value.timeString = new Date(value.time).Format("yyyy-MM-dd");
               value.signInTimeString = new Date(value.signInTime).toLocaleString();
               value.signOutTimeString = new Date(value.signOutTime).toLocaleString();
            });
            $scope.signs = data;
        });
        $scope.name = String($scope.user.name);
        $scope.username =String($scope.user.username);
        $scope.password =String($scope.user.password);
        $scope.joinTime = String($scope.user.joinTime);
        $scope.style=String($scope.user.style);//身份
        //点击以后，改变style，style用于传递到服务器
        $scope.checked=function(sign){
            var style=String(sign);
            $scope.style=style=="0"?"0":"1";
        };
        $scope.updateUser = function () {
            var user={};
            if ($scope.style == "1") {
                user = {"id": $scope.user.id, "name": $scope.name, "username": $scope.username, "password": $scope.password, "style": "1","joinTime"
                    :$scope.joinTime};
            } else if ($scope.style == "0") {
                user = {"id": $scope.user.id, "name": $scope.name, "username": $scope.username, "password": $scope.password, "style": "0","joinTime"
                    :$scope.joinTime};
            }
            $http.post('/user/update', $.param(user), {headers: {'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8'}})
                .success(function (data) {
                    if (data == "success") {
                        $state.go('user');
                        alert("更改信息成功");
                    } else {
                        $state.go('user');
                        alert("添加用户信息失败，服务器存储数据异常");
                    }
                }).error(function () {
                $state.go('user');
                alert("添加用户信息失败，网络连接异常");
            });
            $state.go('user');
        };
        $("#joinTime").datetimepicker({
            format: 'yyyy-mm-dd',
            minView: "month",
            todayBtn:  1,
            autoclose: 1,
            endDate : new Date()
        }).on('hide',function(e) {
            //此处可以触发日期校验。
        });
        $scope.showSignDetail = function (id) {
            var param=String(id);
            param="/sign/details/"+param;
            $http.get(param,{}).success(function (data) {
                angular.forEach(data, function (value, key) {
                    value.leaveTime = new Date(value.leaveBeginTime).toLocaleString();
                    value.returnTime = new Date(value.leaveEndTime).toLocaleString();
                });
                $scope.appLeaveList = data;
                $scope.showDetail = true;
            });
        };
        $scope.cancel = function () {
            $scope.showDetail = false;
        }
    });
