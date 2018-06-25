using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Net.Sockets;
using System.Threading;
using System.Windows.Forms;
using System.Net;
using Steema.TeeChart.Styles;
using System.IO;
using Steema.TeeChart;

namespace prjDebugMonitor
{
    /// <summary>
    /// 日志消息委托
    /// </summary>
    /// <param name="str">日志信息</param>
    /// <param name="flag">日志类型</param>
    public delegate void MsgAsyncDelegate(string str, int flag);
    
    //增加波形点
    public delegate void AddPointDelegate(int num, double value);
    //显示x,y,z轴数据
    public delegate void AddPointDelegateA(List<SensorPackInfo> packs);

    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }

        private TcpListener tcpListener;
        private Thread listenThread;
        private Int32 Port;
        private bool canShowSeries;
        StreamWriter sw = null;
        string otherS = "";
        StreamWriter swdata = null;
        StreamWriter swolddata = null;
        public static DateTime logDate = DateTime.MinValue;

        /// <summary>
        /// 最大的缓冲区大小 未解析的数据流
        /// </summary>
        public const int MaxCacheLen = 1500;

        /// <summary>
        /// 未处理的数据
        /// </summary>
        private byte[] Caches = new byte[MaxCacheLen];

        /// <summary>
        /// 缓冲区指针
        /// </summary>
        private int CacheLen = 0;

        private void btnStart_Click(object sender, EventArgs e)
        {
            int v;
           // tChart1.bu
            if (!Int32.TryParse(txtPort.Text.Trim(), out v))
            {
                MessageBox.Show("请输入端口号!");
                txtPort.Focus();
                return;
            }
            try
            {
                logDate = DateTime.Now;
                sw = File.AppendText(@"log" + logDate.ToString("yyyy-MM-dd-HH-mm-ss") + ".msg.log");
                swdata = File.AppendText(@"log" + logDate.ToString("yyyy-MM-dd-HH-mm-ss") + ".data.log");
                Port = Convert.ToInt32(txtPort.ToString());
                txtMsg.Clear();
                this.tcpListener = new TcpListener(IPAddress.Any, Port);
                this.listenThread = new Thread(new ThreadStart(ListenForClients));
                listenThread.Name = "服务端侦听主线程";
                listenThread.IsBackground = true;
                this.listenThread.Start();
                txtMsg.AppendText("TCP服务器启动(端口：" + this.Port.ToString() + ")。。。\n");
                canShowSeries = true;
                txtPort.Enabled = false;

                btnStart.Enabled = false;
                btnStop.Enabled = true;
                btnClearSeries.Enabled = true;
                btnStopSeries.Enabled = true;
                otherS = "";
                line1.Clear();
                line2.Clear();
                line3.Clear();
            }
            catch (Exception e1)
            {
                txtMsg.AppendText("启动异常：" + e1.Message + Environment.NewLine + e1.StackTrace);
            }
        }

        /// <summary>
        /// 侦听所有的客户端
        /// </summary>
        private void ListenForClients()
        {
            MsgAsyncDelegate dlgt = new MsgAsyncDelegate(this.AddText);
            try
            {
                this.tcpListener.Start();
            }
            catch//(Exception e1)
            {

                this.Invoke(dlgt, "该端口：" +Port.ToString()+" 已被占用！",0);
                return;
            }

            try
            {
                while (true)
                {
                    TcpClient client = this.tcpListener.AcceptTcpClient();

                    //当接收到一客户端
                    client.NoDelay = true;
                    //clients.Add(client);

                    //创建客户端接收线程
                    Thread clientThread = new Thread(new ParameterizedThreadStart(HandleClientComm));
                    clientThread.IsBackground = true;

                    clientThread.Start(client);

                }
            }
            catch (Exception e)//强制中止或程序运行出错 会触发异常
            {
                if (e.Message == "正在中止线程。") return;
                this.Invoke(dlgt, "主线程异常：" + e.Message + Environment.NewLine + e.StackTrace,0);
            }
        }

        /// <summary>
        /// 增加日志内容
        /// </summary>
        /// <param name="str"></param>
        /// <param name="flag"></param>
        public void AddText(string str, int flag)
        {
            if (txtMsg.Lines.Length > 100) txtMsg.Clear();

            string s = DateTime.Now.ToLocalTime() + " " + str;
            txtMsg.AppendText(s +Environment.NewLine);
            sw.WriteLine(s);
            sw.Flush();
        }

        public void AddPointAfter21(List<SensorPackInfo> packs1)
        {
            List<double> xvalue = new List<double>();
            List<double> yvalue = new List<double>();
            List<double> zvalue = new List<double>();

            foreach (SensorPackInfo pack in packs1)
            {
                long val = 0;
                int index = 0;

                foreach (byte b in pack.Data)
                {
                    val = val * 256 + b;
                    index++;
                    if (index % 6 == 2)
                    {
                        xvalue.Add(val);
                        swdata.Write(val.ToString() + ",");
                        val = 0;
                    }

                    if (index % 6 == 4)
                    {
                        yvalue.Add(val);
                        swdata.Write(val.ToString() + ",");
                        val = 0;
                    }

                    if (index % 6 == 0)
                    {
                        zvalue.Add(val);
                        swdata.Write(val.ToString() + Environment.NewLine);
                        val = 0;
                    }
                }
            }
            swdata.Flush();

            AddPointAfter2(xvalue, yvalue, zvalue);

        }
        public void AddPointAfter2(List<double> xvalue, List<double> yvalue, List<double> zvalue)
        {
            if (!canShowSeries) return;

            line1.BeginUpdate();
            line2.BeginUpdate();
            line3.BeginUpdate();
            int cnt = 1000;
            if((line1.XValues.Count+xvalue.Count)>cnt)
            {
                tChart1.Axes.Bottom.Scroll(xvalue.Count, false);
            }
            line1.Add(xvalue.ToArray());

            if ((line2.XValues.Count + yvalue.Count) > cnt)
            {
                tChart2.Axes.Bottom.Scroll( yvalue.Count, false);
            }
            line2.Add(yvalue.ToArray());

            if ((line3.XValues.Count + zvalue.Count) > cnt)
            {
                tChart3.Axes.Bottom.Scroll(zvalue.Count, false);
            }
            line3.Add(zvalue.ToArray());
            line1.EndUpdate();
            line2.EndUpdate();
            line3.EndUpdate();
        }

        public void AddPointAfter1( double xvalue,double yvalue,double zvalue)
        {

            if (tChart1.Series.Count > 0)
            {
                Line line = (Line)tChart1.Series[0];
                //line.BeginUpdate();
                line.Add(xvalue);
                //line.EndUpdate();
                //插入点

            }
            else
            {
                //创建波形
                Line line = new Line();
                line.XValues.DateTime = false;

                //line.Title = "line "+num.ToString();
                tChart1.Series.Add(line);
                //line.BeginUpdate();
                line.Add(xvalue);
                //line.EndUpdate();
            }

            if (tChart2.Series.Count > 0)
            {
                Line line = (Line)tChart2.Series[0];
                //line.BeginUpdate();
                line.Add(yvalue);
                //line.EndUpdate();
                //插入点

            }
            else
            {
                //创建波形
                Line line = new Line();
                line.XValues.DateTime = false;

                //line.Title = "line "+num.ToString();
                tChart2.Series.Add(line);
                //line.BeginUpdate();
                line.Add(yvalue);
                //line.EndUpdate();
            }

            if (tChart3.Series.Count > 0)
            {
                Line line = (Line)tChart3.Series[0];
                //line.BeginUpdate();
                line.Add(zvalue);
                //line.EndUpdate();
                //插入点

            }
            else
            {
                //创建波形
                Line line = new Line();
                line.XValues.DateTime = false;

                //line.Title = "line "+num.ToString();
                tChart3.Series.Add(line);
                //line.BeginUpdate();
                line.Add(zvalue);
                //line.EndUpdate();
            }

        }

        public void AddPointAfter(int num, double value)
        {
            Line line = null;
            TChart chart = null;
            if (num == 0)
                chart = tChart1;
            if (num == 1)
                chart = tChart2;
            if (num == 2)
                chart = tChart3;

            if (chart.Series.Count > 0)
            {
                line = (Line)chart.Series[0];
                line.BeginUpdate();
                line.Add(value);
                line.EndUpdate();
                //插入点

            }
            else
            {
                //创建波形
                line = new Line();
                line.XValues.DateTime = false;

                //line.Title = "line "+num.ToString();
                chart.Series.Add(line);
                line.BeginUpdate();
                line.Add(value);
                line.EndUpdate();
            }
        }
        public void AddPoint(int num, uint value)
        {
            Line line = null;
            TChart chart = null;
            if (num == 0)
                chart = tChart1;
            if (num == 1)
                chart = tChart2;
            if (num == 2)
                chart = tChart3;

            if (chart.Series.Count>0)
            {
                line = (Line)chart.Series[0];
                line.BeginUpdate();
                line.Add((int)value);
                line.EndUpdate();
                //插入点

            }
            else
            {
                //创建波形
                line = new Line();
                line.XValues.DateTime = false;

                //line.Title = "line "+num.ToString();
                chart.Series.Add(line);
                line.BeginUpdate();
                line.Add((int)value);
                line.EndUpdate();
            }
        }
        /// <summary>
        /// 二进制数组转换为字符串
        /// </summary>
        /// <param name="bytes">二进制数组</param>
        /// <param name="len">二进制数组长度</param>
        /// <returns>字符串</returns>
        public static string ConvertBytesToStr(byte[] bytes, int len)
        {
            string str = "";
            for (int i = 0; i < len; i++)
            {
                byte b = bytes[i];
                if (str == "")
                    str += b.ToString("X2");
                else
                    str += " " + b.ToString("X2");
            }
            return str;
        }

        /// <summary>
        /// 每个客户端的处理函数
        /// </summary>
        /// <param name="client">tcp客户端</param>
        private void HandleClientComm(object client)
        {
            TcpClient tcpClient = (TcpClient)client;
            try
            {
                MsgAsyncDelegate dlgt = new MsgAsyncDelegate(this.AddText);

                NetworkStream clientStream = tcpClient.GetStream();
                //提示已经登录
                this.Invoke(dlgt, tcpClient.Client.RemoteEndPoint.ToString() + "  登录！", 0);

                byte[] message = new byte[4096];
                int bytesRead;

                while (true)
                {
                    bytesRead = 0;

                    try
                    {
                        bytesRead = clientStream.Read(message, 0, 4096);
                    }
                    catch
                    {
                        break;
                    }

                    if (bytesRead == 0)
                    {
                        this.Invoke(dlgt, "  收到长度为0的数据！", 0);
                        continue;
                    }
                    else
                    {
                        //--收到信息
                        //--解析数据
                        try
                        {
                            this.Invoke(dlgt, "接收" + tcpClient.Client.RemoteEndPoint.ToString()
                                + "(长度:" + bytesRead.ToString() + ")内容:" + ConvertBytesToStr(message, bytesRead), 2);
                        }
                        catch { }

                        try
                        {
                            AddBytes(message, bytesRead);
                        }
                        catch (Exception ee)
                        {
                            this.Invoke(dlgt, "  解析出错！！！", 0);
                        }

                    }
                }

                try
                {
                    this.Invoke(dlgt, tcpClient.Client.RemoteEndPoint.ToString() + "  退出！", 0);
                }
                catch { }

            }
            catch (Exception e) //强制中止或程序运行出错 会触发异常
            {
                if (e.Message == "正在中止线程。") return;
                MsgAsyncDelegate dlgt = new MsgAsyncDelegate(this.AddText);
            }
        }

        /// <summary>
        /// 未处理缓冲区加入一个字节
        /// </summary>
        /// <param name="b">字节值</param>
        private void CacheAdd(byte b)
        {

            if (CacheLen == MaxCacheLen)
            {
                for (int i = 0; i < MaxCacheLen - 1; i++)
                {
                    Caches[i] = Caches[i + 1];
                }
                Caches[MaxCacheLen - 1] = b;
            }
            else
            {
                Caches[CacheLen] = b;
                CacheLen++;
            }
        }

        /// <summary>
        /// 
        /// </summary>
        /// <param name="message"></param>
        /// <param name="bytesRead"></param>
        public void AddBytes(byte[] message, int bytesRead)
        {
            //if (bytesRead < 10) return;
            //MsgAsyncDelegate dlgt = new MsgAsyncDelegate(ServerFrm.AddText);
            AddPointDelegateA addPointdlgt = new AddPointDelegateA(this.AddPointAfter21);
            List<SensorPackInfo> listP = new List<SensorPackInfo>();
            //采集到的数据与缓冲数据整合----------------------------------
            byte[] bytes = new byte[10000];
            if (CacheLen > 0)
            {
                for (int j = 0; j < CacheLen; j++)
                {
                    bytes[j] = Caches[j];
                }
            }

            //CacheLen = 0;
            for (int j = 0; j < bytesRead; j++)
            {
                bytes[j + CacheLen] = message[j];
            }

            int count = bytesRead + CacheLen;

            CacheLen = 0;
            //---------------------------------
            int i = 0;
            //循环遍历每个字符
            while (i < count - 1)
            {
                //判断是否找到包头
                if ((bytes[i] == 0xA) && (bytes[i + 1] == 0xD))
                {
                    int start = i;
                    i += 2;
                    bool flagEnd = false;
                    while (i < count)
                    {
                        i++;
                        if (i == count) break;
                        //判断是否找到包尾
                        if ((bytes[i - 1] == 0xD) && (bytes[i] == 0xA))
                        {
                            //找到包信息
                            flagEnd = true;
                            byte[] b = new byte[i - start + 1];
                            for (int k = 0; k < (i - start + 1); k++)
                            {
                                b[k] = bytes[start + k];
                            }

                            SensorPackInfo pack = new SensorPackInfo(b, b.Length);
                            if (pack.Flag)//检查正确
                            {
                                 listP.Add(pack);

                            }
                            else //解析错误
                            {
                                if (pack.DataLen > (b.Length - 8)) //如果数据段中包含0d 0a 结尾帧 通过此处进行跳过
                                {
                                    continue;
                                }
                                if (!pack.crcFlag)
                                {
                                    //ServerFrm.Invoke(dlgt, "[解析] CRC错误  数据内容:" 
                                    //+ PrjNewServer.Utils.Tools.ConvertBytesToStr(pack.AllData, pack.AllData.Length), 3);
                                    //提出crc错误的帧 不放入缓冲区中
                                }
                                else
                                {
                                    //放入缓冲中
                                    CacheAdd(bytes[start]);
                                    CacheAdd(bytes[start + 1]);

                                    i = start + 1;
                                }
                            }
                            break;
                        }
                    }

                    if (!flagEnd)
                    {
                        //放入缓冲中
                        for (int kk = start; kk < i; kk++)
                        {
                            CacheAdd(bytes[kk]);
                        }
                    }

                }//未找到包头
                else
                {
                    //放入缓冲中
                    CacheAdd(bytes[i]);
                }
                i++;
            }

            while (i < count)
            {
                //放入缓冲中
                CacheAdd(bytes[i]);
                i++;
            }


        }

        private void btnStopSeries_Click(object sender, EventArgs e)
        {
            canShowSeries = !canShowSeries;
            if (canShowSeries)
                btnStopSeries.Text = "暂停波形";
            else
                btnStopSeries.Text = "显示波形";
        }

        private void btnClearSeries_Click(object sender, EventArgs e)
        {
            //tChart1.Series.Clear();
            //tChart2.Series.Clear();
            //tChart3.Series.Clear();
            btnStopSeries_Click(null, null);
            Thread.Sleep(100);
            line1.Clear();
            line2.Clear();
            line3.Clear();
            
            btnStopSeries_Click(null, null);
        }

        private void btnStop_Click(object sender, EventArgs e)
        {
            try
            {
                this.listenThread.Abort();
                tcpListener.Stop();
                otherS = "";
                /*foreach (TcpClient client in clients)
                {
                    client.Close();
                }

                foreach (Thread thread in ListThread)
                {
                    thread.Abort();
                }*/

                this.listenThread.Abort();
                //MachineCaches.Clear();
                //clients.Clear();
                //clientIPs.Clear();
                //ListThread.Clear();
                this.AddText("TCP服务器停止(端口：" + this.Port.ToString() + ")", 0);
                txtPort.Enabled = true;
                btnStart.Enabled = true;
                btnStop.Enabled = false;
                btnClearSeries.Enabled = false;
                btnStopSeries.Enabled = false;
            }
            catch (Exception e1)
            {
                if (e1.Message == "正在中止线程。") return;
                this.AddText("停止异常:" + e1.Message + Environment.NewLine + e1.StackTrace + ")", 9);
            }
        }

       
    }
}
