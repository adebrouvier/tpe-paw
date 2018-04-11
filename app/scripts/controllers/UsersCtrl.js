'use strict';
define(['tpePaw', 'services/titleService'], function(tpePaw) {

  tpePaw.controller('UsersCtrl', function($scope, $http, $location, $routeParams, titleService) {

    $scope.user = {};
    $scope.participates = [];
    $scope.creates = [];
    $scope.rankings = [];
    $scope.userId = $routeParams.id;
    $scope.userParticipatedTournaments = 1;
    $scope.userCreatedTournaments = 1;
    $scope.userCreatedRankings = 1;
    $http.get('http://localhost:8080/users/' + $scope.userId)
      .then(function successCallback(response) {
        $scope.user = response.data;
        var length = response.data.participates.length;
        for(var i = 0; i < length ; i++) {
          $scope.participates.push(response.data.participates[i]);
        }
        titleService.setTitle($scope.user.username + ' - Versus');
      }, function errorCallback(response) {
        $location.path('#/404');
      });

    $scope.loadParticipatedTournaments = function () {
      $http.get('http://localhost:8080/users/' + $scope.userId + '/participated-tournaments?page=' + $scope.userParticipatedTournaments)
        .then(function successCallback(response) {
          var pLength = response.data.participates.length;
          for(var i = 0; i < pLength ; i++) {
            $scope.participates.push(response.data.participates[i]);
          }

        }, function errorCallback(response) {

        });
      $scope.userParticipatedTournaments += 1;

    };
    $scope.loadCreatedTournaments = function () {
      $http.get('http://localhost:8080/users/' + $scope.userId + '/created-tournaments?page=' + $scope.userCreatedTournaments)
        .then(function successCallback(response) {
          var pLength = response.data.creates.length;
          for(var i = 0; i < pLength ; i++) {
            $scope.creates.push(response.data.creates[i]);
          }
          console.log(response);
        }, function errorCallback(response) {

        });
      $scope.userCreatedTournaments += 1;

    };
    $scope.loadCreatedRankings = function () {
      $http.get('http://localhost:8080/users/' + $scope.userId + '/created-rankings?page=' + $scope.userCreatedRankings)
        .then(function successCallback(response) {
          var pLength = response.data.rankings.length;
          for(var i = 0; i < pLength ; i++) {
            $scope.rankings.push(response.data.rankings[i]);
          }
        }, function errorCallback(response) {

        });
      $scope.userCreatedRankings += 1;

    };

  });

});
