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

    private ArticleDTO(
            Long id,
            String namedId,
            String title,
            SectionDTO section,
            String imageUrl,
            String imageDescription,
            String text) {
        super(id, namedId, title, section);
        this.imageUrl = imageUrl;
        this.imageDescription = imageDescription;
        this.text = text;
    }

    public static ArticleDTO of(Article article) {
        return new ArticleDTO(
                article.getId(),
                article.getNamedId(),
                article.getTitle(),
                SectionDTO.of(article.getSection()),
                article.getImageUrl(),
                article.getImageDescription(),
                article.getText()
        );
    }
}
