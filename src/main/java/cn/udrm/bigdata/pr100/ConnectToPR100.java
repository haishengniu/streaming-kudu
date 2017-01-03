package cn.udrm.bigdata.pr100;

public class ConnectToPR100 {
	public static void main(String[] args) {
		
	}
	
	/*
	private void btnConnect_Click(object sender, EventArgs e)
    {
        remoteIp = IPAddress.Parse(tBoxIP.Text.ToString());
        remotePort = Int32.Parse(tBoxPort.Text.ToString());

        try
        {
            socketTCP = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);
            remoteHostIEP = new IPEndPoint(remoteIp, remotePort);
            remoteHostEP = (EndPoint)remoteHostIEP;
            socketTCP.Connect(remoteHostEP);

            receiveTCPThread = new Thread(new ThreadStart(RecvMsgTCP));
            receiveTCPThread.Start();

            if (socketTCP.Connected)
            {
                btnConnect.Enabled = false;
                btnUnconnect.Enabled = true;
                btnSendSCPICommand.Enabled = true;
                btnStartStartScanning.Enabled = true;
                btnSetMiddleFrequency.Enabled = true;
                btnIFSpanSet.Enabled = true;

                updateFREQuency();//更新中心频率值 
                updateFREQuencySPAN();//更新显示中频全景跨距值

                RunMessage._RunMessage.DisplayMsgs("连接到PR100成功！");    
                //MessageBox.Show("连接到PR100成功！  ", "提示", MessageBoxButtons.OK, MessageBoxIcon.Information);
            }
        }
        catch (Exception err)
        {
            RunMessage._RunMessage.DisplayMsgs(err.Message); 
            MessageBox.Show(err.Message, "错误提示", MessageBoxButtons.OK, MessageBoxIcon.Error);
        }
    }
    */
    
	
}
