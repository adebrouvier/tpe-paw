'use strict';
define(['tpePaw', 'services/apiService', 'services/sessionService'], function(tpePaw) {

    tpePaw.service('AuthService', function($location, apiService, sessionService) {

        this.currentUser = sessionService.currentUser;

        this.loggedIn = sessionService.loggedIn;

        this.login = function(loginForm) {

            loginForm.rememberMe = undefined; // remove remember me field

            apiService.post('/login', loginForm)
                    .then(function successCallback(response) {

                        if (response.headers('Authorization')) {

                            var currentUser = {
                                username: loginForm.username,
                                token: response.headers('Authorization')
                            };

                            if (loginForm.rememberMe) {
                                sessionService.setToken(currentUser, true);
                            }else {
                                sessionService.setToken(currentUser, false);
                            }
                            $location.path('/');
                        }
                    }, function errorCallback(response) {
                        console.log(response);
                        console.log('Login ERROR');
            });
        };

        this.logout = function() {
            sessionService.logout();
        };
    });
});