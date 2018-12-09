'use strict';
define(['tpePaw', 'services/apiService'], function(tpePaw) {

  tpePaw.directive('validTournament', function($q, apiService) {
    return {
      require: 'ngModel',
      restrict: 'A',
      link: function(scope, elem, attrs, ngModel) {

        ngModel.$asyncValidators.validTournament = function(modelValue, viewValue) {
          return apiService.validTournament(attrs.ranking, viewValue)
            .then(
              function(response) {
                var defer = $q.defer();

                if (response.data.valid) {
                  defer.resolve();
                }else{
                  defer.reject();
                }
                return defer.promise;
              }
            );
        };
      }
    };
  });
});
