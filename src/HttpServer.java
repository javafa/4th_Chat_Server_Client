import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {
	public static void main(String[] args) {
		WebServer server = new WebServer(8089);
		server.start();
	}
}

class WebServer extends Thread {
	ServerSocket server;
	public boolean runFlag = true;
	public WebServer(int port){
		try{
			server = new ServerSocket(port);
		}catch(Exception e){e.printStackTrace();}
	}
	public void run(){
		while(runFlag){
			// 1. Ŭ���̾�Ʈ ���� ���
			try{
				Socket client = server.accept();
				// 2. ��û�� ���� ó���� ���ο� thread���� ���ش�
				new Thread(){
					public void run(){
						try{
							// 3. ��Ʈ���� ����
							InputStreamReader isr = new InputStreamReader(client.getInputStream());
							BufferedReader br = new BufferedReader(isr);
							// 4. ������������ ��û�� �ּҷ� �ٴ����� ��ɾ ������� ���� ������ ó��
							String line = br.readLine();
							System.out.println("line="+line);
							// 5. ��û�� ��ɾ��� ù �ٸ� parsing �ؼ� ������ ����
							// Method[ ]�����ڿ�(���������������ּ�)[ ]���������ǹ���
							String cmd[] = line.split(" ");
							if("/hello".equals(cmd[1])){
								String msg = "<h1>Hello!~~~~~~~~~~</h1>";
								OutputStream os = client.getOutputStream();
								// ȭ�鿡�� ������ �ʴ� ��Ÿ����
								os.write("HTTP/1.0 200 OK \r\n".getBytes());
								os.write("Content-Type: text/html \r\n".getBytes());
								os.write(("Content-Length: "+msg.getBytes().length+"\r\n").getBytes());
								// ����� �ٵ� �����ڸ� ����
								os.write("\r\n".getBytes());
								// ���� ���޵Ǵ� ������
								os.write(msg.getBytes());
								os.flush();
							}else{
								
							}
						}catch(Exception e){e.getStackTrace();}
					}
				}.start();
			}catch(Exception e){e.printStackTrace();}
		}
	}
}







