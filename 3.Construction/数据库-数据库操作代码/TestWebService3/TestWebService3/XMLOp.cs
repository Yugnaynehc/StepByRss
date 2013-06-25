using System;
using System.IO;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Xml;
namespace TestWebService3
{
    using System;
    using System.Web;
    using System.Xml;

    /// <summary>  
    /// 操作XML  
    /// </summary>  
    /// <remarks>  
    /// 创建人：zhujt<br/>  
    /// 创建日期：2012-08-22 09:34:24  
    /// </remarks>  
    public class XMLOp
    {
        public XMLOp()
        {

        }

        #region 创建XML
        /// <summary>  
        /// 创建XML  
        /// </summary>  
        /// <remarks>  
        /// 创建人：zhujt<br/>  
        /// 创建日期：2012-08-22 10:42:30  
        /// </remarks>   
        public static void CreateXML()
        {
            // 创建XML文档对象  
            XmlDocument xmldoc = new XmlDocument();
            // 加入XML的声明段落 <?xml version="1.0" encoding="gb2312"?>  
            XmlDeclaration xmldecl = xmldoc.CreateXmlDeclaration("1.0", "gb2312", null);
            // 添加到XML文档  
            xmldoc.AppendChild(xmldecl);

            // 创建元素对象  
            XmlElement xmlelem;
            // 加入一个跟元素  
            xmlelem = xmldoc.CreateElement("", "Employees", "");
            // 添加到XML文档  
            xmldoc.AppendChild(xmlelem);

            for (int i = 1; i < 10; i++)
            {
                XmlNode root = xmldoc.SelectSingleNode("Employees");
                XmlElement xe1 = xmldoc.CreateElement("Node"); // 创建一个接点  
                xe1.SetAttribute("genre", "张三");  // 设置该接点genre属性  
                xe1.SetAttribute("ISBN", "2-3614-8");  //  设置该接点ISBN属性  

                XmlElement xesub1 = xmldoc.CreateElement("title"); // 创建一个接点  
                xesub1.InnerText = "CS 从入门到精通"; // 设置文本接点  
                xe1.AppendChild(xesub1); // 添加到<Node>接点中  
                XmlElement xesub2 = xmldoc.CreateElement("author");
                xesub2.InnerText = "候捷";
                xe1.AppendChild(xesub2); // 添加到<Node>接点中  
                XmlElement xesub3 = xmldoc.CreateElement("price");
                xesub2.InnerText = "58.3";
                xe1.AppendChild(xesub3); // 添加到<Node>接点中  

                root.AppendChild(xe1); //添加到<Employees>接点中  

                // 保存创建好的XML文档  
                xmldoc.Save(System.Web.HttpContext.Current.Server.MapPath("/DataImport/xml/aa.xml"));

            }
        }

        /// <summary>  
        /// 创建XML  
        /// </summary>  
        /// <remarks>  
        /// 创建人：zhujt<br/>  
        /// 创建日期：2012-08-22 10:42:30  
        /// </remarks>   
        public static void CreateXMLFile()
        {
            // 创建一个XML文档  
            XmlTextWriter xmlWriter = new XmlTextWriter(System.Web.HttpContext.Current.Server.MapPath("/DataImport/xml/bb.xml"), System.Text.Encoding.Default);
            // 对输出的格式进行设置  
            xmlWriter.Formatting = Formatting.Indented;  // 设置缩进  
            // 编写版本为“1.0”的XML声明  
            xmlWriter.WriteStartDocument();
            //   
            xmlWriter.WriteStartElement("Employees");
            // 创建接点  
            xmlWriter.WriteStartElement("Node");
            xmlWriter.WriteAttributeString("genre", "张三");
            xmlWriter.WriteAttributeString("ISBN", "2-3631-6");

            xmlWriter.WriteStartElement("title");
            xmlWriter.WriteString("CS从入门到精通");
            xmlWriter.WriteEndElement();

            xmlWriter.WriteStartElement("author");
            xmlWriter.WriteString("候捷");
            xmlWriter.WriteEndElement();

            xmlWriter.WriteStartElement("price");
            xmlWriter.WriteString("58.3");
            xmlWriter.WriteEndElement();

            xmlWriter.WriteEndElement();

            xmlWriter.Close();
        }
        #endregion

