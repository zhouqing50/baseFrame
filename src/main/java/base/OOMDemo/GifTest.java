package base.OOMDemo;

import com.gif4j.GifDecoder;
import com.gif4j.GifEncoder;
import com.gif4j.GifImage;
import com.gif4j.GifTransformer;

import java.io.*;

/**
 * Description:
 * User: zhouq
 * Date: 2016/5/24
 */


public class GifTest {

    public static void main(String[] args) {

        File gifImageFile = new File("D:/me/image/a1.gif");
        GifImage gifImage = null;

        InputStream in = null;
        try {
            in = new FileInputStream(gifImageFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        try {
            while ((len = in.read(buffer)) > -1 ) {
                baos.write(buffer, 0, len);
            }
            baos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ByteArrayInputStream inputStream = new ByteArrayInputStream(baos.toByteArray());
        System.out.println(inputStream.available());
        /*        byte[] buffer = null;
        try {
            buffer = new byte[in.available()];
            in.read(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        try {
            if (inputStream.available() < 100) {
                gifImage = GifDecoder.decode(inputStream);
                // resize gif image to the width=150px with maintaining the aspect ratio of the original gif image dimension
                GifImage resizedGifImage1 = GifTransformer.resize(gifImage, 150, -1, false);
                //File dest1 = new File("D:/me/image/g122.gif");
                try {
                    GifEncoder.encode(resizedGifImage1, out);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println(out.size());
            }else{
                byte[] buffer2 = new byte[1024];
                int len2;
                try {
                    while ((len2 = inputStream.read(buffer2)) > -1 ) {
                        out.write(buffer2, 0, len2);
                    }
                    out.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println(out.size());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        // resize gif image to the width=150px and height=100px
        // GifImage resizedGifImage2 = GifTransformer.resize(gifImage, 150, 100, false);


  /*      ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            byte[] bytes;
*//*                Thumbnails.of("D:/me/image/a4.jpg")
                        .outputQuality(0.95f)
                        .rotate(0)
                        .size(SIZE_1280, SIZE_1280)
                        .outputFormat("jpeg")
                        .toFile("D:/me/image/ba44.jpeg");
                      *//**//*  .toOutputStream(out);
                bytes = out.toByteArray();*//*

            Thumbnails.of("D:/me/image/a3.jpg")
                     .rotate(0)
                    .scale(1f)
                    .outputFormat("jpeg")
                     .toFile("D:/me/image/ba3.jpeg");
        } catch (IOException e) {
            throw new ThumbnailException(e);
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                throw new ThumbnailException(e);
            }
        }*/


    }
}
