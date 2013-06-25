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
    public class Class1
    {
        PublishingSystem pub = new PublishingSystem();
        public string readxml()
        {
            try
            {
                FileStream fileStream = new FileStream("D:/dzd123/照片/GEDC0647", FileMode.Open);

                BinaryReader binaryReader = new BinaryReader(fileStream);

                byte[] imageBuffer = new byte[binaryReader.BaseStream.Length];
                binaryReader.Read(imageBuffer, 0, Convert.ToInt32(binaryReader.BaseStream.Length));
                string textString = System.Convert.ToBase64String(imageBuffer);
                return textString;
            }
            catch (Exception)
            {

                throw;
            }
        }
        public bool intsertxml()
        {
            try
            {

                return true;

            }
            catch (Exception)
            {
                
                throw;
            }
        }
    }
}