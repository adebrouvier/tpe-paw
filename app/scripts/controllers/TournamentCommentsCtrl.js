define(['tpePaw', 'services/titleService', 'directives/tournamentImage', 'directives/tournamentInfo', 'services/apiService'], function (tpePaw) {

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
    $scope.addComment = function () {
      if ($scope.commentForm.$valid) {
        apiService.post('/tournaments/' + $scope.tournamentId + '/comments', $scope.commentForm)
          .then(function successCallback(response) {
            console.log('Added comment');
            var comment = response.data;
            $scope.comments.push(comment);
            $scope.commentForm.comment = "";
            $scope.commentForm.$setUntouched();
          }, function errorCallback(response) {
            console.log('Comment ERROR');
          });
      }
    };

    $scope.replyForm = {};
    $scope.addReply = function () {
      if ($scope.replyForm.$valid) {
        apiService.post('/tournaments/' + $scope.tournamentId + '/comments/' + $scope.parent.id, $scope.replyForm)
          .then(function successCallback(response) {
            console.log('Added reply');
            $('#replyModal').modal('hide');
            $scope.parent.children.push(response.data);
            $scope.replyForm.reply = "";
            $scope.replyForm.$setUntouched();
          }, function errorCallback(response) {
            console.log('Reply error');
          });
      }
    };

    $scope.openReplyModal = function (parent) {
      $scope.parent = parent;
    };

    $scope.clearModal = function () {
      $scope.replyForm = {};
    };
  });
});
