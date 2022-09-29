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

$(document).ready(() => {
  $("#calendar")[0].src = "https://prayer-service.online/u-widget.html";

  setInterval(() => {
    $("#calendar")[0].src = "https://prayer-service.online/u-widget.html";
  }, 60000)
})