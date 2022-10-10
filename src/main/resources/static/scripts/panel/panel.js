const TITLE_MAX_LENGTH = 200;
const DESCRIPTION_MAX_LENGTH = 500;
const TEXT_MAX_LENGTH = 5000;
const URL_MAX_LENGTH = 300;
const IMAGE_DESCRIPTION_LENGTH = 120;
const CONTACT_FIELDS_MAX_LENGTH = 150;

//------------

let blocks = [...$("#blocks>div")];

//------------

let newsOpenBlockButton = $("#news-openBlockBtn");
let newsMenu = $("#news-menu");
let newsBlockTitle = $("#news-blockTitle");
let newsImageInput = $("#news-imageInput");
let newsImageUploadButton = $("#news-imageUploadBtn");
let newsImagePreview = $("#news-imagePreview");
let newsSectionInput = $("#news-sectionInput");
let newsTitleInput = $("#news-titleInput");
let newsDescriptionInput = $("#news-descriptionInput");
let newsTextInput = $("#news-textInput");
let newsForm = $("#news-form");
let newsApplyButton = $("#news-btn");

//------------

let newsListNextPage = 1;
let newsListOpenBlockButton = $("#newsList-openBlockBtn");
let newsListMenu = $("#newsList-menu");
let newsList = $("#newsList")
let newsListFetchPageOFNewsButton = $("#newsList-moreBtn");
let editNewsButtons = [...$("#newsList .edit-btn")];
let deleteNewsButtons = [...$("#newsList .delete-btn")];

//------------

let documentsOpenBlockButton = $("#document-openBlockBtn");
let documentMenu = $("#document-menu");
let documentBlockTitle = $("#document-blockTitle");
let documentSectionInput = $("#document-sectionInput");
let documentTitleInput = $("#document-titleInput");
let documentDescriptionInput = $("#document-descriptionInput");
let documentImageInput = $("#document-imageInput");
let documentImageUploadButton = $("#document-imageUploadBtn");
let documentImagePreview = $("#document-imagePreview");
let documentFileInput = $("#document-fileInput");
let documentFileUploadButton = $("#document-fileUploadBtn");
let documentFileAlertBlock = $("#document-fileAlerts");
let documentForm = $("#document-form");
let documentApplyButton = $("#document-btn");

//------------

let documentsNextPage = 1;
let documentListOpenBlockButton = $("#documentList-openBlockBtn");
let documentListMenu = $("#documentList-menu");
let documentsList = $("#documentsList");
let documentListFetchPageButton = $("#documentList-moreBtn");
let editDocumentButtons = [...$("#documentsList .edit-btn")];
let deleteDocumentButtons = [...$("#documentsList .delete-btn")];

//------------

let articleMenuOpenButtons = [...$(".articlePage-btn")];
let articleMenu = $("#article-menu");
let articleForm = $("#articleForm")
let articleBlockTitle = $("#article-blockTitle");
let articleImageInput = $("#article-imageInput");
let articleImagePreview = $("#article-imagePreview");
let articleImageUploadButton = $("#article-imageUploadBtn");
let articleImageDescriptionInput = $("#article-imageDescriptionInput");
let articleTextInput = $("#article-text");
let articleApplyButton = $("#article-editBtn");

//------------

let contactMenuOpenBlockButton = $("#contacts-openBlockBtn");
let contactMenu = $("#contacts-menu");
let contactsForm = $("#contactsForm");
let contactsTownInput = $("#contacts-townInput");
let contactsAddressInput = $("#contacts-addressInput");
let contactsCountryInput = $("#contacts-countryInput");
let contactsPhoneInput = $("#contacts-phoneInput");
let contactsEmailInput = $("#contacts-emailInput");
let contactsApplyButton = $("#contacts-applyBtn");

//------------

let news = {
    title: "",
    section: {
        id: "0"
    },
    description: "",
    imageURL: "",
    text: "",
};

let doc = {
    title: "",
    section: {
        id: "0",
    },
    description: "",
    imageURL: "",
    documentURL: "",
}

let article = {
    id: 0,
    title: "",
    section: {
        id: "0"
    },
    imageURL: "",
    imageDescription: "",
    text: "",
}

let contacts = {
    id: 0,
    townAndIndex: '',
    address: '',
    country: '',
    phone: '',
    email: ''
}

