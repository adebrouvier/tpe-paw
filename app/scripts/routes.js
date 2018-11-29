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
            '/users/:id': {
                templateUrl: '/views/users.html',
                controller: 'UsersCtrl'
            },
            '/users/:id/created': {
                templateUrl: '/views/userCreated.html',
                controller: 'UsersCtrl'
            },
            '/users/:id/rankings': {
                templateUrl: '/views/userRankings.html',
                controller: 'UsersCtrl'
            },
            '/users/:id/config': {
                templateUrl: '/views/userConfig.html',
                controller: 'UserConfigCtrl'
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
