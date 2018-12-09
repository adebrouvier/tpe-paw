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

        this.delete = function(resource) {

            var config = {};

            if (sessionService.loggedIn()) {
                config.headers = {};
                config.headers['Authorization'] = sessionService.currentUser().token;
            }

            return $http.delete(apiUrl + resource, config);
        };

        this.getTournament = function(tournamentId){
            var url = '/tournaments/' + tournamentId;
            return this.get(url);
        };

        this.getInscriptions = function(tournamentId) {
            var url = '/tournaments/' + tournamentId + '/inscriptions';
            return this.get(url);
        };

        this.requestJoin = function(tournamentId) {
            var url = '/tournaments/' + tournamentId + '/inscriptions';
            return this.post(url);
        };

        this.acceptPlayer = function(tournamentId, playerId) {
            var url = '/tournaments/' + tournamentId + '/inscriptions/' + playerId;
            return this.post(url);
        };

        this.rejectPlayer = function(tournamentId, playerId) {
            var url = '/tournaments/' + tournamentId + '/inscriptions/' + playerId;
            return this.delete(url);
        };

        this.updateMatchData = function(tournamentId, matchId, form) {
            var url = '/tournaments/' + tournamentId + '/match/' + matchId + '/details';
            return this.post(url, form);
        };

        this.getRanking = function(id) {
          var url = '/rankings/' + id;
          return this.get(url);
        };

        this.createRanking = function(form) {
          var url = '/rankings';
          return this.post(url, form);
        };

        this.addTournament = function(rankingId, form) {
          var url = '/rankings/' + rankingId + '/tournaments';
          return this.post(url, form);
        };

        this.removeTournament = function(rankingId, tournamentId) {
          var url = '/rankings/' + rankingId + '/tournaments/' + tournamentId;
          return this.delete(url);
        };

        this.validTournament = function(rankingId, tournamentName){
          var url = '/rankings/' + rankingId + '/tournament/' + tournamentName;
          return this.get(url);
        };
    });
});
