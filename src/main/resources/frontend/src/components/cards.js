const createSectionCard = ({
                               namedId,
                               imageUrl,
                               imagesUrls = [],
                               title,
                               creationDate,
                               description
                           },
                           urlPart,
                           isDescriptionNeeded = false) => {
    let card = document.createElement("a");
    card.setAttribute("href", `/${urlPart}/${namedId}`)
    card.setAttribute("class", "section");

    let imageContainer = document.createElement("div");
    imageContainer.setAttribute("class", "section__image-container");

    let image = document.createElement("img");
    image.setAttribute("src", imageUrl ? imageUrl : imagesUrls[0]);
    image.setAttribute("loading", "lazy");
    image.setAttribute("alt", title);
    image.setAttribute("class", "section__image");

    let sectionInfoContainer = document.createElement("div");
    sectionInfoContainer.setAttribute("class", "section__info");

    let dateContainer = document.createElement("div");
    dateContainer.setAttribute("class", "section__info__date");

    let date = document.createElement("p");
    date.innerText = creationDate;

    let titleContainer = document.createElement("div");
    titleContainer.setAttribute("class", "section__info__title");

    let newsTitle = document.createElement("p");
    newsTitle.innerText = title;

    imageContainer.append(image);
    dateContainer.append(date);
    titleContainer.append(newsTitle);
    sectionInfoContainer.append(dateContainer, titleContainer);

    if (isDescriptionNeeded) {
        let descriptionContainer = document.createElement("div");
        descriptionContainer.setAttribute("class", "section__info__description")

        let descriptionParagraph = document.createElement("p")
        descriptionParagraph.innerText = description;

        descriptionContainer.append(descriptionParagraph);
        sectionInfoContainer.append(descriptionContainer);
    }

    card.append(imageContainer, sectionInfoContainer);

    return card;
}

const createDocumentCard = ({namedId, title, creationDate}) => {
    let card = document.createElement("a");
    card.setAttribute("href", `/documents/${namedId}`)
    card.setAttribute("class", "document");

    let iconContainer = document.createElement("div");
    iconContainer.setAttribute("class", "document__icon");


    let documentInfoContainer = document.createElement("div");
    documentInfoContainer.setAttribute("class", "document__info");

    let documentTitle = document.createElement("p");
    documentTitle.setAttribute("class", "document__info__title");
    documentTitle.innerText = title;

    let date = document.createElement("p");
    date.setAttribute("class", "document__info__date");
    date.innerText = creationDate;


    documentInfoContainer.append(documentTitle, date);
    card.append(iconContainer, documentInfoContainer);

    return card;
}

const createArticleCard = ({namedId, title}) => {
    let card = document.createElement("a");
    card.setAttribute("href", `/article/${namedId}`)
    card.setAttribute("class", "other-article");

    let iconContainer = document.createElement("div");
    iconContainer.setAttribute("class", "other-article__icon");


    let articleTitle = document.createElement("div");
    articleTitle.setAttribute("class", "other-article__title");
    articleTitle.innerText = title;

    card.append(iconContainer, articleTitle);

    return card;
}

export {
    createSectionCard,
    createDocumentCard,
    createArticleCard
};