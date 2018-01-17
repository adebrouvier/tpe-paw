'use strict';

/**
 * @ngdoc overview
 * @name tpePawApp
 * @description
 * # tpePawApp
 *
 * Main module of the application.
 */
angular
  .module('tpePawApp', ['ngRoute'])
  	.config(function ($routeProvider) {
  		$routeProvider
        .when('/', {
          templateUrl: 'views/main.html',
          controller: 'MainCtrl'
        })
        .otherwise({
          redirectTo: '/'
        });
    });
