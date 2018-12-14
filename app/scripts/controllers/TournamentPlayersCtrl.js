define(['tpePaw', 'services/titleService', 'directives/tournamentImage', 'directives/tournamentInfo', 'services/apiService', 'services/authService', 'directives/existingUser'], function (tpePaw) {

    'use strict';
    tpePaw.controller('TournamentPlayersCtrl', function ($scope, $routeParams, $location, titleService, apiService, AuthService) {

        titleService.setTitle('Versus');

        $scope.tournamentId = $routeParams.id;
        $scope.tournament = {};
        $scope.loggedUser = AuthService.currentUser() ? AuthService.currentUser().username : undefined;
        $scope.inscriptions = {};

        $scope.sortableOptions = {
            stop: function(e, ui) {
                $scope.playerOldSeed = ui.item.sortable.index;
                $scope.playerNewSeed = ui.item.sortable.dropindex;

                if ($scope.playerNewSeed != undefined) {
                    $scope.playerOldSeed++;
                    $scope.playerNewSeed++;
                    apiService.post('/tournaments/' + $scope.tournamentId + '/' + $scope.playerOldSeed + '/' + $scope.playerNewSeed)
                        .then(function successCallback(response) {
                          console.log('Swap player');
                          $scope.tournament.players = response.data;
                        }, function errorCallback(response) {
                          console.log('Player add ERROR');
                        });
                }

            },
            axis: 'y',
            cursor: 'move',
            placeholder: 'sortable-placeholder'

        };
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

        $scope.acceptPlayer = function(player) {
            apiService.acceptPlayer($scope.tournamentId, player.id)
            .then(function successCallback(response) {
                    $scope.inscriptions = response.data;
                    $scope.tournament.players.push({
                      id: player.id,
                      name: player.username,
                      username: player.username,
                      seed: tournament.players.length + 1
                    });
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
