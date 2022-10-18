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
    private List<String> imagesUrls;
    private String videoUrl;

    private AlbumDTO(
            Long ID,
            String namedId,
            String title,
            SectionDTO section,
            LocalDateTime creationDate,
            List<String> imagesUrls,
            String videoUrl) {
        super(ID, namedId, title, section);
        this.creationDate = creationDate;
        this.imagesUrls = imagesUrls;
        this.videoUrl = videoUrl;
    }

    public static AlbumDTO of(Album album) {
        return new AlbumDTO(
                album.getID(),
                album.getNamedId(),
                album.getTitle(),
                SectionDTO.of(album.getSection()),
                album.getCreationDate(),
                album.getImagesUrls(),
                album.getVideoUrl()
        );
    }
}
