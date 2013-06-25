using System;
using System.Data;
using System.Configuration;
using System.Linq;
using System.Web;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.HtmlControls;
using System.Web.UI.WebControls;
using System.Web.UI.WebControls.WebParts;
using System.Xml.Linq;
using System.Data.SqlClient;
using System.Text.RegularExpressions;
using System.Collections;
using System.Collections.Generic;
using System.Xml;

namespace TestWebService3
{
    public class Feed_backSystem : IDisposable
    {
        public static SqlConnection sqlCon;
        private String ConServerStr = @"Data Source=O-PC;Initial Catalog=hhhjjj;Persist Security Info=True;User ID=SSL;Password=SSL";
        public static LoginSystem lsy;
        #region//默认构造函数

        public Feed_backSystem()
        {
            if (sqlCon == null)
            {
                sqlCon = new SqlConnection();
                sqlCon.ConnectionString = ConServerStr;
                sqlCon.Open();
                lsy = new LoginSystem();
            }
        }
        #endregion
        #region//关闭/销毁函数，相当于Close()
        public void Dispose()
        {
            if (sqlCon != null)
            {
                sqlCon.Close();
                sqlCon = null;
            }
        }
        #endregion
        #region//评价经验某一步并向作者返回评论消息
        /// <summary>
        /// 评价经验某一步并向作者返回评论消息
        /// </summary>
        /// <param name="ID">用户名</param>
        /// <param name="info">评论内容</param>
        /// <param name="usernamea">评论人</param>
        /// <param name="time">次数</param>
        /// <returns></returns>
       /*public bool commentsbsa(int ID, string info,string usernamea,int time)
        {
            try
            {

            XmlDocument xmlDoc = new XmlDocument();
                    // 文档加载  
                    xmlDoc.Load(System.Web.HttpContext.Current.Server.MapPath("/DATA/" +ID+ ".xml"));
                    // 查找<sbsname>节点 
                    //XmlNode root = xmlDoc.SelectSingleNode(username);
                     XmlNode xe1 = xmlDoc.SelectSingleNode("/" +ID+ "/" + "S-" + sbsname+"/第"+time+"步");

                    // 创建一个<Node>节点  
                    //XmlElement xe1 = (XmlElement)xmlDoc.SelectSingleNode(sbsname);
                    // 设置该节点genre属性  
                    //xe1.SetAttribute("更新时间", System.DateTime.Now.ToString());
                    // 设置该节点ISBN属性  
                    //xe1.SetAttribute("ISBN", "1-1111-1");

                    XmlElement xesub1 = xmlDoc.CreateElement(usernamea+"评论:");
                    xesub1.InnerText = info; // 设置文本节点  
                    xesub1.SetAttribute("评论时间", System.DateTime.Now.ToString());
                    xe1.AppendChild(xesub1);  // 添加到<sbsname>节点中  

                    /*XmlElement xesub2 = xmlDoc.CreateElement("author");
                    xesub2.InnerText = "高手";// 设置文本节点  
                    xe1.AppendChild(xesub2);  // 添加到<Node>节点中  

                    XmlElement xesub3 = xmlDoc.CreateElement("price");
                    xesub3.InnerText = "158.3";// 设置文本节点  
                    xe1.AppendChild(xesub3); // 添加到<Node>节点中  ****/

