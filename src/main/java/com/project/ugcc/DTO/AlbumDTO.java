package com.project.ugcc.DTO;

import com.project.ugcc.models.Album;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class AlbumDTO extends TypeDTO {

    private LocalDateTime creationDate;
    private List<String> imageURLs;

    private AlbumDTO(Long ID, String title, SectionDTO section, LocalDateTime creationDate, List<String> imageURLs) {
        super(ID, title, section);
        this.creationDate = creationDate;
        this.imageURLs = imageURLs;
    }

    public static AlbumDTO of(Album album) {
        return new AlbumDTO(
                album.getID(),
                album.getTitle(),
                SectionDTO.of(album.getSection()),
                album.getCreationDate(),
                album.getImageURLs()
        );
    }
}
