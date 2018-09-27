<%--
  Created by IntelliJ IDEA.
  User: 1105379011
  Date: 2018/9/13
  Time: 17:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
 <head>
    <meta charset="utf-8">
    <meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no, width=device-width">
    <title>菜鸟教程(runoob.com)</title>
    <link href="https://cdn.bootcss.com/ionic/1.3.2/css/ionic.css" rel="stylesheet">
    <script src="https://cdn.bootcss.com/ionic/1.3.2/js/ionic.bundle.min.js"></script>
    <script type="text/javascript">
        angular.module('ionicApp', ['ionic'])

            .controller('SlideController', function($scope) {

                $scope.myActiveSlide = 1;

            })
    </script>
    <style type="text/css">

        body{
            height:100%;
            width:100%;
            float: left;
            background:#fffef5;
        }

        .slider {
            height: 10%;
            margin-top: 50px;
        }
        .slider-slide {
            color: #000;
            background-color: #fff; text-align: center;
            font-family: "HelveticaNeue-Light", "Helvetica Neue Light", "Helvetica Neue", Helvetica, Arial, "Lucida Grande", sans-serif; font-weight: 300; }
        .blue {
            background-color: blue;
        }

        .yellow {
            background-color: yellow;
        }

        .pink {
            background-color: pink;
        }
        .box{
            height:100%;
        }
        .box h1{
            position:relative; top:50%; transform:translateY(-50%);
        }

    </style>
</head>

<body ng-app="ionicApp" animation="slide-left-right-ios7" ng-controller="SlideController">

<ion-slide-box active-slide="myActiveSlide">
    <ion-slide>
        <div class="box blue"><h1>BLUE</h1></div>
    </ion-slide>
    <ion-slide>
        <div class="box yellow"><h1>YELLOW</h1></div>
    </ion-slide>
    <ion-slide>
        <div class="box pink"><h1>PINK</h1></div>
    </ion-slide>
</ion-slide-box>

</body>
</html>
