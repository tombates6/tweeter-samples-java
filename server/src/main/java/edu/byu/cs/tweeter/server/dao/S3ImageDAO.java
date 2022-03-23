package edu.byu.cs.tweeter.server.dao;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AmazonS3Exception;

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
            String key = alias + "-image";
            amazonS3.putObject(bucketName, key, image);
            return amazonS3.getUrl(bucketName, key).toString();
        } catch (AmazonS3Exception e) {
            throw new DataAccessException(e.getMessage(), e.getCause());
        }
    }
}
