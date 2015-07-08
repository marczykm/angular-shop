app.controller('BlogPostCtrl', function($scope, $routeParams, $http){
    $scope.name = "BlogPostCtrl";
    $scope.params = $routeParams;

    console.log($scope.params.id);

    $http.get("http://marczyk.ovh:9999/rest/post?id=" + $routeParams.id).
            success(function(data, status, headers, config){
                $scope.post = data;
                console.log($scope.post);
            }).
            error(function(data, status, headers, config){

            });
});