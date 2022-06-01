//设置菜单按钮的点击事件，实现简单的响应式
var menuClose = false;

if ($(window).width() < 800) {
    $("body > div > div.layui-header > div").css("display", "none");
    $("#div_side_bg").css("display", "none");
    $("body > div > div.layui-header > ul.layui-nav.layui-layout-left > li:nth-child(1) > a")
        .removeClass("layui-icon-shrink-right").addClass("layui-icon-spread-left");
    $("body > div > div.layui-header > ul.layui-nav.layui-layout-left").css("left","0");
    $("body > div > div.layui-body").css("left","0");
    $("body > div > div.layui-footer").css("left","0");
    $("#l-logo").css("display", "none");
    menuClose = true;
}

function makeMenuClose() {
    $("body > div > div.layui-header > div").css("display", "none");
    $("#div_side_bg").css("display", "none");
    $("body > div > div.layui-header > ul.layui-nav.layui-layout-left > li:nth-child(1) > a")
        .removeClass("layui-icon-shrink-right").addClass("layui-icon-spread-left");
    $("body > div > div.layui-header > ul.layui-nav.layui-layout-left").animate({
        left: '-=200px'
    }, "1000");
    $("body > div > div.layui-body").animate({
        left: '-=200px'
    }, "1000");
    $("body > div > div.layui-footer").animate({
        left: '-=200px'
    }, "1000");
    $("#l-logo").css("display", "none");
    menuClose = !menuClose;
}

function makeMenuOpen() {
    $("body > div > div.layui-header > ul.layui-nav.layui-layout-left > li:nth-child(1) > a")
        .removeClass("layui-icon-spread-left").addClass("layui-icon-shrink-right");
    $("body > div > div.layui-header > ul.layui-nav.layui-layout-left").animate({
        left: '+=200px'
    }, "1000");
    $("body > div > div.layui-body").animate({
        left: '+=200px'
    }, "1000");
    $("body > div > div.layui-footer").animate({
        left: '+=200px'
    }, "1000");
    setTimeout(function () {
        $("body > div > div.layui-header > div").css("display", "block");
        $("#div_side_bg").css("display", "block");
    }, 400);
    setTimeout(function () {
        $("#l-logo").css("display", "block");
    }, 500);
    menuClose = !menuClose;
}

$("body > div > div.layui-header > ul.layui-nav.layui-layout-left > li:nth-child(1) > a").click(
    function () {
        if (!menuClose) {
            makeMenuClose();
        } else {
            makeMenuOpen();
        }
    });

//浏览器窗口大小变化时
$(window).resize(function () {
    var window_width = $(window).width(); //获取浏览器窗口宽度
    if (window_width < 800) {
        if (!menuClose) {
            makeMenuClose();
        }
    } else {
        if (menuClose) {
            makeMenuOpen();
        }
    }
});

//打开与当前页面标题相符的菜单项
$("#div_side_bg > div > ul > li").each(function () {
    // if($(this).children("a").text().trim()===$(document).attr('title').trim()){
    //     $(this).addClass("layui-nav-itemed")
    // }
    $(this).addClass("layui-nav-itemed")
})

// 当未处理的入住请求数量为0时,不显示徽章
if ($("#c_y span").text() === "0") {
    $("#c_y").text("住宿申请")
}