'use strict';

define([], function () {
  return {
    defaultRoutePath: '/',
    routes: {
      '/': {
        templateUrl: 'views/home.html',
        controller: 'HomeCtrl'
      },
      '/register': {
        templateUrl: 'views/register.html',
        controller: 'RegisterCtrl'
      },
      '/login': {
        templateUrl: 'views/login.html',
        controller: 'LoginCtrl'
      },
      '/tournament': {
        templateUrl: 'views/tournament/tournament.html',
        controller: 'TournamentCtrl'
      },
      '/tournament/:id': {
        templateUrl: 'views/tournament/tournament-page.html',
        controller: 'TournamentPageCtrl'
      },
      '/tournament/:id/standings': {
        templateUrl: 'views/tournament/tournament-standings.html',
        controller: 'TournamentStandingsCtrl'
      },
      '/tournament/:id/players': {
        templateUrl: 'views/tournament/tournament-players.html',
        controller: 'TournamentPlayersCtrl'
      },
      '/tournament/:id/comments': {
        templateUrl: 'views/tournament/tournament-comments.html',
        controller: 'TournamentCommentsCtrl'
      },
      '/tournament/:id/match/:matchId': {
        templateUrl: 'views/tournament/tournament-match-details.html',
        controller: 'TournamentMatchDetailsCtrl'
      },
      '/users/:username': {
        templateUrl: 'views/users.html',
        controller: 'UsersCtrl'
      },
      '/users/:username/created': {
        templateUrl: 'views/userCreated.html',
        controller: 'UsersCtrl'
      },
      '/users/:username/rankings': {
        templateUrl: 'views/userRankings.html',
        controller: 'UsersCtrl'
      },
      '/users/:username/config': {
        templateUrl: 'views/userConfig.html',
        controller: 'UserConfigCtrl'
      },
      '/search': {
        templateUrl: 'views/search.html',
        controller: 'SearchCtrl'
      },
      '/404': {
        templateUrl: 'views/404.html',
        controller: '404Ctrl'
      },
      '/notifications': {
        templateUrl: 'views/notifications.html',
        controller: 'NotificationsCtrl'
      },
      '/ranking': {
        templateUrl: 'views/ranking.html',
        controller: 'RankingCtrl'
      },
      '/ranking/:id': {
        templateUrl: 'views/ranking-page.html',
        controller: 'RankingPageCtrl'
      },
      '/ranking/:id/tournaments': {
        templateUrl: 'views/ranking-tournaments.html',
        controller: 'RankingTournamentsCtrl'
      },
      /* ===== yeoman hook ===== */
      /* Do not remove these commented lines! Needed for auto-generation */
    }
  };
});
