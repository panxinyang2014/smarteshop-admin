(function() {
    'use strict';
    angular
        .module('smarteshopApp')
        .factory('ProductAdditionalSku', ProductAdditionalSKu);

    ProductAdditionalSKu.$inject = ['$resource', 'DateUtils'];

    function ProductAdditionalSKu ($resource, DateUtils) {
        var resourceUrl =  'api/products/:productId/additionalSkus';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.activeStartDate = DateUtils.convertDateTimeFromServer(data.activeStartDate);
                        data.activeEndDate = DateUtils.convertDateTimeFromServer(data.activeEndDate);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' },
            'generateSkusByBatch':{method:'POST'}
        });
    }
})();
