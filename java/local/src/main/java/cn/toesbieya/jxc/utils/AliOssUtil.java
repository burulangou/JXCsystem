package cn.toesbieya.jxc.utils;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.List;

@Data
@AllArgsConstructor
@Slf4j
public class AliOssUtil {

    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;

    /**
     * 文件上传
     *
     * @param bytes
     * @param objectName
     * @return
     */
    public String upload(byte[] bytes, String objectName) {

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        try {
            // 创建PutObject请求。
            ossClient.putObject(bucketName, objectName, new ByteArrayInputStream(bytes));
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }

        //文件访问路径规则 https://BucketName.Endpoint/ObjectName
        StringBuilder stringBuilder = new StringBuilder("https://");
        stringBuilder
                .append(bucketName)
                .append(".")
                .append(endpoint)
                .append("/")
                .append(objectName);

        log.info("文件上传到:{}", stringBuilder.toString());

        return stringBuilder.toString();
    }

    /**
     * 删除单个文件
     *
     * @param objectName 文件对象名
     */
    public void delete(String objectName) {
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        try {
            ossClient.deleteObject(bucketName, objectName);
            log.info("删除文件成功: {}", objectName);
        } catch (OSSException oe) {
            log.error("删除文件失败: {}, 错误信息: {}", objectName, oe.getErrorMessage());
        } catch (ClientException ce) {
            log.error("删除文件失败: {}, 错误信息: {}", objectName, ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }

    /**
     * 批量删除文件
     *
     * @param objectNames 文件对象名数组
     */
    public void deleteBatch(String... objectNames) {
        if (objectNames == null || objectNames.length == 0) {
            return;
        }

        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        try {
            List<String> objectNameList = Arrays.asList(objectNames);
            ossClient.deleteObjects(new com.aliyun.oss.model.DeleteObjectsRequest(bucketName).withKeys(objectNameList));
            log.info("批量删除文件成功，共删除 {} 个文件", objectNames.length);
        } catch (OSSException oe) {
            log.error("批量删除文件失败，错误信息: {}", oe.getErrorMessage());
        } catch (ClientException ce) {
            log.error("批量删除文件失败，错误信息: {}", ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }

    /**
     * 获取上传token（阿里云OSS不需要预签名token，返回空字符串）
     * 这个方法是为了兼容原有的七牛云接口
     *
     * @return 空字符串
     */
    public String getToken() {
        // 阿里云OSS不需要预签名token，直接返回空字符串
        return "";
    }
}
