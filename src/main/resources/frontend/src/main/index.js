import '../styles/css/normalize.css';
import '../styles/css/itc-slider.min.css';
import '../styles/scss/index.scss';

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

let isBroadcastsLoaded = false;

function createBroadcastCard({
                                 churchFrom,
                                 churchName,
                                 churchTown,
                                 imageUrl,
                                 schedule = "",
                                 youtubeLink,
                                 instagramLink
                             }) {
    let card = document.createElement("div");
    card.setAttribute("class", "modal__dialog-content__block");

    let title = document.createElement("p");
    title.setAttribute("class", "modal__dialog-content__block-title");
    title.innerText = "Трансляція";

    let churchFromParagraph = document.createElement("p");
    churchFromParagraph.innerText = churchFrom;

    let churchNameParagraph = document.createElement("p");
    churchNameParagraph.innerText = churchName;

    let churchTownParagraph = document.createElement("p");
    churchTownParagraph.innerText = `(${churchTown})`;

    let image = document.createElement("img");
    image.setAttribute("src", imageUrl);
    image.setAttribute("alt", `Трансляція ${churchFrom} ${churchName}`);
    image.setAttribute("class", "modal__dialog-content__block-image");

    let scheduleTitleParagraph = document.createElement("p");
    scheduleTitleParagraph.innerText = "Розклад";

    let scheduleParagraphs = schedule.split("\n").map((schedulePart) => {
        let p = document.createElement("p");
        p.innerText = schedulePart;
        return p;
    })

    card.append(title, churchFromParagraph, churchNameParagraph, image, scheduleTitleParagraph);
    scheduleParagraphs.forEach(paragraph => card.append(paragraph));

    if (youtubeLink) {
        let youtubeLinkElement = document.createElement("a");
        youtubeLinkElement.setAttribute("href", youtubeLink);
        youtubeLinkElement.setAttribute("target", "_blank");
        youtubeLinkElement.setAttribute("class", "social-btn youtube");
        youtubeLinkElement.innerText = "YouTube";
        card.append(youtubeLinkElement);
    }

    if (instagramLink) {
        let link = document.createElement("a");
        link.setAttribute("href", instagramLink);
        link.setAttribute("target", "_blank");
        link.setAttribute("class", "social-btn instagram");
        link.innerText = "Instagram";
        card.append(link);
    }

    return card;
}

async function loadBroadcasts() {
    await fetch("/api/broadcasts")
        .then((response) => {
            return response.json();
        }).then((broadcasts) => {
            isBroadcastsLoaded = true;
            let broadcastCards = broadcasts.map((broadcast) => createBroadcastCard(broadcast));
            modalContent.append(broadcastCards);
        }).catch((error) => {
            console.error(error);
        });
}

modalOpenButton.on("click", async (event) => {
    event.preventDefault();
    if (!isBroadcastsLoaded) await loadBroadcasts();
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