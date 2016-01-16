 'use strict';

angular.module('finraAssignmentApp')
    .factory('notificationInterceptor', function ($q, AlertService) {
        return {
            response: function(response) {
                var alertKey = response.headers('X-finraAssignmentApp-alert');
                if (angular.isString(alertKey)) {
                    AlertService.success(alertKey, { param : response.headers('X-finraAssignmentApp-params')});
                }
                return response;
            }
        };
    });
