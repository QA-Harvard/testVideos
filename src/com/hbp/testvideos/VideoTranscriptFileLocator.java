package com.hbp.testvideos;

import java.io.*;

import java.nio.charset.Charset;
import java.util.HashMap;

/**
 * Created by jun.chen on 3/9/16.
 */
public class VideoTranscriptFileLocator {

    /*Mapping from master list Kaltura Id to Video titles- English verion.
      -This mapping can be used for verifying total number of videos <KalturaId, Video title>
    */
    private static HashMap <String, Video> kalturaIdMasterListMap = new HashMap();


    /*Mapping from master playlist files -English version.
     */
    private static HashMap <String, Video> playlistMasterListMap = new HashMap();


    /*For playlist descriptions.
     -This mapping is used to generate the playlist descriptions <Video title, description>
    */
    private static HashMap <String, String> videoPlaylistDescriptions_zhcn = new HashMap();
    private static HashMap <String, String> videoPlaylistDescriptions_esla = new HashMap();
    private static HashMap <String, String> videoPlaylistDescriptions_ptbr = new HashMap();

    public static final String mapping_folder= "/Users/jun.chen/Videos/mapping/";

    public static final String kaltura_id_master_list_file = "kaltura-id-master-list_tab_all.txt";

    public static final String playlist_master_list_file = "playlist-master-list_tab_all.txt";

    public static final String about_zhcn_file = "HMM12_ABOUT_CHS_playlist_tab.txt";
    public static final String about_ptbr_file = "HMM12_ABOUT_ESL_playlist_tab.txt";
    public static final String about_esla_file = "HMM12_ABOUT_PTB_playlist_tab.txt";


    public static final VideoTranscriptFileLocator videoTranscriptFileLocatorInstance = new VideoTranscriptFileLocator();

    //Singleton private constructor.
    private VideoTranscriptFileLocator(){

    }

    public static VideoTranscriptFileLocator getVideoTranscriptFileLocatorInstance(){
        loadAllMappings();
        return videoTranscriptFileLocatorInstance;
    }



    public static void loadAllMappings(){

        loadKalturaIdMasterListMap();
        loadPlaylistMasterList();

        extractVideoPlaylistDescriptions_zhcn();
        extractVideoPlaylistDescriptions_esla();
        extractvideoPlaylistDescriptions_ptbr();

    }

    //Getters and Setters.

    public HashMap<String, Video> getPlaylistMasterListMap() {
        return playlistMasterListMap;
    }

    public void setPlaylistMasterListMap(HashMap<String, Video> playlistMasterListMap) {
        this.playlistMasterListMap = playlistMasterListMap;
    }

    public HashMap getKalturaIdMasterListMap() {
        return kalturaIdMasterListMap;
    }

    public void setKalturaIdMasterListMap(HashMap kalturaIdMasterListMap) {
        this.kalturaIdMasterListMap = kalturaIdMasterListMap;
    }


    public HashMap getVideoPlaylistDescriptions_zhcn() {
        return videoPlaylistDescriptions_zhcn;
    }

    public void setVideoPlaylistDescriptions_zhcn(HashMap videoPlaylistDescriptions_zhcn) {
        this.videoPlaylistDescriptions_zhcn = videoPlaylistDescriptions_zhcn;
    }

    public HashMap getVideoPlaylistDescriptions_esla() {
        return videoPlaylistDescriptions_esla;
    }

    public void setVideoPlaylistDescriptions_esla(HashMap videoPlaylistDescriptions_esla) {
        this.videoPlaylistDescriptions_esla = videoPlaylistDescriptions_esla;
    }

    public HashMap getVideoPlaylistDescriptions_ptbr() {
        return videoPlaylistDescriptions_ptbr;
    }

    public void setVideoPlaylistDescriptions_ptbr(HashMap videoPlaylistDescriptions_ptbr) {
        this.videoPlaylistDescriptions_ptbr = videoPlaylistDescriptions_ptbr;
    }


