package utility.file;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.HttpMethod;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.GroupGrantee;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.Permission;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;

import main.Temper;

import java.io.FileInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

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
	
//    public static String getPresignedURL(String objectKey) throws IOException {
//        try {
//            // Set the presigned URL to expire after one hour.
//            java.util.Date expiration = new java.util.Date();
//            long expTimeMillis = expiration.getTime();
//            expTimeMillis += 1000 * 60 * 60;
//            expiration.setTime(expTimeMillis);
//
//            // Generate the presigned URL.
//            System.out.println("Generating pre-signed URL.");
//            GeneratePresignedUrlRequest generatePresignedUrlRequest =
//                    new GeneratePresignedUrlRequest(bucketName, objectKey)
//                            .withMethod(HttpMethod.GET)
//                            .withExpiration(expiration);
//            URL url = s3Client.generatePresignedUrl(generatePresignedUrlRequest);
//
////            System.out.println("Pre-Signed URL: " + url.toString());
//            return url.toString();
//        } catch (AmazonServiceException e) {
//            // The call was transmitted successfully, but Amazon S3 couldn't process 
//            // it, so it returned an error response.
//            e.printStackTrace();
//        } catch (SdkClientException e) {
//            // Amazon S3 couldn't be contacted for a response, or the client
//            // couldn't parse the response from Amazon S3.
//            e.printStackTrace();
//        }
//        return "";
//    }
    
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
//    
//    public static void main(String[] args) throws IOException {
//    	File file = new File("C:\\Users\\vorga\\Downloads\\beach.jpg");
//    	ObjectMetadata metadata = new ObjectMetadata();
//    	InputStream content = new FileInputStream(file);
//    	URLConnection connection = file.toURL().openConnection();
//    	
//    	metadata.setContentType(connection.getContentType());
//    	metadata.setContentLength(connection.getContentLength());
//	    
//		S3.uploadFile(content, metadata);
//		System.out.println("file sent to s3");
//	}
    
//    public static String post(InputStream pic) throws IOException {
//        index++;
//        String key = "@" + index + Temper.chunk(5);
//        ObjectMetadata dat = new ObjectMetadata();
//        InputStream temp = pic;
//        char[] tmp = IOUtils.toCharArray(temp);
//        
//        dat.setContentLength(tmp.length);
//        PutObjectRequest por = new PutObjectRequest("devonvirdenprojects", key, pic, dat);
//        AccessControlList acl = new AccessControlList();
//        acl.grantPermission(GroupGrantee.AllUsers, Permission.Read);
//        por.setAccessControlList(acl);
//        s3Client.putObject(por);
//        System.out.println("done");
//        return key;
//    }
    
}
