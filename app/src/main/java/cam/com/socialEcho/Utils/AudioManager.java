package cam.com.socialEcho.Utils;

import android.content.ContentResolver;
import android.net.Uri;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by GUL on 09-11-2017.
 */

public class AudioManager {


    private static final String TAG = "AudioManager";

//    public static MediaStore.Audio.Media getBitmap(String imgUrl){
//        File audioFile = new File(imgUrl);
//        FileInputStream fis = null;
//        MediaStore.Audio.Media media = null;
//        try{
//            fis = new FileInputStream(audioFile);
//            media = media.getContentUri(imgUrl);
//        }catch (FileNotFoundException e){
//            Log.e(TAG, "getBitmap: FileNotFoundException: " + e.getMessage() );
//        }finally {
//            try{
//                fis.close();
//            }catch (IOException e){
//                Log.e(TAG, "getBitmap: IOException: " + e.getMessage() );
//            }
//        }
//        return media;
//    }

    /**
     * return byte array from a bitmap
     * quality is greater than 0 but less than 100
     * @param uri
     * @return
     */
    public static byte[] getBytesFromUrl(Uri uri, ContentResolver cr){
        byte[] data = null;
        try {
            InputStream inputStream = cr.openInputStream(uri);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buff = new byte[10240];
            int i = Integer.MAX_VALUE;
            while ((i = inputStream.read(buff, 0, buff.length)) > 0) {
                baos.write(buff, 0, i);
            }
            return baos.toByteArray();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }
}
