let modalOpenButton = $("#modal");
let modalWindowContainer = $(".modal");
let mobileMenuOpenButton = $("#mobile_menu");
let mobileMenuContainer = $(".navigation_mobile");
let mobileSearchOpenButton = $("#search_button");
let mobileSearchContainer = $(".navigation_search");
let mobileSearchInputContainer = $(".navigation_search_input-container");
let mobileSearchInput = $("#mobile_search_input");
let desktopSearchInput = $("#desktop_search_input");
let desktopSearchButton = $("#desktop_search_button");

modalOpenButton.on("click", (event) => {
    event.preventDefault();
    modalWindowContainer.addClass("show");
});

mobileMenuOpenButton.on("click", () => {
    mobileMenuOpenButton.parent().parent().addClass("show");
});

mobileSearchOpenButton.on("click", () => {
    mobileSearchInputContainer.toggleClass("show");
});

mobileSearchInput.on("keyup", (event) => {
    if (event.key === "Enter") {
        alert(event.target.value);
    }
})

desktopSearchButton.on("click", () => {
    alert(desktopSearchInput.val());
})

desktopSearchInput.on("keyup", (event) => {
    if (event.key === "Enter") {
        alert(event.target.value);
    }
})


$(document).on('mouseup', (event) => {
    let currentTarget = $(event.target);

    if (
        modalWindowContainer.has(currentTarget).length === 0 ||
        currentTarget.hasClass("close-btn")
    ) {
        modalWindowContainer.removeClass("show");
    }

    if (mobileMenuContainer.has(currentTarget).length === 0) {
        mobileMenuContainer.removeClass("show");
    }

    if (currentTarget.hasClass("dropdown_title")) {
        currentTarget.parent().toggleClass("show");
    }

    if (mobileSearchContainer.has(currentTarget).length === 0) {
        mobileSearchInputContainer.removeClass("show");
    }
});
