<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="WebForm1.aspx.cs" Inherits="CostManagement.CostVisualization.echarts.WebForm1" %>

<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title></title>
</head>
<body>
    <form id="form1" runat="server">
    <div>
        <asp:GridView ID="GridView1" runat="server" HorizontalAlign="Left" BorderColor="#B9B9B9">
                <RowStyle Wrap="False" BackColor="White"  HorizontalAlign="Left" Font-Size="10pt"  Font-Names="Microsoft YaHei UI" />
                <PagerStyle BackColor="White" ForeColor="#000066" HorizontalAlign="Left" Font-Size="10pt" Wrap="False" Font-Names="Microsoft YaHei UI" />
                <HeaderStyle BackColor="#f4f4f4" Font-Bold="True" ForeColor="Black" Height="30" Font-Size="10pt"  Font-Names="Microsoft YaHei UI" />
            </asp:GridView>
    </div>
    </form>
</body>
</html>
