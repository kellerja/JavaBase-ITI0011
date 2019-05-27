import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 * Created by Jaanus on 20.03.16.
 */
public class Connection {
    /***/
    private static final int PORT = 13131;
    /***/
    private static final int TIMEOUT = 2000;
    /***/
    private Socket conn;
    /***/
    private InputStreamReader input;
    /**
     * @param s - string to make bytearray of.
     * @return byte array of a string.
     */
    private byte[] stringToByteArray(String s) {
        byte[] result = new byte[s.length()];
        for (int i = 0; i < s.length(); i++) {
            result[i] = (byte) s.charAt(i);
        }
        return result;
    }
    /**
     * @param settings - game settings.
     */
    public Connection(String settings) {
        try {
            conn = new Socket("nisu.cs.ttu.ee", PORT);
            conn.setSoTimeout(TIMEOUT);
            input = new InputStreamReader(conn.getInputStream());
            //settings = "{\"uniid\":\"jakell\"}";
            conn.getOutputStream().write(stringToByteArray(settings));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * @param out - message to server.
     */
    public void sendAction(String out) {
        try {
            conn.getOutputStream().write(stringToByteArray(out));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * @throws IOException if unable to read from server.
     * @return server message.
     */
    public String readLine() throws IOException {
        try {
            String result = "";
            char letter;
            do {
                letter = (char) input.read();
                result += letter;
            } while (input.ready());
            return result;
        } catch (SocketTimeoutException e) {
            return null;
        }
    }
    /**
     * @throws IOException if unable to read from server.
     * @return string of score.
     */
    public String readScoreData() throws IOException {
        conn.getOutputStream().write(stringToByteArray("{\n\"parameter\": \"score\"\n}"));
        return readLine();
    }
    /**
     * @throws IOException if unable to read from server.
     * @return string of server map.
     */
    public String serverMap() throws IOException {
        conn.getOutputStream().write(stringToByteArray("{\n\"parameter\": \"state\"\n}"));
        return readLine();
    }
}
