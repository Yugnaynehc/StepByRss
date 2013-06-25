using System;
using System.Data;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;

using System.Collections;

namespace TestWebService3
{
    /// <summary>
    /// Service1 的摘要说明
    /// </summary>
    [WebService(Namespace = "http://tempuri.org/")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    [System.ComponentModel.ToolboxItem(false)]
    // 若要允许使用 ASP.NET AJAX 从脚本中调用此 Web 服务，请取消对下行的注释。
    // [System.Web.Script.Services.ScriptService]
    public class Service1 : System.Web.Services.WebService
    {
        
        //Testwo test = new Testwo();
        PublishingSystem pub = new PublishingSystem();
        LoginSystem log = new LoginSystem();
        CommunicationSystem com=new CommunicationSystem();
        Feed_backSystem fed = new Feed_backSystem();
       
        [WebMethod(Description = "注册")]
        public string register(string username, string userpwd,string email)
        {
            return log.register(username,userpwd,email);
        }
        [WebMethod(Description = "是否已登陆")]
        public string isaLogin(string username)
        {
            return log.isaLogin(username);
        }
        [WebMethod(Description = "登陆")]
        public string login(string username, string userpwd)
        {
            return log.login(username,userpwd);
        }       
        [WebMethod(Description = "添加经验名称")]
        public string  CreateNodea(string username, string sbsname, string keywords, string classes, string sbsdescrible, string sbstool)
        {
            return pub.CreateNodea( username, sbsname,keywords,  classes, sbsdescrible,sbstool);        
        }
        [WebMethod(Description = "添加步骤")]
        public bool CreateNodeb(int time, string info, string ID, string username)
        {
            return  pub.CreateNodeb( time,info, ID, username);
        }
        [WebMethod(Description = "退出")]
        public bool exitsbs(string username)
        {
            return log.exitsbs(username);
        }       
        [WebMethod(Description = "评价经验")]
        public string commentsbsa(string ID, string info, string usernamea)
        {
            return fed.commentsbsa(ID,info,usernamea);
        }
        [WebMethod(Description = "留言功能实现")]
        public string guestbook(string username, string usernamea, string info)
        {
            return fed.guestbook(username, usernamea, info);
        }
        [WebMethod(Description = "最赞经验")]
        public List<string> searchzan()
        {
            return com.searchzan();
        }
        [WebMethod(Description = "赞某条经验")]
        public bool zan(int ID)
        {
            return fed.zan(ID);
        }
        [WebMethod(Description = "最新经验")]
        public List<string> searchnew()
        {
            return com.searchnew();
        }
        [WebMethod(Description = "向ID的XML中插入更新时间和步骤总数")]
        public bool CreateNodec(int ID, int sum)
        {
            return pub.CreateNodec(ID,sum);
        }
        [WebMethod(Description = "把图片添加进ID-P")]
        public bool createNodee(string ID, string time, string textString,string textstringlen)
        {        
            return pub.createNodee( ID, time,textString,textstringlen);
        }
       /*[WebMethod(Description = "获取图片（放在本地）")]
        public bool getp(string ID, string time, string url)
        {
            return pub.getp(ID,time,url); 
        }      */ 
        [WebMethod(Description = "读取步骤")]
        public List<string>getstep(string ID)
        {
            return fed.getstep(ID);
        }
        /*[WebMethod(Description = "传送步骤评论的xml")]
        public List<string>  GetIDXml(string ID)
        {
            return pub.GetIDXml(ID);
        }
        [WebMethod(Description = "传送照片的xml")]
        public List<string> GetIDPXml(string ID)
        {
            return pub.GetIDPXml(ID);
        }*/
        [WebMethod(Description = "获取评论")]
        public List<string> GetCommentsbs(string ID)
        {
            return fed.getcommentsbs(ID);
        }
        [WebMethod(Description = "获取图片")]
        public List<string> GetPhoto(string ID)
        {
            return fed.GetPhoto(ID);
        }
        [WebMethod(Description = "获取留言")]
        public List<string> GetGuestbook(string username)
        {
            return fed.GetGuestbook(username);
        }
        [WebMethod(Description = "搜索用户")]
        public List<string> SearchUser(string info)
        {
            return com.SearchUser(info);
        }
        [WebMethod(Description = "读取概要和工具")]
        public List<string> Getsbsdescrible(string ID)
        {
            return fed.Getsbsdescrible(ID);
        }
        [WebMethod(Description = "查看用户的发表的经验")]
        public List<string> viewsbs(string username)
        {
            return pub.viewsbs(username);
        }
        [WebMethod(Description = "删除经验")]
        public string Deletesbs(string username, string ID)
        {
            return pub.Deletesbs(username, ID);
        }
        [WebMethod(Description = "关注用户")]
        public bool attention(string username, string usernamea)
        {
            return com.attention(username, usernamea);
        }
        [WebMethod(Description = "取消关注")]
        public bool cancelattention(string username, string usernamea)
        {
            return com.cancelattention(username,usernamea);
        }
        [WebMethod(Description = "查看关注信息")]
        public List<string> Viewattention(string usernamea)
        {
            return com.Viewattention(usernamea);
        }
        [WebMethod(Description = "更改昵称")]
        public bool Resetnickname(string username, string nickname)
        {
            return log.Resetnickname(username, nickname);
        }
        [WebMethod(Description = "更改签名")]
        public bool Resetsignaure(string username, string signaure)
        {
            return log.resetsignaure(username, signaure);
        }
        [WebMethod(Description = "更改邮箱")]
        public bool Resetemail(string username, string email)
        {
            return log.resetemail(username, email);
        }
        [WebMethod(Description = "更改手机号码")]
        public bool Resettele(string username, string tele)
        {
            return log.resettele(username,tele);
        }
        [WebMethod(Description = "更改用户爱好")]
        public bool Resethobby(string username, string hobby)
        {
            return log.resethobby(username, hobby);
        }
        [WebMethod(Description = "查看用户个人信息")]
        public List<string> viewinformation(string username, string usernamea)
        {
            return log.viewinformation(username, usernamea);
        }
        [WebMethod(Description="获取积分")]
        public string Getmark(string username)
        {
            return pub.Getmark(username);
        }
        [WebMethod(Description = "查看昵称")]
        public string Getnickname(string username)
        {
            return log.Getnickname(username);
        }
        [WebMethod(Description = "查看邮箱")]
        public string Getemil(string username)
        {
            return log.Getemil(username);
        }
        [WebMethod(Description = "查看爱好")]
        public string Gethobby(string username)
        {
            return log.Gethobby(username);
        }
        [WebMethod(Description = "查看签名")]
        public string Getqianming(string username)
        {
            return log.Getqianming(username);
        }
        [WebMethod(Description = "查看手机")]
        public string Gettele(string username)
        {
            return log.Gettele(username);
        }
        [WebMethod(Description = "查看经验名称")]
        public string viewsbsname(string ID)
        {
            return pub.viewsbsname(ID);
        }
        [WebMethod(Description = "查看更新成员")]
        public string viewsbsusername(string ID)
        {
            return pub.viewsbsusername(ID);
        }
        [WebMethod(Description = "查看工具")]
        public string viewsbstool(string ID)
        {
            return pub.viewsbstool(ID);
        }
        [WebMethod(Description = "查看发布时间")]
        public string viewsbtime(string ID)
        {
            return pub.viewsbtime(ID);
        }
        [WebMethod(Description = "查看工具")]
        public string viewsbsdescrible(string ID)
        {
            return pub.viewsbsdescrible(ID);
        }
        [WebMethod(Description = "查看赞")]
        public string viewsbszan(string ID)
        {
            return pub.viewsbszan(ID);
        }
        [WebMethod(Description = "搜索匹配的经验")]
        public List<string> viewsomesbs(string info)
        {
            return pub.viewsomesbs(info);
        }
        [WebMethod(Description = "发布悬赏经验")]
        public string wanted(string username, string sbsname, string mark)
        {
            return pub.wanted(username,sbsname,mark);
        }
        [WebMethod(Description = "查看悬赏")]
        public List<string> viewexsbs(string username)
        {
            return pub.viewexsbs(username);
        }
        [WebMethod(Description = "撤销悬赏")]
        public bool cancelwanted(string username, string sbsname)
        {
            return pub.cancelwanted(username,sbsname);
        }
        [WebMethod(Description = "响应悬赏")]
        public bool responsewanted(string username, string sbsname, string usernamea, string ID)
        {
            return pub.responsewanted(username,sbsname,usernamea,ID);
        }
        [WebMethod(Description = "处理悬赏")]
        public bool dealresponse(string username, string usernamea, string sbsname)
        {
            return pub.dealresponse(username,usernamea,sbsname);
        }
        [WebMethod(Description = "热度+1")]
        public string Addhot(string ID)
        {
            return fed.Addhot(ID);
        }
        [WebMethod(Description = "最热经验")]
        public List<string> searchhot()
        {
            return com.searchhot();
        }
        [WebMethod(Description = "是否关注")]
        public bool Isattention(string username, string usernamea)
        {
            return com.Isattention(username, usernamea);
        }
        [WebMethod(Description = "举报")]
        public bool report(string username, string sbsname, string reason, string usernamea,string ID)
        {
            return fed.report(username, sbsname,reason, usernamea,ID );
        }
        [WebMethod(Description="积分+1")]
        public string Addex(string username,string ID)
        {
             return fed.Addex(username,ID);
        }
        /*[WebMethod(Description = "")]
        public string commentsbsa(string ID, string info, string usernamea)
        {
            return fed.commentsbsa(ID, info,  usernamea);
        }*/
        [WebMethod(Description = "修改步骤内容")]
        public string Resetsbs(string ID, string time, string info)
        {
            return pub.Resetsbs(ID, time, info);
        }
        [WebMethod(Description = "最热悬赏")]
        public List<string> searchex()
        {
            return com.searchex();
        }
    }
}