let modalOpenButton = $("#modal");
let modalWindowContainer = $(".modal");

let mobileMenuOpenButton = $("#mobile_menu");
let mobileMenuContainer = $(".navigation__mobile");

let mobileSearchOpenButton = $("#search_button");
let mobileSearchContainer = $(".navigation__search");
let mobileSearchInput = $("#mobile_search_input");

let desktopSearchInput = $("#desktop_search_input");
let desktopSearchButton = $("#desktop_search_button");

modalOpenButton.on("click", (event) => {
  event.preventDefault();
  modalWindowContainer.addClass("isActive");
});

mobileMenuOpenButton.on("click", () => {
  mobileMenuContainer.addClass("isActive");
});

mobileSearchOpenButton.on("click", () => {
  mobileSearchContainer.toggleClass("isActive");
});

mobileSearchInput.on("keyup", (event) => {
  if (event.key === "Enter") {
    alert(event.target.value);
  }
});

desktopSearchButton.on("click", () => {
  alert(desktopSearchInput.val());
});

desktopSearchInput.on("keyup", (event) => {
  if (event.key === "Enter") {
    alert(event.target.value);
  }
});

$(document).on("mouseup", (event) => {
  let currentTarget = $(event.target);

  if (
    modalWindowContainer.has(currentTarget).length === 0 ||
    currentTarget.hasClass("js-close-btn")
  ) {
    modalWindowContainer.removeClass("isActive");
  }

  if (mobileMenuContainer.has(currentTarget).length === 0) {
    mobileMenuContainer.removeClass("isActive");
  }

  if (currentTarget.hasClass("dropdown__title")) {
    currentTarget.parent().toggleClass("isActive");
  }

  if (mobileSearchContainer.has(currentTarget).length === 0) {
    mobileSearchContainer.removeClass("isActive");
  }
});

/**
 * Version: 1.0
 * Build Date: 28-Jan-2015
 * Copyright (c) 2015, DyvenSvit (http://dyvensvit.org) All rights reserved.
 * License: Licensed under The MIT License. See license.txt and http://www.datejs.com/license/.
 */

