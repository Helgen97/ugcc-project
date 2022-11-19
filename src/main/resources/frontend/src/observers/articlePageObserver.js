import {createArticleCard} from "../components/cards";
import observer from "./observer";

let mainArticleEnd = document.getElementById("mainArticleEnd");
let otherArticleContainer = document.getElementById("otherArticleContainer");

async function loadRandomArticlesAndAppendThemAsCardsToBlock() {
    await fetch(`/api/articles/pages/random`)
        .then((result) => {
            return result.json()
        })
        .then(({content}) => {
            let articleCards = content.map((article) => createArticleCard(article));
            otherArticleContainer.append(...articleCards);
        })
        .catch((error) => {
            console.error(error);
        });
}

let articlePageObserver = observer(loadRandomArticlesAndAppendThemAsCardsToBlock)

articlePageObserver.observe(mainArticleEnd);