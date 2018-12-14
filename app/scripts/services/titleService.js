'use strict';
define(['tpePaw'], function (tpePaw) {

  tpePaw.service('titleService', function ($window) {

    this.setTitle = function (title) {

      $window.document.title = title;

    };
  });

});
