package com.hand.Exam2_2_Client;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import view.MainWindow;


public class ChatManager {

	private ChatManager(){}
	private static final ChatManager instance = new ChatManager();
	public static ChatManager getCM() {
		return instance;
	}
	
	MainWindow window;
	String IP;
	Socket socket;
	BufferedReader reader;
	PrintWriter writer;
	
	public void setWindow(MainWindow window) {
		this.window = window;
		window.appendText("文本框已经和ChatManager绑定了。");
	}
	
	public void connect(String ip) {
		this.IP = ip;
		new Thread(){

			@Override
			public void run() {
				try {
					socket = new Socket(IP, 12345);
					
					FileOutputStream fos = new FileOutputStream("SampleChapter1.pdf");
					OutputStreamWriter osw = new OutputStreamWriter(fos);
					PrintWriter pw = new PrintWriter(osw,true);
					
					writer = new PrintWriter(
							new OutputStreamWriter(
									socket.getOutputStream()));
					
					reader = new BufferedReader(
							new InputStreamReader(
									socket.getInputStream()));
					String line;
//					while ((line = reader.readLine()) != null) {
//						pw.println(line);
//						//window.appendText("接收中");
//					}
					if ((line = reader.readLine()) != null) {
						pw.println(line);
						//window.appendText("接收中");
					}else{
						window.appendText("接收完毕");
						
					}					
					pw.close();
					osw.close();
					//writer.close();
					reader.close();
					//writer = null;
					reader = null;
					
				} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}
	
	public void send(String out) {
		if (writer != null) {
			writer.write(out+"\n");
			writer.flush();
		}else {
			window.appendText("当前的链接已经中断");
		}
	}
}
