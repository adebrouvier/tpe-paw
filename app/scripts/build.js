/* global paths */
'use strict';
require.config({
  baseUrl: 'scripts',
  paths: {
    affix: '../../bower_components/bootstrap-sass-official/assets/javascripts/bootstrap/affix',
    alert: '../../bower_components/bootstrap-sass-official/assets/javascripts/bootstrap/alert',
    angular: '../../bower_components/angular/angular',
    'angular-route': '../../bower_components/angular-route/angular-route',
    'angular-translate': '../../bower_components/angular-translate/angular-translate',
    button: '../../bower_components/bootstrap-sass-official/assets/javascripts/bootstrap/button',
    bootstrap: '../../bower_components/bootstrap/dist/js/bootstrap.bundle',
    carousel: '../../bower_components/bootstrap-sass-official/assets/javascripts/bootstrap/carousel',
    collapse: '../../bower_components/bootstrap-sass-official/assets/javascripts/bootstrap/collapse',
    dropdown: '../../bower_components/bootstrap-sass-official/assets/javascripts/bootstrap/dropdown',
    'es5-shim': '../../bower_components/es5-shim/es5-shim',
    jquery: '../../bower_components/jquery/dist/jquery',
    'jquery-ui': '../../bower_components/jquery-ui/jquery-ui',
    json3: '../../bower_components/json3/lib/json3',
    moment: '../../bower_components/moment/moment',
    popover: '../../bower_components/bootstrap-sass-official/assets/javascripts/bootstrap/popover',
    requirejs: '../../bower_components/requirejs/require',
    scrollspy: '../../bower_components/bootstrap-sass-official/assets/javascripts/bootstrap/scrollspy',
    tab: '../../bower_components/bootstrap-sass-official/assets/javascripts/bootstrap/tab',
    tooltip: '../../bower_components/bootstrap-sass-official/assets/javascripts/bootstrap/tooltip',
    transition: '../../bower_components/bootstrap-sass-official/assets/javascripts/bootstrap/transition',
    'bootstrap-sass-official': '../../bower_components/bootstrap-sass-official/assets/javascripts/bootstrap',
    'infinite-scroll': '../../bower_components/ngInfiniteScroll/build/ng-infinite-scroll',
    typeahead: '../../bower_components/corejs-typeahead/dist/typeahead.jquery',
    bloodhound: '../../bower_components/corejs-typeahead/dist/bloodhound',
    'angular-animate': '../../bower_components/angular-animate/angular-animate',
    'angular-loading-bar': '../../bower_components/angular-loading-bar/build/loading-bar',
    'corejs-typeahead': '../../bower_components/corejs-typeahead/dist/typeahead.bundle',
    ngInfiniteScroll: '../../bower_components/ngInfiniteScroll/build/ng-infinite-scroll',
    'angular-messages': '../../bower_components/angular-messages/angular-messages',
    'angular-ui-sortable': '../../bower_components/angular-ui-sortable/sortable'
  },
  shim: {
    angular: {
      deps: [
        'jquery'
      ]
    },
    'angular-route': {
      deps: [
        'angular'
      ]
    },
    bootstrap: {
      deps: [
        'jquery'
      ]
    },
    modal: {
      deps: [
        'jquery'
      ]
    },
    tooltip: {
      deps: [
        'jquery'
      ]
    },
    'angular-translate': {
      deps: [
        'angular'
      ]
    },
    'infinite-scroll': {
      deps: [
        'jquery',
        'angular'
      ]
    },
    typeahead: {
      deps: [
        'jquery'
      ]
    },
    'angular-animate': {
      deps: [
        'angular'
      ]
    },
    'angular-loading-bar': {
      deps: [
        'angular',
        'angular-animate'
      ]
    },
    'angular-messages': {
      deps: [
        'angular'
      ]
    },
    'angular-ui-sortable': {
      deps: [
        'angular',
        'jquery',
        'jquery-ui'
      ]
    }
  },
  packages: []
});

if (paths) {
  require.config({
    paths: paths
  });
}

require([
    'angular',
    'tpePaw',
    'controllers/IndexCtrl'
  ],
  function () {
    angular.bootstrap(document, ['tpePaw']);
  }
);
