define(['tpePaw', 'services/titleService', 'directives/tournamentInfo', 'services/apiService'], function (tpePaw) {

    'use strict';
    tpePaw.controller('TournamentPageCtrl', function ($scope, $location, $routeParams, titleService, apiService, AuthService) {

        titleService.setTitle("Versus");

        $scope.tournamentId = $routeParams.id;
        $scope.tournament = {};
        $scope.loggedUser = AuthService.currentUser() ? AuthService.currentUser().username : undefined;

        apiService.get('/tournaments/' + $scope.tournamentId)
            .then(function successCallback(response) {
                $scope.tournament = response.data;
                titleService.setTitle($scope.tournament.name + ' - Versus');
            }, function errorCallback(response) {
                $location.url('/404');
            });

        $scope.matchForm = {};

        $scope.openScoreModal = function(match) {
            $scope.match = match;
            $scope.matchForm.homeResult = match.homePlayerScore;
            $scope.matchForm.awayResult = match.awayPlayerScore;
        };

        $scope.clearModal = function() {
            $scope.matchForm = {};
        };

        $scope.updateScore = function() {
            apiService.post('/tournaments/' + $scope.tournamentId + '/matches/' + $scope.match.id, $scope.matchForm)
            .then(function successCallback(response) {
                console.log('score updated');
                $scope.tournament.matches = response.data;
                $('#scoreModal').modal('hide');
                $scope.matchForm = {};
            }, function errorCallback(response) {
                console.log('score error');
            });
        };

        $scope.editable = function(match) {

            if (match === undefined) {
                return false;
            }

            if (!match.hasOwnProperty('homePlayer') || !match.hasOwnProperty('awayPlayer')) {
                return false;
            }

            return $scope.tournament.status === 'STARTED' &&
            $scope.loggedUser === $scope.tournament.creator.username &&
            match.homePlayer.id !== -1 &&
            match.awayPlayer.id !== -1;
        };

        $scope.endTournament = function() {
            apiService.post('/tournaments/' + $scope.tournamentId + '/end')
            .then(function successCallback(response) {
                console.log('Ended tournament');
                $scope.tournament.status = 'FINISHED';
            }, function errorCallback(response) {
                console.log('End error');
            });
        };
    });
});
