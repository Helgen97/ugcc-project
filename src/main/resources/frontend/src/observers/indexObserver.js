import {createSectionCard} from '../components/cards';
import observer from "./observer";

let churchNewsBlockStart = document.getElementById("churchNewsBlockStart");
let churchSectionsContainer = document.getElementById("churchSectionsContainer");
let publicationBlockStart = document.getElementById("publicationNewsBlockStart");
let publicationSectionsContainer = document.getElementById("publicationSectionsContainer")

async function loadNewsBySectionIdAndAppendThemAsCardsToBlock(sectionId, blockToAppend) {
    await fetch(`/api/news/pages?sectionId=${sectionId}&page=0&size=4`)
        .then((result) => {
            return result.json()
        })
        .then(({content}) => {
            let newsCards = content.map((news) => createSectionCard(news, "news"));
            blockToAppend.append(...newsCards);
        })
        .catch((error) => {
            console.error(error);
        });
}

async function loadSecondSection() {
    await loadNewsBySectionIdAndAppendThemAsCardsToBlock(2, churchSectionsContainer);
}

async function loadThirdSection() {
    await loadNewsBySectionIdAndAppendThemAsCardsToBlock(3, publicationSectionsContainer);
}

let churchNewsObserver = observer(loadSecondSection)

let publicationsObserver = observer(loadThirdSection)

churchNewsObserver.observe(churchNewsBlockStart);
publicationsObserver.observe(publicationBlockStart)

