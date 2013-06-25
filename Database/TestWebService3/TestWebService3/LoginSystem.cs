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
    public class LoginSystem : IDisposable
    {
        public static SqlConnection sqlCon;
        private String ConServerStr = @"Data Source=O-PC;Initial Catalog=hhhjjj;Persist Security Info=True;User ID=SSL;Password=SSL";

        #region//默认构造函数

        public LoginSystem()
        {
            if (sqlCon == null)
            {
                sqlCon = new SqlConnection();
                sqlCon.ConnectionString = ConServerStr;
                sqlCon.Open();
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
        #region//是否可以注册
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
                Regex k8chkName = new Regex("^[[a-zA-Z0-9]{4,12}$");
                
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
            catch (Exception)
            {
                return false;
            }

        }
        #endregion
        #region//注册功能实现
        /// <summary>
        /// 注册功能实现
        /// </summary>
        /// <param name="username">用户名</param>
        /// <param name="userpwd">密码</param>
        /// <returns></returns>
        public string register(string username, string userpwd,string email)
        {
            try
            {
               // Regex k8chkpwd1 = new Regex(@"\d+");
               // Regex k8chkpwd2 = new Regex(@"[a-zA-Z]+");
                Regex k8chkpwd3 = new Regex(@"^[a-zA-Z0-9]{4,16}$");
                Regex emailkwd = new Regex(@"^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$");
                if (isregister(username) && k8chkpwd3.IsMatch(userpwd) && emailkwd.IsMatch(email))
                {
                    string sql = "insert into 用户信息表 (username,userpwd,积分,是否登录,邮箱) values('" + username + "','" + userpwd + "'," + 5 + "," + 0 + ",'"+email+"')";
                    SqlCommand cmd = new SqlCommand(sql, sqlCon);
                    cmd.ExecuteNonQuery();
                    cmd.Dispose();
                    WriteXML(username);
                    createguestbook(username);
                    return "true";
                }
                else
                    return "false";
            }
            catch (Exception e)
            {
                return "00"+e;
            }
        }
        #endregion
        #region//登陆实现
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
                        string sql1 = "update 用户信息表 set 是否登录=1 where username='" + username + "'";
                        SqlCommand cmd1 = new SqlCommand(sql1, sqlCon);
                        cmd1.ExecuteNonQuery();
                        cmd1.Dispose();
                        return "true";
                    }
                    else
                        return "密码错误";
                }

                else
                    return "已经登录过了";

            }
            catch (Exception)
            {
                return "有异常";
            }
        }
        #endregion
        #region//是否已登录
        /// <summary>
        /// 是否已登录
        /// </summary>
        /// <param name="username">用户名</param>
        /// <returns></returns>
        public string isaLogin(string username)
        {


            string sa = "";
            try
            {

                string sql = "select 是否登录 from 用户信息表 where username='" + username + "'";
                SqlCommand cmd = new SqlCommand(sql, sqlCon);
                SqlDataReader dr = cmd.ExecuteReader();
                while (dr.Read())
                {

                    sa = dr[0].ToString().Trim();
                }
                dr.Close();
                cmd.Dispose();
                if (sa == "False")
                {
                    return "未登录";
                }
                else if (sa == "True")
                    return "已登录";
                else
                    return "出现未知异常";

            }
            catch (Exception)
            {
                return "出现异常";
            }
        }
        #endregion
        #region//创建用户个人的XML的文件
        /// <summary>
        /// 创建用户个人的XML的文件
        /// </summary>
        /// <param name="username"></param>
        public void WriteXML(string username)
        {

            XDocument doc = new XDocument(
                new XDeclaration("1.0", "utf-8", "yes"),
                new XComment("创建时间@" + System.DateTime.Now.ToString()),
                new XElement("P-" + username,
                    new XAttribute("更新时间", System.DateTime.Now.ToString()),
                    new XElement("留言板")
                    )


                );
            doc.Save(System.Web.HttpContext.Current.Server.MapPath("/DATA/" + "P-" + username + ".xml"));
        }
        #endregion
        #region//退出
        /// <summary>
        /// 退出
        /// </summary>
        /// <param name="username">用户名</param>
        /// <returns></returns>
        public bool exitsbs(string username)
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
        #endregion

        #region//查看个人信息
        /// <summary>
        /// 查看个人信息
        /// </summary>
        /// <param name="username">用户名</param>
        /// <param name="usernamea">查看人</param>
        /// <returns></returns>
        public List<string> viewinformation(string username,string usernamea)
        {


            List<string> list = new List<string>();
            try
            {

                string sql = "select username,昵称,邮箱,手机,爱好,签名 from 用户信息表 where username='" + username + "'";
                    SqlCommand cmd = new SqlCommand(sql, sqlCon);
                    SqlDataReader reader = cmd.ExecuteReader();

                    while (reader.Read())
                    {
                        //将结果集信息添加到返回向量中
                        list.Add(reader[0].ToString());
                        list.Add(reader[1].ToString());
                        list.Add(reader[2].ToString());
                        list.Add(reader[3].ToString());
                        list.Add(reader[4].ToString());
                        list.Add(reader[5].ToString());
                        
                    }

                    reader.Close();
                    cmd.Dispose();
               

            }
            catch (Exception)
            {

            }
            return list;

        }

        #endregion
        #region//查看昵称
        public string Getnickname(string username)
        {
            string m = "";
            try
            {

                string sql = "select 昵称 from 用户信息表 where username='" + username + "'";
                SqlCommand cmd = new SqlCommand(sql, sqlCon);
                SqlDataReader reader = cmd.ExecuteReader();

                while (reader.Read())
                {
                    //将结果集信息添加到返回向量中
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
        #region//查看邮箱
        public string Getemil(string username)
        {
            string m = "";
            try
            {

                string sql = "select 邮箱 from 用户信息表 where username='" + username + "'";
                SqlCommand cmd = new SqlCommand(sql, sqlCon);
                SqlDataReader reader = cmd.ExecuteReader();

                while (reader.Read())
                {
                    //将结果集信息添加到返回向量中
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
        #region//查看爱好
        public string Gethobby(string username)
        {
            string m = "";
            try
            {

                string sql = "select 昵称 from 用户信息表 where username='" + username + "'";
                SqlCommand cmd = new SqlCommand(sql, sqlCon);
                SqlDataReader reader = cmd.ExecuteReader();

                while (reader.Read())
                {
                    //将结果集信息添加到返回向量中
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
        #region//查看签名
        public string Getqianming(string username)
        {
            string m = "";
            try
            {

                string sql = "select 签名 from 用户信息表 where username='" + username + "'";
                SqlCommand cmd = new SqlCommand(sql, sqlCon);
                SqlDataReader reader = cmd.ExecuteReader();

                while (reader.Read())
                {
                    //将结果集信息添加到返回向量中
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
        #region//查看手机
        public string Gettele(string username)
        {
            string m = "";
            try
            {

                string sql = "select 手机 from 用户信息表 where username='" + username + "'";
                SqlCommand cmd = new SqlCommand(sql, sqlCon);
                SqlDataReader reader = cmd.ExecuteReader();

                while (reader.Read())
                {
                    //将结果集信息添加到返回向量中
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

        #region//修改用户个人资料
        /// <summary>
        /// 修改用户个人资料
        /// </summary>
        /// <param name="username">用户名</param>
        /// <returns>bool</returns>
        public bool modifieddata(string username)
        {

            return true;
        }
        #endregion
        #region//获取设置问题
        /// <summary>
        /// 获取设置问题
        /// </summary>
        /// <param name="username">用户名</param>
        /// <returns></returns>
        public string obtainquestion(string username)
        {
            string sa = "";
            try
            {
                string sql = "select 设定问题 from 用户信息表 where username=' " + username + "'";
                SqlCommand cmd = new SqlCommand(sql, sqlCon);
                SqlDataReader dr = cmd.ExecuteReader();
                while (dr.Read())
                {
                    sa = dr[0].ToString().Trim();
                }
                dr.Close();
                cmd.Dispose();
                return sa;
            }
            catch (Exception)
            {
                return sa;
            }
        }
        #endregion
        #region//检查问题的正确与否
        /// <summary>
        /// 检查问题的正确与否
        /// </summary>
        /// <param name="answer">答案</param>
        /// <param name="username">用户名</param>
        /// <returns></returns>
        public bool verifyquestion(string answer,string username)
        {
            string sa = "";
            try
            {
                string sql = "select 答案 from 用户信息表 where username=' " + username + "'";
                SqlCommand cmd = new SqlCommand(sql, sqlCon);
                SqlDataReader dr = cmd.ExecuteReader();
                while (dr.Read())
                {
                    sa = dr[0].ToString().Trim();
                }
                dr.Close();
                cmd.Dispose();
                if (sa == answer && answer.Trim() != "")
                    return ResetPassword(username);
                else
                    return false;
            }
            catch (Exception)
            {
                return false;
            }

            
        }
        #endregion
        #region
        #endregion
        #region//重置密码
        /// <summary>
        /// 重置密码
        /// </summary>
        /// <param name="username">用户名</param>
        /// <returns></returns>
        public bool ResetPassword(string username)
        {
            try
            {
                string sql = "update 用户信息表 set userpwd='123456' where username='" + username + "'";
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
        #region//修改昵称
        /// <summary>
        /// 修改昵称
        /// </summary>
        /// <param name="username">用户名</param>
        /// <param name="nickname">昵称</param>
        /// <returns></returns>
        public bool Resetnickname(string username, string nickname)
        {
            try
            {
                string sql = "update 用户信息表 set 昵称='"+nickname+"'where username='"+username+"'";
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
        #region//修改签名
        /// <summary>
        /// 修改签名
        /// </summary>
        /// <param name="username">用户名</param>
        /// <param name="signaure">签名的内容</param>
        /// <returns></returns>
        public bool resetsignaure(string username, string signaure)
        {
            try
            {
                string sql = "update 用户信息表 set 签名='" + signaure + "'where username='" + username + "'";
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
        #region//修改邮箱
        /// <summary>
        /// 修改邮箱
        /// </summary>
        /// <param name="username">用户名</param>
        /// <param name="email">邮箱</param>
        /// <returns></returns>
        public bool resetemail(string username, string email)
        {
            try
            {
                string sql = "update 用户信息表 set 邮箱='" + email + "'where username='" + username + "'";
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
        #region//修改所在地
        /// <summary>
        /// 修改所在地
        /// </summary>
        /// <param name="username">用户名</param>
        /// <param name="location">所在地</param>
        /// <returns></returns>
        public bool resetlocation(string username, string location)
        {
            try
            {
                string sql = "update 用户信息表 set 所在地='" + location + "'where username='" + username + "'";
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
        #region//修改手机号码
        /// <summary>
        /// 修改手机号码
        /// </summary>
        /// <param name="username"></param>
        /// <param name="tele"></param>
        /// <returns></returns>
        public bool resettele(string username, string tele)
        {
            try
            {
                string sql = "update 用户信息表 set 手机 ='" + tele + "'where username='" + username + "'";
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
        #region//更新用户爱好
        /// <summary>
        ///更新用户爱好
        /// </summary>
        /// <param name="username"></param>
        /// <param name="hobby"></param>
        /// <returns></returns>
        public bool resethobby(string username,string hobby)
        {
            try
            {
                string sql = "update 用户信息表 set 爱好 ='" + hobby + "'where username='" + username + "'";
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
        #region//更新头像
        /// <summary>
        /// 更新头像
        /// </summary>
        /// <param name="byteArray">二进制文件</param>
        /// <param name="username">用户名</param>
        /// <returns></returns>
        public bool resetimage(byte[] byteArray,string username)
        {
            try
            {
                string sql = "update 用户信息表 set 头像= @photo where username='"+username+"'";
                SqlCommand cmd = new SqlCommand(sql, sqlCon);
                SqlParameter parmeter = new SqlParameter("@photo",SqlDbType.Image);
                parmeter.Value = byteArray;
                cmd.Parameters.Add(parmeter);
                int result = cmd.ExecuteNonQuery();
                if (result > 0)
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

        #region//创建留言板
        /// <summary>
        /// 创建留言板
        /// </summary>
        /// <param name="username">用户名</param>
        public void createguestbook(string username)
        {

            XDocument doc = new XDocument(
            new XDeclaration("1.0", "utf-8", "yes"),
            new XComment("创建时间@" + System.DateTime.Now.ToString()),
            new XElement("P-" + username,
                new XAttribute("更新时间", System.DateTime.Now.ToString()),
                new XElement("留言板")
                )


            );
            doc.Save(System.Web.HttpContext.Current.Server.MapPath("/DATA/P-" + username + ".xml"));

        }
        #endregion
        #region
        public string getURL(string ID)
        {
            return "172.16.72.35:12346/DATA/"+ID+".xml";
        }
        #endregion
        #region
        
        #endregion

    }
}