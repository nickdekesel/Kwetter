'use strict';

var services = angular.module('kwetter.services', ['ngResource', 'ngWebSocket']);
services.factory('UserFactory', function($resource){
    return $resource('http://localhost:8080/Kwetter-Back/webresources/kwetter/users/:firstParam/:secondParam', {firstParam: '@firstParam', secondParam: '@secondParam'}, {
        getArray:{
            method: 'GET',
            isArray: true
        },
        getObject: {
            method: 'GET'
        }
    });
});

services.factory('TweetFactory', function($resource){
    return $resource('http://localhost:8080/Kwetter-Back/webresources/kwetter/tweets/:firstParam/:secondParam', {firstParam: '@firstParam', secondParam: '@secondParam'}, {
        post:{
            method: 'POST',
            params: {tweet: '@tweet'}
        },       
        getArray: {
            method: 'GET',
            isArray: true
        }
    });
});

services.factory('Websocket', function ($websocket) {
    // Open a WebSocket connection
    var url = "ws://localhost:8080/Kwetter-Back/kwetterendpoint";
    var ws = $websocket(url);

    var tweets = [];

    ws.onMessage(function (event) {
        console.log('message: ', event.data);
        var response;
        try {
            response = angular.fromJson(event.data);
            tweets.push(response);
        } catch (e) {
            console.log('error: ', e);
            response = {'error': e};
        }


        //for (var i = 0; i < response.length; i++) {
        //    tweets.push(response);
        //}
    });
    ws.onError(function (event) {
        console.log('connection Error', event);
    });
    ws.onClose(function (event) {
        console.log('connection closed', event);
    });
    ws.onOpen(function () {
        console.log('connection open');
    });

    return {
        tweets: tweets,
        status: function () {
            return ws.readyState;
        },
        send: function (message) {
            if (angular.isString(message)) {
                ws.send(message);
            }
            else if (angular.isObject(message)) {
                ws.send(JSON.stringify(message));
            }
        }
    };
});

// Declare app level module which depends on views, and components
var app = angular.module('kwetter.controllers', [
  'ngRoute',
  'myApp.view1',
  'myApp.view2',
  'myApp.version', 
  'angularMoment',
  'kwetter.services'
]);

app.run(function(amMoment) {
    amMoment.changeLocale('nl');
});

var settedUser;

