(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .factory('CustomerSearch', CustomerSearch);

    CustomerSearch.$inject = ['$resource'];

    function CustomerSearch($resource) {
        var resourceUrl =  'api/_search/customers/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
