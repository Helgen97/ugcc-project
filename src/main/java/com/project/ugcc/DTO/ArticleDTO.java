package com.project.ugcc.DTO;

import com.project.ugcc.models.Article;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class ArticleDTO extends TypeDTO {

    private String imageUrl;
    private String imageDescription;
    private String text;

    private ArticleDTO(Article article) {
        super(article.getId(), article.getNamedId(), article.getTitle(), SectionDTO.of(article.getSection()));
        this.imageUrl = article.getImageUrl();
        this.imageDescription = article.getImageDescription();
        this.text = article.getText();
    }

    public static ArticleDTO of(Article article) {
        return new ArticleDTO(article);
    }
}
