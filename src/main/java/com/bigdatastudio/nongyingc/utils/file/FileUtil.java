package com.bigdatastudio.nongyingc.utils.file;


import com.bigdatastudio.nongyingc.enums.FILE;
import com.bigdatastudio.nongyingc.utils.result.ResultMap;
import com.bigdatastudio.nongyingc.utils.thumbnailator.ThumbnailUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @author 成大事
 * @since 2022/7/7 14:32
 * 一个文件和图片的上传的工具类
 */
@Slf4j
public class FileUtil {
    private FileUtil() {
        throw new IllegalStateException("FileUtil class");
    }

    /**
     * 保存图片到指定文件夹
     * @param file 文件
     * @return Map
     */
    public static ResultMap saveFile(MultipartFile file , String uId) throws IOException {
        //如果文件为空 返回
        if (file.isEmpty()) {
            return ResultMap.result(FILE.FILE_IS_NULL.getValue(),FILE.FILE_IS_NULL.getMsg());
        }
        //获取文件的字节流
        byte[] bytes = file.getBytes();
        //获取文件的后缀名
        String fileOriginalFilename = file.getOriginalFilename();
        String substring = "";
        if (!StringUtils.isEmpty(fileOriginalFilename)){
            substring = fileOriginalFilename.substring(fileOriginalFilename.lastIndexOf("."));
        }
        log.info("fileUtil 中的  后缀是:  {}",substring);
        log.info("fileUtil 中的  getContentType是:  {}",file.getContentType());
        String suffix = FileTypeJudgeUtil.FILE_TYPE_MAP.get(file.getContentType());
        log.info("fileUtil 中的 suffix 是:  {}",suffix);
        if(substring.equals(suffix)){
            //如果后缀名没有被改过
            if (TikaUtil.fileTypeDetect(bytes, substring)) {
                //将文件名 重命名
                String filePath = getFilePath(uId, substring);
                log.info("filePath:   {}",filePath);
                File uploadFile = new File(filePath);
                file.transferTo(uploadFile);
                return ResultMap.result().put(FILE.SUCCESS.getMsg(),filePath);
            }
            //如果后缀名被改过
            return ResultMap.result(FILE.SUFFIX_CHANGED.getValue(), FILE.SUFFIX_CHANGED.getMsg());
        }
        //暂时不支持此种格式上传
        return ResultMap.result(FILE.FILE_TYPE_NOT_SUPPORTED.getValue(), FILE.FILE_TYPE_NOT_SUPPORTED.getMsg());


    }



    /**
     * 保存图片到指定文件夹
     * @param file 文件
     * @return Map
     */
    public static ResultMap saveImage(MultipartFile file , String uId, boolean isThumbnail) throws IOException {
        //如果文件为空 返回
        if (!file.isEmpty()) {
            //获取文件的字节流
            byte[] bytes = file.getBytes();
            //获取文件的后缀名
            String suffix = FileTypeJudgeUtil.FILE_TYPE_MAP.get(file.getContentType());
            //如果后缀名没有被改过
            if (TikaUtil.fileTypeDetect(bytes, suffix)) {
                //将文件名 重命名
                String filePath = getFilePath(uId, suffix);
                log.info("filePath:   {}",filePath);
                //是否要压缩
                if (isThumbnail) {
                    ThumbnailUtil.changeSize(file, 0.5, filePath);
                }
                File uploadFile = new File(filePath);
                //保存到本地
                file.transferTo(uploadFile);
                return ResultMap.result(FILE.SUCCESS.getValue(), FILE.SUCCESS.getMsg());
            }
            //如果后缀名被改过
            return ResultMap.result(FILE.SUFFIX_CHANGED.getValue(), FILE.SUFFIX_CHANGED.getMsg());

        }
        return ResultMap.result(FILE.FILE_IS_NULL.getValue(),FILE.FILE_IS_NULL.getMsg());
    }

    /**
     * 获取文件的路径
     * @param uId            用户的id
     * @param suffix         后缀名
     * @return               文件路径
     */
    public static String getFilePath(String uId,String suffix){
        String folder = createFolder();
        long timeMillis = System.currentTimeMillis();
        String fileName = uId + timeMillis + suffix;
        return folder+fileName;
    }


    /**
     * 创建文件夹
     * @return   如果不存在，就创建
     */
    public static String createFolder(){
        //指定文件夹。如果不存在就创建
        String folderPath = FILE.LOCAL_PATH.getMsg();
        File temp = new File(folderPath);
        if (!temp.exists()){
            boolean mkdir = temp.mkdir();
            if (mkdir){
                return folderPath;
            }
        }
        return folderPath;
    }






}