        #region 添加节点
        /// <summary>  
        /// 添加节点  
        /// </summary>  
        /// <remarks>  
        /// 创建人：zhujt<br/>  
        /// 创建日期：2012-08-22 11:09:07  
        /// </remarks>   
        public static void CreateNode()
        {
            XmlDocument xmlDoc = new XmlDocument();
            // 文档加载  
            xmlDoc.Load(System.Web.HttpContext.Current.Server.MapPath("/DataImport/xml/bb.xml"));
            // 查找<Employees>  
            XmlNode root = xmlDoc.SelectSingleNode("Employees");
            // 创建一个<Node>节点  
            XmlElement xe1 = xmlDoc.CreateElement("Node");
            // 设置该节点genre属性  
            xe1.SetAttribute("genre", "李四");
            // 设置该节点ISBN属性  
            xe1.SetAttribute("ISBN", "1-1111-1");

            XmlElement xesub1 = xmlDoc.CreateElement("title");
            xesub1.InnerText = "C#入门帮助"; // 设置文本节点  
            xe1.AppendChild(xesub1);  // 添加到<Node>节点中  

            XmlElement xesub2 = xmlDoc.CreateElement("author");
            xesub2.InnerText = "高手";// 设置文本节点  
            xe1.AppendChild(xesub2);  // 添加到<Node>节点中  

            XmlElement xesub3 = xmlDoc.CreateElement("price");
            xesub3.InnerText = "158.3";// 设置文本节点  
            xe1.AppendChild(xesub3); // 添加到<Node>节点中  

            root.AppendChild(xe1); //添加到<Employees>节点中   
            xmlDoc.Save(System.Web.HttpContext.Current.Server.MapPath("/DataImport/xml/bb.xml"));

        }
        #endregion

        #region 修改节点的值（属性和子节点）
        /// <summary>  
        /// 修改节点的值（属性和子节点）  
        /// </summary>  
        /// <remarks>  
        /// 创建人：zhujt<br/>  
        /// 创建日期：2012-08-22 15:11:21  
        /// </remarks>   
        public static void ModifyNodeUp()
        {
            XmlDocument xmlDoc = new XmlDocument();
            // 文档加载  
            xmlDoc.Load(System.Web.HttpContext.Current.Server.MapPath("/DataImport/xml/bb.xml"));
            // 获取Employees节点的所有子节点  
            XmlNodeList nodeList = xmlDoc.SelectSingleNode("Employees").ChildNodes;

            // 遍历所有子节点  
            foreach (XmlNode xn in nodeList)
            {
                // 将子节点类型转换为XmlElement类型  
                XmlElement xe = (XmlElement)xn;
                // 如果genre属性值为“张三”  
                if (xe.GetAttribute("genre") == "张三")
                {
                    // 则修改该属性为“update张三”  
                    xe.SetAttribute("genre", "update张三");
                    //继续获取xe子节点的所有子节点  
                    XmlNodeList nls = xe.ChildNodes;
                    // 遍历  
                    foreach (XmlNode xn1 in nls)
                    {
                        // 类型转换  
                        XmlElement xe2 = (XmlElement)xn1;
                        // 如果找到  
                        if (xe2.Name == "author")
                            xe2.InnerText = "亚圣";//则修改  
                    }
                }
            }

            xmlDoc.Save(System.Web.HttpContext.Current.Server.MapPath("/DataImport/xml/bb.xml"));
        }
        #endregion

        #region 修改节点的值（添加节点的属性和添加节点的子节点）
        /// <summary>  
        /// 修改节点的值（添加节点的属性和添加节点的子节点）  
        /// </summary>  
        /// <remarks>  
        /// 创建人：zhujt<br/>  
        /// 创建日期：2012-08-22 15:23:46  
        /// </remarks>   
        public static void ModifyNodeAd()
        {
            XmlDocument xmlDoc = new XmlDocument();
            // 文档加载  
            xmlDoc.Load(System.Web.HttpContext.Current.Server.MapPath("/DataImport/xml/bb.xml"));
            // 获取Employees节点的所有子节点  
            XmlNodeList nodeList = xmlDoc.SelectSingleNode("Employees").ChildNodes;

            // 遍历所有子节点  
            foreach (XmlNode xn in nodeList)
            {
                // 将子节点类型转换为XmlElement类型  
                XmlElement xe = (XmlElement)xn;
                xe.SetAttribute("test", "1111"); // 添加test属性  

                XmlElement xesub = xmlDoc.CreateElement("flag"); // 创建子节点  
                xesub.InnerText = "123";
                xe.AppendChild(xesub);
            }

            xmlDoc.Save(System.Web.HttpContext.Current.Server.MapPath("/DataImport/xml/bb.xml"));
        }
        #endregion

