/**
  * responsiveMenu();
  * parallax();
  * recentOwl();
  * tabs();
  * accordionToggle();
  * flatOwl();
  * flatVideo();
  * flatIsotopeCourse();
  * flatSearch();
  * flatCounter();
  * flatRetinaLogo();
  * goTop();
  * headerFixed();
  * flatAnimsition();
  * inViewport();
  * countDownTime();
  * swClick();
  * Animation();
  * preLoader();
  *
**/
;(function($) {
    "use strict";

    var isMobile = {

        Android: function() {

            return navigator.userAgent.match(/Android/i);

        },

        BlackBerry: function() {

            return navigator.userAgent.match(/BlackBerry/i);

        },

        iOS: function() {

            return navigator.userAgent.match(/iPhone|iPad|iPod/i);

        },

        Opera: function() {

            return navigator.userAgent.match(/Opera Mini/i);

        },

        Windows: function() {

            return navigator.userAgent.match(/IEMobile/i);

        },

        any: function() {

            return (isMobile.Android() || isMobile.BlackBerry() || isMobile.iOS() || isMobile.Opera() || isMobile.Windows());

        }

    };

    var parallax = function() {
        if ( $().parallax && isMobile.any() === null ) {

            $('.parallax1').parallax("50%", -0.5); 

            $('.parallax2').parallax("50%", 0); 

            $('.parallax3').parallax("50%", -0.5); 

            $('.parallax4').parallax("50%", -0.5);

            $('.parallax5').parallax("50%", -0.5);

            $('.parallax6').parallax("50%", -0.5);

            $('.parallax7').parallax("50%", -0.9);

            $('.parallax12').parallax("50%", -0.5);

            $('.parallax13').parallax("50%", -0.5);
        }
    };
    
    var recentOwl = function() {
        if ( $().owlCarousel ) {
            $('.flat-recentOwl').each(function(){
                var
                $this = $(this),
                auto = $this.data("auto"),
                item = $this.data("column"),
                item2 = $this.data("column2"),
                item3 = $this.data("column3"),
                nav = $this.data("nav"),
                dots = $this.data("dots"),
                gap = Number($this.data("gap")),
                loop=  $this.data("loop");

                $this.find('.owl-carousel').owlCarousel({
                    margin: gap,
                    dots:dots,
                    nav: nav,
                    navText: ['<span class="fa fa-angle-left" aria-hidden="true"></span>','<span class="fa fa-angle-right" aria-hidden="true"></span>'],
                    navigation : true,
                    pagination: true,
                    autoplay: auto,
                    autoplayTimeout: 5000,
                    paginationNumbers: 2,
                    loop: loop,
                    responsive: {
                        0:{
                            items:item3
                        },
                        600:{
                            items:item2
                        },
                        1000:{
                            items:item
                        }
                    }
                });
            });
        };
    }

    var flatOwl = function() {
        if ( $().owlCarousel ) {
            $('.flat-carousel').each(function(){
                var
                $this = $(this),
                auto = $this.data("auto"),
                item = $this.data("column"),
                item2 = $this.data("column2"),
                item3 = $this.data("column3"),
                nav = $this.data("nav"),
                dots = $this.data("dots"),
                gap = Number($this.data("gap")),
                loop=  $this.data("loop");

                $this.find('.owl-carousel').owlCarousel({
                    margin: gap,
                    dots:dots,
                    nav: nav,
                    navText: ['<i class="fa fa-angle-left" aria-hidden="true"></i>','<i class="fa fa-angle-right" aria-hidden="true"></i>'],
                    navigation : true,
                    pagination: true,
                    autoplay: auto,
                    autoplayTimeout: 5000,
                    paginationNumbers: 2,
                    loop: loop,
                    responsive: {
                        0:{
                            items:item3
                        },
                        600:{
                            items:item2
                        },
                        1000:{
                            items:item
                        }
                    }
                });
            });
        };
    }

    var tabs =  function() {
        $('.flat-tabs').each(function(){
            var 
            list ="",
            title = $(this).find('.item-title'),
            titleWrap = $(this).children('.tab-title') ;

            $(this).find('.tab-title li').filter(':first').addClass('active');
            $(this).find('.tab-content-wrap').children().hide().filter(':first').addClass('active').show();
           

            console.log($(this).find('.tab-content-wrap').children().filter(2));

            $(this).find('.tab-title li').on('click', function(e) {
                var
                idx = $(this).index(),
                content = $(this).closest('.flat-tabs').find('.tab-content-wrap').children().eq(idx);

                $(this).addClass('active').siblings().removeClass('active');
                content.fadeIn('slow').addClass('active').siblings().hide().removeClass('active');

                e.preventDefault();
            });
        });
    };

    var accordionToggle = function() {
        $('.flat-faqs').each(function () {
            var speed = {duration: 400};
            $('.flat-faqs .toggle-content').hide();
            $('.flat-faqs .accordion-toggle .toggle-title.active').siblings('.toggle-content').show();
            $('.flat-faqs .accordion').find('.toggle-title').on('click', function() {
                $(this).toggleClass('active');
                $(this).next().slideToggle(speed);
                $(".flat-faqs .toggle-content").not($(this).next()).slideUp(speed);
                if ($(this).is('.active')) {
                    $(this).closest('.accordion').find('.toggle-title.active').toggleClass('active')
                    $(this).toggleClass('active');
                };
            });
        });
    };

    var flatVideo = function() {
        $(".fancybox").on("click", function(){
            $.fancybox({
                href: this.href,
                type: $(this).data("type")
            }); // fancybox
            return false   
        }); // on
    }

    var flatIsotopeCourse = function() {
        $(".flat-courses").each(function () {
            if ( $().isotope ) {           
                var $container = $('.isotope-courses');
                $container.imagesLoaded(function(){

                    $container.isotope({
                        itemSelector: '.course',
                        transitionDuration: '1s',
                        layoutMode: 'fitRows'
                    });
                });

                $('.flat-filter-isotype li').on('click',function() {                           
                    var selector = $(this).find("a").attr('data-filter');
                    $('.flat-filter-isotype li').removeClass('active');
                    $(this).addClass('active');
                    $container.isotope({ filter: selector });
                    return false;
                });
            };
        });         
    };

    var flatSearch = function () {
       $(document).on('click', function(e) {   
            var clickID = e.target.id;   
            if ( ( clickID !== 's' ) ) {
                $('.top-search').removeClass('show');   
                $('.show-search').removeClass('active');             
            } 
        });

        $('.show-search').on('click', function(event){
            event.stopPropagation();
        });

        $('.search-form').on('click', function(event){
            event.stopPropagation();
        });        

        $('.show-search').on('click', function (e) {           
            if( !$( this ).hasClass( "active" ) )
                $( this ).addClass( 'active' );
            else
                $( this ).removeClass( 'active' );
             e.preventDefault();

            if( !$('.top-search' ).hasClass( "show" ) )
                $( '.top-search' ).addClass( 'show' );
            else
                $( '.top-search' ).removeClass( 'show' );
        });
    };

    var flatCounter = function() {
        if ( $().countTo ) {
            $('.counter').on('on-appear', function() {
                $(this).find('.numb').each(function() {
                    var to = $(this).data('to'),
                        speed = $(this).data('speed');
                        
                    $(this).countTo({
                        to: to,
                        speed: speed
                    });
                });
            });
        }
    };

    var inViewport =  function() {
        $('[data-inviewport="yes"]').waypoint(function() {
            $(this).trigger('on-appear');
        }, { offset: '90%', triggerOnce: true });

        $(window).on('load', function() {
            setTimeout(function() {
                $.waypoints('refresh');
            }, 100);
        });
    };

    var goTop = function(){
        $(window).scroll(function() {
            var wh = window.innerWidth;
            if ($(this).scrollTop() > 100) {
                $('#scroll-top').addClass('show');
            } else {
                $('#scroll-top').removeClass('show');
            }
        });

        $("#scroll-top").on("click", function() {
            $("html, body").animate({
                scrollTop: 0
            }, 600);
            return false
        });
    };

    var flatRetinaLogo = function() {
        var retina = window.devicePixelRatio > 1 ? true : false;

        var $logo = $('#logo img');
        var $logo_retina = $logo.data('retina');

        var $logo_ft = $('#logo-ft img');
        var $logo_retina_ft = $logo_ft.data('retina');
        
        if ( retina && $logo_retina ) {
            $logo.attr({
                src: $logo.data('retina'),
                width: $logo.data('width'),
                height: $logo.data('height')
            });
        } 

        if ( retina && $logo_retina_ft ) {
            $logo_ft.attr({
                src: $logo_ft.data('retina'),
                width: $logo_ft.data('width'),
                height: $logo_ft.data('height')
            });
        }
    };

    var preLoader =function handlePreloader() {
        $(window).on("load", function () {
            if($('.preloader').length){
                $('.preloader').delay(400).fadeOut(600);
            }
        });
    };

    var responsiveMenu = function() {
        var menuType = 'desktop';

        $(window).on('load resize', function() {
            var currMenuType = 'desktop';

            if ( matchMedia( 'only screen and (max-width: 991px)' ).matches ) {
                currMenuType = 'mobile';
            }

            if ( currMenuType !== menuType ) {
                menuType = currMenuType;

                if ( currMenuType === 'mobile' ) {
                    var $mobileMenu = $('#main-nav').attr('id', 'mainnav-mobi').hide();
                    var hasChildMenu = $('#mainnav-mobi').find('li:has(ul)');

                    $('#header .container-header ').after($mobileMenu);
                    hasChildMenu.children('ul').hide();
                    hasChildMenu.children('a').after('<span class="btn-submenu"></span>');
                    $('.btn-menu').removeClass('active');
                } else {
                    var $desktopMenu = $('#mainnav-mobi').attr('id', 'main-nav').removeAttr('style');

                    $desktopMenu.find('.submenu').removeAttr('style');
                    $('#header .container-header ').find('.nav-wrap').append($desktopMenu);
                    $('.btn-submenu').remove();
                }
            }
        });

        $('.mobile-button').on('click', function() {         
            $('#mainnav-mobi').slideToggle(300);
            $(this).toggleClass('active');
        });

        $(document).on('click', '#mainnav-mobi li .btn-submenu', function(e) {
            $(this).toggleClass('active').next('ul').slideToggle(300);
            e.stopImmediatePropagation()
        });
    };

    var headerFixed = function() {
        var nav = $('#header');
        $(window).on('load', function(){
            var header = $('#header');           
            var offsetTop = $('#header').offset().top;
            var headerHeight = $('#header').height();             
            var buffer  = $('<div>', { height: headerHeight }).insertAfter(header);   
                buffer.hide();                 

            $(window).on('load scroll', function(){
                if ( $(window).scrollTop() > offsetTop  ) {
                    $('#header').addClass('fixed-header');
                    buffer.show();
                } else {
                    $('#header').removeClass('fixed-header');
                    buffer.hide();
                }
            });
        });
    };

    var countDownTime =function () {
        $(".count-time").each(function() {
            var day = $(".count-time").data("day"),
                month = $(".count-time").data("month")-1,
                year = $(".count-time").data("year"),
                hour = $(".count-time").data("hour"),
                minute = $(".count-time").data("minute"),
                second = $(".count-time").data("second");
 
            var countDownDate = new Date(year,month,day,hour,minute,second).getTime();

            // Update the count down every 1 second
            var x = setInterval(function() {

                // Get todays date and time
                var now = new Date().getTime();

                // Find the distance between now and the count down date
                  var distance = countDownDate - now;

                // Time calculations for days, hours, minutes and seconds
                var days = Math.floor(distance / (1000 * 60 * 60 * 24));
                var hours = Math.floor((distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
                var minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
                var seconds = Math.floor((distance % (1000 * 60)) / 1000);

                // Display the result in the element with id="demo"
                $(".days .numb").html(days);
                $(".hours .numb").html(hours);
                $(".minutes .numb").html(minutes);
                $(".seconds .numb").html(seconds);

                // If the count down is finished, write some text 
                if (distance < 0) {
                    clearInterval(x);
                    $(".days .numb").html("0");
                    $(".hours .numb").html("0");
                    $(".minutes .numb").html("0");
                    $(".seconds .numb").html("0");
                }
            }, 1000);
        });
    };

    var swClick = function () {
        function activeLayout () {
            $(".switcher-container" ).on( "click", "a.sw-light", function() {
                $(this).toggleClass( "active" );
                $('body').addClass('home-boxed');  
                $('body').css({'background': '#f6f6f6' });                
                $('.sw-pattern.pattern').css ({ "top": "100%", "opacity": 1, "z-index": "10"});
            }).on( "click", "a.sw-dark", function() {
                $('.sw-pattern.pattern').css ({ "top": "98%", "opacity": 0, "z-index": "-1"});
                $(this).removeClass('active').addClass('active');
                $('body').removeClass('home-boxed');
                $('body').css({'background': '#fff' });
                return false;
            })       
        }

        function activePattern () {
            $('.sw-pattern').on('click', function () {
                $('.sw-pattern.pattern a').removeClass('current');
                $(this).addClass('current');
                $('body').css({'background': 'url("' + $(this).data('image') + '")', 'background-size' : '30px 30px', 'background-repeat': 'repeat' });
                return false;
            })
        }
        activeLayout(); 
        activePattern();
    } // Swicher Click


    var Animation = function() {

        $('.effect-animation').each( function() {


            var $this = $(this),

                animateClass  = $this.data('animation'),

                animateDelay  = $this.data('animation-delay'),

                animateOffset = $this.data('animation-offset');



            $this.css({

                '-webkit-animation-delay':  animateDelay,

                '-moz-animation-delay':     animateDelay,

                'animation-delay':          animateDelay

            });

        

            $this.waypoint(function() {
                $this.addClass('animated ' + animateClass);

                },{

                    triggerOnce: true,

                    offset: animateOffset

            });

        });

    };
     

     $(function() {
        responsiveMenu();
        parallax();
        recentOwl();
        tabs();
        accordionToggle();
        flatOwl();
        flatVideo();
        flatIsotopeCourse();
        flatSearch();
        flatCounter();
        flatRetinaLogo();
        goTop();
        headerFixed();
        countDownTime();
        swClick();
        Animation();
        $( window ).load(function() {
            inViewport();
        });
        preLoader();
    });
})(jQuery);