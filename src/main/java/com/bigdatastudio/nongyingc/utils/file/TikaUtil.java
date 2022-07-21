package com.bigdatastudio.nongyingc.utils.file;


import com.github.xiaoymin.knife4j.core.util.CollectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.Tika;
import org.apache.tika.metadata.HttpHeaders;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.metadata.TikaMetadataKeys;
import org.apache.tika.mime.MediaType;
import org.apache.tika.mime.MimeType;
import org.apache.tika.mime.MimeTypes;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.helpers.DefaultHandler;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 成大事
 * @since 2022/7/5 20:20
 * 使用Apache tika 进行类型判断
 */
@Slf4j
public class TikaUtil {

    private TikaUtil(){

    }
    private static final MimeTypes DEFAULT_MIME_TYPES = MimeTypes.getDefaultMimeTypes();

    /**
     * 文件类型检测  如果没改后缀名返回true  如果改了返回false
     * @param bytes    文件的字节流
     * @param suffix    文件的后缀
     * @return     如果没改后缀名返回true  如果改了返回false
     */
    public static boolean fileTypeDetect(byte[] bytes, String suffix) {
        try {
            Tika tika = new Tika();
            String detectedMediaType = tika.detect(bytes);
            log.info("通过文件byte 获取minetype ：{}",detectedMediaType);
            String suffixType = tika.detect(bytes,suffix);
            log.info("通过文件byte 和 文件后缀 获取minetype ：{}",suffixType);
            MimeType mimeType = DEFAULT_MIME_TYPES.forName(suffixType);
            mimeType.getExtensions().forEach(System.out::println);
            return CollectionUtils.isNotEmpty(mimeType.getExtensions())
                    && mimeType.getExtensions().stream().anyMatch(ext -> ext.equals(suffix));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String getMimeType(MultipartFile file) {
        AutoDetectParser parser = new AutoDetectParser();
        Map<MediaType, Parser> map = new HashMap<>(2);
        parser.setParsers(map);
        Metadata metadata = new Metadata();
        metadata.add(TikaMetadataKeys.RESOURCE_NAME_KEY, file.getName());
        try (InputStream stream = file.getInputStream()) {
            parser.parse(stream, new DefaultHandler(), metadata, new ParseContext());
        }catch (Exception e){
            throw new RuntimeException();
        }
        return metadata.get(HttpHeaders.CONTENT_TYPE);

    }



}
