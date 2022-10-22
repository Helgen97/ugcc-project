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

    private SectionDTO(Long id, String title, String namedId, Category category) {
        this.id = id;
        this.title = title;
        this.namedId = namedId;
        this.category = category;
    }

    public static SectionDTO of(Section section) {
        return new SectionDTO(section.getId(), section.getTitle(), section.getNamedId(), section.getCategory());
    }
}
