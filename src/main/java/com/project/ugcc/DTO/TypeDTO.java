package com.project.ugcc.DTO;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public abstract class TypeDTO {

    private Long ID;
    private String namedId;
    private String title;
    private SectionDTO section;
}