                    //root.AppendChild(xe1); //添加到<Employees>节点中  
        /*xmlDoc.Save(System.Web.HttpContext.Current.Server.MapPath("/DATA/" + "P-" + username + ".xml"));
        return true;
               
}
catch (Exception)
{
    return false;
}
}*/
        #endregion
        #region//对经验做出整体的评价
        /// <summary>
        /// 对经验做出整体的评价
        /// </summary>
        /// <param name="ID">经验ID</param>
        /// <param name="info">评价内容</param>
        /// <param name="usernamea">评价人</param>
        /// <returns></returns>
        public string commentsbsa(string ID, string info, string usernamea)
        {
            try
            {

                XmlDocument xmlDoc = new XmlDocument();
                // 文档加载  
                xmlDoc.Load(System.Web.HttpContext.Current.Server.MapPath("/DATA/" + ID+ ".xml"));
                // 查找<sbsname>节点 
                //XmlNode root = xmlDoc.SelectSingleNode(username);
                XmlNode xe1 = xmlDoc.SelectSingleNode("/S-" +ID+ "/" +"评论" );

                XmlElement xesub1 = xmlDoc.CreateElement("P-"+usernamea);
                xesub1.SetAttribute("name",usernamea);
                xesub1.InnerText = info; // 设置文本节点 
                xesub1.SetAttribute("评论时间", System.DateTime.Now.ToString());
                xe1.AppendChild(xesub1);  // 添加到<sbsname>节点中  

                xmlDoc.Save(System.Web.HttpContext.Current.Server.MapPath("/DATA/" + ID+ ".xml"));
                return "true";

            }
            catch (Exception e)
            {
                return "false"+e;
            }
        }
        #endregion

        #region//读取评论 
        public List<string> getcommentsbs(string ID)
        {
            List<string> list = new List<string>();
            try
            {
                XmlDocument xmlDoc = new XmlDocument();
                xmlDoc.Load(System.Web.HttpContext.Current.Server.MapPath("/DATA/" + ID + ".xml"));
                XmlNodeList nodeList = xmlDoc.SelectSingleNode("/S-" + ID + "/评论").ChildNodes;
                foreach (XmlNode xn in nodeList)
                {
                    XmlElement xe = (XmlElement)xn;
                    list.Add(xe.GetAttribute("name"));
                    list.Add(xe.GetAttribute("评论时间"));
                    list.Add(xe.InnerText);
                }

            }
            catch (Exception)
            {
                
                throw;
            }
            return list;
        }
        #endregion
        #region //读取步骤
        public List<string> getstep(string ID)
        {
            List<string> list = new List<string>();

            try
            {
                XmlDocument xmlDoc = new XmlDocument();
                xmlDoc.Load(System.Web.HttpContext.Current.Server.MapPath("/DATA/" + ID + ".xml"));
                XmlNodeList nodeList = xmlDoc.SelectSingleNode("/S-" + ID + "/步骤").ChildNodes;
                foreach (XmlNode xn in nodeList)
                {
                    XmlElement xe = (XmlElement)xn;
                    list.Add(xe.GetAttribute("time"));
                    list.Add(xe.InnerText);
                }
            }
            catch (Exception)
            {
                
                throw;
            }
            return list;
        }
        #endregion
        #region//读取图片
        public List<string> GetPhoto(string ID)
        {
            List<string> list = new List<string>();
            try
            {
                XmlDocument xmlDoc = new XmlDocument();
                xmlDoc.Load(System.Web.HttpContext.Current.Server.MapPath("/DATA/" + ID + "-P.xml"));
                XmlNodeList nodeList = xmlDoc.SelectSingleNode("/S-" + ID + "-P/image").ChildNodes;
                foreach (XmlNode xn in nodeList)
                {
                    XmlElement xe = (XmlElement)xn;
                    list.Add(xe.GetAttribute("lengh"));
                    list.Add(xe.GetAttribute("time"));
                    list.Add(xe.InnerText);
                }

            }
            catch (Exception)
            {

                throw;
            }
            return list;
        }
        
