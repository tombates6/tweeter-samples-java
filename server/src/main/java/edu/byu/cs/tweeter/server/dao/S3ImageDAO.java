package edu.byu.cs.tweeter.server.dao;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

import edu.byu.cs.tweeter.server.dao.exceptions.DataAccessException;

public class S3ImageDAO implements IImageDAO {
    private static AmazonS3 amazonS3 = AmazonS3ClientBuilder
            .standard()
            .withRegion(Regions.US_WEST_2)
            .build();
    private final String bucketName = "tbates6-tweeter-images";

    @Override
    public String uploadImage(String alias, String image) throws DataAccessException {
        try {
            String filename = alias + "-image.jpeg";
            InputStream fis = new ByteArrayInputStream(image.getBytes());

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(image.getBytes().length);
            metadata.setContentType("image/jpeg");
            amazonS3.putObject(new PutObjectRequest(bucketName, filename, fis, metadata).withCannedAcl(CannedAccessControlList.PublicRead));

            return amazonS3.getUrl(bucketName, filename).toString();
        } catch (AmazonS3Exception e) {
            throw new DataAccessException(e.getMessage(), e.getCause());
        }
    }
}
