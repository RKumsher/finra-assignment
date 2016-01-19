'use strict';

angular.module('finraAssignmentApp')
    .controller('PhoneNumberController', function ($scope, PhoneNumber) {
        $scope.phoneNumbers = [];
        $scope.authorities = ["ROLE_USER", "ROLE_ADMIN"];

        $scope.phoneNumber;
        $scope.page = 1;
        $scope.generatePhoneNumbers = function () {
            PhoneNumber.query({phoneNumber: $scope.phoneNumber, page: $scope.page - 1, size: 10}, function (result, headers) {
                $scope.totalItems = headers('X-Total-Count');
                $scope.phoneNumbers = result;
            });
        };

        $scope.loadPage = function (page) {
            $scope.page = page;
            $scope.generatePhoneNumbers();
        };

    });