app.controller('TweetController', ['$window','$scope', 'UserFactory', 'TweetFactory', 'Websocket', function($window, $scope, UserFactory, TweetFactory, Websocket){
    
    $scope.mentions = [];
    $scope.trendings = [];
    $scope.timelineTweets = [];    
    
    $scope.newTweet = {};
    
    init();    
    function init(){
        if(settedUser !== undefined){
            $scope.curUser = settedUser;  
            update();
        }else{
            UserFactory.getObject({firstParam: 'random'}, function(user){
                $scope.curUser = user;
                settedUser = $scope.curUser;
                update();
            }); 
        }       
    }; 
    
    function update(){
        getFollowing();
        getFollowers();
        getAllTweets();  
        getUserTweets();
        updateTimeline();
        updateMentions();
        updateTrendings();
    };
    
    $scope.update = function(){
      update();  
    };  
    
    
    function getFollowing(){
        $scope.following = UserFactory.getArray({firstParam: $scope.curUser.id, secondParam: "following"});
    };
    
    function getFollowers(){
        $scope.followers = UserFactory.getArray({firstParam: $scope.curUser.id, secondParam: "followers"});       
    };
    
    function getUserTweets(){
        $scope.userTweets = TweetFactory.getArray({firstParam: $scope.curUser.id});
    };
    
    function setUser(id){
        UserFactory.getObject({firstParam: 'byId', secondParam: id}, function(user){
                $scope.curUser = user;
                settedUser = $scope.curUser;
                update();
        });
    };
    
    $scope.getTimeline = function(){
        return $scope.timelineTweets.concat(Websocket.tweets);
    };
    
    $scope.getAllTweets = function(){
        return $scope.allTweets.concat(Websocket.tweets);
    };
    
    $scope.getUser = function(id){
        return UserFactory.getObject({firstParam: "byId", secondParam: id});
    };
    
    $scope.selectUser = function(userid){   
        UserFactory.getObject({firstParam: 'byId' , secondParam: userid}, function(user){
                $scope.curUser = user;
                settedUser = $scope.curUser;
                $scope.update();
        }); 
    };      
    
    $scope.addTweet = function(){
        if($scope.newTweet.tweet.length <=140){
            //this.newTweet.id = this.curUser.tweets.length;
            //this.newTweet.date = Date.now();
            $scope.newTweet.username = $scope.curUser.username;
            Websocket.send($scope.newTweet);
            TweetFactory.post({firstParam: "add", secondParam: $scope.curUser.username, tweet: $scope.newTweet.tweet}, function(result){
                //console.log(result);
                if(result.done === "true"){
                    setUser($scope.curUser.id);
                    $scope.newTweet = {};
                }
                
            }); 
            
        }
    };   
    
    function getAllTweets(){
        $scope.allTweets = TweetFactory.getArray({});
    };
    
    /*
    function getTweetsFrom(username){
        $scope.timelineTweets = TweetFactory.getArray({firstParam: username});
    };*/
    
    function getTweetsFrom(userid){
        $scope.timelineTweets = TweetFactory.getArray({firstParam: userid});
    };
    
    function updateTimeline(){
        $scope.timelineTweets = TweetFactory.getArray({firstParam: "timeline", secondParam: $scope.curUser.id});
    };   
    
    function updateMentions(){
        $scope.mentions = TweetFactory.getArray({firstParam: "mentions", secondParam: $scope.curUser.username});
    };
    
    function updateTrendings(){
        $scope.trendings = TweetFactory.getArray({firstParam: "trends"});
    };
    
    $scope.getTrending = function(hashtag){
        for(var i=0; i<$scope.trendings.length; i++){
            if($scope.trendings[i].hashtag === hashtag){
                return $scope.trendings[i];
            }
        }
    };
    
    $scope.setTrending = function(hashtag){
        $scope.curHashtag = hashtag;        
    };
    
    $scope.redirect = function(redirect){
      $window.location.href = 'index.html#'+redirect;
    };
   
}]);

app.controller('TabController', function(){
    this.tab = 0;
    
    this.setTab = function(tab){
        this.tab = tab;
    };
    
    this.isTab = function(tab){
      return this.tab===tab; 
    };
});

app.controller('TimeLineController', function(){
    this.tab = 0;
    
    this.setTab = function(tab){
        this.tab = tab;
    };
    
    this.isTab = function(tab){
      return this.tab===tab; 
    };
});


var testUsers = [
    {
        id: 0, name: "Klaas", location: "Eindhoven", web:"www.klaas.nl", 
        bio: "ik ben klaas en ik tweet heel graag",
        image: "images/pod_orange.png",
        tweets: [
            {id: 0, tweet: 'Hoi dit is een test tweet', date: 1425299389136},
            {id: 1, tweet: 'Hoi dit is een test tweet2', date: 1425299389136}
        ],
        followers: [ 1, 2 ]
    }, {id: 1, name: "Piet", location: "Eindhoven", web:"www.piet.nl", 
        bio: "ik ben Piet en ik tweet minder graag dan klaas",
        image: "https://pbs.twimg.com/profile_images/1466439955/imagesCAMKCXAI_400x400.jpg",
        tweets:[
            {id: 0, tweet: 'Hoi dit is een test tweet3', date: 1425299389136} 
        ] ,
        followers: [ 0 ]  
    }, {id: 2, name: "Frank", location: "Amsterdam", web:"www.hallotjes.nl", 
        bio: "ik ben Frank, wat is een computer",
        image: "data:image/jpeg;base64,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",
        tweets:[
            {id: 0, tweet: 'Hoi dit is frank zijn tweet', date: 1425299389136} 
        ],
        followers: [ 0, 1 ]
    }];



app.config(['$routeProvider', function($routeProvider) {
    $routeProvider.when('/view1',{
        //controller: 'TweetController',
        templateUrl: 'view1/view1.html'
    }).when('/view2', {
        //controller: 'TweetController',
        templateUrl: 'view2/view2.html'
    }).otherwise({redirectTo: '/view1'});
}]);
