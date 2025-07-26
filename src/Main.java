import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Main {
    public static void main(String[] args) {
        String texto = "Ola RC4...";
        CrifraRC4.setChave("123456");
        String resultado = CrifraRC4.Encriptar(texto);
        System.out.println(resultado);
        CrifraRC4.setChave("123456");
        System.out.println(CrifraRC4.Dencriptar(resultado));
    }
}

class CrifraRC4 {
    private static int[] S = new int[256];
    private static int[] K = new int[256];

    private CrifraRC4(){}

    public static void setChave(String chave) {
        int len = chave.length();
        for (int i = 0; i <= 255; i++) {
            S[i] = i;
            K[i] = chave.charAt(i % len);
        }
        generateKSA();
    }

    private static void generateKSA() {
        int j = 0;
        for (int i = 0; i <= 255; i++) {
            j = (j + S[i] + K[i]) % 256;
            int temp = S[i];
            S[i] = S[j];
            S[j] = temp;
        }
    }

    private static byte[] transform(byte[] bytes) {
        int i = 0;
        int j = 0;
        int[] S_ = S.clone();
        byte[] resultado = new byte[bytes.length];

        for (int k = 0; k < bytes.length; k++) {
            i = (i + 1) % 256;
            j = (j + S_[i]) % 256;
            int temp = S_[i];
            S_[i] = S_[j];
            S_[j] = temp;
            int t = (S_[i] + S_[j]) % 256;
            int b = S_[t];
            resultado[k] = (byte) (bytes[k] ^ b);
        }

        return resultado;
    }

    public static String Encriptar(String texto) {
        byte[] input = texto.getBytes(StandardCharsets.UTF_8);
        byte[] output = transform(input);
        return Base64.getEncoder().encodeToString(output);
    }

    public static String Dencriptar(String textoEncriptado) {
        byte[] input = Base64.getDecoder().decode(textoEncriptado);
        byte[] output = transform(input);
        return new String(output, StandardCharsets.UTF_8);
    }
}
