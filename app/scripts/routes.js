'use strict';

define([], function() {
    return {
        defaultRoutePath: '/',
        routes: {
            '/': {
                templateUrl: '/views/home.html',
                controller: 'HomeCtrl'
            },
            '/register': {
                templateUrl: '/views/register.html',
                controller: 'RegisterCtrl'
            },
            '/login': {
                templateUrl: '/views/login.html',
                controller: 'LoginCtrl'
            },
            '/tournament': {
                templateUrl: '/views/tournament/tournament.html',
                controller: 'TournamentCtrl'
            },
            '/tournament/:id': {
                templateUrl: '/views/tournament/tournament-page.html',
                controller: 'TournamentPageCtrl'
            },
            '/tournament/:id/standings': {
                templateUrl: '/views/tournament/tournament-standings.html',
                controller: 'TournamentStandingsCtrl'
            },
            '/tournament/:id/players': {
                templateUrl: '/views/tournament/tournament-players.html',
                controller: 'TournamentPlayersCtrl'
            },
            '/tournament/:id/comments': {
                templateUrl: '/views/tournament/tournament-comments.html',
                controller: 'TournamentCommentsCtrl'
            },
            '/search':{
                templateUrl: '/views/search.html',
                controller: 'SearchCtrl'
            },
            '/404':{
                templateUrl: '/views/404.html',
                controller: '404Ctrl'
            }
            /* ===== yeoman hook ===== */
            /* Do not remove these commented lines! Needed for auto-generation */
        }
    };
});
