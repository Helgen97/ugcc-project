package com.project.ugcc.DTO;

import com.project.ugcc.models.Album;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class AlbumDTO extends TypeDTO {

    private String creationDate;
    private List<String> imagesUrls;
    private String videoUrl;

    private AlbumDTO(
            Long id,
            String namedId,
            String title,
            SectionDTO section,
            String creationDate,
            List<String> imagesUrls,
            String videoUrl) {
        super(id, namedId, title, section);
        this.creationDate = creationDate;
        this.imagesUrls = imagesUrls;
        this.videoUrl = videoUrl;
    }

    public static AlbumDTO of(Album album) {
        return new AlbumDTO(
                album.getId(),
                album.getNamedId(),
                album.getTitle(),
                SectionDTO.of(album.getSection()),
                album.getCreationDate(),
                album.getImagesUrls(),
                album.getVideoUrl()
        );
    }
}