//------------

function hideBlocks() {
    blocks.forEach((el) => {
        el.style.display = "none";
    });
}

function createAlert(alertType, alertText) {
    let alert = document.createElement("div");
    alert.setAttribute("class", `alert alert-${alertType} alert-dismissible fade show`);
    alert.setAttribute("role", "alert");
    alert.innerText = alertText;

    let alertButton = document.createElement("button");
    alertButton.setAttribute("type", "button");
    alertButton.setAttribute("class", "close");
    alertButton.setAttribute("data-dismiss", "alert");
    alertButton.setAttribute("aria-label", "Close");

    let alertButtonText = document.createElement("span");
    alertButtonText.setAttribute("aria-hidden", "true");
    alertButtonText.innerHTML = `&times;`;

    alertButton.append(alertButtonText);
    alert.append(alertButton);

    return alert;
}

function scrollTo(element) {
    $("html,body").animate({
        scrollTop: $(element).offset().top
    }, 500);

}

function createPrependAndScrollToAlert(alertType, alertText, wherePrepend) {
    let alert = createAlert(alertType, alertText);
    $(wherePrepend).prepend(alert);
    scrollTo(alert);
}

function createCard(title, id, editFunction, deleteFunction) {
    let card = document.createElement("div");
    card.setAttribute("class", "card mb-3");

    let cardBody = document.createElement("div");
    cardBody.setAttribute("class", "card-body");

    let h5 = document.createElement("h5");
    h5.setAttribute("class", "card-title");
    h5.innerText = `${id}. ${title}`;

    let cardText = document.createElement("div");
    cardText.setAttribute("class", "d-flex justify-content-between text-center");

    let editButton = document.createElement("button");
    editButton.setAttribute("class", "w-75 mr-2 btn btn-primary edit-btn");
    editButton.setAttribute("data-id", `${id}`);
    editButton.innerText = "Редагувати";
    editButton.addEventListener('click', editFunction);

    let deleteButton = document.createElement("button");
    deleteButton.setAttribute("class", "w-25 ml-2 btn btn-danger delete-btn");
    deleteButton.setAttribute("data-id", `${id}`);
    deleteButton.innerText = "Видалити";
    deleteButton.addEventListener('click', deleteFunction);

    cardText.append(editButton);
    cardText.append(deleteButton);
    cardBody.append(h5);
    cardBody.append(cardText)
    card.append(cardBody);

    return card;
}

function inputKeyUp(input, object, field, maxLength) {
    let value = $(input).val();
    object[field] = value;
    $(input).next(".inputMaxLength").text(maxLength > value.length ? `Залишилось символів: ${maxLength - value.length}` : "Перевищено ліміт символів");
}

function summernoteInputKeyUp(input, object, field, maxLength) {
    let value = $(input).summernote('code');
    object[field] = value;
    $(input).next().next(".inputMaxLength").text(maxLength > value.length ? `Залишилось символів: ${maxLength - value.length}` : "Перевищено ліміт символів")
}

function imageInputChange(imageInput, imagePreview) {
    const [file] = imageInput.files;
    if (file) {
        imagePreview.src = URL.createObjectURL(file);
    }
    $(imageInput).next(".custom-file-label").html($(imageInput).val());
}

async function uploadImage(fieldToPutData, imageInput, imagePreview) {
    const [file] = imageInput.files;
    if (!file) {
        createPrependAndScrollToAlert("warning", "Спочатку виберіть зображення.", imagePreview);
        return
    }
    let formData = new FormData();
    formData.append("file", file);

    await axios({
        method: "post",
        url: "/api/upload?folder=images",
        enctype: 'multipart/form-data',
        cache: false,
        data: formData,
        processData: false,
        contentType: false,
    }).then(function (response) {
        fieldToPutData["imageURL"] = response.data;
        createPrependAndScrollToAlert("success", "Зображення успішно завантажено.", imagePreview);
    }).catch(function (error) {
        console.error(error)
        createPrependAndScrollToAlert("danger", "Сталася помилка. Зображення не завантажено. Спробуйте ще раз.", imagePreview);
    });
}

