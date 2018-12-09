'use strict';
define(['tpePaw', 'services/titleService', 'services/apiService', 'directives/gameAutocomplete'], function(tpePaw) {

    tpePaw.controller('TournamentCtrl', function($scope, $filter, titleService, $location, apiService) {

        titleService.setTitle($filter('translate')('TOURNAMENT_TITLE') + ' - Versus');

    	$scope.tournamentForm = {};

        $scope.processForm = function () {
            if ($scope.tournamentForm.$valid) {
                apiService.post('/tournaments', $scope.tournamentForm)
                    .then(function successCallback(response) {
                        console.log('Successfully created tournament');
                        $location.path('/tournament/' + response.data.id);
                    }, function errorCallback(response) {
                        console.log('Tournament creation ERROR');
                });
            }else {
                console.log('Invalid form');
            }
        };
    });
});
