'use strict';

angular.module('finraAssignmentApp')
    .factory('PhoneNumber', function ($resource) {
        return $resource('api/phonenumbers', {}, {
            'query': {method: 'GET', isArray: true}
        });
    });
