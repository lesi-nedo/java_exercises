/**
 * 
 */
package assignment6;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

/**
 * @author nedo1993
 *
 */
public class WebServer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try (ServerSocket server = new ServerSocket()){
			server.bind(new InetSocketAddress(InetAddress.getLocalHost(), 5959));
			while(true) {
				try(Socket client=server.accept();
					BufferedReader buf=new BufferedReader(new InputStreamReader(client.getInputStream()))){
					String req = buf.readLine();
					String[] req_split=req.split(" ");
					if(!req_split[0].equals("GET")) {
						System.err.println("Non valid request");
						continue;
					}
					String path = req_split[1];
					if(path.startsWith("/"))
						path=path.substring(1);
					path=java.net.URLDecoder.decode(path, StandardCharsets.UTF_8);
					DataOutputStream out = new DataOutputStream(client.getOutputStream());
					File file = new File(path);
					if(!file.exists() || file.isDirectory()) {
						String err_msg="File Not Found\n";
						out.writeBytes("HTTP/1.1 404 Not Found \r\n");
						out.writeBytes("Content-Type: text/plain \r\n");
						out.writeBytes("Content-Length: "+err_msg.length()+"\r\n");
						out.writeBytes("\r\n");
						out.writeBytes(err_msg);
						out.flush();
					} else {
						String mime=Files.probeContentType(file.toPath());
						int length=(int) file.length();
						out.writeBytes("HTTP/1.1 200 OK \r\n");
						out.writeBytes("Content-Type: " +mime+"\r\n");
						out.writeBytes("Content-Length: "+length+"\r\n");
						out.writeBytes("\r\n");
						out.write(Files.readAllBytes(file.toPath()), 0, length);
						out.flush();
						
					}
					out.close();
				} catch(IOException ex) {
					ex.printStackTrace();
				}
			}
		} catch(IOException ex) {
			ex.printStackTrace();
		}

	}

}
