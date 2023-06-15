package com.oo.service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @description:
 * @author: mango
 * @date: 2023/5/28 22:45
 */
@Transactional
public interface PaperGraphService {

    /**
     * 上传图片
     *
     * @param graph
     * @return
     * @throws IOException
     */
    public String upload(MultipartFile graph, String findUploadPath) throws IOException;


}