$(document).ready(function () {
  var d = new Date();
  var year = d.getFullYear();
  var month = d.getMonth() + 1;
  var day = d.getDate();
  var wscreen = $(window).width();
  var oldmonth = d.getMonth() + 1;
  d.setDate(d.getDate() - 13);
  var oldday = d.getDate();
  var lines = new Array();
  var words = new Array();
  var fontcolor = "black";
  var bgcolor = "#ffffff";
  var bgbody = "#c97d41";
  var icopist = "";
  var txtpist = "";
  var tday = "";
  var tmonth = "";
  var caldata = "";
  var wframe = $("#widget").width();
  var hframe = $("#widget").height();
  //console.log(wframe);
  //console.log(hframe);

  $("#dscalendar").html("");

  $.get(
    "https://prayer-service.online/assets/" +
      year +
      "/" +
      (month < 10 ? "0" : "") +
      month +
      "/c1.txt",
    function (data) {
      lines = data.split("\n");

      for (var i = 0; i < lines.length; i++) {
        if (parseInt(lines[i].slice(0, 2)) == day) {
          words = lines[i].split("|");

          if (words[2] == 1) {
            fontcolor = "red";
            bgbody = "#FF3D00";
          }
          if (words[2] == 2) {
            fontcolor = "red";
            bgbody = "#D50000";
          }
          if (words[2] == 3) {
            fontcolor = "red";
            bgbody = "#0288D1";
          }
          if (words[3] == 1) {
            bgcolor = "#F3E5F5";
          }
          if (words[3] == 2) {
            bgcolor = "#F3E5F5";
          }
          if (words[3] == 3) {
            bgcolor = "#E8F5E9";
            txtpist = "Загальниця";
          }
          if (words[4] == 1) {
            icopist = '<img src="/img/ic_bpist.png" class="icogrid">';
            bgbody = "#AA00FF";
            txtpist = "Піст";
          }
          if (words[4] == 2) {
            icopist =
              "<img src='/img/ic_spist.png' style='width:32px; height:32px;'>";
            bgbody = "#AB47BC";
            txtpist = "Строгий піст";
          }

          switch (parseInt(words[1])) {
            case 1:
              tday = "пн";
              break;
            case 2:
              tday = "вт";
              break;
            case 3:
              tday = "ср";
              break;
            case 4:
              tday = "чт";
              break;
            case 5:
              tday = "пт";
              break;
            case 6:
              tday = "сб";
              break;
            case 7:
              tday = "нд";
              break;
          }

          switch (month) {
            case 1:
              tmonth = "Січень";
              break;
            case 2:
              tmonth = "Лютий";
              break;
            case 3:
              tmonth = "Березень";
              break;
            case 4:
              tmonth = "Квітень";
              break;
            case 5:
              tmonth = "Травень";
              break;
            case 6:
              tmonth = "Червень";
              break;
            case 7:
              tmonth = "Липень";
              break;
            case 8:
              tmonth = "Серпень";
              break;
            case 9:
              tmonth = "Вересень";
              break;
            case 10:
              tmonth = "Жовтень";
              break;
            case 11:
              tmonth = "Листопад";
              break;
            case 12:
              tmonth = "Грудень";
              break;
          }

          $("#widget").css("width", wframe - wframe * 0.02);
          $(".footer").css("width", wframe - wframe * 0.02);
          $("#widget").css("height", hframe - wframe * 0.02);
          $("#today").css("height", hframe - 50 - wframe * 0.02);
          $("#ctitlew").css("background-color", bgbody);
          $(".footer").css("background-color", bgbody);
          $("#widget").css("border-color", bgbody);
          $(".footer").css("border-color", bgbody);
          $("#widget").css("background-color", bgcolor);
          $("#lday")
            .html(tday)
            .css("color", fontcolor)
            .css("font-size", wscreen * 0.05);
          $("#ldate")
            .html(day)
            .css("color", fontcolor)
            .css("font-size", wscreen * 0.15);
          $("#lolddate")
            .html(oldday + "/" + (oldmonth < 10 ? "0" : "") + oldmonth)
            .css("color", fontcolor)
            .css("font-size", wscreen * 0.05);
          $("#lmonth")
            .html(tmonth)
            .css("color", fontcolor)
            .css("font-size", wscreen * 0.05);
          $("#lyear")
            .html(year)
            .css("color", fontcolor)
            .css("font-size", wscreen * 0.05);
          $("#licopist").html(icopist);
          $("#licopist").css("height", wframe * 0.1);
          $("#licopist").css("width", wframe * 0.1);
          $("#lpist")
            .html(txtpist)
            .css("color", fontcolor)
            .css("font-size", wscreen * 0.05);
          ftext = words[5].substring(1, words[5].length);
          fsize = 16;

          $("#today")
            .html(
              ftext
                .replace(
                  "#",
                  '<img src="/img/ic_st1.png"' +
                    ' style="width:' +
                    fsize +
                    "px; height:" +
                    fsize +
                    'px;"' +
                    ">"
                )
                .replace(
                  "*",
                  '<img src="/img/ic_st2.png"' +
                    ' style="width:' +
                    fsize +
                    "px; height:" +
                    fsize +
                    'px;"' +
                    ">"
                )
                .replace(
                  "+",
                  '<img src="/img/ic_st3.png"' +
                    ' style="width:' +
                    fsize +
                    "px; height:" +
                    fsize +
                    'px;"' +
                    ">"
                )
                .replace(
                  "@",
                  '<img src="/img/ic_st4.png"' +
                    ' style="width:' +
                    fsize +
                    "px; height:" +
                    fsize +
                    'px;"' +
                    ">"
                )
                .replace(
                  "&",
                  '<img src="/img/ic_st5.png"' +
                    ' style="width:' +
                    fsize +
                    "px; height:" +
                    fsize +
                    'px;"' +
                    ">"
                ) +
                "<p>" +
                words[7].replace("*", " ")
            )
            .append("<hr><small>" + words[6].replace("*", "") + "</small>");
          $("#ico").css("height", wframe * 0.1);
          $("#ico").css("width", wframe * 0.1);
        }
      }
    }
  );
});
