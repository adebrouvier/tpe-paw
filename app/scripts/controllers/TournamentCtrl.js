'use strict';
define(['tpePaw', 'services/titleService', 'directives/gameAutocomplete'], function(tpePaw) {

    tpePaw.controller('TournamentCtrl', function($scope, $http, $filter, titleService, $location) {

        titleService.setTitle($filter('translate')('TOURNAMENT_TITLE') + ' - Versus');

    	$scope.tournamentForm = {};

        $scope.processForm = function () {
            if ($scope.tournamentForm.$valid){
                $http.post('http://localhost:8080/tournaments', $scope.tournamentForm)
                    .then(function successCallback(response) {
                        console.log('Successfully created tournament');
                        $location.path('/tournament/' + response.data.id);
                    }, function errorCallback(response) {
                        console.log('Tournament creation ERROR');
                });
            }else{
                console.log('Invalid form');
            }
        };
    });
});