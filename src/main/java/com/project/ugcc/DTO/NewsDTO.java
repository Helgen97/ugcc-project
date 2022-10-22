package com.project.ugcc.DTO;

import com.project.ugcc.models.News;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class NewsDTO extends TypeDTO {

    private String description;
    private String imageUrl;
    private String text;
    private String creationDate;

    private NewsDTO(
            Long id,
            String namedId,
            String title,
            SectionDTO section,
            String description,
            String imageUrl,
            String text,
            String creationDate) {
        super(id, namedId, title, section);
        this.description = description;
        this.imageUrl = imageUrl;
        this.text = text;
        this.creationDate = creationDate;
    }

    public static NewsDTO of(News news) {
        return new NewsDTO(
                news.getId(),
                news.getNamedId(),
                news.getTitle(),
                SectionDTO.of(news.getSection()),
                news.getDescription(),
                news.getImageUrl(),
                news.getText(),
                news.getCreationDate());
    }
}