async function uploadFile() {
    const [file] = documentFileInput[0].files;
    if (!file) {
        createPrependAndScrollToAlert("warning", "Спочатку виберіть файл.", documentFileAlertBlock);
        return;
    }
    let formData = new FormData();
    formData.append("file", file);

    await axios({
        method: "post",
        url: "/api/upload?folder=documents",
        enctype: 'multipart/form-data',
        cache: false,
        data: formData,
        processData: false,
        contentType: false,
    }).then(function (response) {
        doc["documentURL"] = response.data;
        createPrependAndScrollToAlert("success", "Файл успішно завантажено.", documentFileAlertBlock);
    }).catch(function (error) {
        console.error(error)
        createPrependAndScrollToAlert("danger", "Сталася помилка. Файл не завантажено. Спробуйте ще раз.", documentFileAlertBlock);
    });
}

//----------

function resetNewsInputsAndObject() {
    news = {
        title: "",
        description: "",
        section: {
            id: "0"
        },
        imageURL: "",
        text: "",
    };
    newsBlockTitle.text("Створити новину");
    newsSectionInput.val("0").change();
    newsImageInput.val("").keyup();
    newsImageInput.next(".custom-file-label").html("Виберіть зображення");
    newsImagePreview[0].src = "./imgs/uploadPhoto.png";
    newsTitleInput.val("").keyup();
    newsDescriptionInput.val("").keyup();
    newsTextInput.summernote("code", "");
    newsApplyButton.text("Створити");
    newsApplyButton.attr("data-purpose", "create");
}

function prepareFormToEditNews() {
    newsBlockTitle.text("Редагування новини")
    newsSectionInput.val(news.section.id).change();
    newsImageInput.next(".custom-file-label").html(news.imageURL);
    newsImagePreview[0].src = news.imageURL;
    newsTitleInput.val(news.title).keyup();
    newsDescriptionInput.val(news.description).keyup();
    newsTextInput.summernote('code', news.text);
    newsTextInput.keyup();
    newsApplyButton.text("Редагувати")
    newsApplyButton.attr("data-purpose", "change")
}

function newsImageInputChange() {
    imageInputChange(newsImageInput[0], newsImagePreview[0]);
}

async function newsUploadImage() {
    await uploadImage(news, newsImageInput[0], newsImagePreview.parent());
}

function isOneOfNewsFieldsIsEmpty() {
    return (newsSectionInput.val() === "0" || news.section.id === "0") ||
        news.title.length === 0 ||
        news.description.length === 0 ||
        news.imageURL.length === 0 ||
        news.text.length === 0
}

function isOneOfNewsFieldsIsReachSymbolsLimit() {
    return news.title.length > TITLE_MAX_LENGTH ||
        news.description.length > DESCRIPTION_MAX_LENGTH ||
        news.imageURL.length > URL_MAX_LENGTH ||
        news.text.length > TEXT_MAX_LENGTH
}

function isOneOfNewsFieldIsEmptyOrReachSymbolsLimit() {
    if (isOneOfNewsFieldsIsEmpty()) {
        createPrependAndScrollToAlert("warning", " Заповніть порожні поля!", newsForm)
        return true;
    } else if (isOneOfNewsFieldsIsReachSymbolsLimit()) {
        createPrependAndScrollToAlert("warning", " Деякі поля перевищили ліміт символів!", newsForm)
        return true;
    }
    return false;
}

async function createNews() {
    await axios.post(
        `/api/news?sectionId=${newsSectionInput.val()}`,
        JSON.stringify(news), {
            headers: {
                'Content-Type': "application/json"
            }
        }
    ).then(function (response) {
        createPrependAndScrollToAlert("success", `Новина успішно створена`, newsForm);
        newsList.prepend(createCard(response.data.title, response.data.id, editNewsButtonClick, deleteNewsButtonClick))
    }).catch(function (error) {
        console.error(error)
        createPrependAndScrollToAlert("danger", `Помилка при створенні новини. Спробуйте ще раз`, newsForm);
    });
}

async function changeNews() {
    await axios.put(
        "/api/news",
        JSON.stringify(news),
        {
            headers: {
                'Content-Type': "application/json"
            }
        }
    ).then(function (response) {
        createPrependAndScrollToAlert("success", `Новина успішно змінена`, newsForm);
    }).catch(function (error) {
        console.error(error);
        createPrependAndScrollToAlert("danger", `Помилка при редагуванні новини. Спробуйте ще раз`, newsForm);
    })
}

