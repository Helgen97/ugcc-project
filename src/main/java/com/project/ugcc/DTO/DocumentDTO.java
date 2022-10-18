package com.project.ugcc.DTO;

import com.project.ugcc.models.Document;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class DocumentDTO extends TypeDTO{

    private String description;
    private String imageURL;
    private String documentURL;
    private LocalDateTime creationDate;

    private DocumentDTO(Long ID,
                        String namedId,
                        String title,
                        SectionDTO section,
                        String description,
                        String imageURL,
                        String documentURL,
                        LocalDateTime creationDate) {
        super(ID, namedId, title, section);
        this.description = description;
        this.imageURL = imageURL;
        this.documentURL = documentURL;
        this.creationDate = creationDate;
    }

    public static DocumentDTO of(Document document) {
        return new DocumentDTO(
                document.getID(),
                document.getNamedId(),
                document.getTitle(),
                SectionDTO.of(document.getSection()),
                document.getDescription(),
                document.getImageURL(),
                document.getDocumentURL(),
                document.getCreationDate());
    }

}
