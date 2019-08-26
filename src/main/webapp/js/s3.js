/**
 * 
 */

var albumBucketName = 'BUCKET_NAME';
var bucketRegion = 'REGION';
var IdentityPoolId = 'IDENTITY_POOL_ID';

AWS.config.update({
	region : bucketRegion,
	credentials : new AWS.CognitoIdentityCredentials({
		IdentityPoolId : IdentityPoolId
	})
});

var s3 = new AWS.S3({
	apiVersion : '2006-03-01',
	params : {
		Bucket : albumBucketName
	}
});

function listAlbums() {
	s3
			.listObjects(
					{
						Delimiter : '/'
					},
					function(err, data) {
						if (err) {
							return alert('There was an error listing your albums: '
									+ err.message);
						} else {
							var albums = data.CommonPrefixes.map(function(
									commonPrefix) {
								var prefix = commonPrefix.Prefix;
								var albumName = decodeURIComponent(prefix
										.replace('/', ''));
								return getHtml([
										'<li>',
										'<span onclick="deleteAlbum(\''
												+ albumName + '\')">X</span>',
										'<span onclick="viewAlbum(\''
												+ albumName + '\')">',
										albumName, '</span>', '</li>' ]);
							});
							var message = albums.length ? getHtml([
									'<p>Click on an album name to view it.</p>',
									'<p>Click on the X to delete the album.</p>' ])
									: '<p>You do not have any albums. Please Create album.';
							var htmlTemplate = [
									'<h2>Albums</h2>',
									message,
									'<ul>',
									getHtml(albums),
									'</ul>',
									'<button onclick="createAlbum(prompt(\'Enter Album Name:\'))">',
									'Create New Album', '</button>' ]
							document.getElementById('app').innerHTML = getHtml(htmlTemplate);
						}
					});
}

function createAlbum(albumName) {
	  albumName = albumName.trim();
	  if (!albumName) {
	    return alert('Album names must contain at least one non-space character.');
	  }
	  if (albumName.indexOf('/') !== -1) {
	    return alert('Album names cannot contain slashes.');
	  }
	  var albumKey = encodeURIComponent(albumName) + '/';
	  s3.headObject({Key: albumKey}, function(err, data) {
	    if (!err) {
	      return alert('Album already exists.');
	    }
	    if (err.code !== 'NotFound') {
	      return alert('There was an error creating your album: ' + err.message);
	    }
	    s3.putObject({Key: albumKey}, function(err, data) {
	      if (err) {
	        return alert('There was an error creating your album: ' + err.message);
	      }
	      alert('Successfully created album.');
	      viewAlbum(albumName);
	    });
	  });
	}