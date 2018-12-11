'use strict';
define(['tpePaw', 'services/apiService'], function(tpePaw) {

  tpePaw.directive('availableUsername', function($q, apiService) {
    return {
      require: 'ngModel',
      restrict: 'A',
      link: function(scope, elem, attrs, ngModel) {

        ngModel.$asyncValidators.availableUsername = function(modelValue, viewValue) {
          return apiService.availableUsername(viewValue)
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
