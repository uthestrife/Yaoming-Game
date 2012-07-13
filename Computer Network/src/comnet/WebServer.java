/**
 * WebServer.java
 *
 * Nama berkas kode sumber : WebServer.java
 *
 * kelas ini berperan langsung sebagai Web Server lokal seperti layaknya
 * perangkat lunak seperti XAMPP atau WAMPServer. kelas ini berperan menerima
 * socket sehingga nanti diproses lebih lanjut. respon yang diterima akan
 * diproses sesuai dengan tindakan yang seharusnya.
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
import java.net.ServerSocket;
import java.net.Socket;

/**
 * kelas ini berperan langsung sebagai Web Server lokal seperti layaknya
 * perangkat lunak seperti XAMPP atau WAMPServer. kelas ini berperan menerima
 * socket sehingga nanti diproses lebih lanjut. respon yang diterima akan
 * diproses sesuai dengan tindakan yang seharusnya.
 *
 * @author Andry Luthfi
 * @author Yulistiyan Wardhana
 * @version 1.0.2.0 (27-Oktober-2011)
 */
public final class WebServer {

    /**
     * kelas utama yang akan simulasikan kegunaan dari socket dan komponen
     * lainnya. disini akan dibuat thread selalu true dan disini akan melayani
     * setiap respon yang dihasilkan dan diproses lalu balik lagi menunggu
     * repon selanjutnya.
     * 
     * @param arguments parameter lebih yang diberikan pengguna, disini diharap-
     *        kan ada. yakni satu parameter, dan parameter itu adalah nomor port
     * @throws IOException kesalahan input output
     * @throws Exception kesalahan lainnya
     */
    public static void main(String arguments[]) throws IOException, Exception {
        // jika parameter sesuai harapan, berjumlah 1
        if (arguments.length == 1) {
            //set the port number
            int port = Integer.parseInt(arguments[0]);
            if (port >= 1024 && port <= 65535) {
                ServerSocket soketAwal = new ServerSocket(port);
                while (true) {
                    Socket soket = soketAwal.accept();
                    // Construct an object to process the HTTP request message.
                    HTTPRequest request = new HTTPRequest(soket);
                    // Create a new thread to process the request.
                    Thread thread = new Thread(request);
                    // Start the thread.
                    thread.start();
                }
            } else {
                // selain itu maka salah, port tidak tersedia
                System.out.println("Nomor PORT yang anda masukan masih salah, intervalnya [1024-65535]");
            }
        } else {
            // lebih dari 1 atau tidak ada maka diasumsikan salah
            System.out.println("tolong jalankan program dengan instruksi panggilan : java WebServer <nomor port [1024-65535]>");
        }


    }
}
