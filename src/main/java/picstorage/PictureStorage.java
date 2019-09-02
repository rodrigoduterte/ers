package picstorage;

import java.awt.FileDialog;
import java.io.File;
import javax.swing.JFrame;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AccessControlList;
import com.amazonaws.services.s3.model.GroupGrantee;
import com.amazonaws.services.s3.model.Permission;
import com.amazonaws.services.s3.model.PutObjectRequest;


import main.Temper;

public class PictureStorage {

	static int index = 0;

	private PictureStorage() {
	}

	public static String post() {
		index++;
		String key = "@"+index+ Temper.chunk(5);

		AWSCredentials credentials = new BasicAWSCredentials("AKIAIOG7U2NO2MEQZNTA",
				"SVPyZnfzLh3xB+XmyKEOocDlxraadKS89jP+DjtV");
		AmazonS3 s3client = AmazonS3ClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(credentials)).withRegion(Regions.US_EAST_2).build();

		JFrame jf = new JFrame();
		FileDialog fd = new FileDialog(jf);
		fd.setVisible(true);
		fd.setAlwaysOnTop(false);
//		fd.toFront();
		File[] pic = fd.getFiles();
		System.out.println(pic[0].getAbsoluteFile());
//		if (pic.length > 0) {
//			PutObjectRequest por = new PutObjectRequest("devonvirdenprojects", key, pic[0]);
//			AccessControlList acl = new AccessControlList();
//			acl.grantPermission(GroupGrantee.AllUsers, Permission.Read);
//			por.setAccessControlList(acl);
//			s3client.putObject(por);
//		}
//		System.out.println("done");
		jf.dispose();
		fd.dispose();
		return key;
	}
}
