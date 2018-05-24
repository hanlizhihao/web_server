app.controller('BillController', function ($scope, $rootScope, $http, $state) {
    $scope.bills = [];
    $scope.addBillReason = String();
    $scope.addBillPrice = String();
    $scope.addBillRemark = String();
    $scope.addBillTime = String();
    $http.get('/bills/1', {}).success(function (data) {
        if (data[0].phoneNumber == 404) {
            alert("服务器获取数据失败");
        } else {
            $scope.bills = data;
            $rootScope.bills = data;
        }
    }).error(function () {
        alert("获取数据失败");
    });
    $scope.addBill = function () {
        $("#billForm").validate({
            rules: {
                billReason: {
                    required: true,
                    maxlength: 40
                },
                billPrice: {
                    required: true
                },
                billRemark: {
                    maxlength: 500,
                }
            },
            onkeyup: false,
            focusCleanup: true,
            success: "valid",
            submitHandler: function (form) {

            }
        });
        var billModel = {
            name: $scope.addBillReason,
            price: $scope.addBillPrice,
            comment: $scope.addBillRemark,
            occurrenceTime: $scope.addBillTime
        };
        $http.post('/bill/add', $.param(billModel), {headers: {'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8'}})
            .success(function (data) {
                if (data == "success") {
                    $http.get('/bills/1', {}).success(function (data) {
                        if (data[0].name == 404) {
                            alert("服务器获取数据失败");
                        } else {
                            $scope.bills = data;
                            $rootScope.bills = data;
                        }
                    }).error(function () {
                        alert("获取数据失败");
                    });
                } else {
                    alert("添加账单信息失败，服务器存储数据异常");
                }
            }).error(function () {
            alert("添加账单信息失败，网络连接异常");
        });
        $("#modal-demo").modal("hide");
    };
    $scope.deleteBill = function (id) {
        var param = String(id);
        param = "/bill/delete/" + param;
        $http.get(param, {}).success(function (data) {
            if ("success" == data) {
                for (var i = 0; i < $scope.bills.length; i++) {
                    if ($scope.bills[i].id == id) {
                        $rootScope.bills.splice(i, 1);
                        $scope.bills = $rootScope.bills;
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
    stompUpdateBill($scope, $rootScope, $http);
});
app.controller('BillDetailsController', function ($state, $scope, $rootScope, $http, $stateParams) {
    var id = Number($stateParams.id);
    var bills = $rootScope.bills;
    for (var i = 0; i < bills.length; i++) {
        if (bills[i].id == id) {
            $scope.bill = bills[i];
            break;
        }
    }
    $scope.billReason = String($scope.bill.name);
    $scope.billPrice = Number($scope.bill.price);
    $scope.billRemark = String($scope.bill.comment);
    $scope.occurrenceTime = String($scope.bill.occurrenceTime);
    $scope.recordTime = String($scope.bill.recordTime);
    if ($scope.bill.price == null) {
        $scope.bill.price = Number(0);
    }
    $scope.updateBill = function () {
        var phone = String($scope.phoneNumber);
        var billModel = {
            "id": id, "name": $scope.billReason, "price": $scope.billPrice,
            "comment": $scope.billRemark, "occurrenceTime": $scope.occurrenceTime, "recordTime": $scope.recordTime
        };
        $http.post('/bill/update', $.param(billModel), {headers: {'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8'}})
            .success(function (data) {
                if (data == "success") {
                    $state.go('bill');
                    alert("更新账单信息成功");
                } else {
                    $state.go('bill');
                    alert("更新账单信息失败，服务器存储数据异常");
                }
            }).error(function () {
            $state.go('bill');
            alert("更新账单信息失败，网络连接异常");
        });
        $state.go('bill');
    };
});