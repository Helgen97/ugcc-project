package com.project.ugcc.DTO;

import com.project.ugcc.models.News;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class NewsDTO extends TypeDTO {

    private String description;
    private String imageURL;
    private String text;
    private LocalDateTime date;

    private NewsDTO(
            Long ID,
            String namedId,
            String title,
            SectionDTO section,
            String description,
            String imageURL,
            String text,
            LocalDateTime date) {
        super(ID, namedId, title, section);
        this.description = description;
        this.imageURL = imageURL;
        this.text = text;
        this.date = date;
    }

    public static NewsDTO of(News news) {
        return new NewsDTO(
                news.getID(),
                news.getNamedId(),
                news.getTitle(),
                SectionDTO.of(news.getSection()),
                news.getDescription(),
                news.getImageURL(),
                news.getText(),
                news.getDate());
    }
}
