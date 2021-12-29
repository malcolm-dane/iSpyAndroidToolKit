package mdane.myapplication6.util;

import android.os.Environment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by - on 6/9/2017.
 */

public class BetterPrinter {

    public static File openFileFor(String a) {
        File imageDirectory = null;
        String storageState = Environment.getExternalStorageState();
        if (storageState.equals(Environment.MEDIA_MOUNTED)) {
            imageDirectory = new File(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),
                    "sms/");
            if (!imageDirectory.exists() && !imageDirectory.mkdirs()) {
                imageDirectory = null;
            } else {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_mm_dd_hh_mm_ss",
                        Locale.getDefault());

                return new File(imageDirectory.getPath() +
                        File.separator + a+".txt");
            }
        }
        return null;
    }
    public static void maketheFile(ArrayList aList, String a)  {
        //PrintWriter pw= null;
        try{
            String content = "This is my content which would be appended " +
                    "at the end of the specified file";
            //Specify the file name and path here
            File file =new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                    ".aLog/.audio/"+a);

    	/* This logic is to create the file if the
    	 * file is not already present
    	 */
            if(!file.exists()){
                file.createNewFile();
           OpenAndWrite(aList,a,file);
            }
            else{OpenAndWrite(aList,a,file);}

            //Here true is to append the content to file


            System.out.println("Data successfully appended at the end of file");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
private static void OpenAndWrite(ArrayList aList,String a,File file){
    FileWriter fw = null;
    try {
        fw = new FileWriter(file,true);
        BufferedWriter bw = new BufferedWriter(fw);
        for(int i=0;i<aList.size();i++){  bw.write(aList.get(i).toString());
         }

         bw.close();
    } catch (IOException e) {
        e.printStackTrace();
    }
    //BufferedWriter writer give better performance

        //Closing BufferedWriter Stream
       }

}


