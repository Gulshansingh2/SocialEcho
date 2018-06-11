package cam.com.socialEcho.Utils;

import android.os.Environment;

/**
 * Created by GUL on 07-11-2017.
 */

public class FilePaths {
    //"storage/emulated/0"
    public String ROOT_DIR = Environment.getExternalStorageDirectory().getPath();

    public String PICTURES = ROOT_DIR + "/Pictures";
    public String CAMERA = ROOT_DIR + "/DCIM/camera";
    public String SCREENSHOTS = ROOT_DIR + "/DCIM/Screenshots";
    public String WHATSAPP_IMAGES = ROOT_DIR + "/WhatsApp/Media/WhatsApp Images";

    public String FIREBASE_IMAGE_STORAGE = "photos/users/";
    public String FIREBASE_AUDIO_STORAGE = "audios/users/";

}
