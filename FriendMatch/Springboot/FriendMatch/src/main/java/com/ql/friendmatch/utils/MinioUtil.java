package com.ql.friendmatch.utils;

import io.minio.*;
import io.minio.errors.*;
import io.minio.messages.Bucket;
import io.minio.messages.Item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Component
@Slf4j
public class MinioUtil {
    @Resource
    private MinioClient minioClient;

    final long IMG_SIZE = 1024 * 1024 * 3;

    /**
     * 检查存储桶是否存在
     */
    public boolean bucketExists(String bucketName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        return minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
    }

    /**
     * 创建存储桶
     */
    public boolean makeBucket(String bucketName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        boolean flag = bucketExists(bucketName);
        if (!flag) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            return true;
        } else {
            return false;
        }
    }

    public <InvalidBucketNameException extends Throwable> StatObjectResponse statObject(String bucketName, String objectName) throws IOException, InvalidResponseException, InvalidKeyException, NoSuchAlgorithmException, ServerException, ErrorResponseException, XmlParserException, InvalidBucketNameException, InsufficientDataException, InternalException {
        boolean flag = bucketExists(bucketName);
        if (flag) {
            StatObjectResponse statObject = null;
            try {
                statObject = minioClient.statObject(StatObjectArgs.builder().bucket(bucketName).object(objectName).build());
            } catch (ErrorResponseException e) {
                log.error("ErrorResponseException", e);
            } catch (InsufficientDataException e) {
                log.error("ErrorResponseException", e);
                e.printStackTrace();
            } catch (InternalException e) {
                log.error("InternalException", e);
            }
//            catch (InvalidBucketNameException){
//                log.error("InvalidBucketNameException");
//            }
            catch (InvalidKeyException e) {
                log.error("InvalidKeyException", e);
            } catch (InvalidResponseException e) {
                log.error("InvalidResponseException", e);
            } catch (IOException e) {
                log.error("IOException", e);
            } catch (NoSuchAlgorithmException e) {
                log.error("NoSuchAlgorithmException", e);
            } catch (ServerException e) {
                log.error("ServerException", e);
            } catch (XmlParserException e) {
                log.error("XmlParserException", e);
            }
            return statObject;
        }
        return null;
    }


    /**
     * 通过文件上传到对象
     */
    public boolean uploadObject(String bucketName, String objectName, String fileName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        boolean flag = bucketExists(bucketName);
//        System.out.println(flag);
        if (flag) {
            minioClient.uploadObject(
                    UploadObjectArgs.builder()
                            .bucket(bucketName).object(objectName).filename(fileName).build());
            StatObjectResponse statObject = statObject(bucketName, objectName);
            System.out.println(statObject);

            return statObject != null && statObject.size() > 0;
        }
        return false;
    }

    /**
     * 文件上传
     */

    public boolean putObject(String bucketName, String objectName, InputStream inputStream, String contentType) throws IOException, InvalidResponseException, InvalidKeyException, NoSuchAlgorithmException, ServerException, ErrorResponseException, XmlParserException, InsufficientDataException, InternalException {
        boolean flag = bucketExists(bucketName);
        if (flag) {
            minioClient.putObject(PutObjectArgs.builder().bucket(bucketName).object(objectName).stream(
                            //不清楚文件的大小时，可以传-1，10485760。如果知道大小也可以传入size，partsize。
                            inputStream, -1, 10485760)
                    .contentType(contentType)
                    .build());
            StatObjectResponse statObject = statObject(bucketName, objectName);
            if (statObject != null && statObject.size() > 0) {
                return true;
            }
        }
        return false;
    }

}