newsOpenBlockButton.on("click", (e) => {
    e.preventDefault();
    hideBlocks();
    resetNewsInputsAndObject();
    newsMenu.toggle();
});

newsSectionInput.on('change', function () {
    news.section.id = $(this).val();
})

newsImageInput.on('change', newsImageInputChange);

newsImageUploadButton.on('click', newsUploadImage);

newsTitleInput.on('keyup', function () {
    inputKeyUp(this, news, "title", TITLE_MAX_LENGTH);
});

newsDescriptionInput.on('keyup', function () {
    inputKeyUp(this, news, "description", DESCRIPTION_MAX_LENGTH);
});

newsTextInput.summernote({
    placeholder: "Ваш текст новини...",
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
    callbacks: {
        onKeyup: function () {
            summernoteInputKeyUp(this, news, "text", TEXT_MAX_LENGTH);
        }
    }
});

newsApplyButton.on("click", async function () {
    if (isOneOfNewsFieldIsEmptyOrReachSymbolsLimit()) {
        return;
    }

    let buttonPurpose = this.dataset.purpose;
    if (buttonPurpose === "create") {
        await createNews();
    } else if (buttonPurpose === "change") {
        await changeNews();
    }
})

//----------

async function editNewsButtonClick() {
    let newsID = $(this)[0].dataset.id;
    let card = $(this).parent().parent().parent();

    if (confirm("Ви дійсно бажаєте редагувати цю новину?")) {
        await axios.get(`/api/news/${newsID}`).then(function (response) {
            news = response.data;
            prepareFormToEditNews();
            hideBlocks();
            newsMenu.toggle();
        }).catch(function (error) {
            console.error(error);
            createPrependAndScrollToAlert("danger", "Сталася помилка. Перезавантажте сторінку та спробуйте ще раз!", card.parent())
        })
    }
}

async function deleteNewsButtonClick() {
    let newsID = $(this)[0].dataset.id;
    let card = $(this).parent().parent().parent();

    if (confirm("Ви дійсно бажаєте видалити цю новину?")) {
        await axios.delete(`/api/news/${newsID}`).then(function () {
            card.toggle();
        }).catch(function (error) {
            console.error(error);
            let alert = createAlert("danger", "При видаленні сталася помилка.")
            card.parent().prepend(alert);
            scrollTo(alert);
        })
    }
}

newsListOpenBlockButton.on("click", (e) => {
    e.preventDefault();
    hideBlocks();
    newsListMenu.toggle();
});

newsListFetchPageOFNewsButton.on("click", async function () {
    if (this.dataset.totalPages.toString() === newsListNextPage.toString()) {
        this.setAttribute("disabled", "");
        createPrependAndScrollToAlert("warning", "Загружені всі новини! ", newsList);
        return;
    }

    await axios.get(`api/news/pages?page=${newsListNextPage}&size=10`)
        .then(function (response) {
            let newsCards = response.data.content.map(
                (news) => createCard(news.title, news.id, editNewsButtonClick, deleteNewsButtonClick)
            );
            newsList.append(newsCards);
            newsListNextPage++;
        })
        .catch(function (error) {
            console.error(error);
            createPrependAndScrollToAlert("danger", "При завантаженні інших новин сталася помилка", newsList);
        })
})

editNewsButtons.forEach((button) => {
    $(button).on('click', editNewsButtonClick)
});

deleteNewsButtons.forEach((button) => {
    $(button).on('click', deleteNewsButtonClick)
});

//----------

function resetDocumentsInputsAndObject() {
    doc = {
        title: "",
        section: {
            id: "0",
        },
        description: "",
        imageURL: "",
        documentURL: "",
    };
    documentBlockTitle.text("Створити документ");
    documentSectionInput.val("0").change();
    documentTitleInput.val("").keyup();
    documentDescriptionInput.val("").keyup();
    documentImageInput.val("");
    documentImageInput.next(".custom-file-label").html("Виберіть зображення");
    documentImagePreview[0].src = "./imgs/uploadPhoto.png";
    documentFileInput.val("");
    documentFileInput.next(".custom-file-label").html("Виберіть файл");
    documentApplyButton.text("Створити документ");
    documentApplyButton.attr("data-purpose", "create");
}

