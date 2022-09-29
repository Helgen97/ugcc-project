$("#edit-news-area").summernote({
  placeholder: "Текст новини",
  tabsize: 3,
  height: 300,
  lang: "uk-UA",
  toolbar: [
    // [groupName, [list of button]]
    ["fontstyle", ["bold", "italic", "underline", "clear"]],
    ["fontsize", ["fontsize"]],
    ["fontfamily", ["fontname"]],
    ["color", ["color"]],
    ["para", ["ul", "ol", "paragraph"]],
    ["insert", ["link", "picture", "video"]],
    ["misk", ["undo", "redo"]],
    ["misk", ["fullscreen", "help"]],
  ],
});

let blocks = [...$("#blocks>div")];

function hideBlocks() {
  blocks.forEach((el) => {
    el.style.display = "none";
  });
}

function createAlert(alertType, alertText) {
  return `<div class="alert alert-${alertType} alert-dismissible fade show" role="alert">
  ${alertText}
  <button type="button" class="close" data-dismiss="alert" aria-label="Close">
    <span aria-hidden="true">&times;</span>
  </button>
</div>`;
}

$("#news_list").on("click", (e) => {
  e.preventDefault();
  hideBlocks();
  $("#news-list-menu").toggle();
});


let addNewsOpenBlockButton = $("#add-news-openBlock-btn");
let addNewsMenu = $("#add-news-menu");
let addNewsSectionInput = $("#add-news-section-input");
let mainImageInput = $("#add-news-main-image-input");
let mainImageUploadButton = $("#add-news-main-image-upload-btn");
let mainImagePreview = $("#add-news-main-image-preview");
let addNewsTitleInput = $("#add-news-name-input");
let addNewsDescriptionInput = $("#add-news-description-input");
let addNewsTextInput = $("#add-news-text-input");
let addNewsForm = $("#add-news-form");
let addNewsButton = $("#add-news-btn");

addNewsTextInput.summernote({
  placeholder: "Текст новини",
  tabsize: 3,
  height: 300,
  lang: "uk-UA",
  toolbar: [
    // [groupName, [list of button]]
    ["fontstyle", ["bold", "italic", "underline", "clear"]],
    ["fontsize", ["fontsize"]],
    ["fontfamily", ["fontname"]],
    ["color", ["color"]],
    ["para", ["ul", "ol", "paragraph"]],
    ["insert", ["link", "picture", "video"]],
    ["misk", ["undo", "redo"]],
    ["misk", ["fullscreen", "help"]],
  ],
});

addNewsOpenBlockButton.on("click", (e) => {
  e.preventDefault();
  hideBlocks();
  addNewsMenu.toggle();
});

addNewsButton.on("click", () => {
  let news = {
    section: addNewsSectionInput.val(),
    title: addNewsTitleInput.val(),
    description: addNewsDescriptionInput.val(),
    text: addNewsTextInput.summernote('code'),
  }
  //Complete ajax to post new news;
  addNewsForm.prepend(createAlert("success", `Новина успішно створена, <a href="#">переглянути</a>`))
})

mainImageInput.on('change', function () {
  const [file] = mainImageInput[0].files;
  if (file) {
    mainImagePreview[0].src = URL.createObjectURL(file);
  }
  $(this).next(".custom-file-label").html($(this).val());
})

mainImageUploadButton.on('click', () => {
  const file = mainImageInput[0].files[0];
  // let formData = new FormData();
  // formData.append("file", file)
  // $.ajax({
  //   type: "POST",
  //   enctype: 'multipart/form-data',
  //   url: "http://localhost:8080/upload",
  //   cache: false,
  //   data: formData,
  //   processData: false,
  //   contentType: false,
  // })
})


let editNewsButtons = [...$(".edit-news-btn")];
let deleteNewsButtons = [...$(".delete-news-btn")];

editNewsButtons.forEach((button) => {
  $(button).on('click', function () {
    let newsID = $(this)[0].dataset.newsid;
  })
});


let articlePageButtons = [...$(".article-page-btn")];
let articleMenu = $("#article-edit-menu");
let articleImageDescriptionInput = $("#article-image-description-input");
let articleTextArea = $("#article-text-area");
let articleEditButton = $("#article-edit-btn");
let articleEditForm = $("#article-edit-form");

articleTextArea.summernote({
  placeholder: "Текст статті",
  tabsize: 3,
  height: 300,
  lang: "uk-UA",
  toolbar: [
    ["fontstyle", ["bold", "italic", "underline", "clear"]],
    ["fontsize", ["fontsize"]],
    ["fontfamily", ["fontname"]],
    ["color", ["color"]],
    ["para", ["ul", "ol", "paragraph"]],
    ["insert", ["link", "picture", "video"]],
    ["misk", ["undo", "redo"]],
    ["misk", ["fullscreen", "help"]],
  ],
});

articlePageButtons.forEach((button) => {
  $(button).on('click', (event) => {
    event.preventDefault();
    hideBlocks();
    articleMenu.toggle();
  });
});

articleEditButton.on("click", () => {
  let article = {
    mainImageURL: "",
    mainImageDescription: articleImageDescriptionInput[0].value,
    articleText: articleTextArea.summernote('code'),
  }

  articleEditForm.prepend(createAlert("success", "Дані сторінки успішно змінено"));
})



let contactMenuOpenBlockButton = $("#contact-menu-openBlock-btn");
let contactMenu = $("#contacts-edit-menu");
let townInput = $("#town-input");
let addressInput = $("#address-input");
let countryInput = $("#country-input");
let phoneInput = $("#phone-input");
let emailInput = $("#email-input");
let changeContactsButton = $("#change-contacts-btn");
let changeContactsForm = $("#change-contacts-form");

contactMenuOpenBlockButton.on("click", (e) => {
  e.preventDefault();
  hideBlocks();
  contactMenu.toggle();
});

changeContactsButton.on("click", () => {
  let contacts = {
    town: townInput[0].value,
    address: addressInput[0].value,
    country: countryInput[0].value,
    phone: phoneInput[0].value,
    email: emailInput[0].value,
  };

  // Complete ajax to change contacts

  changeContactsForm.prepend(
    createAlert("success", "Контактні данні успішно змінено!")
  );
});


let changeCredentialsOpenBlockButton = $("#change-credentials-openBlock-btn");
let changeCredentialsMenu = $("#change-credentials-menu");
let loginInput = $("#login-input");
let passwordInput = $("#password-input");
let changeCredentialsButton = $("#change-credentials-btn");
let changeCredentialsForm = $("#change-credentials-form");

changeCredentialsOpenBlockButton.on("click", (e) => {
  e.preventDefault();
  hideBlocks();
  changeCredentialsMenu.toggle();
});

changeCredentialsButton.on("click", () => {
  let userParams = {
    login: loginInput[0].value,
    password: passwordInput[0].value,
  };
  changeCredentialsForm.prepend(
    createAlert("success", "Дані для входу успішно змінено")
  );

  // Comlete ajax request to change credentials
});
