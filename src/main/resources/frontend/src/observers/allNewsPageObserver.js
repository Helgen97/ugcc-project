import {createSectionCard} from "../components/cards";
import observer from "./observer";

let exarchateBlockEnd = document.getElementById("exarchateBlockEnd");
let churchNewsContainer = document.getElementById("churchNewsContainer");
let churchBlockEnd = document.getElementById("churchBlockEnd");
let publicationsContainer = document.getElementById("publicationsContainer");

async function loadNewsBySectionIdAndAppendThemAsCardsToBlock(sectionId, blockToAppend) {
    await fetch(`/api/news/pages?sectionId=${sectionId}&page=0&size=5`)
        .then((result) => {
            return result.json()
        })
        .then(({content}) => {
            let newsCards = content.map((news) => createSectionCard(news, "news", true));
            blockToAppend.append(...newsCards);
        })
        .catch((error) => {
            console.error(error);
        });
}

async function loadSecondSection() {
    await loadNewsBySectionIdAndAppendThemAsCardsToBlock(2, churchNewsContainer);
}

async function loadThirdSection() {
    await loadNewsBySectionIdAndAppendThemAsCardsToBlock(3, publicationsContainer);
}

let churchNewsObserver = observer(loadSecondSection)
let publicationsObserver = observer(loadThirdSection)

churchNewsObserver.observe(exarchateBlockEnd);
publicationsObserver.observe(churchBlockEnd)