function prepareFormToEditDocument() {
    documentBlockTitle.text("Редагувати документ");
    documentSectionInput.val(doc.section.id).change();
    documentTitleInput.val(doc.title).keyup()
    documentDescriptionInput.val(doc.description).keyup();
    documentImageInput.val("");
    documentImageInput.next(".custom-file-label").html(doc.imageURL);
    documentImagePreview[0].src = doc.imageURL;
    documentFileInput.val("");
    documentFileInput.next(".custom-file-label").html(doc.documentURL);
    documentApplyButton.text("Змінити документ");
    documentApplyButton.attr("data-purpose", "change");
}

async function documentUploadImage() {
    await uploadImage(doc, documentImageInput[0], documentImagePreview.parent());
}

function documentsImageInputChange() {
    imageInputChange(documentImageInput[0], documentImagePreview[0])
}

function isOneOfDocFieldsIsEmpty() {
    return (documentSectionInput.val() === "0" || doc.section.id === "0") ||
        doc.title.length === 0 ||
        doc.description.length === 0 ||
        doc.imageURL.length === 0 ||
        doc.documentURL.length === 0
}

function isOneOfDocFieldsIsReachSymbolsLimit() {
    return doc.title.length > TITLE_MAX_LENGTH ||
        doc.description.length > DESCRIPTION_MAX_LENGTH ||
        doc.imageURL.length > URL_MAX_LENGTH ||
        doc.documentURL.length > URL_MAX_LENGTH
}

function isOneOfDocFieldsIsEmptyOrReachSymbolLimit() {
    if (isOneOfDocFieldsIsEmpty()) {
        createPrependAndScrollToAlert("warning", " Заповніть порожні поля!", documentForm)
        return true;
    } else if (isOneOfDocFieldsIsReachSymbolsLimit()) {
        createPrependAndScrollToAlert("warning", " Деякі поля перевищили ліміт символів!", documentForm)
        return true;
    }
    return false;
}

async function createDocument() {
    await axios.post(
        `/api/documents?sectionId=${documentSectionInput.val()}`,
        JSON.stringify(doc), {
            headers: {
                'Content-Type': "application/json"
            }
        }
    ).then(function (response) {
        createPrependAndScrollToAlert("success", `Документ успішно завантажено, <a href='/document/${response.data.id}' target="_blank">переглянути</a>`, documentForm);
        documentsList.prepend(createCard(response.data.title, response.data.id, editDocumentButtonClick, deleteDocumentButtonClick));
    }).catch(function (error) {
        console.error(error)
        createPrependAndScrollToAlert("danger", `Помилка при створенні новини. Спробуйте ще раз`, documentForm);
    });
}

async function changeDocument() {
    await axios.put(
        "/api/documents",
        JSON.stringify(doc),
        {
            headers: {
                'Content-Type': "application/json"
            }
        }
    ).then(function (response) {
        createPrependAndScrollToAlert("success", "Документ успішно змінений", documentForm);
    }).catch(function (error) {
        console.error(error);
        createPrependAndScrollToAlert("danger", `Помилка при редагуванні документа. Спробуйте ще раз`, documentForm);
    })
}

documentsOpenBlockButton.on('click', (e) => {
    e.preventDefault();
    hideBlocks();
    resetDocumentsInputsAndObject();
    documentMenu.toggle();
})

documentSectionInput.on('change', () => {
    doc.section.id = documentSectionInput.val();
})

documentTitleInput.on('keyup', function () {
    inputKeyUp(this, doc, "title", TITLE_MAX_LENGTH);
})

documentDescriptionInput.on('keyup', function () {
    inputKeyUp(this, doc, "description", DESCRIPTION_MAX_LENGTH)
})

documentImageInput.on('change', documentsImageInputChange);

documentImageUploadButton.on('click', documentUploadImage);

documentFileInput.on('change', function () {
    $(this).next(".custom-file-label").html($(this).val());
});

documentFileUploadButton.on('click', async () => uploadFile());

documentApplyButton.on('click', async function () {
    if (isOneOfDocFieldsIsEmptyOrReachSymbolLimit()) {
        return;
    }

    let buttonPurpose = this.dataset.purpose;
    if (buttonPurpose === "create") {
        await createDocument();
    } else if (buttonPurpose === "change") {
        await changeDocument();
    }
})

