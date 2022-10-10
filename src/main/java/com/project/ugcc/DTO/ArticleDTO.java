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

    private String imageURL;
    private String imageDescription;
    private String text;

    private ArticleDTO(Long ID, String title, SectionDTO section, String imageURL, String imageDescription, String text) {
        super(ID, title, section);
        this.imageURL = imageURL;
        this.imageDescription = imageDescription;
        this.text = text;
    }

    public static ArticleDTO of(Article article) {
        return new ArticleDTO(
                article.getID(),
                article.getTitle(),
                SectionDTO.of(article.getSection()),
                article.getImageURL(),
                article.getImageDescription(),
                article.getText()
        );
    }
}
