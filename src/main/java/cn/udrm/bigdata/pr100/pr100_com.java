package cn.udrm.bigdata.pr100;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;

public class pr100_com extends Thread
{
    Thread UDPDataThread;//建立线程用于显示
    //Thread playBackThread;//建立线程用于回放
    String strPlayFilePath = "";
//    FileStream fsData;
//    StreamReader srData;

//    InputStream is;
//	OutputStream os;

    private static final int PORT = 48792;
    private static DatagramSocket dataSocket;
    private static DatagramPacket dataPacket;
    private static byte[] receiveByte;
    private static String receiveStr;

    private Socket socketUdp;

    private String _RecvPort = "49000";
    private String _RecvIP="172.16.8.175";
    private boolean _NetDisplayFlag = false;
    private String _DataSaveFilePath = "";

    public static void Init() {
        try {
            dataSocket = new DatagramSocket(PORT);
            receiveByte = new byte[0x80000];
            dataPacket = new DatagramPacket(receiveByte, receiveByte.length);
            receiveStr = "";
            int i = 0;
            while (i == 0)// 无数据，则循环
            {
                dataSocket.receive(dataPacket);
                i = dataPacket.getLength();
                // 接收数据
                if (i >= 0) {
                    // 指定接收到数据的长度,可使接收数据正常显示,开始时很容易忽略这一点
                   Object receiveStr = receiveByte;
                 // ObjectInputStream in=new ObjectInputStream();
                   // ByteArrayInputStream bs=new ByteArrayInputStream(dataPacket.getData());
                   // ObjectInputStream os=new ObjectInputStream(bs);
                    //Bytearray m = (Bytearray)os.readObject();
                    Pscan_type psc=(Pscan_type)receiveStr;
                    System.out.println(psc.StartFreq_high);
                    i = 0;// 循环接收
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void NetDisplayFlag(boolean value)
    {
        if (value == true)
        {
            if (_NetDisplayFlag == true)
            {
                StopRecvData();
            }
//                StartRecvData();
            _NetDisplayFlag = value;
        }
        else
        {
            if (_NetDisplayFlag == true)
            {
                StopRecvData();
            }
            _NetDisplayFlag = value;
        }
    }

    public boolean getNetDisplayFlag()
    {
        return _NetDisplayFlag;
    }

    private void StopRecvData()
    {
        try
        {
            dataSocket.close();
//        	dataPacket.close();
        }
        catch(Exception e)
        { }
        if (UDPDataThread != null && UDPDataThread.isAlive())
        {
//            UDPDataThread.Abort();
            UDPDataThread.interrupt();
        }
        try
        {
            CloseUDPConnection();
        }
        catch(Exception e)
        { }
    }

    //释放UDP资源，注销接收线程
    public void CloseUDPConnection()
    {
        if (socketUdp != null)
        {
//            socketUdp.Close();
            try {
                socketUdp.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }



    @Override
    public void run() {
        // TODO Auto-generated method stub
        Init();
    }

    public static void main(String[] args) throws Exception
    {
        Init();
    }
}