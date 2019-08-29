package utility.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.UUID;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;

/*
 * aws_access_key_id = YOUR_ACCESS_KEY_ID
 * aws_secret_access_key = YOUR_SECRET_ACCESS_KEY
*/

public class S3 {
	AmazonS3 s3 = new AmazonS3Client();
	Region usEast = Region.getRegion(Regions.US_EAST_1);

	String bucketName = "my-first-s3-bucket-" + UUID.randomUUID();
	String key = new String("MyObjectKey");

	// try {
	// s3.putObject(new PutObjectRequest(bucketName, key, createSampleFile()));
	// } catch(AmazonServiceException ase) {
	//
	// }
}