        #endregion
        #region//读取留言
        public List<string> GetGuestbook(string username)
        {
            List<string> list = new List<string>();
            try
            {
                XmlDocument xmlDoc = new XmlDocument();
                xmlDoc.Load(System.Web.HttpContext.Current.Server.MapPath("/DATA/" + "P-" + username + ".xml"));
                XmlNodeList nodeList = xmlDoc.SelectSingleNode("/" + "P-" + username + "/留言板").ChildNodes;
                foreach (XmlNode xn in nodeList)
                {
                    XmlElement xe = (XmlElement)xn;
                    list.Add(xe.GetAttribute("name"));
                    list.Add(xe.GetAttribute("留言时间"));
                    list.Add(xe.InnerText);
                }

            }
            catch (Exception)
            {

                throw;
            }
            return list;
        }
        #endregion
        #region//读取概要和工具
        public List<string> Getsbsdescrible(string ID)
        {
            List<string> list = new List<string>();
            try
            {
                int id = Convert.ToInt32(ID);
                string sql = "select 经验描述,使用工具 from 经验 where ID="+id;
                SqlCommand cmd = new SqlCommand(sql, sqlCon);
                SqlDataReader reader = cmd.ExecuteReader();

                while (reader.Read())
                {
                    list.Add(reader[0].ToString());
                    list.Add(reader[1].ToString());
                }

                reader.Close();
                cmd.Dispose();               

            }
            catch (Exception)
            {
                
                throw;
            }
            return list;
        }
        #endregion
        #region//热度+1
        public string Addhot(string ID)
        {
            try
            {
                string sql = "update 经验 set 热度=热度+1 where ID="+Convert.ToInt32(ID);
                SqlCommand cmd = new SqlCommand(sql,sqlCon);
                cmd.ExecuteNonQuery();
                cmd.Dispose();
            }
            catch (Exception)
            {
                
                throw;
            }
            return "成功";
            
        }
        #endregion
        #region//积分+1
        public string Addex(string username,string ID)
        {
            try
            {
                if (lsy.isaLogin(username)=="已登录")
                {
                    string sql = "update 用户信息表 set 积分=积分+1 where ID=" + Convert.ToInt32(ID);
                    SqlCommand cmd = new SqlCommand(sql, sqlCon);
                    cmd.ExecuteNonQuery();
                    cmd.Dispose();
                }
                
            }
            catch (Exception)
            {
                
                throw;
            }
            return "成功";                                
          
        }
        #endregion

        #region//留言板留言
        /// <summary>
        /// 留言板留言
        /// </summary>
        /// <param name="username"></param>
        /// <param name="usernamea"></param>
        /// <param name="info"></param>
        /// <returns></returns>
        public string  guestbook(string username, string usernamea, string info)
        {
            try
            {

                XmlDocument xmlDoc = new XmlDocument();
                // 文档加载  
                xmlDoc.Load(System.Web.HttpContext.Current.Server.MapPath("/DATA/" + "P-" + username + ".xml"));
                
                XmlNode xe1 = xmlDoc.SelectSingleNode("/" + "P-" + username+"/留言板" );
                XmlElement xesub1 = xmlDoc.CreateElement("P-"+usernamea );
                xesub1.SetAttribute("name", usernamea);
                
                xesub1.SetAttribute("留言时间",System.DateTime.Now.ToString() );
                xesub1.InnerText = info;
                xe1.PrependChild(xesub1);
                               
                xmlDoc.Save(System.Web.HttpContext.Current.Server.MapPath("/DATA/" + "P-" + username + ".xml"));
                return "true";

            }
            catch (Exception e)
            {
                return "false"+e;
            }
           // return true;
                 
        }
        #endregion
        #region//赞某条经验并向经验返回赞的数量
        /// <summary>
        /// 赞某条经验并向经验返回赞的数量
        /// </summary>
        /// <param name="ID">经验ID</param>
        /// <returns></returns>
        public bool zan(int ID)
        {
            try
            {
                string sql = "update 经验 set 赞=赞+1 where ID="+ID;
                SqlCommand cmd = new SqlCommand(sql,sqlCon);
                cmd.ExecuteNonQuery();
                cmd.Dispose();
                return true;

            }
            catch (Exception)
            {
                return false;                
            }

            
        }
        #endregion
        #region//举报用户经验并向用户发出警告
        /// <summary>
        /// 举报用户经验并向用户发出警告
        /// </summary>
        /// <param name="username">用户名</param>
        /// <param name="sbsname">sbs名称</param>
        /// <param name="reason">举报原因</param>
        /// <param name="usernamea">举报人</param>
        public bool report(string username,string sbsname,string reason,string usernamea,string ID)
        {
            try
            {
                string sql = "insert into 反馈 values('" + sbsname + "','" + usernamea + "','" + reason + "','" + username + "','"+ID+"')";
                SqlCommand cmd = new SqlCommand(sql,sqlCon);
                cmd.ExecuteNonQuery();
                cmd.Dispose();
                return true;
            }
            catch (Exception)
            {

                return false;
            }
        }
        #endregion
    }
}