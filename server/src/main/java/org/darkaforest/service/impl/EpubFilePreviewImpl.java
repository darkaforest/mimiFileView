package org.darkaforest.service.impl;

import org.darkaforest.model.FileAttribute;
import org.darkaforest.service.FilePreview;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

/**
 * svg 图片文件处理
 * @author kl (http://kailing.pub)
 * @since 2021/2/8
 */
@Service
public class EpubFilePreviewImpl implements FilePreview {

    private final CommonPreviewImpl commonPreview;

    public EpubFilePreviewImpl(CommonPreviewImpl commonPreview) {
        this.commonPreview = commonPreview;
    }

    @Override
    public String filePreviewHandle(String url, Model model, FileAttribute fileAttribute) {
        commonPreview.filePreviewHandle(url,model,fileAttribute);
        return EPUB_PREVIEW_PAGE;
    }
}
