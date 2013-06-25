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
    /// <summary>
    /// 一个操作数据库的类，所有对SQLServer的操作都写在这个类中，使用的时候实例化一个然后直接调用就可以
    /// </summary>
    public class Testwo : IDisposable
    {
        public static SqlConnection sqlCon;  //用于连接数据库

        //将下面的引号之间的内容换成上面记录下的属性中的连接字符串
        //dfasdlfasdfsad
        private String ConServerStr = @"Data Source=O-PC;Initial Catalog=hhhjjj;Persist Security Info=True;User ID=SSL;Password=SSL";

        //默认构造函数
        public Testwo()
        {
            if (sqlCon == null)
            {
                sqlCon = new SqlConnection();
                sqlCon.ConnectionString = ConServerStr;
                sqlCon.Open();
            }
        }

        //关闭/销毁函数，相当于Close()
        public void Dispose()
        {
            if (sqlCon != null)
            {
                sqlCon.Close();
                sqlCon = null;
            }
        }

        /// <summary>
        /// 获取所有货物的信息
        /// </summary>
        /// <returns>所有货物信息</returns>
        /*public List<string> selectAllCargoInfor()
        {
            List<string> list = new List<string>();

            try
            {
                string sql = "select * from C";
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

            }
            return list;
        }

        /// <summary>
        /// 增加一条货物信息
        /// </summary>
        /// <param name="Cname">货物名称</param>
        /// <param name="Cnum">货物数量</param>
        public bool insertCargoInfo(string Cname, int Cnum)
        {
            try
            {
                string sql = "insert into C (Cname,Cnum) values ('" + Cname + "'," + Cnum + ")";
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

        /// <summary>
        /// 删除一条货物信息
        /// </summary>
        /// <param name="Cno">货物编号</param>
        public bool deleteCargoInfo(string Cno)
        {
            try
            {
                string sql = "delete from C where Cno=" + Cno;
                SqlCommand cmd = new SqlCommand(sql, sqlCon);
                cmd.ExecuteNonQuery();
                cmd.Dispose();

                return true;
            }
            catch (Exception)
            {
                return false;
            }
        }*/
        /// <summary>
        /// 是否可以注册
        /// </summary>
        /// <param name="username">注册用户名</param>
        /// <returns></returns>
        public bool isregister(string username)
        {
            string name = "";
            try
            {
                Regex k8chkName = new Regex("^[[a-zA-Z0-9]{4,8}$");
                //密码7-12 字母大小写以及数字
                /*Regex k8chkpwd1 = new Regex(@"\d+");
                Regex k8chkpwd2 = new Regex(@"[a-zA-Z]+");
                Regex k8chkpwd3 = new Regex(@"^[a-zA-Z0-9]{7,16}$");*/
                if (k8chkName.IsMatch(username))
                {
                    string sql = "select username from 用户信息表 where username='" + username + "'";
                    SqlCommand cmd = new SqlCommand(sql, sqlCon);
                    SqlDataReader dr = cmd.ExecuteReader();
                    while (dr.Read())
                    {
                        name = dr[0].ToString().Trim();

                    }
                    dr.Close();
                    cmd.Dispose();
                    if (username == name && username != "")
                        return false;
                    else
                        return true;
                }
                else
                    return false;

            }
            catch(Exception)
            {
                return false;
            }
           
        }
        /// <summary>
        /// 注册功能实现
        /// </summary>
        /// <param name="username">用户名</param>
        /// <param name="userpwd">密码</param>
        /// <returns></returns>
        public bool register(string username ,string userpwd)
        {
            try
            {
                //Regex k8chkpwd1 = new Regex(@"\d+");
               // Regex k8chkpwd2 = new Regex(@"[a-zA-Z]+");
                Regex k8chkpwd3 = new Regex(@"^[a-zA-Z0-9]{7,16}$");
                if (isregister(username) && username != "" && userpwd != "" && k8chkpwd3.IsMatch(userpwd))
                {
                    string sql = "insert into 用户信息表 (username,userpwd,积分,是否登录) values('" + username + "','" + userpwd + "',"+5+","+0+")";
                    SqlCommand cmd = new SqlCommand(sql, sqlCon);
                    cmd.ExecuteNonQuery();
                    cmd.Dispose();
                    WriteXML(username);
                    return true;
                }
                else
                    return false;
            }
            catch(Exception )
            {
                return false;
            }
        }
        
        /// <summary>
        /// 登陆实现
        /// </summary>
        /// <param name="username">用户名</param>
        /// <param name="userpwd">密码</param>
        /// <returns></returns>
        public string login(string username, string userpwd)
        {
            string pwd = "";
            string abc = isaLogin(username);
            //string ghhj = "测试";
            try
            {
                if (abc == "未登录")
                {
                string sql = "select userpwd from 用户信息表 where username='" + username + "'";
                SqlCommand cmd = new SqlCommand(sql, sqlCon);
                SqlDataReader dr = cmd.ExecuteReader();
                while (dr.Read())
                {
                    pwd = dr[0].ToString().Trim();
                  
                }
                dr.Close();
                cmd.Dispose();
                if (userpwd == pwd && userpwd != "")
                {
                    string sql1 = "update 用户信息表 set 是否登录=1 where username='"+username+"'";
                    SqlCommand cmd1 = new SqlCommand(sql1, sqlCon);
                    cmd1.ExecuteNonQuery();
                    cmd1.Dispose();
                    return "true";
                }
                else
                    return "false" + isaLogin(username);
                }

               else
                    return "false" + isaLogin(username)+"0000000";

            }
            catch (Exception)
            {
                return "falseotrue";
            }
        }
        /// <summary>
        /// 是否已登录
        /// </summary>
        /// <param name="username">用户名</param>
        /// <returns></returns>
        public string  isaLogin(string username)
        {


            string sa = "";
            try
            {
                
                string sql = "select 是否登录 from 用户信息表 where username='" + username + "'";
                SqlCommand cmd = new SqlCommand(sql, sqlCon);
                SqlDataReader dr = cmd.ExecuteReader();
                while (dr.Read())
                {
                   
                    sa =dr[0].ToString().Trim();
                }
                dr.Close();
                cmd.Dispose();
                if (sa == "False")
                {
                    return "未登录";
                }
                else if (sa =="True")
                    return "已登录";
                else
                    return "出现未知异常";

            }
            catch (Exception)
            {
                return "出现异常";
            }
        }
        /// <summary>
        /// 创建用户的xml
        /// </summary>
        /// <param name="username">用户名</param>
        public void WriteXML(string username )
        {
            
            XDocument doc = new XDocument(
                new XDeclaration("1.0","utf-8","yes"),
                new XComment("创建时间@"+System.DateTime.Now),
                new XElement("P-"+username,
                    new XAttribute("更新时间",System.DateTime.Now))

                    
                );
            doc.Save(System.Web.HttpContext.Current.Server.MapPath("/DATA/" + "P-" + username + ".xml"));
        }
        /// <summary>
        /// 向XML中插入sbs名称
        /// </summary>
        /// <param name="username">用户名</param>
        /// <param name="sbsname">sbs名称</param>
        public string CreateNodea(string username,string sbsname,string keywords,string classes)
        {
            try
            {

                if (isaLogin(username) == "已登录")
                {
                    string sql1 = "insert into 经验 (经验名称,更新成员,关键字,分类) values('" + sbsname + "','" + username + "','" + keywords + "','" + classes + "')";
                    SqlCommand cmd1 = new SqlCommand(sql1, sqlCon);
                    cmd1.ExecuteNonQuery();
                    cmd1.Dispose();
                    XmlDocument xmlDoc = new XmlDocument();
                    // 文档加载  
                    xmlDoc.Load(System.Web.HttpContext.Current.Server.MapPath("/Data/" + "P-" + username + ".xml"));
                    // 查找<username>  
                    XmlNode root = xmlDoc.SelectSingleNode("P-" + username);
                    // 创建一个<sbsname>节点  
                    XmlElement xe1 = xmlDoc.CreateElement("S-" + sbsname);
                    // 设置该节点genre属性  
                    //xe1.SetAttribute("",);
                    xe1.SetAttribute("更新时间", System.DateTime.Now.ToString());
                    // 设置该节点ISBN属性  
                    //xe1.SetAttribute("ISBN", "1-1111-1");

                    //XmlElement xesub1 = xmlDoc.CreateElement("第"+time.ToString()+"步");
                    //xesub1.InnerText = "C#入门帮助"; // 设置文本节点  
                    //xe1.AppendChild(xesub1);  // 添加到<Node>节点中  

                    //XmlElement xesub2 = xmlDoc.CreateElement("author");
                    // xesub2.InnerText = "高手";// 设置文本节点  
                    //xe1.AppendChild(xesub2);  // 添加到<Node>节点中  

                    //XmlElement xesub3 = xmlDoc.CreateElement("price");
                    //xesub3.InnerText = "158.3";// 设置文本节点  
                    //xe1.AppendChild(xesub3); // 添加到<Node>节点中  

                    root.AppendChild(xe1); //添加到<username>节点中  
                    //cmd1.ExecuteNonQuery();
                    //cmd1.Dispose();
                    xmlDoc.Save(System.Web.HttpContext.Current.Server.MapPath("/Data/" + "P-" + username + ".xml"));


                    return "true";
                }
                else
                    return "false";

                 
            }
            catch (Exception e)
            {
                return "false"+e;
            }
        }
        /// <summary>
        /// 向具体sbs添加步骤（可以用循环实现）
        /// </summary>
        /// <param name="username">用户名</param>
        /// <param name="sbsname">sbs名称</param>
        /// <param name="time">次数</param>
        /// <param name="info">步骤信息</param>
        public string  CreateNodeb(string username, string sbsname, int time,string info)
        {
            try
            {
                if (isaLogin(username)=="已登录")
                {
                    XmlDocument xmlDoc = new XmlDocument();
                    // 文档加载  
                    xmlDoc.Load(System.Web.HttpContext.Current.Server.MapPath("/DATA/" + "P-" + username + ".xml"));
                    // 查找<sbsname>节点 
                    //XmlNode root = xmlDoc.SelectSingleNode(username);
                    // XmlNode xe1 = xmlDoc.SelectSingleNode("/" + "P-" + username + "/" + "S-" + sbsname);

                    // 创建一个<Node>节点  
                    XmlElement xe1 = (XmlElement)xmlDoc.SelectSingleNode(sbsname);
                    // 设置该节点genre属性  
                    xe1.SetAttribute("更新时间", System.DateTime.Now.ToString());
                    // 设置该节点ISBN属性  
                    //xe1.SetAttribute("ISBN", "1-1111-1");

                    XmlElement xesub1 = xmlDoc.CreateElement("第" + time.ToString() + "步");
                    xesub1.InnerText = info; // 设置文本节点  
                    xe1.AppendChild(xesub1);  // 添加到<sbsname>节点中  

                    /*XmlElement xesub2 = xmlDoc.CreateElement("author");
                    xesub2.InnerText = "高手";// 设置文本节点  
                    xe1.AppendChild(xesub2);  // 添加到<Node>节点中  

                    XmlElement xesub3 = xmlDoc.CreateElement("price");
                    xesub3.InnerText = "158.3";// 设置文本节点  
                    xe1.AppendChild(xesub3); // 添加到<Node>节点中  ****/

                    //root.AppendChild(xe1); //添加到<Employees>节点中  
                    xmlDoc.Save(System.Web.HttpContext.Current.Server.MapPath("/DATA/" + "P-" + username + ".xml"));
                    return "true";
                }
                else
                    return "false";
            }
            catch (Exception e)
            {
                return "false"+e;
            }
        }

        /// <summary>
        /// 读取某个节点
        /// </summary>
        /// <param name="username">用户名</param>
        /// <param name="sbsname">sbs名称</param>
        /// <param name="time">次数</param>
        /// <returns>某一步的内容</returns>
        public string query(string username,string sbsname,int time )
        {
            try
            {
                
                XmlDocument xmlDoc = new XmlDocument();
                xmlDoc.Load(System.Web.HttpContext.Current.Server.MapPath("/DATA/" + username + ".xml"));
                XmlNode xe1 = xmlDoc.SelectSingleNode("/" + username + "/" + sbsname + "/第" + time.ToString() + "步");
                return xe1.InnerText;
            }
            catch (Exception e)
            {
                return "发生异常"+e;
            }
        }
        /// <summary>
        /// 删除sbs名称
        /// </summary>
        /// <param name="username">用户名</param>
        /// <param name="sbsname">sbs名称</param>
        /// <returns></returns>
        public bool delectsbs(string username,string sbsname)
        {
            try
            {
                if (isaLogin(username) == "已登录")
                {
                    XmlDocument xmlDoc = new XmlDocument();
                    xmlDoc.Load(System.Web.HttpContext.Current.Server.MapPath("/DATA/" + "P-" + username + ".xml"));
                    XmlNode root = xmlDoc.SelectSingleNode("/P-"+username);
                    XmlNode xe1 = xmlDoc.SelectSingleNode("/" + "P-" + username + "/" + "S-" + sbsname);
                    root.RemoveChild(xe1);
                    //xe1.RemoveAll();
                    //return xe1.InnerText;
                    xmlDoc.Save(System.Web.HttpContext.Current.Server.MapPath("/DATA/" + "P-" + username + ".xml"));
                    return true;
                }
                else
                    return false;
            }
            catch (Exception)
            {
                //return "123";
                return false;
            }
        }
        /// <summary>
        /// 退出
        /// </summary>
        /// <param name="username">用户名</param>
        /// <returns></returns>
        public bool exitsbs(string username )
        {
            try
            {
                if (isaLogin(username) == "已登录")
                {
                    string sql = "update 用户信息表 set 是否登录=0 where username='" + username + "'";
                    SqlCommand cmd = new SqlCommand(sql, sqlCon);
                    cmd.ExecuteNonQuery();
                    cmd.Dispose();
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
        /// <summary>
        /// 按关键字查询
        /// </summary>
        /// <param name="keywords">关键字</param>
        /// <returns></returns>
        public ArrayList searchsbskw(string keywords)
        {
            ArrayList list = new ArrayList();

            try
            {
                string sql = "select 经验名称 from 经验 where 关键字 like'%"+keywords+"%'";
                SqlCommand cmd = new SqlCommand(sql, sqlCon);
                SqlDataReader reader = cmd.ExecuteReader();

                while (reader.Read())
                {
                    //将结果集信息添加到返回向量中
                    list.Add(reader[0].ToString());
                    

                }

                reader.Close();
                cmd.Dispose();

            }
            catch (Exception)
            {

            }
            return list;
        }
        public ArrayList searchsbsc(string classes)
        {
            ArrayList array= new ArrayList();
            try
            {
                string sql = "select 经验名称 from 经验 where 分类='" + classes + "'";
                SqlCommand cmd = new SqlCommand(sql, sqlCon);
                SqlDataReader reader = cmd.ExecuteReader();

                while (reader.Read())
                {
                    //将结果集信息添加到返回向量中
                    array.Add(reader[0].ToString());


                }

                reader.Close();
                cmd.Dispose();

            }
            catch (Exception)
            {

            }            
                return array;
            
        }
        /// <summary>
        /// 对经验做出整体的评价
        /// </summary>
        /// <param name="username">作者</param>
        /// <param name="sbsname">sbs名称</param>
        /// <param name="info">评价内容</param>
        /// <param name="usernamea">评价人</param>
        /// <returns></returns>
        public string commentsbsa(string username, string sbsname, string info, string usernamea)
        {
            try
            {

                XmlDocument xmlDoc = new XmlDocument();
                // 文档加载  
                xmlDoc.Load(System.Web.HttpContext.Current.Server.MapPath("/DATA/" + "P-" + username + ".xml"));
                // 查找<sbsname>节点 
                //XmlNode root = xmlDoc.SelectSingleNode("/P-"+username);
                XmlNode xe1 = xmlDoc.SelectSingleNode("/" + "P-" + username + "/" + "S-" + sbsname);

                // 创建一个<Node>节点  
                //XmlElement xe1 = (XmlElement)xmlDoc.SelectSingleNode(sbsname);
                // 设置该节点genre属性  
                //xe1.SetAttribute("更新时间", System.DateTime.Now.ToString());
                // 设置该节点ISBN属性  
                //xe1.SetAttribute("ISBN", "1-1111-1");

                XmlElement xesub1 = xmlDoc.CreateElement(usernamea+"评论");
                //xesub1.SetAttribute("评论时间", System.DateTime.Now.ToString());
                xesub1.InnerText ="651516"; // 设置文本节点 
               
                xe1.AppendChild(xesub1);  // 添加到<sbsname>节点中  

                /*XmlElement xesub2 = xmlDoc.CreateElement("author");
                xesub2.InnerText = "高手";// 设置文本节点  
                xe1.AppendChild(xesub2);  // 添加到<Node>节点中  

                XmlElement xesub3 = xmlDoc.CreateElement("price");
                xesub3.InnerText = "158.3";// 设置文本节点  
                xe1.AppendChild(xesub3); // 添加到<Node>节点中  ****/

                //root.AppendChild(xe1); //添加到<Employees>节点中  
                xmlDoc.Save(System.Web.HttpContext.Current.Server.MapPath("/DATA/" + "P-" + username + ".xml"));
                return "true";

            }
            catch (Exception e)
            {
                return "false"+e;
            }
        }

    }
}