//----------

async function editDocumentButtonClick() {
    let documentID = $(this)[0].dataset.id;

    if (confirm("Ви дійсно бажаєте редагувати цей документ?")) {
        await axios.get(`/api/documents/${documentID}`).then(function (response) {
            doc = response.data;
            prepareFormToEditDocument();
            hideBlocks();
            documentMenu.toggle();
        }).catch(function (error) {
            console.error(error);
            createPrependAndScrollToAlert("danger", "Сталася помилка. Перезавантажте сторінку та спробуйте ще раз!", documentsList)
        })
    }
}

async function deleteDocumentButtonClick() {
    let documentID = $(this)[0].dataset.id;
    let card = $(this).parent().parent().parent();

    if (confirm("Ви дійсно бажаєте видалити цей документ?")) {
        await axios.delete(`/api/documents/${documentID}`).then(function () {
            card.toggle();
        }).catch(function (error) {
            console.error(error);
            createPrependAndScrollToAlert("danger", "При видаленні сталася помилка.", documentsList)
        })
    }
}

documentListOpenBlockButton.on("click", function (e) {
    e.preventDefault();
    hideBlocks();
    documentListMenu.toggle();
})

documentListFetchPageButton.on("click", async function () {
    if (this.dataset.totalPages.toString() === documentsNextPage.toString()) {
        this.setAttribute("disabled", "");
        createPrependAndScrollToAlert("warning", "Загружені всі документи! ", documentsList)
        return;
    }

    await axios.get(`/api/documents/pages?page=${documentsNextPage}&size=10`)
        .then(function (response) {
            let documentCards = response.data.content.map(
                (document) => createCard(document.id, document.title, editDocumentButtonClick, deleteDocumentButtonClick));
            documentsList.append(documentCards);
            documentsNextPage++;
        })
        .catch(function (error) {
            console.error(error);
            createPrependAndScrollToAlert("danger", "При завантаженні інших документів сталася помилка", documentsList);
        })
})

editDocumentButtons.forEach((button) => {
    $(button).on('click', editDocumentButtonClick);
})

deleteDocumentButtons.forEach((button) => {
    $(button).on('click', deleteNewsButtonClick);
})

//----------

function prepareFormToEditArticle() {
    articleBlockTitle.text(article.title);
    articleImageInput.val("");
    articleImagePreview[0].src = article.imageURL;
    articleImageInput.next(".custom-file-label").html(article.imageURL);
    articleImageDescriptionInput.val(article.imageDescription).keyup();
    articleTextInput.summernote('code', article.text);
}

function articleImageInputChange() {
    imageInputChange(articleImageInput[0], articleImagePreview[0]);
}

async function articleImageUpload() {
    await uploadImage(article, articleImageInput[0], articleImagePreview.parent());
}

function isOneOfArticleFieldsIsEmpty() {
    return article.imageURL.length === 0 ||
        article.imageDescription.length === 0 ||
        article.text.length === 0
}

function isOneOfArticleFieldsIsReachSymbolLimit() {
    return article.imageURL.length > URL_MAX_LENGTH ||
        article.imageDescription.length > IMAGE_DESCRIPTION_LENGTH ||
        article.text.length > TEXT_MAX_LENGTH;
}

function isOneOfArticleFieldsIsEmptyOrReachSymbolLimits() {
    if (isOneOfArticleFieldsIsEmpty()) {
        createPrependAndScrollToAlert("warning", " Заповніть порожні поля!", articleForm)
        return true;
    } else if (isOneOfArticleFieldsIsReachSymbolLimit()) {
        createPrependAndScrollToAlert("warning", " Деякі поля перевищили ліміт символів!", articleForm)
        return true;
    }
    return false;
}

articleMenuOpenButtons.forEach((button) => {
    $(button).on('click', async function () {
        let articleId = this.dataset.id;

        await axios.get(`/api/articles/${articleId}`)
            .then(function (response) {
                article = response.data;
                hideBlocks();
                prepareFormToEditArticle();
                articleMenu.toggle();
            }).catch(function (error) {
                console.error(error);
            })
    })
})

articleImageInput.on('change', articleImageInputChange);

