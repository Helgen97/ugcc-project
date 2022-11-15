package com.project.ugcc.DTO;

import com.project.ugcc.models.Document;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class DocumentDTO extends TypeDTO {

    private String description;
    private String imageUrl;
    private String documentUrl;
    private String creationDate;

    private DocumentDTO(Document document) {
        super(
                document.getId(),
                document.getNamedId(),
                document.getTitle(),
                SectionDTO.of(document.getSection())
        );
        this.description = document.getDescription();
        this.imageUrl = document.getImageUrl();
        this.documentUrl = document.getDocumentUrl();
        this.creationDate = document.getCreationDate();
    }

    public static DocumentDTO of(Document document) {
        return new DocumentDTO(document);
    }

}