        #region 删除节点中的某一个属性
        /// <summary>  
        /// 删除节点中的某一个属性  
        /// </summary>  
        /// <remarks>  
        /// 创建人：zhujt<br/>  
        /// 创建日期：2012-08-22 15:29:19  
        /// </remarks>   
        public static void DelNodeAttribute()
        {
            XmlDocument xmlDoc = new XmlDocument();
            // 文档加载  
            xmlDoc.Load(System.Web.HttpContext.Current.Server.MapPath("/DataImport/xml/bb.xml"));
            // 获取Employees节点的所有子节点  
            XmlNodeList nodeList = xmlDoc.SelectSingleNode("Employees").ChildNodes;

            // 遍历所有子节点  
            foreach (XmlNode xn in nodeList)
            {
                // 将子节点类型转换为XmlElement类型  
                XmlElement xe = (XmlElement)xn;
                xe.RemoveAttribute("genre"); // 删除genre属性  

                // 继续获取xe子节点的所有子节点  
                XmlNodeList nls = xe.ChildNodes;

                // 遍历  
                foreach (XmlNode xn1 in nls)
                {
                    XmlElement xe2 = (XmlElement)xn1; // 转换类型  

                    if (xe2.Name == "flag") // 如果找到  
                        xe.RemoveChild(xe2);  // 则删除  
                }
            }

            xmlDoc.Save(System.Web.HttpContext.Current.Server.MapPath("/DataImport/xml/bb.xml"));
        }
        #endregion

        #region 删除节点
        /// <summary>  
        /// 删除节点  
        /// </summary>  
        /// <remarks>  
        /// 创建人：zhujt<br/>  
        /// 创建日期：2012-08-22 15:35:39  
        /// </remarks>   
        public static void DelNode()
        {
            XmlDocument xmlDoc = new XmlDocument();
            // 获取子节点  
            XmlNode root = xmlDoc.SelectSingleNode("Employees");
            // 文档加载  
            xmlDoc.Load(System.Web.HttpContext.Current.Server.MapPath("/DataImport/xml/bb.xml"));
            // 获取Employees节点的所有子节点  
            XmlNodeList nodeList = xmlDoc.SelectSingleNode("Employees").ChildNodes;

            for (int i = 0; i < nodeList.Count; i++)
            {
                XmlElement xe = (XmlElement)nodeList.Item(i);

                if (xe.GetAttribute("genre") == "李四")
                {
                    root.RemoveChild(xe); // 此地方可能出现异常  
                    if (i < nodeList.Count)
                        i = i - 1;
                }
            }

            xmlDoc.Save(System.Web.HttpContext.Current.Server.MapPath("/DataImport/xml/bb.xml"));
        }
        #endregion

        #region 按照文本文件读取XML
        /// <summary>  
        /// 按照文本文件读取XML  
        /// </summary>  
        /// <remarks>  
        /// 创建人：zhujt<br/>  
        /// 创建日期：2012-08-22 15:50:51  
        /// </remarks>   
        public static string XMLReader()
        {
            // 创建对象  
            System.IO.StreamReader myFile = new System.IO.StreamReader(System.Web.HttpContext.Current.Server.MapPath("/DataImport/xml/bb.xml"), System.Text.Encoding.Default);
            // myString是读出的字符串  
            string myString = myFile.ReadToEnd();
            myFile.Close();
            return myString;
        }
        #endregion
        #region//将xml写成二进制字节流
        /// <summary>
        /// 将xml写成二进制字节流
        /// </summary>
        /// <param name="ID"></param>
        /// <returns></returns>
        public byte[] readxml(int ID)
        {
            try
            {
                FileStream fileStream = new FileStream(System.Web.HttpContext.Current.Server.MapPath("/DATA/"+ID+".xml"), FileMode.Open);  
  
                BinaryReader binaryReader = new BinaryReader(fileStream);  
  
                byte[] imageBuffer = new byte[binaryReader.BaseStream.Length];
                return imageBuffer;
            }
            catch (Exception )
            {
                
                throw;
            }
        }

        #endregion
    }  
}