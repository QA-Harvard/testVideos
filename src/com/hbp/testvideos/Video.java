package com.hbp.testvideos;

/**
 * Created by jun.chen on 3/15/16.
 */
public class Video {

    private String videoTitle;
    private String videoDescription;
    private String kalturaId;
    private String videoBio;
    private String videoFormattedTitle;
    private String videoBioFileName;
    private String videoTranscriptFileName;

    private String playlistFileName;

    //Regular video file.
    public Video(String kalturaId, String videoFormattedTitle, String videoTitle,
                 String videoTranscriptFileName, String videoBio, String videoBioFileName ){
        this.kalturaId= kalturaId;
        this.videoTitle = videoTitle;
        this.videoBio = videoBio;
        this.videoFormattedTitle = videoFormattedTitle;
        this.videoBioFileName= videoBioFileName;
        this.videoTranscriptFileName = videoTranscriptFileName;

    }

    //For playlist file.
    public Video(String videoTitle, String playlistFileName,
                  String videoBioFileName ){
        this.videoTitle = videoTitle;
        this.playlistFileName = playlistFileName;
        this.videoBioFileName= videoBioFileName;

    }


    public String getVideoBio() {
        return videoBio;
    }

    public void setVideoBio(String videoBio) {
        this.videoBio = videoBio;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public String getVideoDescription() {
        return videoDescription;
    }

    public void setVideoDescription(String videoDescription) {
        this.videoDescription = videoDescription;
    }

    public String getKalturaId() {
        return kalturaId;
    }

    public void setKalturaId(String kalturaId) {
        this.kalturaId = kalturaId;
    }


    public String getVideoFormattedTitle() {
        return videoFormattedTitle;
    }

    public void setVideoFormattedTitle(String videoFormattedTitle) {
        this.videoFormattedTitle = videoFormattedTitle;
    }

    public String getVideoBioFileName() {
        return videoBioFileName;
    }

    public void setVideoBioFileName(String videoBioFileName) {
        this.videoBioFileName = videoBioFileName;
    }

    public String getVideoTranscriptFileName() {
        return videoTranscriptFileName;
    }

    public void setVideoTranscriptFileName(String videoTranscriptFileName) {
        this.videoTranscriptFileName = videoTranscriptFileName;
    }

    public String getPlaylistFileName() {
        return playlistFileName;
    }

    public void setPlaylistFileName(String playlistFileName) {
        this.playlistFileName = playlistFileName;
    }
}
