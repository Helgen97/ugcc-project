package com.project.ugcc.DTO;

import com.project.ugcc.models.Category;
import com.project.ugcc.models.Section;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class SectionDTO {

    private Long ID;
    private String title;
    private Category category;

    private SectionDTO(Long ID, String title, Category category) {
        this.ID = ID;
        this.title = title;
        this.category = category;
    }

    public static SectionDTO of(Section section) {
        return new SectionDTO(section.getID(), section.getTitle(), section.getCategory());
    }
}
