'use strict';
define(['tpePaw', 'services/titleService', 'services/apiService'], function(tpePaw) {

    tpePaw.controller('RegisterCtrl', function($scope, $filter, titleService, apiService) {

        titleService.setTitle($filter('translate')('REGISTER_TITLE') + ' - Versus');

    	$scope.registerForm = {};

    	$scope.processForm = function () {

            apiService.post('/users', $scope.registerForm)
                .then(function successCallback(response) {
                    console.log('Successfully registered user');
                }, function errorCallback(response) {
                    console.log('Register ERROR');
                });
        };
    });
});

