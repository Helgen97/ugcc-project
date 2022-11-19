import {createSectionCard} from "../components/cards";
import observer from "./observer";

let mainNewsEnd = document.getElementById("mainNewsEnd");
let otherNewsListContainer = document.getElementById("otherNewsListContainer");

async function loadRandomNewsAndAppendThemAsCardsToBlock() {
    await fetch(`/api/news/pages/random`)
        .then((result) => {
            return result.json()
        })
        .then(({content}) => {
            let newsCards = content.map((news) => createSectionCard(news, "news"));
            otherNewsListContainer.append(...newsCards);
        })
        .catch((error) => {
            console.error(error);
        });
}

let newsPageObserver = observer(loadRandomNewsAndAppendThemAsCardsToBlock)

newsPageObserver.observe(mainNewsEnd);