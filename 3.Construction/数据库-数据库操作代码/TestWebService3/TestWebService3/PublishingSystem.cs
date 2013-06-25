using System;
using System.IO;
using System.Data;
using System.Data.SqlClient;
using System.Configuration;
using System.Linq;
using System.Web;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.HtmlControls;
using System.Web.UI.WebControls;
using System.Web.UI.WebControls.WebParts;
using System.Xml.Linq;
using System.Text.RegularExpressions;
using System.Collections;
using System.Collections.Generic;
using System.Xml;

namespace TestWebService3
{
    public class PublishingSystem :IDisposable
    {
        public static SqlConnection sqlCon;
        private String ConServerStr = @"Data Source=O-PC;Initial Catalog=hhhjjj;Persist Security Info=True;User ID=SSL;Password=SSL";
        public static LoginSystem lsy;
        #region//默认构造函数

        public PublishingSystem()
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
        #region//创建用于保存对于ID的图片
        /// <summary>
        /// 创建用于保存对于ID的图片
        /// </summary>
        /// <param name="ID">经验ID</param>
        public void writexml(int ID)
        {
            XDocument doc = new XDocument(
                new XDeclaration("1.0", "utf-8", "yes"),
                new XComment("创建时间@" + System.DateTime.Now.ToString()),
                new XElement("S-"+Convert.ToString(ID)+"-P",
                    new XAttribute("更新时间", System.DateTime.Now.ToString()),
                    new XElement("image")
                    //new XElement("步骤")
                    )


                );
            doc.Save(System.Web.HttpContext.Current.Server.MapPath("/DATA/" + ID + "-P" + ".xml"));
        }
        #endregion
        #region//创建ID的XML文件
        /// <summary>
        /// 创建ID的XML文件
        /// </summary>
        /// <param name="ID">经验ID</param>
        public void WriteXML(int ID)
        {

            XDocument doc = new XDocument(
                new XDeclaration("1.0", "utf-8", "yes"),
                new XComment("创建时间@" + System.DateTime.Now.ToString()),
                new XElement("S-"+Convert.ToString(ID),
                    new XAttribute("更新时间", System.DateTime.Now.ToString()),
                    new XElement("步骤"),
                    new XElement("评论")
                    )


                );
            doc.Save(System.Web.HttpContext.Current.Server.MapPath("/DATA/" +ID + ".xml"));
        }
        #endregion
        #region//向数据库中插入sbs名称并创建相应的ID，ID-P文件
        /// <summary>
        /// 向数据库中插入sbs名称并创建相应的ID，ID-P文件
        /// </summary>
        /// <param name="username">用户名</param>
        /// <param name="sbsname">sbs名称</param>
        /// <param name="sbsdescrible">sbs描述</param>
        /// <param name="sbstool">使用工具</param>

