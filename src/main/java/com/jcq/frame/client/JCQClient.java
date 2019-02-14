package com.jcq.frame.client;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * 
 * @author reborn
 *
 */
public class JcqClient {

	/**
	 * 端口号 默认1011
	 */
	private int port = 1011;
	/**
	 * 主机号 默认为127.0.0.1
	 */
	private String host = "127.0.0.1";
	private Socket socket = null;

	/**
	 * 根据自定义的端口号与主机号创建CQ 客户端
	 * 
	 * @param port
	 * @param host
	 */
	public JcqClient(int port, String host) {
		super();
		this.port = port;
		this.host = host;
	}

	public JcqClient() {
	}

	/**
	 * 初始化一个client实例
	 */
	public Socket getInstance() {
		try {
			socket = new Socket(host, port);
			System.out.println("client init ...");
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("init error ...");
		}
		return socket;
	}

	/**
	 * 2个异常 测试代码
	 * 
	 * @param args
	 * @throws UnknownHostException 找不到主机地址异常
	 * @throws IOException          传输流异常
	 */
	public static void main(String[] args) throws UnknownHostException, IOException {
		JcqClient sc = new JcqClient();
		sc.getInstance();
		sc.sendGroupMessage("hello", "986771570", "960441931");
		sc.closeClinet();

	}

	/**
	 * 向指定的QQ发送信息
	 * 
	 * @param message 消息
	 * @param fromQQ  指定QQ号
	 */
	public void sendPrivateMessage(String message, String fromQQ) {
		OutputStreamWriter write = null;
		try {
			write = new OutputStreamWriter(socket.getOutputStream(), "UTF-8");
           //等待发送的数据
			String send = "sendPrivateMessage:" + message + "&" + fromQQ;
			write.write(send);
			System.out.println("msg send ok!");
		} catch (IOException  e) {
			e.printStackTrace();
			// 记录错误日志
			throw new RuntimeException("客户端发送数据失败" + e);
		} finally {
			try {
				if (write != null) {
					write.close();
				}
			} catch (IOException e) {
				// 安静的关闭
				e.printStackTrace();
			}

		}
	}

	/**
	 * 向QQ群里指定QQ发送消息
	 * 
	 * @param message   消息
	 * @param fromQQ    指定的QQ
	 * @param fromGroup 指定的QQ群
	 */
	public void sendGroupMessage(String message, String fromQQ, String fromGroup) {
		OutputStreamWriter write = null;
		try {
			write = new OutputStreamWriter(socket.getOutputStream(), "UTF-8");
			// 等待发送的数据
			String send = "sendGroupMessage:" + message + "&" + fromQQ + "&" + fromGroup;
			write.write(send);
			System.out.println("msg send ok!");

		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("客户端发送数据失败" + e);
		} finally {
			try {
				if (write != null) {
					write.close();
				}
			} catch (IOException e) {
				e.printStackTrace();// 安静的关闭
			}

		}
	}

	/**
	 * 消息发送后应该关闭socket隧道 防止长时间占用链接，应该使用线程池来进行维护 关闭客户端
	 * 
	 * @param socket
	 */
	public void closeClinet() {
		try {
			if (socket != null) {
				socket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();// 安静的关闭
		}
	}

}
