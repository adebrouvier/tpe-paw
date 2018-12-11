'use strict';
define(['tpePaw', 'services/sessionService'], function(tpePaw) {

    tpePaw.service('apiService', function($http, sessionService) {

        var apiUrl = 'http://localhost:8080';

        this.get = function(resource, params) {

            var config = {params: params}; // Add params

            if (sessionService.loggedIn()) {
                config.headers = {};
                config.headers['Authorization'] = sessionService.currentUser().token;
            }

            return $http.get(apiUrl + resource, config);
        };

        this.post = function(resource, params) {

            var config = {};
            console.log(params);

            if (sessionService.loggedIn()) {
                config.headers = {};
                config.headers['Authorization'] = sessionService.currentUser().token;
            }

            return $http.post(apiUrl + resource, params, config);
        };

        this.put = function(resource, params) {

            var config = {};

            if (sessionService.loggedIn()) {
                config.headers = {};
                config.headers['Authorization'] = sessionService.currentUser().token;
            }

            return $http.put(apiUrl + resource, params, config);
        };

        this.updateUser = function(username, formData, metadata) {
          return $http.put(apiUrl + '/users/' + username, formData, metadata)
        };
    });
});
