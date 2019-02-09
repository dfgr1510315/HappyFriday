<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script src="https://cdn.staticfile.org/jquery/3.2.1/jquery.min.js"></script>
    <link href="https://vjs.zencdn.net/7.4.1/video-js.css" rel="stylesheet">
    <script src='https://vjs.zencdn.net/7.4.1/video.js'></script>
</head>

<style type="text/css">
    .video-js .vjs-time-control{display:block;}
    .video-js .vjs-remaining-time{display: none;}
</style>

<body>


<video id="video1" class="video-js vjs-default-skin"  width="375" height="200" controls preload="none"

       data-setup='{ "html5" : { "nativeTextTracks" : false } }'>

    <source src="${pageContext.request.contextPath}/Upload/33969c4371226b916139c168128a2fd2.mp4" type="video/mp4">

    <p class="vjs-no-js">  播放视频需要启用 JavaScript，推荐使用<a href="http://videojs.com/html5-video-support/" target="_blank">支持HTML5</a>的浏览器访问。</p>

</video>

</body>
<script type="text/javascript">
    var player = videojs('video1', { }, function () {
        console.log('Good to go!');
    });

    player.on('play', function () {
        console.log('开始/恢复播放');
    });

    player.on('pause', function () {
        console.log('暂停播放');
    });

    player.on('ended', function () {
        console.log('结束播放');
    });

    player.on('timeupdate', function () {
        console.log(player.currentTime());
    });

    player.on('loadedmetadata', function() {
        player.currentTime(20);		    //跳转
    });

</script>

</html>
