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
    $scope.hasParticipatesData = true;
    $scope.hasCreatesData = true;
    $scope.hasRankingsData = true;
    $http.get('http://localhost:8080/users/' + $scope.userId)
      .then(function successCallback(response) {
        $scope.user = response.data;
        if ($scope.user.hasOwnProperty('participates')) {
          var length = response.data.participates.length;
          for (var i = 0; i < length ; i++) {
            $scope.participates.push(response.data.participates[i]);
          }
        }
        if ($scope.user.hasOwnProperty('creates')) {
          var pLength = response.data.creates.length;
          for (var i = 0; i < pLength; i++) {
            $scope.creates.push(response.data.creates[i]);
          }
        }
        if ($scope.user.hasOwnProperty('rankings')) {
          var pLength = response.data.rankings.length;
          for (var i = 0; i < pLength; i++) {
            $scope.rankings.push(response.data.rankings[i]);
          }
        }
        titleService.setTitle($scope.user.username + ' - Versus');
      }, function errorCallback(response) {
        $location.path('#/404');
      });

    $scope.loadParticipatedTournaments = function () {
      if (document.getElementById('participates').classList.item(2) === 'active') {
        $http.get('http://localhost:8080/users/' + $scope.userId + '/participated-tournaments?page=' + $scope.userParticipatedTournaments)
          .then(function successCallback(response) {
            if (response.data.hasOwnProperty('participates')) {
              var pLength = response.data.participates.length;
              for (var i = 0; i < pLength; i++) {
                $scope.participates.push(response.data.participates[i]);
              }
              $scope.hasParticipatesData = true;
            } else {
              $scope.hasParticipatesData = false;
            }

          }, function errorCallback(response) {

          });
        if ($scope.hasParticipatesData) {
          $scope.userParticipatedTournaments += 1;
        }
        console.log($scope.userParticipatedTournaments);
      }

    };
    $scope.loadCreatedTournaments = function () {
      if (document.getElementById('creates').classList.item(2) === 'active') {
        $http.get('http://localhost:8080/users/' + $scope.userId + '/created-tournaments?page=' + $scope.userCreatedTournaments)
          .then(function successCallback(response) {
            if (response.data.hasOwnProperty('creates')) {
              var pLength = response.data.creates.length;
              for (var i = 0; i < pLength; i++) {
                $scope.creates.push(response.data.creates[i]);
              }
              $scope.hasCreatesData = true;
            } else {
              $scope.hasCreatesData = false;
            }
          }, function errorCallback(response) {

          });
        if ($scope.hasCreatesData) {
          $scope.userCreatedTournaments += 1;
        }
      }

    };
    $scope.loadCreatedRankings = function () {
      if (document.getElementById('rankings').classList.item(2) === 'active') {
        $http.get('http://localhost:8080/users/' + $scope.userId + '/created-rankings?page=' + $scope.userCreatedRankings)
          .then(function successCallback(response) {
            if (response.data.hasOwnProperty('rankings')) {
              var pLength = response.data.rankings.length;
              for (var i = 0; i < pLength; i++) {
                $scope.rankings.push(response.data.rankings[i]);
              }
              $scope.hasRankingsData = true;
            } else {
              $scope.hasRankingsData = false;
            }
          }, function errorCallback(response) {

          });
        if ($scope.hasRankingsData) {
          $scope.userCreatedRankings += 1;
        }
      }

    };

  });

});
