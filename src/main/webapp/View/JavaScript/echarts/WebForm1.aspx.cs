using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace CostManagement.CostVisualization.echarts
{
    public partial class WebForm1 : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            //string sqlField_Summation = "AnnualCumulative";Expenses_
            //string sqlFiled_Production = "AnnualCumulative";
            string sqlField_Summation = "AnnualCumulative";
            string sqlFiled_Production = "AnnualCumulative";
            BLL.DepartmentCostSummary departmentCostSummary = new BLL.DepartmentCostSummary();
            DataTable dt = departmentCostSummary.getDataTable(sqlField_Summation, sqlFiled_Production);
            GridView1.DataSource = dt;
            GridView1.DataBind();
        }
    }
}