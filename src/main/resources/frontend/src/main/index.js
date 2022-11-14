import '../styles/css/normalize.css';
import '../styles/css/itc-slider.min.css';
import '../styles/css/index.css';

import $ from 'jquery';
import '../lib/slider/itc-slider.min.js';
import 'bootstrap/js/dist/carousel';


let modalOpenButton = $("#modal");
let modalWindowContainer = $(".modal");

let mobileMenuOpenButton = $("#mobile_menu");
let mobileMenuContainer = $(".navigation__mobile");

let mobileSearchOpenButton = $("#search_button");
let mobileSearchContainer = $(".navigation__search");
let mobileSearchInput = $("#mobile_search_input");

let desktopSearchInput = $("#desktop_search_input");
let desktopSearchButton = $("#desktop_search_button");

let modalContent = $(".modal__dialog-content");

function createBroadcastCard(broadcastTitle, imageSource, broadcastLink) {
    let card = document.createElement("div");
    card.setAttribute("class", "modal__dialog-content__block");

    let title = document.createElement("p");
    title.setAttribute("class", "modal__dialog-content__block-title");
    title.innerText = "Трансляція";

    let image = document.createElement("img");
    image.setAttribute("src", imageSource);
    image.setAttribute("alt", broadcastTitle);
    image.setAttribute("class", "modal__dialog-content__block-image");

    let link = document.createElement("a");
    link.setAttribute("href", broadcastLink);
    link.setAttribute("target", "_blank");
    link.setAttribute("class", "social-btn youtube");
    link.innerText = "YouTube";

    card.append(title, image, link);
    return card;
}


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
        let baseUrl = window.location.origin;
        window.location.href = `${baseUrl}/search?query=${event.target.value}`
    }
});

desktopSearchButton.on("click", () => {
    let baseUrl = window.location.origin;
    window.location.href = `${baseUrl}/search?query=${desktopSearchInput.val()}`
});

desktopSearchInput.on("keyup", (event) => {
    if (event.key === "Enter") {
        let baseUrl = window.location.origin;
        window.location.href = `${baseUrl}/search?query=${event.target.value}`
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
        event.preventDefault();
        currentTarget.parent().toggleClass("isActive");
    }

    if (mobileSearchContainer.has(currentTarget).length === 0) {
        mobileSearchContainer.removeClass("isActive");
    }
});

$(document).ready(() => {
    $("#calendar")[0].src = "https://prayer-service.online/u-widget.html";

    setInterval(() => {
        $("#calendar")[0].src = "https://prayer-service.online/u-widget.html";
    }, 45000)
})