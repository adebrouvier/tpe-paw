'use strict';
define(['tpePaw'], function(tpePaw) {

    tpePaw.service('apiService', function($http) {

        var apiUrl = 'http://localhost:8080/';

        this.get = function(resource, params) {
            return $http.get(apiUrl + resource, params);
        }

        this.post = function(resource, params) {
            return $http.post(apiUrl + resource, params);
        }

        this.put = function(resource, params) {
            return $http.put(apiUrl + resource, params);
        }
    });
});