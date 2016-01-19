'use strict';

angular.module('finraAssignmentApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('phone-number', {
                parent: 'admin',
                url: '/phonenumbers',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'finra_assignment'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/phonenumber/phone-number.html',
                        controller: 'PhoneNumberController'
                    }
                },
                resolve: {

                }
            });
    });
