'use strict';

angular.module('finraAssignmentApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