        public string CreateNodea(string username, string sbsname, string keywords, string classes,string sbsdescrible,string sbstool)
        {

            try
            {

                if (lsy.isaLogin(username) == "已登录")
                {
                    string sql1 = "insert into 经验 (经验名称,更新成员,关键字,分类,经验描述,使用工具,赞,时间,热度) values('" + sbsname + "','" + username + "','" + keywords + "','" + classes + "','" + sbsdescrible + "','" + sbstool + "'," + 1 +",'"+System.DateTime.Now.ToString()+"',"+1+")" + "SELECT @@IDENTITY AS returnName from 经验";
                    SqlCommand cmd1 = new SqlCommand(sql1, sqlCon);
                    int m=Convert.ToInt32( cmd1.ExecuteScalar());
                    cmd1.Dispose();
                    WriteXML(m);
                    writexml(m);
                    

                    return Convert.ToString(m);
                }
                else
                    return "false"+"未登录";


            }
            catch (Exception e)
            {
                return "false" +e;
            }
        }
        #endregion
        #region//向具体sbs添加步骤（可以用循环实现）
        /// <summary>
        /// 向具体sbs添加步骤（可以用循环实现）
        /// </summary>
        /// <param name="ID">经验ID</param>
        /// <param name="time">次数</param>
        /// <param name="info">步骤信息</param>
        /// <param name="username">用户名</param>
        public bool CreateNodeb( int time, string info,string ID,string username)
        {
            try
            {
                if (lsy.isaLogin(username) == "已登录")
                {
                    XmlDocument xmlDoc = new XmlDocument();
                    // 文档加载  
                    xmlDoc.Load(System.Web.HttpContext.Current.Server.MapPath("/DATA/" +ID+ ".xml"));
                    // 查找<sbsname>节点 
                    //XmlNode root = xmlDoc.SelectSingleNode(username);
                    // XmlNode xe1 = xmlDoc.SelectSingleNode("/" + "P-" + username + "/" + "S-" + sbsname);

                    // 创建一个<Node>节点  
                    XmlElement xe1 = (XmlElement)xmlDoc.SelectSingleNode("/S-"+Convert.ToString(ID)+"/步骤");
                    // 设置该节点genre属性  
                   // xe1.SetAttribute("更新时间", System.DateTime.Now.ToString());
                    // 设置该节点ISBN属性  
                    //xe1.SetAttribute("ISBN", "1-1111-1");
                    xe1.SetAttribute("步骤总数",time.ToString());
                    XmlElement xesub1 = xmlDoc.CreateElement("第" + time.ToString() + "步");
                    xesub1.SetAttribute("time",time.ToString());
                    xesub1.InnerText = info; // 设置文本节点  
                    xe1.AppendChild(xesub1);  // 添加到<sbsname>节点中  

                    /*XmlElement xesub2 = xmlDoc.CreateElement("author");
                    xesub2.InnerText = "高手";// 设置文本节点  
                    xe1.AppendChild(xesub2);  // 添加到<Node>节点中  

                    XmlElement xesub3 = xmlDoc.CreateElement("price");
                    xesub3.InnerText = "158.3";// 设置文本节点  
                    xe1.AppendChild(xesub3); // 添加到<Node>节点中  ****/

                    //root.AppendChild(xe1); //添加到<Employees>节点中  
                    xmlDoc.Save(System.Web.HttpContext.Current.Server.MapPath("/DATA/" + ID + ".xml"));
                    return true;
                }
                else
                    return false;
            }
            catch (Exception)
            {
                return false;
            }
        }
        #endregion
        #region//向ID的XML中插入更新时间
        /// <summary>
        /// 向ID的XML中插入步骤总数
        /// </summary>
        /// <param name="ID"></param>
        /// <param name="sum"></param>
        /// <returns></returns>
        public bool CreateNodec(int ID, int sum)
        {
            try
            {
                XmlDocument xmlDoc = new XmlDocument();
                    // 文档加载  
                xmlDoc.Load(System.Web.HttpContext.Current.Server.MapPath("/DATA/" +ID+ ".xml"));
                XmlElement xe1 = (XmlElement)xmlDoc.SelectSingleNode("/S-" + Convert.ToString(ID) + "/步骤");
                //xe1.SetAttribute("更新时间", System.DateTime.Now.ToString());
                xe1.SetAttribute("步骤总数",sum.ToString());
                xmlDoc.Save(System.Web.HttpContext.Current.Server.MapPath("/DATA/" + ID + ".xml"));
                return true;
            }
            catch (Exception)
            {

                return false;
            }
        }
        #endregion
        #region//读取某个节点
        /// <summary>
        /// 读取某个节点
        /// </summary>
        /// <param name="username">用户名</param>
        /// <param name="sbsname">sbs名称</param>
        /// <param name="time">次数</param>
        /// <returns>某一步的内容</returns>
        /*public string query(int ID, string sbsname, int time)
        {
            try
            {

                XmlDocument xmlDoc = new XmlDocument();
                xmlDoc.Load(System.Web.HttpContext.Current.Server.MapPath("/DATA/" +ID+ ".xml"));
                XmlNode xe1 = xmlDoc.SelectSingleNode("/" + username + "/" + sbsname + "/第" + time.ToString() + "步");
                return xe1.InnerText;
            }
            catch (Exception e)
            {
                return "发生异常" + e;
            }
        }*/
        #endregion
        #region//删除sbs名称
        /// <summary>
        /// 删除sbs名称
        /// </summary>
        /// <param name="username">用户名</param>
        /// <param name="sbsname">sbs名称</param>
        /// <returns></returns>
        public string Deletesbs(string username,string ID)
        {
            try
            {
                if (lsy.isaLogin(username) == "已登录")
                {
                    //string sql="update 经验 set 更新成员 = '"+" "+ "',经验名称 = '"+" "+"',赞="+0+"where ID="+Convert.ToInt32(ID);
                    string sql = "delete from 经验 " + " where ID=" + Convert.ToInt32(ID);
                    SqlCommand cmd = new SqlCommand(sql,sqlCon);
                    cmd.ExecuteNonQuery();
                    cmd.Dispose();

                    System.IO.File.Delete(System.Web.HttpContext.Current.Server.MapPath("/DATA/" + ID + ".xml"));
                    System.IO.File.Delete(System.Web.HttpContext.Current.Server.MapPath("/DATA/" + ID + "-P.xml"));
                    return "true";
                }
                else
                    return "false";
            }
            catch (Exception e)
            {
                //return "123";
                return "false"+e;
            }
        }
        #endregion
        #region//修改名称，概要，工具
        public bool Retsetsbsname(string id, string name, string descr, string tool)
        {
            try
            {
                string sql = "update 经验 set 经验名称 ='" + name + "',经验描述 ='" + descr + "',使用工具 ='" + tool + "' where ID=" + Convert.ToInt32(id);
                SqlCommand cmd = new SqlCommand(sql, sqlCon);
                cmd.ExecuteNonQuery();
                cmd.Dispose();
                return true;
            }
            catch (Exception)
            {
                throw;
            }

        }
        
