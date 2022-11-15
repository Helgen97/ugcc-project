package com.project.ugcc.DTO;

import com.project.ugcc.models.Broadcast;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class BroadcastDTO {

    private Long id;
    private String churchFrom;
    private String churchName;
    private String churchTown;
    private String imageUrl;
    private String schedule;
    private String youtubeLink;
    private String instagramLink;

    private BroadcastDTO(Broadcast broadcast) {
        this.id = broadcast.getId();
        this.churchFrom = broadcast.getChurchFrom();
        this.churchName = broadcast.getChurchName();
        this.churchTown = broadcast.getChurchTown();
        this.imageUrl = broadcast.getImageUrl();
        this.schedule = broadcast.getSchedule();
        this.youtubeLink = broadcast.getYoutubeLink();
        this.instagramLink = broadcast.getInstagramLink();
    }

    public static BroadcastDTO of(Broadcast broadcast) {
        return new BroadcastDTO(broadcast);
    }
}
