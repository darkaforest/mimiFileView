package org.darkaforest.service.impl;

import org.darkaforest.model.FileAttribute;
import org.darkaforest.service.FilePreview;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

/**
 * Dcm 文件处理
 */
@Service
public class DcmFilePreviewImpl implements FilePreview {

    private final CommonPreviewImpl commonPreview;

    public DcmFilePreviewImpl(CommonPreviewImpl commonPreview) {
        this.commonPreview = commonPreview;
    }

    @Override
    public String filePreviewHandle(String url, Model model, FileAttribute fileAttribute) {
        commonPreview.filePreviewHandle(url,model,fileAttribute);
        return DCM_FILE_PREVIEW_PAGE;
    }
}
