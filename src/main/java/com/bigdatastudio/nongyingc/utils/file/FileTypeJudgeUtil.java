package com.bigdatastudio.nongyingc.utils.file;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 成大事
 * @since 2022/7/7 8:38
 */
@Slf4j
public class FileTypeJudgeUtil {

    private FileTypeJudgeUtil(){

    }

    protected static final Map<String,String> FILE_TYPE_MAP = new HashMap<>();


    static{

        //文档文件类型的
        FILE_TYPE_MAP.put("application/postscript",".ai ");
        FILE_TYPE_MAP.put("application/x-msdownload; format=pe64",".exe");
        FILE_TYPE_MAP.put("application/x-msdownload",".exe");
        FILE_TYPE_MAP.put("application/vnd.ms-word",".doc");
        FILE_TYPE_MAP.put("application/vnd.openxmlformats-officedocument.wordprocessingml.document",".docx");
        FILE_TYPE_MAP.put("application/vnd.ms-excel",".xls");
        FILE_TYPE_MAP.put("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",".xlsx");
        FILE_TYPE_MAP.put("application/vnd.ms-powerpoint",".ppt");
        FILE_TYPE_MAP.put("application/vnd.openxmlformats-officedocument.presentationml.presentation",".pptx");
        FILE_TYPE_MAP.put("application/pdf",".pdf");
        FILE_TYPE_MAP.put("application/xml",".xml");
        FILE_TYPE_MAP.put("application/vnd.oasis.opendocument.text",".odt");
        FILE_TYPE_MAP.put("application/x-shockwave-flash",".swf");


        //压缩文件类型的
        FILE_TYPE_MAP.put("application/x-gzip",".gz");
        FILE_TYPE_MAP.put("application/x-bzip2",".bz2");
        FILE_TYPE_MAP.put("application/zip",".zip");
        FILE_TYPE_MAP.put("application/x-tar",".rar");
        FILE_TYPE_MAP.put("application/x-7z-compressed",".7z");


        //文字类型
        FILE_TYPE_MAP.put("text/plain",".txt");
        FILE_TYPE_MAP.put("text/x-php",".php");
        FILE_TYPE_MAP.put("text/html",".html");
        FILE_TYPE_MAP.put("text/javascript",".js");
        FILE_TYPE_MAP.put("text/rtf",".rtf");
        FILE_TYPE_MAP.put("text/rtfd",".rtfd");
        FILE_TYPE_MAP.put("text/x-python",".py");
        FILE_TYPE_MAP.put("text/x-java-source",".java");
        FILE_TYPE_MAP.put("text/x-ruby",".rb");
        FILE_TYPE_MAP.put("text/x-shellscript",".sh");
        FILE_TYPE_MAP.put("text/x-perl",".pl");
        FILE_TYPE_MAP.put("text/x-sql",".sql");
        FILE_TYPE_MAP.put("text/csv",".csv");
        FILE_TYPE_MAP.put("text/x-web-markdown",".md");

        //图片类型的
        FILE_TYPE_MAP.put("image/x-ms-bmp",".bmp");
        FILE_TYPE_MAP.put("image/gif",".gif");
        FILE_TYPE_MAP.put("image/jpeg",".jpg");
        FILE_TYPE_MAP.put("image/webp",".webp");
        FILE_TYPE_MAP.put("image/png",".png");
        FILE_TYPE_MAP.put("image/tiff",".tiff");
        FILE_TYPE_MAP.put("image/x-targa",".tga");
        FILE_TYPE_MAP.put("image/vnd.adobe.photoshop",".psd");
        FILE_TYPE_MAP.put("image/vnd.microsoft.icon",".ico");



        //音频文件类型的
        FILE_TYPE_MAP.put("audio/mpeg",".mp3");
        FILE_TYPE_MAP.put("audio/midi",".mid");
        FILE_TYPE_MAP.put("audio/ogg",".ogg");
        FILE_TYPE_MAP.put("audio/mp4",".mp4a");
        FILE_TYPE_MAP.put("audio/wav",".wav");
        FILE_TYPE_MAP.put("audio/x-ms-wma",".wma");
        FILE_TYPE_MAP.put("audio/x-flac",".flac");



        //视频文件类型的
        FILE_TYPE_MAP.put("video/x-msvideo",".avi");
        FILE_TYPE_MAP.put("video/x-dv",".dv");
        FILE_TYPE_MAP.put("video/mp4",".mp4");
        FILE_TYPE_MAP.put("video/mpeg",".mpeg");
        FILE_TYPE_MAP.put("video/quicktime",".mov");
        FILE_TYPE_MAP.put("video/x-ms-wmv",".wm");
        FILE_TYPE_MAP.put("video/x-flv",".flv");
        FILE_TYPE_MAP.put("video/x-matroska",".mkv");









    }




}
