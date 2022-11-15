package com.project.ugcc.DTO;

import com.project.ugcc.models.Category;
import com.project.ugcc.models.Section;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class SectionDTO {

    private Long id;
    private String title;
    private String namedId;
    private Category category;

    private SectionDTO(Section section) {
        this.id = section.getId();
        this.title = section.getTitle();
        this.namedId = section.getNamedId();
        this.category = section.getCategory();
    }

    public static SectionDTO of(Section section) {
        return new SectionDTO(section);
    }
}