    //Populate the master Kaltura video list.
    private static void loadKalturaIdMasterListMap() {
        try {

            FileInputStream fis = new FileInputStream(mapping_folder +kaltura_id_master_list_file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            String line;

            while ((line = reader.readLine()) != null) {
                // process the line.
                String[] lineParts = line.split("\t");
                String kalturaId = lineParts[0].trim();
                String videoFormattedTitle = lineParts[1].trim();
                String videoTitle = lineParts[2].trim();
                String videoTranscriptFileName = lineParts[3].trim();
                String videoBio = lineParts[4].trim();
                String videoBioFileName = lineParts[5].trim();

                Video video = new Video(
                        kalturaId,
                        videoFormattedTitle,
                        videoTitle,
                        videoTranscriptFileName,
                        videoBio,
                        videoBioFileName
                );

                kalturaIdMasterListMap.put(kalturaId,video);

            }

        }catch(IOException io){
            io.printStackTrace();

        }

    }

    private static void loadPlaylistMasterList(){
        try {

            FileInputStream fis = new FileInputStream(mapping_folder +playlist_master_list_file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            String line;

            while ((line = reader.readLine()) != null) {
                // process the line.

                String[] lineParts = line.split("\t");
                String videoTitle = lineParts[0].trim();
                String playlistXmlFileName = lineParts[1].trim();
                String videoBioFileName = lineParts[2].trim();

                Video playlistVideo = new Video(videoTitle, playlistXmlFileName,videoBioFileName);

                playlistMasterListMap.put(videoTitle, playlistVideo);

            }

        }catch(IOException io){
            io.printStackTrace();
        }

    }


    private static void extractVideoPlaylistDescriptions_zhcn(){

        try {

            FileInputStream fis = new FileInputStream(mapping_folder +about_zhcn_file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis, Charset.forName("UTF-16")));
            String line;

            while ((line = reader.readLine()) != null) {
                // process the line.
                String[] lineParts = line.split("\t");
                String videoTitle = lineParts[0].trim();
                String kalturaId = lineParts[1].trim();

                if(videoTitle.equalsIgnoreCase(kalturaId)){ //Retrieve only the playlists.
                    String videoDescription = lineParts[2].trim();
                    videoPlaylistDescriptions_zhcn.put(videoTitle, videoDescription);
                }

            }

        }catch(IOException io){
            io.printStackTrace();
        }
    }

    private static void extractVideoPlaylistDescriptions_esla(){
        try {

            FileInputStream fis = new FileInputStream(mapping_folder + about_esla_file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis, Charset.forName("UTF-16")));
            String line;

            while ((line = reader.readLine()) != null) {
                // process the line.
                String[] lineParts = line.split("\t");
                String videoTitle = lineParts[0].trim();
                String kalturaId = lineParts[1].trim();

                if(videoTitle.equalsIgnoreCase(kalturaId)){ //Retrieve only the playlists.
                    String videoDescription = lineParts[2].trim();
                    videoPlaylistDescriptions_esla.put(videoTitle, videoDescription);
                }
            }

        }catch(IOException io){
            io.printStackTrace();
        }

    }

    private static void extractvideoPlaylistDescriptions_ptbr(){
        try {

            FileInputStream fis = new FileInputStream(mapping_folder +about_ptbr_file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis, Charset.forName("UTF-16")));
            String line;

            while ((line = reader.readLine()) != null) {
                // process the line.
                String[] lineParts = line.split("\t");
                String videoTitle = lineParts[0].trim();
                String kalturaId = lineParts[1].trim();

                if(videoTitle.equalsIgnoreCase(kalturaId)){ //Retrieve only the playlists.
                    String videoDescription = lineParts[2].trim();
                    videoPlaylistDescriptions_ptbr.put(videoTitle, videoDescription);
                }
            }

        }catch(IOException io){
            io.printStackTrace();

        }

    }




}
