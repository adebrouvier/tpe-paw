define(['tpePaw', 'services/titleService', 'directives/tournamentImage', 'directives/tournamentInfo', 'services/apiService', 'directives/onFinishRender'], function (tpePaw) {

  'use strict';
  tpePaw.controller('TournamentPageCtrl', function ($scope, $location, $routeParams, $filter, titleService, apiService, AuthService) {

    titleService.setTitle("Versus");

    $scope.tournamentId = $routeParams.id;
    $scope.tournament = {};
    $scope.loggedUser = AuthService.currentUser() ? AuthService.currentUser().username : undefined;
    $scope.signedUp = false;
    $scope.participates = false;

    /**
     * brackets format
     **/
    $scope.matchCount = 1;
    $scope.margin = 0;
    $scope.padding = 15;
    $scope.leftPosition = 0;
    $scope.roundCount = 1;
    $scope.roundSize = 1;

    $scope.tournamentFormat = function () {
      var tournamentContainer = document.getElementById('tournament-container');
      tournamentContainer.setAttribute('class', 'tournament-container');
      var height = ($scope.roundSize * 60 + 44);
      tournamentContainer.setAttribute('style', 'height: ' + height + 'px');
      var match;
      var col;
      for (var i = 0; i < $scope.tournamentSize; i++) {
        match = document.getElementById('match-' + i);

        if ($scope.matchCount === 1) {
          var round = document.createElement('div');
          round.setAttribute('class', 'round');
          round.innerHTML = '';
          tournamentContainer.removeChild(match);
          col = document.createElement('div');
          col.setAttribute('id', 'col-' + $scope.roundCount);
          col.setAttribute('class', 'tournament-col');
          col.setAttribute('style', 'left: ' + $scope.leftPosition + 'px');
          tournamentContainer.appendChild(col);
          col.appendChild(round);
          if ($scope.roundCount !== 1) {
            var margin = $scope.margin + 21;
            match.setAttribute('style', 'margin-top: ' + margin + 'px');
          } else {
            match.setAttribute('style', 'margin-top: ' + 20 + 'px');
          }
          col.appendChild(match);
          if ($scope.roundSize === 1) {
            round.textContent = $filter('translate')('TOURNAMENT_PAGE_FINAL');
          } else if ($scope.roundSize === 2) {
            round.textContent = $filter('translate')('TOURNAMENT_PAGE_SEMIFINAL');
          } else {
            round.textContent = $filter('translate')('TOURNAMENT_PAGE_ROUND') + $scope.roundCount;
          }
        } else {
          match.setAttribute('style', 'padding-top: ' + $scope.padding + 'px');
          col = document.getElementById('col-' + $scope.roundCount);
          col.appendChild(match);
        }
        if ($scope.roundSize !== 1) {
          if ($scope.matchCount % 2 === 0) {
            var rightBranch = document.createElement('div');
            rightBranch.setAttribute('class', 'match-right-branch');
            match.appendChild(rightBranch);
          } else if ($scope.matchCount === 1) {
            var leftBranch = document.createElement('div');
            leftBranch.setAttribute('class', 'match-left-branch');
            var height = $scope.padding + 23;
            leftBranch.setAttribute('style', 'top: 19.5px; height: ' + height + 'px');
            var nextBranch = document.createElement('div');
            nextBranch.setAttribute('class', 'match-next-branch');
            var top = $scope.padding / 2 + 43;
            nextBranch.setAttribute('style', 'top: ' + top + 'px');
            match.appendChild(leftBranch);
            match.appendChild(nextBranch);
          } else {
            var leftBranch = document.createElement('div');
            leftBranch.setAttribute('class', 'match-left-branch');
            var top = $scope.padding + 19;
            var height = $scope.padding + 24;
            leftBranch.setAttribute('style', 'top: ' + top + 'px;height: ' + height + 'px');
            var nextBranch = document.createElement('div');
            nextBranch.setAttribute('class', 'match-next-branch');
            top = $scope.padding / 2 + $scope.padding + 43;
            nextBranch.setAttribute('style', 'top: ' + top + 'px');
            match.appendChild(leftBranch);
            match.appendChild(nextBranch);
          }
        }
        $scope.modifyVariables();
      }
    };

    $scope.modifyVariables = function () {
      if ($scope.matchCount === $scope.roundSize) {
        $scope.matchCount = 0;
        $scope.roundSize = $scope.roundSize / 2;
        $scope.margin = ($scope.padding + 44) / 2 + $scope.margin;
        $scope.padding = 2 * $scope.margin + 15;
        $scope.leftPosition = $scope.leftPosition + 250;
        $scope.roundCount = $scope.roundCount + 1;
      }

      $scope.matchCount = $scope.matchCount + 1;
    };


    apiService.get('/tournaments/' + $scope.tournamentId)
      .then(function successCallback(response) {
        $scope.tournament = response.data;
        titleService.setTitle($scope.tournament.name + ' - Versus');
        var participates = $filter('filter')($scope.tournament.players, {'username': $scope.loggedUser});
        if (participates.length >= 1) {
          $scope.participates = true;
        }
        $scope.roundSize = Math.ceil($scope.tournament.matches.length / 2);
        $scope.tournamentSize = $scope.tournament.matches.length;
      }, function errorCallback(response) {
        $location.url('/404');
      });

    $scope.matchForm = {};

    $scope.openScoreModal = function (match) {
      $scope.match = match;
      $scope.matchForm.homeResult = match.homePlayerScore;
      $scope.matchForm.awayResult = match.awayPlayerScore;
    };

    $scope.clearModal = function () {
      $scope.matchForm = {};
    };

    $scope.updateScore = function () {
      apiService.post('/tournaments/' + $scope.tournamentId + '/matches/' + $scope.match.id, $scope.matchForm)
        .then(function successCallback(response) {
          console.log('score updated');
          $scope.updateMatches(response.data);
          $('#scoreModal').modal('hide');
          $scope.matchForm = {};
        }, function errorCallback(response) {
          console.log('score error');
        });
    };

    $scope.updateMatches = function (newMatches) {
      var length = newMatches.length;
      for (var i = 0; i < length; i++) {
        if (newMatches[i].homePlayer !== undefined) {
          $scope.tournament.matches[i].homePlayer = newMatches[i].homePlayer;
        } else {
        }
        if (newMatches[i].awayPlayer !== undefined) {
          $scope.tournament.matches[i].awayPlayer = newMatches[i].awayPlayer;
        } else {
          delete $scope.tournament.matches[i].awayPlayer;
        }
        $scope.tournament.matches[i].homePlayerScore = newMatches[i].homePlayerScore;
        $scope.tournament.matches[i].awayPlayerScore = newMatches[i].awayPlayerScore;
      }
    };

    $scope.editable = function (match) {

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

    $scope.endTournament = function () {
      apiService.post('/tournaments/' + $scope.tournamentId + '/end')
        .then(function successCallback(response) {
          console.log('Ended tournament');
          $scope.tournament.status = 'FINISHED';
        }, function errorCallback(response) {
          console.log('End error');
        });
    };

    apiService.getInscriptions($scope.tournamentId)
      .then(function successCallback(response) {
        $scope.inscriptions = response.data;
        var signedUp = $filter('filter')($scope.inscriptions, {'username': $scope.loggedUser});
        if (signedUp.length >= 1) {
          $scope.signedUp = true;
        }
      }, function errorCallback(response) {
        console.log('Cant retrieve inscriptions');
      });

    $scope.joinRequest = function () {
      apiService.requestJoin($scope.tournamentId)
        .then(function successCallback(response) {
          console.log('Request submitted');
          $scope.signedUp = true;
        }, function errorCallback(response) {
          console.log('Request error');
        });
    };
  });
});
