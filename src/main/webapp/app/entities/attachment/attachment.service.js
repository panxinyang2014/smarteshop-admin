(function() {
    'use strict';
    angular
        .module('smarteshopApp')
        .factory('Attachment', Attachment);

    Attachment.$inject = ['$resource'];

    function Attachment ($resource) {
        var resourceUrl =  'api/attachments/:id';

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
