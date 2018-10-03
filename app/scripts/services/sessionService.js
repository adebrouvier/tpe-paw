'use strict';
define(['tpePaw'], function(tpePaw) {

    tpePaw.service('sessionService', function($window) {

        this.currentUser = function() {

            /* TODO: get user from token */

            if ($window.localStorage.currentUser != null) {
                return angular.fromJson($window.localStorage.currentUser);
            }

            if ($window.sessionStorage.currentUser != null) {
                return angular.fromJson($window.sessionStorage.currentUser);
            }
        };

        this.getToken = function () {
            return $window.sessionStorage['currentUser'] || $window.localStorage.currentUser;
        };

        this.setToken = function(currentUser, persist) {
            if (persist) {
                $window.localStorage.currentUser = angular.toJson(currentUser);
            }else {
                $window.sessionStorage.currentUser = angular.toJson(currentUser);
            }
        };

        this.loggedIn = function() {
            if ($window.localStorage.currentUser != null || $window.sessionStorage.currentUser != null) {
                return true;
            }else {
                return false;
            }
        };

        this.logout = function() {
            $window.localStorage.removeItem('currentUser');
            $window.sessionStorage.removeItem('currentUser');
        };
    });
});