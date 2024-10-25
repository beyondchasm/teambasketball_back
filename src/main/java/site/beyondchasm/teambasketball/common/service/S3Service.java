package site.beyondchasm.teambasketball.common.service;

import java.io.File;
import java.nio.file.Path;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

@Service
public class S3Service {

  private final S3Client s3;

  @Value("${aws.s3.bucket-name}")
  private String bucketName;

  public S3Service(@Value("${aws.s3.access-key}") String accessKey,
      @Value("${aws.s3.secret-key}") String secretKey,
      @Value("${aws.s3.region}") String region) {
    this.s3 = S3Client.builder()
        .region(Region.of(region))
        .credentialsProvider(StaticCredentialsProvider.create(
            AwsBasicCredentials.create(accessKey, secretKey)
        ))
        .build();
  }

  // 파일 업로드
  public String uploadFile(String fileName, File file) {
    String fileUrl = "https://" + bucketName + ".s3." + s3.serviceClientConfiguration().region()
        + ".amazonaws.com/" + fileName;

    try {
      PutObjectRequest request = PutObjectRequest.builder()
          .bucket(bucketName)
          .key(fileName)
          .build();
      s3.putObject(request, Path.of(file.getAbsolutePath()));
    } catch (S3Exception e) {
      e.printStackTrace();
      return null;
    }

    return fileUrl;
  }

  // 파일 다운로드
  public byte[] downloadFile(String fileName) {
    try {
      GetObjectRequest request = GetObjectRequest.builder()
          .bucket(bucketName)
          .key(fileName)
          .build();

      // 파일을 byte[]로 다운로드
      return s3.getObject(request, ResponseTransformer.toBytes()).asByteArray();
    } catch (S3Exception e) {
      e.printStackTrace();
      return null;
    }
  }
}
