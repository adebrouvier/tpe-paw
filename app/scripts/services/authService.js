'use strict';
define(['tpePaw'], function(tpePaw) {

    tpePaw.service('AuthService', function($window, $location, $http) {

        this.currentUser = function() {

            /* TODO: get user from token */

            if ($window.localStorage['currentUser'] != null) {
                return angular.fromJson($window.localStorage['currentUser']);
            }
    
            if ($window.sessionStorage['currentUser'] != null) {
                return angular.fromJson($window.sessionStorage['currentUser']);
            }
        };

        this.loggedIn = function() {
            if ($window.localStorage['currentUser'] != null || $window.sessionStorage['currentUser'] != null) {
                return true;
            }else {
                return false;
            }
        };

        this.login = function(loginForm) {

            $http.post('http://localhost:8080/login', loginForm)
                    .then(function successCallback(response) {

                        if (response.headers('Authorization')) {

                            var currentUser = {
                                username: loginForm.username,
                                token: response.headers('Authorization')
                            };

                            if (loginForm.rememberMe) {
                                $window.localStorage['currentUser'] = angular.toJson(currentUser);
                            }else {
                                $window.sessionStorage['currentUser'] = angular.toJson(currentUser);
                            }
                            $location.path('/');
                            console.log('loggedIn');
                        }
                    }, function errorCallback(response) {
                        console.log('Login ERROR');
            });
        };

        this.logout = function() {
            $window.localStorage.removeItem('currentUser');
			$window.sessionStorage.removeItem('currentUser');
        };
    });
});