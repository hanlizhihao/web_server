app.controller('UnderwayCarryController', function ($scope, $http, $rootScope, $state, $stateParams) {
            //用于初始化，一开始不想显示的图标
            $scope.vipFalse = false;
            $scope.vipTrue = false;
            $scope.vipText=false;
            var indents=$rootScope.indents;
            var id = Number($stateParams.id);
            for (var i = 0; i < indents.length; i++) {
                if (indents[i].id == id) {
                    $scope.reserve = indents[i].reserve;
                    $scope.indent= indents[i];
                    break;
                }
            }
            /**
             * 用于获取每行菜品的价格，向$scope.reserve[i]中添加新的属性,price，用于代表每行菜品的价格
             * */
            function getPerGreensPrice() {
                for (var i = 0; i < $scope.reserve.length; i++) {
                    for (var y = 0; y < $rootScope.findMenu.length; y++) {
                        if ($scope.reserve[i].name == $rootScope.findMenu[y].name) {
                            //通过count统计订单的价格
                            $scope.reserve[i].price = $scope.reserve[i].count * $rootScope.findMenu[y].price;
                            break;
                        }
                    }
                }
            }
            getPerGreensPrice();
            /**
             * 用于查找菜单，返回订单菜单的总价,id为被更改的菜的id
             */
            $scope.getPrice = function getPrice(id) {
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
                getPerGreensPrice();
            };
            $scope.telephone = String();
            //验证手机号是否是会员
            $scope.verify = function () {
                var vip = {"telephone": $scope.telephone};
                $http.post('/vip/validate', $.param(vip), {headers: {'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8'}})
                        .success(function (data) {
                            if ("success" == data) {
                                $scope.vipTrue = true;
                                $scope.vipFalse=false;
                                $scope.vipText=true;
                                $scope.validateText="该号码是会员";
                            } else {
                                $scope.vipFalse = true;
                                $scope.vipTrue=false;
                                $scope.vipText=true;
                                $scope.validateText="该号码不是是会员";
                            }
                        }).error();
            };
            $scope.discount = function () {
                var discount = Number(document.getElementById("discount").value);
                var price=Number(0);
                price= $scope.indent.price * (discount * 0.1);
                price=Math.round(price);
                console.log(price);
                $scope.indent.price=price;
            };
            $scope.carry = function (id) {
                var indentStyle = {};
                indentStyle.id = id;
                indentStyle.style = 1;
                //若不是会员，结账时将自动加入会员
                if($scope.vipFalse){
                    $http.post('/indent/style',
                            $.param({id: indentStyle.id, style: indentStyle.style,vip:$scope.telephone}),
                            {headers: {'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8'}}
                    ).success(function (data) {
                        if (data == "success") {
                            $state.go('/underway');
                        } else {
                            alert("取消订单失败");
                        }
                    }).error(function (a) {
                        alert("服务器发生错误");
                    });
                }else{
                    $http.post('/indent/style',
                            $.param({id: indentStyle.id, style: indentStyle.style}),
                            {headers: {'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8'}}
                    ).success(function (data) {
                        if (data == "success") {
                            $state.go('/underway');
                        } else {
                            alert("取消订单失败");
                        }
                    }).error(function (a) {
                        alert("服务器发生错误");
                    });
                }
            };
        });