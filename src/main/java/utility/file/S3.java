package utility.file;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AccessControlList;
import com.amazonaws.services.s3.model.GroupGrantee;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.Permission;
import com.amazonaws.services.s3.model.PutObjectRequest;

import main.Temper;

import java.io.IOException;
import java.io.InputStream;

public class S3 {
	private static String URL = "https://devonvirdenprojects.s3.us-east-2.amazonaws.com"; 
	private static AWSCredentials credentials = new BasicAWSCredentials("AKIAIOG7U2NO2MEQZNTA",
			"SVPyZnfzLh3xB+XmyKEOocDlxraadKS89jP+DjtV");
	private static String bucketName = "devonvirdenprojects";
	private static Regions clientRegion = Regions.US_EAST_2;
	private static AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
            .withRegion(clientRegion)
            .withCredentials(new AWSStaticCredentialsProvider(credentials))
            .build();
	
	public static String getCompleteURL(String uri) {
		return URL + uri;
	}
    
    public static boolean objectExists(String objectKey) {
    	boolean exists = false;
    	try {
    		exists = s3Client.doesObjectExist(bucketName, objectKey);
    	} catch (AmazonServiceException e) {} 
    	  catch (SdkClientException e) {}
    	return exists;
    }
    
    public static String uploadFile(InputStream content, 
    		ObjectMetadata metadata) throws IOException {
    	String objectKey = "";
    	do {
    		///Keep generating Filename/S3Key until it doesn't exists
    		objectKey = "@" + Temper.chunk(5);
    	} while (objectExists(objectKey));
    	
        try {
        	PutObjectRequest por = new PutObjectRequest(bucketName, objectKey, content, metadata);
    	    AccessControlList acl = new AccessControlList();
    	    acl.grantPermission(GroupGrantee.AllUsers, Permission.Read);
    	    por.setAccessControlList(acl);
            s3Client.putObject(por);
        } catch (AmazonServiceException e) {
        	objectKey = "";
            e.printStackTrace();
        } catch (SdkClientException e) {
        	objectKey = "";
            e.printStackTrace();
        }
        return objectKey;
    }
}