        #endregion
        #region//修改步骤
        public string Resetsbs(string ID,string time,string info)
        {
            try
            {
                XmlDocument xmlDoc = new XmlDocument();
                xmlDoc.Load(System.Web.HttpContext.Current.Server.MapPath("/DATA/" + ID + ".xml"));
                XmlElement xe2;
                XmlNode xe = xmlDoc.SelectSingleNode("/S-" + ID + "/步骤");
                XmlElement xn = (XmlElement)xe;
                int m = Convert.ToInt32(xn.GetAttribute("步骤总数"));
                int n = Convert.ToInt32(time);
                if (m >= n)
                {
                    XmlNode xe1 = xmlDoc.SelectSingleNode("/S-" + ID + "/步骤/第" + time + "步");
                    xe1.InnerText = info;
                }
                else
                {
                    xe2 = xmlDoc.CreateElement("第"+time+"步");
                    xe2.SetAttribute("time",time);
                    xe2.InnerText = info;
                    xe.AppendChild(xe2);
                    xn.SetAttribute("步骤总数",time);
                }
                xmlDoc.Save(System.Web.HttpContext.Current.Server.MapPath("/DATA/" + ID + ".xml"));
            }
            catch (Exception)
            {
                throw;
            }
            return "成功";

        }
        #endregion
        #region//修改经验的图片
        public bool Resetpho(string id, string phoinfo, string time,string length)
        {
            try
            {
                XmlDocument xmlDoc = new XmlDocument();
                xmlDoc.Load(System.Web.HttpContext.Current.Server.MapPath("/DATA/" + id + "-P.xml"));
                XmlNodeList xno = xmlDoc.SelectSingleNode("/S-" + id + "-P/image").ChildNodes;
                XmlNode xnop = xmlDoc.SelectSingleNode("/S-" + id + "-P/image");
                XmlNode xe1;
                XmlElement xe2;
                bool flag = false;
                foreach (XmlNode xn in xno)
                {
                    XmlElement xe = (XmlElement)xn;
                    if (xe.GetAttribute("time") == time)
                    {
                        xe1 = xmlDoc.SelectSingleNode("/S-" + id + "-P/image/image" + time);
                        XmlElement xn1 = (XmlElement)xe1;
                        xn1.SetAttribute("lengh", length);
                        xn1.InnerText = phoinfo;
                        flag = true;
                    }
                }
                if (!flag)
                {
                    xe2 = xmlDoc.CreateElement("image"+time);
                    xe2.SetAttribute("lengh", length);
                    xe2.SetAttribute("time",time);
                    xe2.InnerText = phoinfo;
                    xnop.AppendChild(xe2);
                        ;

                }
                
                    

                
                
                
                xmlDoc.Save(System.Web.HttpContext.Current.Server.MapPath("/DATA/" + id + "-P.xml"));
                return true;
            }
            catch (Exception)
            {
                
                throw;
            }
        }
        #endregion
        #region//查看用户发布的经验
        /// <summary>
        /// 查看用户发布的经验
        /// </summary>
        /// <param name="username"></param>
        /// <returns></returns>
        public List<string> viewsbs(string username)
        {

            List<string> list = new List<string>();
            try
            {
                
                    string sql = "select 经验名称,ID,时间 from 经验 where 更新成员 ='" + username + "'";
                    SqlCommand cmd = new SqlCommand(sql, sqlCon);
                    SqlDataReader reader = cmd.ExecuteReader();

                    while (reader.Read())
                    {
                        //将结果集信息添加到返回向量中
                        list.Add(reader[0].ToString());
                        list.Add(reader[1].ToString());
                        list.Add(reader[2].ToString());
                        
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
        #region //查看经验名称
        public string viewsbsname(string ID)
        {
            string m = "";
            try
            {
                string sql = "select 经验名称 from 经验 where ID=" +Convert.ToInt32( ID);
                SqlCommand cmd = new SqlCommand(sql, sqlCon);
                SqlDataReader reader = cmd.ExecuteReader();
                while (reader.Read())
                {
                    m = reader[0].ToString();
                }   
                reader.Close();
                cmd.Dispose();

            }
            catch (Exception)
            {
                throw;

            }
            return m;
        }
        #endregion
        #region//搜索匹配的经验
        public List<string> viewsomesbs(string info)
        {
            List<string> list = new List<string>();
            try
            {
                string sql = "select 经验名称,更新成员,ID,时间 from 经验 where 经验名称 like '%" + info + "%'";
                SqlCommand cmd = new SqlCommand(sql, sqlCon);
                SqlDataReader reader = cmd.ExecuteReader();

                while (reader.Read())
                {
                    //将结果集信息添加到返回向量中
                    list.Add(reader[0].ToString());
                    list.Add(reader[1].ToString());
                    list.Add(reader[2].ToString());
                    list.Add(reader[3].ToString());

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
        #region //查看更新成员
        public string viewsbsusername(string ID)
        {
            string m = "";
            try
            {
                string sql = "select 更新成员 from 经验 where ID=" + Convert.ToInt32(ID);
                SqlCommand cmd = new SqlCommand(sql, sqlCon);
                SqlDataReader reader = cmd.ExecuteReader();
                while (reader.Read())
                {
                    m = reader[0].ToString();
                }
                reader.Close();
                cmd.Dispose();

            }
            catch (Exception)
            {
                throw;

            }
            return m;
        }
        #endregion
        #region //查看工具
        public string viewsbstool(string ID)
        {
            string m = "";
            try
            {
                string sql = "select 使用工具 from 经验 where ID=" + Convert.ToInt32(ID);
                SqlCommand cmd = new SqlCommand(sql, sqlCon);
                SqlDataReader reader = cmd.ExecuteReader();
                while (reader.Read())
                {
                    m = reader[0].ToString();
                }
                reader.Close();
                cmd.Dispose();

            }
            catch (Exception)
            {
                throw;

            }
            return m;
        }
        #endregion
        #region //查看发布时间
        public string viewsbtime(string ID)
        {
            string m = "";
            try
            {
                string sql = "select 时间 from 经验 where ID=" + Convert.ToInt32(ID);
                SqlCommand cmd = new SqlCommand(sql, sqlCon);
                SqlDataReader reader = cmd.ExecuteReader();
                while (reader.Read())
                {
                    m = reader[0].ToString();
                }
                reader.Close();
                cmd.Dispose();

            }
            catch (Exception)
            {
                throw;

            }
            return m;
        }
        #endregion
        #region //查看经验描述
        public string viewsbsdescrible(string ID)
        {
            string m = "";
            try
            {
                string sql = "select 经验描述 from 经验 where ID=" + Convert.ToInt32(ID);
                SqlCommand cmd = new SqlCommand(sql, sqlCon);
                SqlDataReader reader = cmd.ExecuteReader();
                while (reader.Read())
                {
                    m = reader[0].ToString();
                }
                reader.Close();
                cmd.Dispose();

            }
            catch (Exception)
            {
                throw;

            }
            return m;
        }
        #endregion
        #region //查看赞 
        public string viewsbszan(string ID)
        {
            string m = "";
            try
            {
                string sql = "select 赞 from 经验 where ID=" + Convert.ToInt32(ID);
                SqlCommand cmd = new SqlCommand(sql, sqlCon);
                SqlDataReader reader = cmd.ExecuteReader();
                while (reader.Read())
                {
                    m = reader[0].ToString();
                }
                reader.Close();
                cmd.Dispose();

            }
            catch (Exception)
            {
                throw;

            }
            return m;
        }
        #endregion
        #region//查看具体的某个经验
        /// <summary>
        /// 查看具体的某个经验
        /// </summary>
        /// <param name="ID">经验ID</param>
        /// <returns></returns>
        public List<string> viewsbsa(int ID)
        {
            List<string> list=new List<string>();
            try
            {
                string sql = "select 经验描述,使用工具 from 经验 where ID="+ID;
                SqlCommand cmd = new SqlCommand(sql,sqlCon);
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

        #region//查看悬赏
        public List<string> viewexsbs(string username)
        {

            List<string> list = new List<string>();
            try
            {
              
                string sql = "select *from 悬赏";
                SqlCommand cmd = new SqlCommand(sql, sqlCon);
                SqlDataReader reader = cmd.ExecuteReader();

                while (reader.Read())
                {
                    //将结果集信息添加到返回向量中
                    list.Add(reader[0].ToString());
                    list.Add(reader[1].ToString());
                    list.Add(reader[2].ToString());
                    
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
        #region//发布悬赏经验
        /// <summary>
        /// 发布悬赏经验
        /// </summary>
        /// <param name="username">发布人</param>
        /// <param name="sbsname">悬赏经验名称</param>
        /// <param name="mark">悬赏分数</param>
        
        public string wanted(string username,string sbsname,string mark)
        {
            try
            {         
                if(obtainmark(username)>=Convert.ToInt32(mark))
                {
                    string sql = "insert into 悬赏 values('" + sbsname + "'," + Convert.ToInt32(mark) + ",'" + username + "')";
                SqlCommand cmd = new SqlCommand(sql, sqlCon);
                cmd.ExecuteNonQuery();
                cmd.Dispose();
                deduction(username,sbsname);
                return "true";
                }
                else
                   return "悬赏积分大于已有积分";
                           
            }
            catch (Exception)
            {
                throw;
            }
        
        }
        #endregion
        #region//是否可以悬赏
        /// <summary>
        /// 是否可以悬赏
        /// </summary>
        /// <param name="sbsname">悬赏的经验名</param>
        /// <returns></returns>
        public bool iswanted(string sbsname)
        {
            try
            {
                if (sbsname != "")
                {
                    string sql = "select 悬赏问题 from 悬赏 where 悬赏问题='" + sbsname + "'";
                    SqlCommand cmd = new SqlCommand(sql, sqlCon);
                    SqlDataReader reader = cmd.ExecuteReader();
                    string sname = "";
                    while (reader.Read())
                    {
                        sname = reader[0].ToString().Trim();
                    }

                    reader.Close();
                    cmd.Dispose();
                    if (sname == "")
                        return true;
                    else
                        return false;
                }
                else
                    return false;

            }
            catch (Exception)
            {
                return false;
            }
            
            
        }
        #endregion
        #region//撤销悬赏
        /// <summary>
        /// 撤销悬赏
        /// </summary>
        /// <param name="username"></param>
        /// <param name="sbsname"></param>
        /// <returns></returns>
        public bool cancelwanted(string username,string sbsname)
        {
            try
            {
                if (lsy.isaLogin(username) == "已登录")
                {
                    string sql = "delete from 悬赏 where 悬赏问题='" + sbsname + "'";
                    SqlCommand cmd = new SqlCommand(sql, sqlCon);
                    cmd.ExecuteNonQuery();
                    cmd.Dispose();
                    return true;
                }
                else
                {
                    return false;
                }
            }
            catch(Exception)
            {
                return false;
            }
        }
        #endregion
        #region//
        public List<string> Getresponsewanted(string username)
        {
            List<string> list = new List<string>();
            try
            {
                string sql = "select 悬赏经验,ID from 接受悬赏表 where 悬赏人='"+username+"'";
                SqlCommand cmd = new SqlCommand(sql,sqlCon);
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
        #region//响应悬赏经验
        /// <summary>
        /// 响应悬赏经验
        /// </summary>
        /// <param name="username">发起人</param>
        /// <param name="sbsname">悬赏经验名称</param>
        /// <param name="usernamea">响应人</param>
        public bool responsewanted( string username, string sbsname, string usernamea,string ID)
        {
            try
            {               
                string sql = "insert into 接受悬赏表 values('"+usernamea+"','"+sbsname+"','"+ID+"')";
                SqlCommand cmd = new SqlCommand(sql, sqlCon);
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
        #region//是否已响应该悬赏（是则不能响应即按钮失效）
        /// <summary>
        /// 是否已响应该悬赏（是则不能响应即按钮失效）
        /// </summary>
        /// <param name="sbsname"></param>
        /// <param name="usernamea"></param>
        /// <returns></returns>
        public bool isresponsewanted(string sbsname, string usernamea)
        {
            try
            {
                string sql = "select 悬赏经验 from 接受悬赏表 where 接受悬赏人='"+usernamea+"',and 悬赏经验='"+sbsname+"'";
                SqlCommand cmd = new SqlCommand(sql, sqlCon);
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
        #region//处理悬赏
        /// <summary>
        /// 处理悬赏
        /// </summary>
        /// <param name="username">发起人</param>
        /// <param name="usernamea">响应人</param>

        public bool dealresponse(string username,string usernamea,string sbsname)
        {
            try
            {
                if (award(usernamea, sbsname) &&cancelwanted(username,sbsname))
                
                    return true;

                else
                    return false;
            }
            catch (Exception)
            {

                return false;
            }
        }
        #endregion
        #region//获取用户积分
        /// <summary>
        /// 获取用户积分
        /// </summary>
        /// <param name="username">用户名</param>
        /// <returns></returns>
        public int obtainmark(string username)
        {
            int tmp = 0;
            try
            {

                string sql = "select 积分 from 用户信息表 where username='" + username + "'";
                SqlCommand cmd = new SqlCommand(sql, sqlCon);
                SqlDataReader reader = cmd.ExecuteReader();
                while (reader.Read())
                {
                    tmp = Convert.ToInt32(reader[0].ToString());
                }
                reader.Close();
                cmd.Dispose();
                return tmp;
            }
            catch (Exception)
            {

                throw;
            }
        }
        #endregion
        #region
        public string Getmark(string username)
        {
            return obtainmark(username).ToString();
        }
        #endregion
        #region//获取悬赏积分
        /// <summary>
        /// 获取悬赏积分
        /// </summary>
        /// <param name="sbsname">sbs名称</param>
        /// <returns></returns>
        public int obtainmark1(string sbsname)
        {
            int tmp = 0;
            try
            {
                
                string sql = "select 悬赏积分 from 悬赏 where 悬赏问题='"+sbsname+"'";
                SqlCommand cmd = new SqlCommand(sql, sqlCon);
                SqlDataReader reader = cmd.ExecuteReader();
                while (reader.Read())
                {
                    tmp = Convert.ToInt32(reader[0].ToString());
                }
                reader.Close();
                cmd.Dispose();
                return tmp;
            }
            catch (Exception)
            {

                return tmp;
            }
        }
        #endregion
        #region//为响应悬赏者加分
        /// <summary>
        /// 为响应悬赏者加分
        /// </summary>
        /// <param name="usernamea">响应者</param>
        /// <returns></returns>
        public bool award(string usernamea,string sbsname)
        {
            
            try
            {
                
                int temp=obtainmark1(sbsname);
                
                string sql = "update 用户信息表 set 积分=积分+" + temp + "where username='" + usernamea + "'";
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
        #region//为悬赏扣除相应分数
        /// <summary>
        /// 为悬赏扣除相应分数
        /// </summary>
        /// <param name="username"></param>
        /// <returns></returns>
        public string deduction(string username,string sbsname)
        {

            try
            {

                int temp = obtainmark1(sbsname);
                int tmp=obtainmark(username);
                if (tmp >= temp)
                {

                    string sql = "update 用户信息表 set 积分=积分-" + temp + "where username='" + username + "'";
                    SqlCommand cmd = new SqlCommand(sql, sqlCon);
                    cmd.ExecuteNonQuery();
                    cmd.Dispose();
                    return "true";
                }
                else
                {
                    return "悬赏积分大于已有积分";
                }
            }
            catch (Exception)
            {

                throw;
            }
        }
        #endregion        

        #region//将xml写成二进制字节流
        /// <summary>
        /// 将xml写成二进制字节流
        /// </summary>
        /// <param name="ID"></param>
        /// <returns></returns>
        public List<string> readxml()
        {
            List<string> list = new List<string>();
            try
            {
                
                FileStream fileStream = new FileStream(System.Web.HttpContext.Current.Server.MapPath("/DATA/" + "3.xml"/* + ".jpg"*/), FileMode.Open);

                BinaryReader binaryReader = new BinaryReader(fileStream);

                byte[] imageBuffer = new byte[binaryReader.BaseStream.Length];
                binaryReader.Read(imageBuffer, 0, Convert.ToInt32(binaryReader.BaseStream.Length));
                string textstringlen = Convert.ToString(binaryReader.BaseStream.Length);
                string textString = System.Convert.ToBase64String(imageBuffer);
                
                fileStream.Close();
                binaryReader.Close();
                list.Add(textstringlen);
                list.Add(textString);
                
                
            }
            catch (Exception)
            {

                throw;
            }
            return list;
        }

        #endregion
        #region //传送步骤评论的xml
        public List<string> GetIDXml(string ID)
        {
            List<string> list = new List<string>();
            try
            {

                FileStream fileStream = new FileStream(System.Web.HttpContext.Current.Server.MapPath("/DATA/" +ID+ ".xml"/* + ".jpg"*/), FileMode.Open);

                BinaryReader binaryReader = new BinaryReader(fileStream);

                byte[] imageBuffer = new byte[binaryReader.BaseStream.Length];
                binaryReader.Read(imageBuffer, 0, Convert.ToInt32(binaryReader.BaseStream.Length));
                string textstringlen = Convert.ToString(binaryReader.BaseStream.Length);
                string textString = System.Convert.ToBase64String(imageBuffer);

                fileStream.Close();
                binaryReader.Close();
                list.Add(textstringlen);
                list.Add(textString);


            }
            catch (Exception)
            {

                throw;
            }
            return list;
        }
        #endregion
        #region//传送照片的xml
        public List<string> GetIDPXml(string ID)
        {
            List<string> list = new List<string>();
            try
            {

                FileStream fileStream = new FileStream(System.Web.HttpContext.Current.Server.MapPath("/DATA/" +ID+ "-P.xml"/* + ".jpg"*/), FileMode.Open);

                BinaryReader binaryReader = new BinaryReader(fileStream);

                byte[] imageBuffer = new byte[binaryReader.BaseStream.Length];
                binaryReader.Read(imageBuffer, 0, Convert.ToInt32(binaryReader.BaseStream.Length));
                string textstringlen = Convert.ToString(binaryReader.BaseStream.Length);
                string textString = System.Convert.ToBase64String(imageBuffer);

                fileStream.Close();
                binaryReader.Close();
                list.Add(textstringlen);
                list.Add(textString);


            }
            catch (Exception)
            {

                throw;
            }
            return list;
        }
        #endregion
        #region//添加图片
        /// <summary>
        /// 添加图片
        /// </summary>
        /// <param name="ID"></param>
        /// <param name="time"></param>
        /// <param name="textString"></param>
        /// <param name="textstringlen"></param>
        /// <returns></returns>
        public bool createNodee(string ID, string time,string textString,string textstringlen )
        {
            try
            {
                
                XmlDocument xmlDoc = new XmlDocument();
                // 文档加载  
                xmlDoc.Load(System.Web.HttpContext.Current.Server.MapPath("/DATA/" + ID + "-P" + ".xml"));
               
                XmlNode root = xmlDoc.SelectSingleNode("/S-" + ID + "-P/image");
                XmlElement xe = (XmlElement)root;
                xe.SetAttribute("步骤总数",time);
                XmlElement xesub2 = xmlDoc.CreateElement("image" + time);   
             
                xesub2.SetAttribute("lengh", textstringlen);
                xesub2.SetAttribute("time", time);   
                xesub2.InnerText = textString;// 设置文本节点  
                root.AppendChild(xesub2);          
 
                xmlDoc.Save(System.Web.HttpContext.Current.Server.MapPath("/DATA/" + ID + "-P" + ".xml"));
                return true;
            }
            catch (Exception)
            {
                
                throw;
            }
        }
        #endregion
        
        #region
        public bool createmoded(string ID,string time)
        {
            try
            {
                 //readxml(ID);
                List<string> list = readxml();
                string textString = list[1].ToString();
                string textstringlen = list[0].ToString();
        
                XmlDocument xmlDoc = new XmlDocument();                
                xmlDoc.Load(System.Web.HttpContext.Current.Server.MapPath("/DATA/" +ID +"-P"+ ".xml"));
                
                XmlNode root = xmlDoc.SelectSingleNode("/S-"+ID+"-P/image");

                XmlElement xesub2 = xmlDoc.CreateElement("image"+time);                
                xesub2.SetAttribute("lengh",textstringlen);
                xesub2.SetAttribute("time", time);
                xesub2.InnerText = textString;// 设置文本节点  
                root.AppendChild(xesub2); 

              xmlDoc.Save(System.Web.HttpContext.Current.Server.MapPath("/DATA/" +ID+ "-P"  + ".xml"));
                return true;
            }
            catch (Exception)
            {
                
                throw;
            }
            
        }
        #endregion
        #region
       public bool getp(string ID,string time,string url)
       {
           try
           {
               XmlDocument xmlDoc = new XmlDocument();
               // 文档加载  
               xmlDoc.Load(System.Web.HttpContext.Current.Server.MapPath("/DATA/" + ID+ "-P" + ".xml"));
               XmlTextReader xmltxtrd = new XmlTextReader(System.Web.HttpContext.Current.Server.MapPath("/DATA/" + ID+ "-P" + ".xml"));
               XmlNode root = xmlDoc.SelectSingleNode("/S-" +ID  + "-P/image/image"+time);
               //XmlElement xesub2 = xmlDoc.CreateElement();
               string textstringlen = ((XmlElement)root).GetAttribute("lengh");
               string textString = ((XmlElement)root).InnerText;
 
               FileStream fileStream = new FileStream(System.Web.HttpContext.Current.Server.MapPath("/DATA/" +url+".jpeg"), FileMode.Create);

               BinaryWriter binaryWriter = new BinaryWriter(fileStream);

               byte[] base64buffer = new byte[Convert.ToInt32(textstringlen)];
               while (xmltxtrd.Read())
               {
                   if (xmltxtrd.NodeType == XmlNodeType.Element && xmltxtrd.Name == "image"+time)
                   {


                       int readbyte = xmltxtrd.ReadBase64(base64buffer, 0, Convert.ToInt32(textstringlen));
                       binaryWriter.Write(base64buffer, 0, readbyte);
                       
                       
                   }
               }
               
               //binaryWriter.Write(base64buffer, 0, Convert.ToInt32(textstringlen));  
               binaryWriter.Flush();

               binaryWriter.Close();

               fileStream.Close();  
  

               return true;
           }
           catch (Exception )
           {
               
               throw;
           }
       }
        #endregion
        #region
       public bool getxml()
       {
           try
           {
               XmlDocument xmlDoc = new XmlDocument();
               // 文档加载  
               xmlDoc.Load(System.Web.HttpContext.Current.Server.MapPath("/DATA/" + 7 + "-P" + ".xml"));
               XmlTextReader xmltxtrd = new XmlTextReader(System.Web.HttpContext.Current.Server.MapPath("/DATA/" + 7 + "-P" + ".xml"));
               XmlNode root = xmlDoc.SelectSingleNode("/S-" + "7" + "-P/image/sxml52");
               //XmlElement xesub2 = xmlDoc.CreateElement();
               string textstringlen = ((XmlElement)root).GetAttribute("lengh");
               string textString = ((XmlElement)root).InnerText;
               FileStream fileStream = new FileStream(System.Web.HttpContext.Current.Server.MapPath("/DATA/" + "asf556dsfsdffsaf.xml"), FileMode.Create);

               BinaryWriter binaryWriter = new BinaryWriter(fileStream);

               byte[] base64buffer = new byte[Convert.ToInt32(textstringlen)];
               while (xmltxtrd.Read())
               {
                   if (xmltxtrd.NodeType == XmlNodeType.Element && xmltxtrd.Name == "sxml52")
                   {


                       int readbyte = xmltxtrd.ReadBase64(base64buffer, 0, Convert.ToInt32(textstringlen));
                       binaryWriter.Write(base64buffer, 0, readbyte);


                   }
               }

               //binaryWriter.Write(base64buffer, 0, Convert.ToInt32(textstringlen));  
               binaryWriter.Flush();

               binaryWriter.Close();

               fileStream.Close();

               return true;
           }
           catch (Exception)
           {

               throw;
           }
       }
        #endregion
    }
}