package com.shatteredpixel.shatteredpixeldungeon.capstone;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.File;

public class AWSService {
    private static final String BUCKET_NAME = "myspdbucket";
    private static final String ACCESS_KEY = "AKIA2DYHDM3376WHCSYX ";
    private static final String SECRET_KEY = "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";
    private AmazonS3 amazonS3;

    public AWSService() {
        AWSCredentials awsCredentials = new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);
        amazonS3 = new AmazonS3Client(awsCredentials);
    }

    public void uploadFile(File file) {
        if (amazonS3 != null) {
            try {
                PutObjectRequest putObjectRequest =
                        new PutObjectRequest(BUCKET_NAME + "/sub_dir_name"/*sub directory*/, file.getName(), file);
                putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead); // file permission
                amazonS3.putObject(putObjectRequest); // upload file

            } catch (AmazonServiceException ase) {
                ase.printStackTrace();
            } finally {
                amazonS3 = null;
            }
        }
    }
    public void downloadFile() {
        /*
         출력문이랑 getObject에 써있는 test.csv는 제가 테스트용으로 만든 csv 파일이름입니다.
         temp_data.csv 같은 다른 파일을 다운로드하고 싶은신 경우 이름만 바꿔주시면 됩니다.
         FileOutputStream 부분에 pathname이라고 써있는 인자값이 파일이 저장될 경로랑 파일이름을 적어놓는 부분인데
         저는 C:\\Program Files로 설정했더니 액세스 거부가 떠서 그냥 임시로 D에다 저장하는걸로 했습니다.
         추후에 윈도우 설정을 해서 C:\\Program Files에 저장될 수 있도록 하면 될 거 같습니다.
         */
        if (amazonS3 != null) {
            System.out.format("Downloading %s from S3 bucket %s...\n", "test.csv", BUCKET_NAME + "/sub_dir_name");
            try {

                S3Object o = amazonS3.getObject(BUCKET_NAME + "/sub_dir_name"/*sub directory*/, "test.csv");
                S3ObjectInputStream s3is = o.getObjectContent();
                FileOutputStream fos = new FileOutputStream(new File("C:\\Program Files\\test.csv"));
                byte[] read_buf = new byte[1024];
                int read_len = 0;
                while ((read_len = s3is.read(read_buf)) > 0) {
                    fos.write(read_buf, 0, read_len);
                }
                s3is.close();
                fos.close();
            } catch (AmazonServiceException e) {
                System.err.println(e.getErrorMessage());
                System.exit(1);
            } catch (FileNotFoundException e) {
                System.err.println(e.getMessage());
                System.exit(1);
            } catch (IOException e) {
                System.err.println(e.getMessage());
                System.exit(1);

            }
        }
    }
}
