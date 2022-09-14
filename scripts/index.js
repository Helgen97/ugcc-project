document.addEventListener('click', (ev) => {
    if(ev.target.classList.contains("dropdown_title")){
        ev.target.parentNode.classList.toggle("show");
    };
})

$(document).on("click", (e) => {
    if(e.target.classList.contains("dropdown_title")) {
        e.target.classList.toggle("show")
    }
})


$("#mobile_menu").on('click', (e) => {
    $("#mobile_menu").parent().parent().addClass("show");
})

$(document).mouseup(function (e) {
    var container = $(".navigation_mobile");
    if (container.has(e.target).length === 0){
        container.removeClass("show");
    }
});

$("#search_button").on("click", () => {
    $(".navigation_search_input-container").toggleClass("show");
})