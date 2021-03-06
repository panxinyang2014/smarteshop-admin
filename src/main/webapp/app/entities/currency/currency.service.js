(function() {
    'use strict';
    angular
        .module('smarteshopApp')
        .factory('Currency', Currency);

    Currency.$inject = ['$resource'];

    function Currency ($resource) {
        var resourceUrl =  'api/currencies/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
