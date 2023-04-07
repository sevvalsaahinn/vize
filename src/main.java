import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

public class main {
    public static void main(String[] args) throws IOException {

        System.out.println("1- Elit üye ekleme\n2- Genel Üye ekleme\n3- Mail Gönderme");
        //Scanner input = new Scanner(System.in);
        //int secim = input.nextInt();
        System.out.print("İşlem: ");
        int secim=1;
        //int secim = input.nextInt();

        if (secim == 1) {

            Scanner input = new Scanner(System.in);
            System.out.print("İsim: ");
            String isim = input.nextLine();
            System.out.print("Soyisim: ");
            String soyisim = input.nextLine();
            System.out.print("Email: ");
            String email = input.nextLine();

            ElitUye elit = new ElitUye(isim,soyisim,email);

            FileWriter writer = new FileWriter("ElitUye.txt");
            writer.write(isim + "\t" + soyisim + "\t" + email);
            writer.close();
            System.out.println("Elit Üye Dosyasına yazıldı.");
        }

        if (secim == 2) {

            System.out.print("İsim: ");
            String isim = input.nextLine();
            System.out.print("Soyisim: ");
            String soyisim = input.nextLine();
            System.out.print("Email: ");
            String email = input.nextLine();

            GenelUye genel = new GenelUye(isim,soyisim,email);

            FileWriter writer = new FileWriter("GenelUye.txt");
            writer.write(isim + "\t" + soyisim + "\t" + email);
            writer.close();
            System.out.println("Genel Üye Dosyasına yazıldı.");
            
        }

        if (secim == 3) {

            System.out.println("mail işlemi yapılcak");
            //mail gonderme
            
        }
    }
}
