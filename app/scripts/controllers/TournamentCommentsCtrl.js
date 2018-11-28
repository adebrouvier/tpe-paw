define(['tpePaw', 'services/titleService', 'directives/tournamentInfo', 'services/apiService'], function (tpePaw) {

    'use strict';
    tpePaw.controller('TournamentCommentsCtrl', function ($scope, $routeParams, $location, titleService, apiService, AuthService) {

        titleService.setTitle('Versus');

        $scope.tournamentId = $routeParams.id;
        $scope.tournament = {};
        $scope.loggedUser = AuthService.currentUser() ? AuthService.currentUser().username : undefined;

        /* Get tournament */
        apiService.get('/tournaments/' + $scope.tournamentId)
            .then(function successCallback(response) {
                $scope.tournament = response.data;
                titleService.setTitle($scope.tournament.name + ' - Versus');
            }, function errorCallback(response) {
                $location.url('/404');
        });

        /* Get tournament comments*/
        apiService.get('/tournaments/' + $scope.tournamentId + '/comments')
            .then(function successCallback(response) {
                $scope.comments = response.data;
                titleService.setTitle($scope.tournament.name + ' - Versus');
            });

        $scope.commentForm = {};
        $scope.addComment = function() {
            if ($scope.commentForm.$valid) {
                apiService.post('/tournaments/' + $scope.tournamentId + '/comments', $scope.commentForm)
                    .then(function successCallback(response) {
                        console.log('Added comment');
                }, function errorCallback(response) {
                    console.log('Comment ERROR');
                });
            }
        };
    });
});
