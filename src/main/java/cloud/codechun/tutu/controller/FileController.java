package cloud.codechun.tutu.controller;


import cloud.codechun.tutu.common.BaseResponse;
import cloud.codechun.tutu.common.ResultUtils;
import cloud.codechun.tutu.exception.BusinessException;
import cloud.codechun.tutu.exception.ErrorCode;
import cloud.codechun.tutu.manager.CosManager;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;



@RestController
@RequestMapping
@Slf4j
public class FileController {


    @Resource
    private CosManager cosManager;


    /**
     * 测试文件上传
     *
     * @param multipartFile
     * @return
     */
//    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    @PostMapping("/test/upload")
    public BaseResponse<String> testUploadFile(@RequestPart("file") MultipartFile multipartFile) {
        // 文件目录
        String filename = multipartFile.getOriginalFilename();
        //上传的文件夹
        String filepath = String.format("/test/%s", filename);
        File file = null;
        try {
            // 将 multipartFile中的文件 传入给file
            file = File.createTempFile(filepath, null);
            multipartFile.transferTo(file);
            cosManager.putObject(filepath, file);
            // 返回可访问地址  地址前加上cos域名就可以访问
            return ResultUtils.success(filepath);
        } catch (Exception e) {
            log.error("file upload error, filepath = " + filepath, e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "上传失败");
        } finally {
            if (file != null) {
                // 删除临时文件
                boolean delete = file.delete();
                if (!delete) {
                    log.error("file delete error, filepath = {}", filepath);
                }
            }
        }
    }

    /**
     * 测试图片压缩成webp上传
     *
     * @param multipartFile
     * @return
     */
//    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    @PostMapping("/test2/upload")
    public BaseResponse<String> testUploadFile2(@RequestPart("file") MultipartFile multipartFile) {
        // 文件目录
        String filename = multipartFile.getOriginalFilename();
        //上传的文件夹
        String filepath = String.format("/test/%s", filename);
        File file = null;
        try {
            // 将 multipartFile中的文件 传入给file
            file = File.createTempFile(filepath, null);
            multipartFile.transferTo(file);
            cosManager.putPictureObject(filepath, file);
            // 返回可访问地址  地址前加上cos域名就可以访问
            return ResultUtils.success(filepath);
        } catch (Exception e) {
            log.error("file upload error, filepath = " + filepath, e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "上传失败");
        } finally {
            if (file != null) {
                // 删除临时文件
                boolean delete = file.delete();
                if (!delete) {
                    log.error("file delete error, filepath = {}", filepath);
                }
            }
        }
    }






}
