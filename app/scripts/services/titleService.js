define(['tpePaw'], function(tpePaw) {

    'use strict';
    tpePaw.service('titleService', function($window) {

        this.setTitle = function(title){

            $window.document.title = title;
            
        }
    });

});