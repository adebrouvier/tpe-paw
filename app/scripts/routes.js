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
            '/users/:username': {
                templateUrl: '/views/users.html',
                controller: 'UsersCtrl'
            },
            '/users/:username/created': {
                templateUrl: '/views/userCreated.html',
                controller: 'UsersCtrl'
            },
            '/users/:username/rankings': {
                templateUrl: '/views/userRankings.html',
                controller: 'UsersCtrl'
            },
            '/users/:username/config': {
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
