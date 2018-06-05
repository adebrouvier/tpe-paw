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
                templateUrl: '/views/tournament.html',
                controller: 'TournamentCtrl'
            },
            '/tournament/:id': {
                templateUrl: '/views/tournament-page.html',
                controller: 'TournamentPageCtrl'
            }
            /* ===== yeoman hook ===== */
            /* Do not remove these commented lines! Needed for auto-generation */
        }
    };
});
