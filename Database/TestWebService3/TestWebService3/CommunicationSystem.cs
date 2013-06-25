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
    public class CommunicationSystem :IDisposable
    {
        public static SqlConnection sqlCon;
        private String ConServerStr = @"Data Source=O-PC;Initial Catalog=hhhjjj;Persist Security Info=True;User ID=SSL;Password=SSL";
        public static LoginSystem lsy;
        #region//默认构造函数

        public CommunicationSystem()
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
        #region//按关键字查询
        /// <summary>
        /// 按关键字查询
        /// </summary>
        /// <param name="keywords">关键字</param>
        /// <returns></returns>
        public List<string> searchsbskw(string keywords)
        {
            List<string> list = new List<string>();

            try
            {
                string sql = "select 经验名称,ID from 经验 where 关键字 like'%" + keywords + "%'";
                SqlCommand cmd = new SqlCommand(sql, sqlCon);
                SqlDataReader reader = cmd.ExecuteReader();

                while (reader.Read())
                {
                    //将结果集信息添加到返回向量中
                    list.Add(reader[0].ToString());
                    list.Add(reader[1].ToString());

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
        #region//按分类查询
        /// <summary>
        /// 按分类查询
        /// </summary>
        /// <param name="classes">分类</param>
        /// <returns></returns>
        public List<string> searchsbsc(string classes)
        {
            List<string> list = new List<string>();
            try
            {
                string sql = "select 经验名称,ID from 经验 where 分类='" + classes + "'";
                SqlCommand cmd = new SqlCommand(sql, sqlCon);
                SqlDataReader reader = cmd.ExecuteReader();

                while (reader.Read())
                {
                    //将结果集信息添加到返回向量中
                    list.Add(reader[0].ToString());
                    list.Add(reader[1].ToString());

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

        #region//关注用户
        /// <summary>
        /// 关注用户
        /// </summary>
        /// <param name="username">被关注用户</param>
        /// <param name="usernamea">发起关注用户</param>
        /// <returns></returns>

        public bool attention(string username, string usernamea)
        {
            try
            {
                string sql = "insert into 关注信息 values('"+username+"','"+usernamea+"')";
                SqlCommand cmd = new SqlCommand(sql,sqlCon);
                cmd.ExecuteNonQuery();
                cmd.Dispose();
                return true;
            }
            catch (Exception)
            {

                return false;
            }
            //return true; 
        }
        #endregion
        #region//取消关注
        /// <summary>
        /// 取消关注
        /// </summary>
        /// <param name="username">被关注人</param>
        /// <param name="usernamea">发起关注人</param>
        /// <returns></returns>
        public bool cancelattention(string username,string usernamea)
        {

            try
            {
                string sql = "delete from 关注信息 where 用户='"+username+"'and 关注人='"+usernamea+"'";
                SqlCommand cmd = new SqlCommand(sql,sqlCon);
                cmd.ExecuteNonQuery();
                cmd.Dispose();

                return true;

            }
            catch (Exception)
            {

                return false;
            }
            //return true;
        }
        #endregion
        #region//查看关注信息
        /// <summary>
        /// 查看关注信息
        /// </summary>
        /// <param name="usernamea"></param>
        /// <returns></returns>
        public List<string> Viewattention(string usernamea)
        {
            List<string> list = new List<string>();
            try
            {
                string sql = "select 用户 from 关注信息 where 关注人='"+usernamea+"'";
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
                
                throw;
            }
            return list;
        }
        #endregion
        #region//是否关注
        public bool Isattention(string username, string usernamea)
        {
            string m = "";
            try
            {
                string sql = "select 用户 from 关注信息 where 用户='"+username+"'and 关注人='"+usernamea+"'";
                SqlCommand cmd = new SqlCommand(sql,sqlCon);
                SqlDataReader reader = cmd.ExecuteReader();
                while (reader.Read())
                {
                    m = reader[0].ToString().Trim();
                }
                reader.Close();
                cmd.Dispose();
                if (m == "")
                    return false;
                else
                    return true;
                
                
            }
            catch (Exception)
            {
                
                throw;
            }
            
        }

        
        #endregion
        #region//最赞经验
        /// <summary>
        /// 最赞经验
        /// </summary>
        /// <returns></returns>
        public List<string> searchzan()
        {
            List<string> list = new List<string>();

            try
            {
                string sql = "select 经验名称,更新成员,ID,赞 from 经验  order by 赞 desc";
                SqlCommand cmd = new SqlCommand(sql, sqlCon);
                SqlDataReader reader = cmd.ExecuteReader();

                while (reader.Read())
                {
                    //将结果集信息添加到返回向量中
                    list.Add(reader[0].ToString());
                    list.Add(reader[1].ToString());
                    list.Add(reader[2].ToString());
                    list.Add("赞: "+reader[3].ToString());



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
        #region//最新经验
        /// <summary>
        /// 最新经验
        /// </summary>
        /// <returns></returns>
        public List<string> searchnew()
        {
            List<string> list = new List<string>();
            try
            {
                string sql = "select 经验名称,更新成员,ID,时间 from 经验 order by ID DESC";
                SqlCommand cmd = new SqlCommand(sql, sqlCon);
                SqlDataReader reader = cmd.ExecuteReader();
               
                    for (int i = 0; i < 5; i++)
                    {
                        reader.Read();
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
        #region//最热经验
        public List<string> searchhot()
        {
            List<string> list = new List<string>();
            try
            {
                string sql = "select 经验名称, 更新成员,ID,热度 from 经验 order by 热度 desc";
                SqlCommand cmd = new SqlCommand(sql,sqlCon);
                SqlDataReader reader = cmd.ExecuteReader();
                while (reader.Read())
                {
                    list.Add(reader[0].ToString());
                    list.Add(reader[1].ToString());
                    list.Add(reader[2].ToString());
                    list.Add("热度: " + reader[3].ToString());
                  
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
        #region
        public List<string> searchex()
        {
            List<string> list = new List<string>();
            try
            {
                string sql = "select * from 悬赏 order by 悬赏积分 desc";
                SqlCommand cmd = new SqlCommand(sql,sqlCon);
                SqlDataReader reader = cmd.ExecuteReader();
                while (reader.Read())
                {
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



        #region//搜索用户
        /// <summary>
        /// 
        /// </summary>
        /// <param name="info"></param>
        /// <returns></returns>
        public List<string> SearchUser(string info)
        {
            List<string> list = new List<string>();
            try
            {
                string sql = "select username from 用户信息表 where username like'%" + info + "%'";
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
            catch(Exception)
            {
                throw;
            }
            return list;

        }
        #endregion
    }
}