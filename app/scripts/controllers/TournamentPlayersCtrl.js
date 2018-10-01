define(['tpePaw', 'services/titleService', 'directives/tournamentInfo', 'services/apiService', 'services/authService'], function (tpePaw) {

    'use strict';
    tpePaw.controller('TournamentPlayersCtrl', function ($scope, $routeParams, titleService, apiService, AuthService) {

        titleService.setTitle('Versus');

        $scope.tournamentId = $routeParams.id;
        $scope.tournament = {};
        $scope.loggedUser = AuthService.currentUser().username;

        apiService.get('/tournaments/' + $scope.tournamentId)
            .then(function successCallback(response) {
                $scope.tournament = response.data;
                titleService.setTitle($scope.tournament.name + ' - Versus');
            }, function errorCallback(response) {
                $location.url('/404');
        });

        this.addPlayer = function(){

            apiService.post('/tournaments/'  + $scope.tournamentId + '/players')
                .then(function successCallback(response) {

            }, function errorCallback(response) {
                $location.url('/404');
            });
        };
    });
});