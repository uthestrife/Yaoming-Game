/**
 * HTTPRequest.java
 *
 * Nama berkas kode sumber : HTTPRequest.java
 *
 * kelas ini berperan langsung sebagai pemroses socket yang diberikan saat
 * pembuatan objek dari kelas ini. di kelas ini akan memproses socket yang
 * diberikan. alur dasarnya adalah jika diakses suatu URL pada suatu browser
 * dengan localhost:<nomor-port>// atau sub-direktori lainnya maka akan
 * memeriksa apakah sebuah direktori atau file jika suatu file maka akan akses
 * langsung (membuka jika file gambar atau file yang diketahui dan yang tidak
 * diketahui akan diunduh oleh browser), namun jika direktori maka akan dicari
 * dulu apakah ada index.html atau tidak di direktori tersebut. jika ada maka
 * akan dibuka langsung, namun jika tidak maka akan medaftarkan semua daftar
 * file atau sub-direktori yang ada di direktori tersebut.
 *
 * Versi:
 * - 1.0.0.0 (25-Oktober-2011):
 *      - membuat rancangan awalan program dengan berdasarkan referensi pada
 *        slide dan referensi yang ada di Scele.
 *      - memahami dan mempelajari mengenai Socket programming dasar.
 * - 1.0.1.0 (26-Oktober-2011):
 *      - mencoba memecahkan template dasar yang diberikan pada referensi di
 *        Scele
 * - 1.0.2.0 (27-Oktober-2011):
 *      - membangun sistem dengan yang siap dengan berjalan dalam port terserah
 *        pengguna, namun dalam kisaran nilai 1024-65535
 *
 * Hak Cipta 2011, Andry Luthfi (0906629044) dan Yulistiyan Wardhana (0906563363)
 */
package comnet;

import java.io.IOException;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.StringTokenizer;

/**
 * kelas ini berperan langsung sebagai pemroses socket yang diberikan saat
 * pembuatan objek dari kelas ini. di kelas ini akan memproses socket yang
 * diberikan. alur dasarnya adalah jika diakses suatu URL pada suatu browser
 * dengan localhost:<nomor-port>// atau sub-direktori lainnya maka akan
 * memeriksa apakah sebuah direktori atau file jika suatu file maka akan akses
 * langsung (membuka jika file gambar atau file yang diketahui dan yang tidak
 * diketahui akan diunduh oleh browser), namun jika direktori maka akan dicari
 * dulu apakah ada index.html atau tidak di direktori tersebut. jika ada maka
 * akan dibuka langsung, namun jika tidak maka akan medaftarkan semua daftar
 * file atau sub-direktori yang ada di direktori tersebut.
 *
 * @author Andry Luthfi
 * @author Yulistiyan Wardhana
 * @version 1.0.2.0 (27-Oktober-2011)
 */
public class HTTPRequest implements Runnable {
    /**
     * standarisasi end line
     */
    public final static String CRLF = "\r\n";
    /**
     * socket yang diterima
     */
    private Socket socket;
    /**
     * pemeriksa apakah ada index.html ada atau tidak
     */
    private boolean isIndexHTML;

    // Constructor public
    public HTTPRequest(Socket socket) throws Exception {
        this.socket = socket;
        this.isIndexHTML = false;
    }

    // Implement the run() method of the Runnable interface.
    public void run() {
        try {
            // memanggil fungsi processRequest
            processRequest();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void processRequest() throws IOException {
        // mendapatkan referensi dari masukan socket dan keluaran.
        InputStream is = socket.getInputStream();
        DataOutputStream os = new DataOutputStream(socket.getOutputStream());
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        // ambil requestline pertama
        String requestLine = br.readLine();
        // tampilkan requestline
        System.out.println("HEAD LINE :  " + requestLine);
        StringTokenizer tokens = new StringTokenizer(requestLine);
        tokens.nextToken(); // buang informasi string GET
        String fileName = tokens.nextToken(); // ambil informasi nama file
        // menambahkan titik 
        fileName = "." + fileName;
        FileInputStream fis = null;
        File file = null;
        boolean fileExists = true;
        try {
            System.out.println("FILE NAME : " + fileName);
            file = new File(fileName);
            if (file.isFile()) {
                fis = new FileInputStream(fileName);
            } else {
                fileExists = false;
            }

        } catch (FileNotFoundException e) {
            fileExists = false;
            e.printStackTrace();
        }

        // Construct the response message.
        String statusLine = null;
        String contentTypeLine = null;
        String entityBody = null;
     
        if (file.isDirectory()) {
            String parent = file.getParent();
            System.out.println("PARENT : " + parent);
            parent = parent == null ? "" : parent.equals(".") ? "" : parent.substring(2);
            String mesg = "<a href=\"/" + parent + "\">..</a><br />";
            for (File dir : file.listFiles()) {
                if (dir.getName().equals("index.html")) {
                    isIndexHTML = true;
                    fis = new FileInputStream(dir);
                    break;
                }

                mesg += "<a href=\"." + dir.getPath() + "\">" + dir.getName() + "</a><br />";
            }
            statusLine = "";
            contentTypeLine = "";
            entityBody = mesg;

        } else if (fileExists) {
            statusLine = "HTTP/1.0 200 OK" + CRLF;
            contentTypeLine = "Content-type: " + contentType(fileName) + CRLF;
        } else {
            statusLine = "HTTP/1.0 404 NOT FOUND" + CRLF;
            contentTypeLine = "Content-type: " + contentType(fileName) + CRLF;
            entityBody = "" + "" + "Not Found" + CRLF;
        }
        // Send the status line.
        os.writeBytes(statusLine);

        // Send the content type line.
        os.writeBytes(contentTypeLine);

        // Send a blank line to indicate the end of the header lines.
        os.writeBytes(CRLF);
        if (fileExists || isIndexHTML) {
            sendBytes(fis, os);
            fis.close();
        } else {
            os.writeBytes(entityBody);
        }
        os.close();
        br.close();
        socket.close();
    }

    private static void sendBytes(FileInputStream fis, OutputStream os) throws IOException {
        // Construct a 1K buffer to hold bytes on their way to the socket.
        byte[] buffer = new byte[1024];
        int bytes = 0;
        // Copy requested file into the socket's output stream.
        while ((bytes = fis.read(buffer)) != -1) {
            os.write(buffer, 0, bytes);
        }
    }

    private static String contentType(String fileName) {
        if (fileName.endsWith(".htm") || fileName.endsWith(".html")) {
            return "text/html";
        } else if (fileName.endsWith(".jpeg") || fileName.endsWith(".jpg")) {
            return "image/jpeg";
        } else if (fileName.endsWith(".png")) {
            return "image/png";
        } else if (fileName.endsWith(".bmp")) {
            return "image/bmp";
        }
        return "application/octet-stream";
    }
}
