var footerHTML = "<span style=\"margin-right: 36px\">Copyright© 2022 All Rights Reserved.\n" +
    "\n" +
    "成都理工大学 所有</span>" +
    "<span style=\"color:rgb(144, 145, 146)\">成都市二仙桥东三路1号成都理工大学</span>";
var footerDOM = document.getElementById("sdms-footer");
if (typeof footerDOM !== "undefined" && footerDOM != null) {
    footerDOM.innerHTML = footerHTML;
}