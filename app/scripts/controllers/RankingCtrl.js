define(['tpePaw', 'services/titleService'], function (tpePaw) {

    'use strict';
    tpePaw.controller('RankingCtrl', function ($scope, $http, titleService) {

        titleService.setTitle("Versus - Ranking");

        $scope.rankingForm = {};

        $scope.processForm = function () {

        $http.post('http://localhost:8080/rankings', $scope.rankingForm)
            .then(function successCallback(response) {
                console.log('Successfully created ranking');
            }, function errorCallback(response) {
                console.log('Ranking creation error');
            });
        };

    });
});