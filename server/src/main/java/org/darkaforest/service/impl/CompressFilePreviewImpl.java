package org.darkaforest.service.impl;

import org.darkaforest.config.ConfigConstants;
import org.darkaforest.model.FileAttribute;
import org.darkaforest.model.ReturnResponse;
import org.darkaforest.service.FilePreview;
import org.darkaforest.utils.DownloadUtils;
import org.darkaforest.service.FileHandlerService;
import org.darkaforest.service.CompressFileReader;
import org.darkaforest.utils.KkFileUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

/**
 * Created by kl on 2018/1/17.
 * Content :处理压缩包文件
 */
@Service
public class CompressFilePreviewImpl implements FilePreview {

    private final FileHandlerService fileHandlerService;
    private final CompressFileReader compressFileReader;
    private final OtherFilePreviewImpl otherFilePreview;
    private static final String Rar_PASSWORD_MSG = "password";
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(CompressFileReader.class);


    public CompressFilePreviewImpl(FileHandlerService fileHandlerService, CompressFileReader compressFileReader, OtherFilePreviewImpl otherFilePreview) {
        this.fileHandlerService = fileHandlerService;
        this.compressFileReader = compressFileReader;
        this.otherFilePreview = otherFilePreview;
    }

    @Override
    public String filePreviewHandle(String url, Model model, FileAttribute fileAttribute) {
        String fileName = fileAttribute.getName();
        String filePassword = fileAttribute.getFilePassword();
        boolean forceUpdatedCache = fileAttribute.forceUpdatedCache();
        String fileTree = null;
        // 判断文件名是否存在(redis缓存读取)
        if (forceUpdatedCache || !StringUtils.hasText(fileHandlerService.getConvertedFile(fileName)) || !ConfigConstants.isCacheEnabled()) {
            ReturnResponse<String> response = DownloadUtils.downLoad(fileAttribute, fileName);
            if (response.isFailure()) {
                return otherFilePreview.notSupportedFile(model, fileAttribute, response.getMsg());
            }
            String filePath = response.getContent();
            try {
                fileTree = compressFileReader.unRar(filePath, filePassword, fileName, fileAttribute);
            } catch (Exception e) {
                if (e.getMessage().toLowerCase().contains(Rar_PASSWORD_MSG)) {
                    model.addAttribute("needFilePassword", true);
                    return EXEL_FILE_PREVIEW_PAGE;
                }else {
                    logger.error("Error processing RAR file: " + e.getMessage(), e);
                }
            }
            if (!ObjectUtils.isEmpty(fileTree)) {
                //是否保留压缩包源文件
                if (!fileAttribute.isCompressFile() && ConfigConstants.getDeleteSourceFile()) {
                    KkFileUtils.deleteFileByPath(filePath);
                }
                if (ConfigConstants.isCacheEnabled()) {
                    // 加入缓存
                    fileHandlerService.addConvertedFile(fileName, fileTree);
                }
            } else {
                return otherFilePreview.notSupportedFile(model, fileAttribute, "该压缩包文件无法处理!");
            }
        } else {
            fileTree = fileHandlerService.getConvertedFile(fileName);
        }
        model.addAttribute("fileName", fileName);
        model.addAttribute("fileTree", fileTree);
        return COMPRESS_FILE_PREVIEW_PAGE;
    }
}
