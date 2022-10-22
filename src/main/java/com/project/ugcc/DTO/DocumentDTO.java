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

    private DocumentDTO(Long ID,
                        String namedId,
                        String title,
                        SectionDTO section,
                        String description,
                        String imageUrl,
                        String documentUrl,
                        String creationDate) {
        super(ID, namedId, title, section);
        this.description = description;
        this.imageUrl = imageUrl;
        this.documentUrl = documentUrl;
        this.creationDate = creationDate;
    }

    public static DocumentDTO of(Document document) {
        return new DocumentDTO(
                document.getId(),
                document.getNamedId(),
                document.getTitle(),
                SectionDTO.of(document.getSection()),
                document.getDescription(),
                document.getImageUrl(),
                document.getDocumentUrl(),
                document.getCreationDate());
    }

}