articleImageUploadButton.on('click', articleImageUpload);

articleImageDescriptionInput.on('keyup', function () {
    inputKeyUp(this, article, "imageDescription", IMAGE_DESCRIPTION_LENGTH)
})

articleTextInput.summernote({
    placeholder: "Ваш текст сторінки...",
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
    callbacks: {
        onKeyup: function () {
            summernoteInputKeyUp(this, article, "text", TEXT_MAX_LENGTH)
        }
    }
})

articleApplyButton.on('click', async function () {
    if (isOneOfArticleFieldsIsEmptyOrReachSymbolLimits()) {
        return;
    }

    await axios.put(
        "/api/articles",
        JSON.stringify(article),
        {
            headers: {
                'Content-Type': "application/json"
            }
        })
        .then(function (response) {
            createPrependAndScrollToAlert("success", "Стаття успішно змінена", articleForm);
        })
        .catch(function (error) {
            console.error(error);
            createPrependAndScrollToAlert("danger", `Помилка при редагуванні статті. Спробуйте ще раз`, articleForm);
        })
})

//----------

function prepareFormToEditContacts() {
    contactsTownInput.val(contacts.townAndIndex).keyup();
    contactsAddressInput.val(contacts.address).keyup();
    contactsCountryInput.val(contacts.country).keyup();
    contactsPhoneInput.val(contacts.phone).keyup();
    contactsEmailInput.val(contacts.email).keyup();
}

function isOneOfContactFieldsIsEmpty() {
    return contacts.townAndIndex.length === 0 ||
        contacts.address.length === 0 ||
        contacts.country.length === 0 ||
        contacts.phone.length === 0 ||
        contacts.email.length === 0
}

function isOneOfContactFieldsIsReachSymbolLimit() {
    return contacts.townAndIndex.length > CONTACT_FIELDS_MAX_LENGTH ||
        contacts.address.length > CONTACT_FIELDS_MAX_LENGTH ||
        contacts.country.length > CONTACT_FIELDS_MAX_LENGTH ||
        contacts.phone.length > CONTACT_FIELDS_MAX_LENGTH ||
        contacts.email.length > CONTACT_FIELDS_MAX_LENGTH
}

function isOneOfContactFieldsIsEmptyOrReachSymbolLimits() {
    if (isOneOfContactFieldsIsEmpty()) {
        createPrependAndScrollToAlert("warning", " Заповніть порожні поля!", contactsForm)
        return true;
    } else if (isOneOfContactFieldsIsReachSymbolLimit()) {
        createPrependAndScrollToAlert("warning", " Деякі поля перевищили ліміт символів!", contactsForm)
        return true;
    }
    return false;
}

contactMenuOpenBlockButton.on('click', async function () {
    await axios.get("/api/contacts/1")
        .then(function (response) {
            contacts = response.data;
            hideBlocks();
            prepareFormToEditContacts();
            contactMenu.toggle();
        }).catch(function (error) {
            console.log(error)
        })
})

contactsTownInput.on('keyup', function () {
    inputKeyUp(this, contacts, "townAndIndex", CONTACT_FIELDS_MAX_LENGTH);
})

contactsAddressInput.on('keyup', function () {
    inputKeyUp(this, contacts, "address", CONTACT_FIELDS_MAX_LENGTH);
})

contactsCountryInput.on('keyup', function () {
    inputKeyUp(this, contacts, "country", CONTACT_FIELDS_MAX_LENGTH);
})

contactsPhoneInput.on('keyup', function () {
    inputKeyUp(this, contacts, "phone", CONTACT_FIELDS_MAX_LENGTH);
})

contactsEmailInput.on('keyup', function () {
    inputKeyUp(this, contacts, "email", CONTACT_FIELDS_MAX_LENGTH)
})

contactsApplyButton.on('click', async function () {
    if (isOneOfContactFieldsIsEmptyOrReachSymbolLimits()) {
        return;
    }

    await axios.put('/api/contacts')
        .then(function (response) {
            createPrependAndScrollToAlert("success", "Контактні дані успішно змінені", contactsForm);
        })
        .catch(function (error) {
            console.error(error);
            createPrependAndScrollToAlert("danger", `Помилка при редагуванні контактних данних. Спробуйте ще раз`, contactsForm);
        })
})


