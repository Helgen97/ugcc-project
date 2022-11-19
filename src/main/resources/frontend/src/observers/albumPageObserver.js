import {createSectionCard} from "../components/cards";
import observer from "./observer";

let mainAlbumEnd = document.getElementById("mainAlbumEnd");
let otherAlbumContainer = document.getElementById("otherAlbumsContainer");

async function loadRandomAlbumsAndAppendThemAsCardsToBlock() {
    await fetch(`/api/albums/pages/random`)
        .then((result) => {
            return result.json()
        })
        .then(({content}) => {
            let albumCards = content.map((album) => createSectionCard(album, "albums"));
            otherAlbumContainer.append(...albumCards);
        })
        .catch((error) => {
            console.error(error);
        });
}

let albumPageObserver = observer(loadRandomAlbumsAndAppendThemAsCardsToBlock)

albumPageObserver.observe(mainAlbumEnd);