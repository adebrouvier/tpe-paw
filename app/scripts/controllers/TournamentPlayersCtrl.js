define(['tpePaw', 'services/titleService', 'directives/tournamentInfo', 'services/apiService', 'services/authService'], function (tpePaw) {

    'use strict';
    tpePaw.controller('TournamentPlayersCtrl', function ($scope, $routeParams, $location, titleService, apiService, AuthService) {

        titleService.setTitle('Versus');

        $scope.tournamentId = $routeParams.id;
        $scope.tournament = {};
        $scope.loggedUser = AuthService.currentUser() ? AuthService.currentUser().username : undefined;
        $scope.inscriptions = {};

        apiService.get('/tournaments/' + $scope.tournamentId)
            .then(function successCallback(response) {
                $scope.tournament = response.data;
                titleService.setTitle($scope.tournament.name + ' - Versus');
            }, function errorCallback(response) {
                $location.url('/404');
        });

        apiService.getInscriptions($scope.tournamentId)
            .then(function successCallback(response) {
                $scope.inscriptions = response.data;
            }, function errorCallback(response) {
                console.log('Cant retrieve inscriptions');
        });

        $scope.playerForm = {};

        $scope.addPlayer = function() {

            if ($scope.playerForm.$valid) {

                apiService.post('/tournaments/' + $scope.tournamentId + '/players', $scope.playerForm)
                    .then(function successCallback(response) {
                        console.log('Added player');
                        $scope.tournament.players = response.data;
                }, function errorCallback(response) {
                    console.log('Player add ERROR');
                });
            }else {
                console.log('Invalid form');
            }
        };

        $scope.removePlayer = function(playerId) {
            // TODO: replace with DELETE
            apiService.post('/tournaments/' + $scope.tournamentId + '/players/' + playerId)
            .then(function successCallback(response) {
                    console.log('Deleted player');
                    $scope.tournament.players = response.data;
                }, function errorCallback(response) {
                    console.log('Cannot delete player');
            });
        };

        $scope.bracketForm = {};
        $scope.generateBracket = function() {
            apiService.post('/tournaments/' + $scope.tournamentId + '/generate')
            .then(function successCallback(response) {
                $location.url('/tournament/' + $scope.tournamentId);
                }, function errorCallback(response) {
                    console.log('Cannot generate bracket');
            });
        };

        $scope.acceptPlayer = function(playerId) {
            apiService.acceptPlayer($scope.tournamentId, playerId)
            .then(function successCallback(response) {
                    $scope.inscriptions = response.data;
                }, function errorCallback(response) {
                    console.log('Can not accept join request');
            });
        };

        $scope.rejectPlayer = function(playerId) {
            apiService.rejectPlayer($scope.tournamentId, playerId)
            .then(function successCallback(response) {
                    $scope.inscriptions = response.data;
                }, function errorCallback(response) {
                    console.log('Can not reject join request');
            });
        };
    });
});
