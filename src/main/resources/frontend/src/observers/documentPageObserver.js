import {createDocumentCard} from "../components/cards";
import observer from "./observer";

let mainDocumentEnd = document.getElementById("mainDocumentEnd");
let otherDocumentsContainer = document.getElementById("otherDocumentsContainer");

async function loadRandomDocumentsAndAppendThemToBlock() {
    await fetch(`/api/documents/pages/random`)
        .then((result) => {
            return result.json()
        })
        .then(({content}) => {
            let documentsCards = content.map((document) => createDocumentCard(document));
            otherDocumentsContainer.append(...documentsCards);
        })
        .catch((error) => {
            console.error(error);
        });
}

let documentPageObserver = observer(loadRandomDocumentsAndAppendThemToBlock)

documentPageObserver.observe(mainDocumentEnd);