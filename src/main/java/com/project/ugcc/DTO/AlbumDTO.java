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
    private String videoSource;

    private AlbumDTO(Album album) {
        super(album.getId(), album.getNamedId(), album.getTitle(), SectionDTO.of(album.getSection()));
        this.creationDate = album.getCreationDate();
        this.imagesUrls = album.getImagesUrls();
        this.videoSource = album.getVideoSource();
    }

    public static AlbumDTO of(Album album) {
        return new AlbumDTO(album);
    }
}
