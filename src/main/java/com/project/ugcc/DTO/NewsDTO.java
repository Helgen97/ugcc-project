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

    private NewsDTO(News news) {
        super(news.getId(), news.getNamedId(), news.getTitle(), SectionDTO.of(news.getSection()));
        this.description = news.getDescription();
        this.imageUrl = news.getImageUrl();
        this.text = news.getText();
        this.creationDate = news.getCreationDate();
    }

    public static NewsDTO of(News news) {
        return new NewsDTO(news);
    }
}
