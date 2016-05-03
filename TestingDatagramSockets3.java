/*
TestingDatagramSockets3.java
Uses IPv6 Addressing
*/

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;

public class TestingDatagramSockets3
{
	final static String BCAST_ADDR = "FF02:0:0:0:0:1:1:7";
	final static int BCAST_PORT = 7777;

	public static void main(String[] args)
	{
		try
		{

			System.out.println("Casting BCAST_ADDR.");
			InetAddress bcastInetAddress = InetAddress.getByName(BCAST_ADDR);

			System.out.print("Multicast Address: ");
			System.out.println(bcastInetAddress.isMulticastAddress());

			System.out.print("Link Local: ");
			System.out.println(bcastInetAddress.isMCLinkLocal());

			System.out.println("Creating the MulticastSocketAddress.");
			InetSocketAddress broadcastAddress = new InetSocketAddress(BCAST_ADDR, BCAST_PORT);

			System.out.println("Creating MulticastSocket.");
			MulticastSocket ms = new MulticastSocket(broadcastAddress);

			System.out.println("Joining Multicast Group.");
			ms.joinGroup(bcastInetAddress);

			System.out.print("Interface: ");
			System.out.println(ms.getInterface());

			System.out.print("Network Interface: ");
			System.out.println(ms.getNetworkInterface());

			System.out.println("Creating DatagramSocket.");
			DatagramSocket ds = new DatagramSocket();

			System.out.println("Sending message from DatagramSocket.");
			String message = "Success!";
			DatagramPacket msgPacket = new DatagramPacket(message.getBytes(), message.getBytes().length, broadcastAddress);
			ds.send(msgPacket);

			System.out.println("Recieving message from MulticastSocket.");
			byte[] buf = new byte[256];
			DatagramPacket messagePacket = new DatagramPacket(buf, buf.length);
			ms.receive(messagePacket);
			message = new String(buf, 0, buf.length);
			System.out.println("Message: " + message);

			ms.close();
			ds.close();


		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

	}
}
