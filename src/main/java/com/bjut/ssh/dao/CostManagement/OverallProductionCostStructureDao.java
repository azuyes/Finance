package com.bjut.ssh.dao.CostManagement;

import com.bjut.ssh.entity.Msg;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title: LaborCostDao
 * @Description: TODO
 * @Author: lxy
 * @CreateDate: 2018/8/3 13:55
 * @Version: 1.0
 */

@Repository
@Transactional
public class OverallProductionCostStructureDao {

    @Autowired
    private SessionFactory sessionFactory;

    public Session getSession() {
        return this.sessionFactory.openSession();
    }

    public void close(Session session) {
        if (session != null)
            session.close();
    }

    //获取图表的主题
    public Msg getEchartsTheme() {
        Session session = null;
        Transaction tx = null;
        session = getSession();
        tx = session.beginTransaction();

        List<String> themeList = new ArrayList<>();

        Query query = session.createQuery("select graphTheme from Cbtbsz where graphNo = :a");
        query.setString("a", "000011");
        String graphTheme = (String) query.uniqueResult();
        themeList.add(graphTheme);

        tx.commit();
        close(session);
        return Msg.success().add("OverallProductionCostStructure_getEchartsTheme", themeList);
    }

    //部门下拉菜单获取部门
    public Msg getDepartmentSelect() {
        Session session = null;
        Transaction tx = null;
        //List<Departmentbase> DepartmentbaseList = null;

        session = getSession();
        tx = session.beginTransaction();

        String CatNo_department = null;

        Query query_getCatNo = session.createQuery("select itemData1 from Cbqtsz where itemName = :a");
        query_getCatNo.setString("a", "部门");
        CatNo_department = (String) query_getCatNo.uniqueResult();

        Query query = session.createQuery("select spName,spNo from Lshszd where catNo = :aaa and spLevel = :bbb");
        query.setString("aaa", CatNo_department);
        query.setString("bbb", "1");
        List<Map<String, Object>> list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("0", "全部");//核算名称
        map.put("1", "All");//核算编号

        list.add(0, map);//数字0的意思是把这个map放到list的头部
        System.out.println(list);
        if (list.size() > 0) {
            tx.commit();
            close(session);
            return Msg.success().add("OverallProductionCostStructure_departmentSelect", list);
        } else {
            tx.commit();
            close(session);
            return Msg.fail().add("errorInfo", "编号重复，请重新输入");
        }

    }

    //产品下拉菜单获取产品
    public Msg getProductSelect() {
        Session session = null;
        Transaction tx = null;
        session = getSession();
        tx = session.beginTransaction();

        String CatNo_product = null;
        Query query_getCatNo = session.createQuery("select itemData1 from Cbqtsz where itemName = :a");
        query_getCatNo.setString("a", "产品");
        CatNo_product = (String) query_getCatNo.uniqueResult();

        Query query = session.createQuery("select spName,spNo from Lshszd where catNo = :aaa and spLevel = :bbb");
        query.setString("aaa", CatNo_product);
        query.setString("bbb", "1");
        List<Map<String, Object>> list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("0", "全部");//核算名称
        map.put("1", "All");//核算编号
        list.add(0, map);//数字0的意思是把这个map放到list的头部
        Map<String, Object> map1 = new HashMap<String, Object>();
        map1.put("0", "不选择");//核算名称
        map1.put("1", "null");//核算编号
        list.add(0, map1);//数字0的意思是把这个map放到list的头部
        System.out.println(list);
        if (list.size() > 0) {
            tx.commit();
            close(session);
            return Msg.success().add("OverallProductionCostStructure_productSelect", list);
        } else {
            tx.commit();
            close(session);
            return Msg.fail().add("errorInfo", "编号重复，请重新输入");
        }
    }

    //根据选择的时间获取图表的数据
    public Msg getValData(String selectCompany, String selectProduct, String selectTime) {
        String sqlField_Summation = "AnnualCumulative";
        List<List<String>> Item_list = new ArrayList<>();//总体的项目，用于传回前端
        List<String> itemName_list = new ArrayList<>();//记录项目名称
        List<String> itemMoney_list = new ArrayList<>();//记录项目的金额
        List<String> ItemNo_List = new ArrayList<>();//科目列表
        List<String> DefinedItemNo_List = new ArrayList<>();//自定义科目列表
        int itemNoListNumber = 0;//科目总的数量，包括自定义的\
        List<Double> CostSum_List = new ArrayList<>();//每个科目金额列表，该列表长度就是上面定义的ItemNoListNumber

        //获取科目
        ItemNo_List = getItemNo(selectCompany, selectProduct);
        DefinedItemNo_List = getDefinedItemNo();
        if (ItemNo_List == null) {
            itemNoListNumber = DefinedItemNo_List.size();
        } else if (DefinedItemNo_List == null) {
            itemNoListNumber = ItemNo_List.size();
        } else {
            itemNoListNumber = ItemNo_List.size() + DefinedItemNo_List.size();
        }
        //获取所有项目的名称
        itemName_list = getItemNam(selectCompany, selectProduct);
        Item_list.add(itemName_list);

        Session session = null;
        Transaction tx = null;

        //根据选择的时间不同，对sqlField_Summation(借方金额)，sqlFiled_Production（产量数量）进行赋值
        if (selectTime.equals("AnnualCumulative")) {
            sqlField_Summation = "debitMoneyAcm";//注意这里的annualCumulative是和前面的不一样
        } else {
            int len = selectTime.length();
            if (len == 1) {
                //selectTime = ("0" + selectTime.substring(len - 1, len + 1));     //转换月份的格式，如将“1”转成“01”
                selectTime = "0" + selectTime;
            }
            sqlField_Summation = "debitMoney" + selectTime;//注意，这个与之前的命名不同
        }

        //---------公司栏选择全部----------------------------------------------------------------------------
        if (selectCompany.equals("All")) {
            try {
                session = getSession();
                tx = session.beginTransaction();

                //获取所有一级部门的核算编号
                List<String> departmentList = getDepartmentId();
                //获取所有一级产品的核算编号
                List<String> productList = getProductId();

                for (String dicDepartment : departmentList) {
                    if (selectProduct.equals("All")) {
                        for (String dicProduct : productList) {
                            int i = 0;
                            if (ItemNo_List != null) {
                                for (String itemNo : ItemNo_List) {//非自定义科目
                                    double sumIndex = 0;//中间变量
                                    if (CostSum_List.size() >= i + 1) {
                                        sumIndex = CostSum_List.get(i);
                                        sumIndex += GetCost_All(sqlField_Summation, itemNo, dicDepartment, dicProduct);
                                        CostSum_List.set(i, sumIndex);
                                    } else {
                                        sumIndex += GetCost_All(sqlField_Summation, itemNo, dicDepartment, dicProduct);
                                        CostSum_List.add(sumIndex);
                                    }
                                    i++;
                                }
                            }
                            if (DefinedItemNo_List != null) {
                                for (String itemNo : DefinedItemNo_List) {//自定义科目
                                    double sumIndex = 0;//中间变量
                                    List<String> itemNoByDefined_list = getItemNoByDefined(itemNo);
                                    for (String itemNo2 : itemNoByDefined_list) {
                                        if (CostSum_List.size() >= i + 1) {
                                            sumIndex = CostSum_List.get(i);
                                        }
                                        sumIndex += GetCost_All(sqlField_Summation, itemNo2, dicDepartment, dicProduct);
                                    }
                                    if (CostSum_List.size() >= i + 1) {
                                        CostSum_List.set(i, sumIndex);
                                    } else {
                                        CostSum_List.add(sumIndex);
                                    }
                                    i++;
                                }
                            }
                        }
                    } else {
                        int i = 0;
                        if (ItemNo_List != null) {
                            for (String itemNo : ItemNo_List) {
                                double sumIndex = 0;//中间变量
                                if (CostSum_List.size() >= i + 1) {
                                    sumIndex = CostSum_List.get(i);
                                    sumIndex += GetCost_All(sqlField_Summation, itemNo, dicDepartment, selectProduct);
                                    CostSum_List.set(i, sumIndex);
                                } else {
                                    sumIndex += GetCost_All(sqlField_Summation, itemNo, dicDepartment, selectProduct);
                                    CostSum_List.add(sumIndex);
                                }
                                i++;
                            }
                        }
                        if (DefinedItemNo_List != null) {
                            for (String itemNo : DefinedItemNo_List) {//自定义科目
                                double sumIndex = 0;//中间变量
                                List<String> itemNoByDefined_list = getItemNoByDefined(itemNo);
                                for (String itemNo2 : itemNoByDefined_list) {
                                    if (CostSum_List.size() >= i + 1) {
                                        sumIndex = CostSum_List.get(i);
                                    }
                                    sumIndex += GetCost_All(sqlField_Summation, itemNo2, dicDepartment, selectProduct);
                                }
                                if (CostSum_List.size() >= i + 1) {
                                    CostSum_List.set(i, sumIndex);
                                } else {
                                    CostSum_List.add(sumIndex);
                                }
                                i++;
                            }
                        }
                    }
                }
            } catch (Exception e) {
                tx.rollback();
                close(session);
                e.printStackTrace();
            }
            for (Double itemMoney_Double : CostSum_List) {
                String itemMoney_String = itemMoney_Double.toString();
                itemMoney_list.add(itemMoney_String);
            }
            Item_list.add(itemMoney_list);

            tx.commit();
            close(session);
            return Msg.success().add("OverallProductionCostStructure_value_List", Item_list);
            // ---------当选择了具体的公司时-------------------------------------------------------------------------------
        } else {
            try {
                session = getSession();
                tx = session.beginTransaction();
                //获取所有一级产品的核算编号
                List<String> productList = getProductId();

                if (selectProduct.equals("All")) {
                    for (String dicProduct : productList) {
                        int i = 0;
                        if (ItemNo_List != null) {
                            for (String itemNo : ItemNo_List) {
                                double sumIndex = 0;//中间变量
                                if (CostSum_List.size() >= i + 1) {
                                    sumIndex = CostSum_List.get(i);
                                    sumIndex += GetCost_All(sqlField_Summation, itemNo, selectCompany, dicProduct);
                                    CostSum_List.set(i, sumIndex);
                                } else {
                                    sumIndex += GetCost_All(sqlField_Summation, itemNo, selectCompany, dicProduct);
                                    CostSum_List.add(sumIndex);
                                }
                                i++;
                            }
                        }
                        if (DefinedItemNo_List != null) {
                            for (String itemNo : DefinedItemNo_List) {//自定义科目
                                double sumIndex = 0;//中间变量
                                List<String> itemNoByDefined_list = getItemNoByDefined(itemNo);
                                for (String itemNo2 : itemNoByDefined_list) {
                                    if (CostSum_List.size() >= i + 1) {
                                        sumIndex = CostSum_List.get(i);
                                    }
                                    sumIndex += GetCost_All(sqlField_Summation, itemNo2, selectCompany, dicProduct);
                                }
                                if (CostSum_List.size() >= i + 1) {
                                    CostSum_List.set(i, sumIndex);
                                } else {
                                    CostSum_List.add(sumIndex);
                                }
                                i++;
                            }
                        }
                    }
                } else {
                    int i = 0;
                    if (ItemNo_List != null) {
                        for (String itemNo : ItemNo_List) {
                            double sumIndex = 0;//中间变量
                            if (CostSum_List.size() >= i + 1) {
                                sumIndex = CostSum_List.get(i);
                                sumIndex += GetCost_All(sqlField_Summation, itemNo, selectCompany, selectProduct);
                                CostSum_List.set(i, sumIndex);
                            } else {
                                sumIndex += GetCost_All(sqlField_Summation, itemNo, selectCompany, selectProduct);
                                CostSum_List.add(sumIndex);
                            }
                            i++;
                        }
                    }
                    if (DefinedItemNo_List != null) {
                        for (String itemNo : DefinedItemNo_List) {//自定义科目
                            double sumIndex = 0;//中间变量
                            List<String> itemNoByDefined_list = getItemNoByDefined(itemNo);
                            for (String itemNo2 : itemNoByDefined_list) {
                                if (CostSum_List.size() >= i + 1) {
                                    sumIndex = CostSum_List.get(i);
                                }
                                sumIndex += GetCost_All(sqlField_Summation, itemNo2, selectCompany, selectProduct);
                            }
                            if (CostSum_List.size() >= i + 1) {
                                CostSum_List.set(i, sumIndex);
                            } else {
                                CostSum_List.add(sumIndex);
                            }
                            i++;
                        }
                    }
                }

                //还差一个其余变量没有写

            } catch (Exception e) {
                tx.rollback();
                close(session);
                e.printStackTrace();
            }
            for (Double itemMoney_Double : CostSum_List) {
                String itemMoney_String = itemMoney_Double.toString();
                itemMoney_list.add(itemMoney_String);
            }
            Item_list.add(itemMoney_list);

            tx.commit();
            close(session);
            return Msg.success().add("OverallProductionCostStructure_value_List", Item_list);
        }
    }

    //根据选择的时间获取图表的数据
    public Msg getDatagridData(String selectCompany, String selectProduct, String selectTime) {
        String sqlField_Summation = "AnnualCumulative";
        List<Map<String, Object>> Item_list = new ArrayList<>();//总体的项目，用于传回前端
        List<String> itemName_list = new ArrayList<>();//记录项目名称
        List<String> itemMoney_list = new ArrayList<>();//记录项目的金额
        List<String> ItemNo_List = new ArrayList<>();//科目列表
        List<String> DefinedItemNo_List = new ArrayList<>();//自定义科目列表
        int itemNoListNumber = 0;//科目总的数量，包括自定义的\
        List<Double> CostSum_List = new ArrayList<>();//每个科目金额列表，该列表长度就是上面定义的ItemNoListNumber

        //获取科目
        ItemNo_List = getItemNo(selectCompany, selectProduct);
        DefinedItemNo_List = getDefinedItemNo();
        if (ItemNo_List == null) {
            itemNoListNumber = DefinedItemNo_List.size();
        } else if (DefinedItemNo_List == null) {
            itemNoListNumber = ItemNo_List.size();
        } else {
            itemNoListNumber = ItemNo_List.size() + DefinedItemNo_List.size();
        }
        //获取所有项目的名称
        itemName_list = getItemNam(selectCompany, selectProduct);

        Session session = null;
        Transaction tx = null;

        //根据选择的时间不同，对sqlField_Summation(借方金额)，sqlFiled_Production（产量数量）进行赋值
        if (selectTime.equals("AnnualCumulative")) {
            sqlField_Summation = "debitMoneyAcm";//注意这里的annualCumulative是和前面的不一样
        } else {
            int len = selectTime.length();
            if (len == 1) {
                //selectTime = ("0" + selectTime.substring(len - 1, len + 1));     //转换月份的格式，如将“1”转成“01”
                selectTime = "0" + selectTime;
            }
            sqlField_Summation = "debitMoney" + selectTime;//注意，这个与之前的命名不同
        }

        //---------公司栏选择全部----------------------------------------------------------------------------
        if (selectCompany.equals("All")) {
            try {
                session = getSession();
                tx = session.beginTransaction();

                //获取所有一级部门的核算编号
                List<String> departmentList = getDepartmentId();
                //获取所有一级产品的核算编号
                List<String> productList = getProductId();

                for (String dicDepartment : departmentList) {
                    if (selectProduct.equals("All")) {
                        for (String dicProduct : productList) {
                            int i = 0;
                            if (ItemNo_List != null) {
                                for (String itemNo : ItemNo_List) {//非自定义科目
                                    double sumIndex = 0;//中间变量
                                    if (CostSum_List.size() >= i + 1) {
                                        sumIndex = CostSum_List.get(i);
                                        sumIndex += GetCost_All(sqlField_Summation, itemNo, dicDepartment, dicProduct);
                                        CostSum_List.set(i, sumIndex);
                                    } else {
                                        sumIndex += GetCost_All(sqlField_Summation, itemNo, dicDepartment, dicProduct);
                                        CostSum_List.add(sumIndex);
                                    }
                                    i++;
                                }
                            }
                            if (DefinedItemNo_List != null) {
                                for (String itemNo : DefinedItemNo_List) {//自定义科目
                                    double sumIndex = 0;//中间变量
                                    List<String> itemNoByDefined_list = getItemNoByDefined(itemNo);
                                    for (String itemNo2 : itemNoByDefined_list) {
                                        if (CostSum_List.size() >= i + 1) {
                                            sumIndex = CostSum_List.get(i);
                                        }
                                        sumIndex += GetCost_All(sqlField_Summation, itemNo2, dicDepartment, dicProduct);
                                    }
                                    if (CostSum_List.size() >= i + 1) {
                                        CostSum_List.set(i, sumIndex);
                                    } else {
                                        CostSum_List.add(sumIndex);
                                    }
                                    i++;
                                }
                            }
                        }
                    } else {
                        int i = 0;
                        if (ItemNo_List != null) {
                            for (String itemNo : ItemNo_List) {
                                double sumIndex = 0;//中间变量
                                if (CostSum_List.size() >= i + 1) {
                                    sumIndex = CostSum_List.get(i);
                                    sumIndex += GetCost_All(sqlField_Summation, itemNo, dicDepartment, selectProduct);
                                    CostSum_List.set(i, sumIndex);
                                } else {
                                    sumIndex += GetCost_All(sqlField_Summation, itemNo, dicDepartment, selectProduct);
                                    CostSum_List.add(sumIndex);
                                }
                                i++;
                            }
                        }
                        if (DefinedItemNo_List != null) {
                            for (String itemNo : DefinedItemNo_List) {//自定义科目
                                double sumIndex = 0;//中间变量
                                List<String> itemNoByDefined_list = getItemNoByDefined(itemNo);
                                for (String itemNo2 : itemNoByDefined_list) {
                                    if (CostSum_List.size() >= i + 1) {
                                        sumIndex = CostSum_List.get(i);
                                    }
                                    sumIndex += GetCost_All(sqlField_Summation, itemNo2, dicDepartment, selectProduct);
                                }
                                if (CostSum_List.size() >= i + 1) {
                                    CostSum_List.set(i, sumIndex);
                                } else {
                                    CostSum_List.add(sumIndex);
                                }
                                i++;
                            }
                        }
                    }
                }
            } catch (Exception e) {
                tx.rollback();
                close(session);
                e.printStackTrace();
            }
            for (int i = 0; i < CostSum_List.size(); i++) {
                Double itemMoney_Double = CostSum_List.get(i);
                String itemMoney_String = itemMoney_Double.toString();
                String itemName_String = itemName_list.get(i);
                Map<String, Object> map1 = new HashMap<String, Object>();
                map1.put("text", itemName_String);
                map1.put("productCost", itemMoney_String);
                Item_list.add(map1);
            }

            tx.commit();
            close(session);
            return Msg.success().add("OverallProductionCostStructure_datagridValue_List", Item_list);
            // ---------当选择了具体的公司时-------------------------------------------------------------------------------
        } else {
            try {
                session = getSession();
                tx = session.beginTransaction();
                //获取所有一级产品的核算编号
                List<String> productList = getProductId();

                if (selectProduct.equals("All")) {
                    for (String dicProduct : productList) {
                        int i = 0;
                        if (ItemNo_List != null) {
                            for (String itemNo : ItemNo_List) {
                                double sumIndex = 0;//中间变量
                                if (CostSum_List.size() >= i + 1) {
                                    sumIndex = CostSum_List.get(i);
                                    sumIndex += GetCost_All(sqlField_Summation, itemNo, selectCompany, dicProduct);
                                    CostSum_List.set(i, sumIndex);
                                } else {
                                    sumIndex += GetCost_All(sqlField_Summation, itemNo, selectCompany, dicProduct);
                                    CostSum_List.add(sumIndex);
                                }
                                i++;
                            }
                        }
                        if (DefinedItemNo_List != null) {
                            for (String itemNo : DefinedItemNo_List) {//自定义科目
                                double sumIndex = 0;//中间变量
                                List<String> itemNoByDefined_list = getItemNoByDefined(itemNo);
                                for (String itemNo2 : itemNoByDefined_list) {
                                    if (CostSum_List.size() >= i + 1) {
                                        sumIndex = CostSum_List.get(i);
                                    }
                                    sumIndex += GetCost_All(sqlField_Summation, itemNo2, selectCompany, dicProduct);
                                }
                                if (CostSum_List.size() >= i + 1) {
                                    CostSum_List.set(i, sumIndex);
                                } else {
                                    CostSum_List.add(sumIndex);
                                }
                                i++;
                            }
                        }
                    }
                } else {
                    int i = 0;
                    if (ItemNo_List != null) {
                        for (String itemNo : ItemNo_List) {
                            double sumIndex = 0;//中间变量
                            if (CostSum_List.size() >= i + 1) {
                                sumIndex = CostSum_List.get(i);
                                sumIndex += GetCost_All(sqlField_Summation, itemNo, selectCompany, selectProduct);
                                CostSum_List.set(i, sumIndex);
                            } else {
                                sumIndex += GetCost_All(sqlField_Summation, itemNo, selectCompany, selectProduct);
                                CostSum_List.add(sumIndex);
                            }
                            i++;
                        }
                    }
                    if (DefinedItemNo_List != null) {
                        for (String itemNo : DefinedItemNo_List) {//自定义科目
                            double sumIndex = 0;//中间变量
                            List<String> itemNoByDefined_list = getItemNoByDefined(itemNo);
                            for (String itemNo2 : itemNoByDefined_list) {
                                if (CostSum_List.size() >= i + 1) {
                                    sumIndex = CostSum_List.get(i);
                                }
                                sumIndex += GetCost_All(sqlField_Summation, itemNo2, selectCompany, selectProduct);
                            }
                            if (CostSum_List.size() >= i + 1) {
                                CostSum_List.set(i, sumIndex);
                            } else {
                                CostSum_List.add(sumIndex);
                            }
                            i++;
                        }
                    }
                }

                //还差一个其余变量没有写

            } catch (Exception e) {
                tx.rollback();
                close(session);
                e.printStackTrace();
            }
            for (int i = 0; i < CostSum_List.size(); i++) {
                Double itemMoney_Double = CostSum_List.get(i);
                String itemMoney_String = itemMoney_Double.toString();
                String itemName_String = itemName_list.get(i);
                Map<String, Object> map1 = new HashMap<String, Object>();
                map1.put("text", itemName_String);
                map1.put("productCost", itemMoney_String);
                Item_list.add(map1);
            }

            tx.commit();
            close(session);
            return Msg.success().add("OverallProductionCostStructure_datagridValue_List", Item_list);
        }
    }

    //获取生产部门编号
    public List<String> getDepartmentId() {
        Session session = null;
        Transaction tx = null;

        session = getSession();
        tx = session.beginTransaction();

        String CatNo_department = null;

        Query query_getCatNo = session.createQuery("select itemData1 from Cbqtsz where itemName = :a");
        query_getCatNo.setString("a", "部门");
        CatNo_department = (String) query_getCatNo.uniqueResult();

        Query query = session.createQuery("select spNo from Lshszd where catNo = :aaa and spLevel = :bbb");
        query.setString("aaa", CatNo_department);
        query.setString("bbb", "1");
        List<String> list = query.list();

        System.out.println(list);
        if (list.size() > 0) {
            tx.commit();
            close(session);
            return list;
        } else {
            tx.commit();
            close(session);
            return null;
        }

    }

    //获取产品编号
    public List<String> getProductId() {
        Session session = null;
        Transaction tx = null;
        session = getSession();
        tx = session.beginTransaction();

        String CatNo_product = null;
        Query query_getCatNo = session.createQuery("select itemData1 from Cbqtsz where itemName = :a");
        query_getCatNo.setString("a", "产品");
        CatNo_product = (String) query_getCatNo.uniqueResult();

        Query query = session.createQuery("select spNo from Lshszd where catNo = :aaa and spLevel = :bbb");
        query.setString("aaa", CatNo_product);
        query.setString("bbb", "1");
        List<String> list = query.list();

        if (list.size() > 0) {
            tx.commit();
            close(session);
            return list;
        } else {
            tx.commit();
            close(session);
            return null;
        }
    }

    //获取图表中可以出现的所有科目编号
    public List getItemNo(String selectCompany, String selectProduct) {
        Session session = null;
        Transaction tx = null;
        try {
            session = getSession();
            tx = session.beginTransaction();

            List<String> itemNoList = new ArrayList<>();

            Query query = session.createQuery("select graphData1 from Cbtbsz where graphNo = :a");
            query.setString("a", "000011");
            String itemNo = (String) query.uniqueResult();

            String[] split_itemNo = itemNo.split("\\;");
            //将数组的每个数据加进list
            for (String item1 : split_itemNo) {
                itemNoList.add(item1);
            }

            System.out.println(itemNoList);
            tx.commit();
            close(session);
            return itemNoList;
        } catch (Exception e) {
            tx.rollback();
            close(session);
            e.printStackTrace();
            return null;
        }
    }

    //获取图表中可以出现的所有自定义科目编号
    public List getDefinedItemNo() {
        Session session = null;
        Transaction tx = null;
        session = getSession();
        tx = session.beginTransaction();

        List<String> itemNoList = new ArrayList<>();

        Query query = session.createQuery("select graphData2 from Cbtbsz where graphNo = :a");
        query.setString("a", "000011");
        String itemNo = (String) query.uniqueResult();

        String[] split_itemNo = itemNo.split("\\;");
        //将数组的每个数据加进list
        for (String item1 : split_itemNo) {
            itemNoList.add(item1);
        }

        System.out.println(itemNoList);
        tx.commit();
        close(session);
        return itemNoList;
    }

    //根据自定义科目获取相应的科目编号
    public List getItemNoByDefined(String DefinedItemNo) {
        Session session = null;
        Transaction tx = null;
        session = getSession();
        tx = session.beginTransaction();

        List<String> itemNoList = new ArrayList<>();

        Query query = session.createQuery("select itemData1 from Cbqtsz where itemNo = :a");
        query.setString("a", DefinedItemNo);
        String itemNo = (String) query.uniqueResult();

        String[] split_itemNo = itemNo.split("\\;");
        //将数组的每个数据加进list
        for (String item1 : split_itemNo) {
            itemNoList.add(item1);
        }

        System.out.println(itemNoList);
        tx.commit();
        close(session);
        return itemNoList;
    }

    //根据科目编号和核算编号在核算金额表中获得借方金额总和
    public double GetCost_All(String sqlField_Summation, String itemNo, String departmentSpNo, String productSpNo) {
        Session session1 = null;
        Transaction tx1 = null;
        try {
            session1 = getSession();
            tx1 = session1.beginTransaction();
            double result = 0;
            if (productSpNo.equals("null")) {
                Query query3 = session1.createQuery("select sum(" + sqlField_Summation + ") from Lshsje where itemNo = :a and spNo1 = :b and spNo2 is null");//这里有个问题，spno有两个spno1和spno2，目前不知道如何比较
                query3.setString("a", itemNo);
                query3.setString("b", departmentSpNo);
                result = (double) query3.uniqueResult();
            } else {
                Query query3 = session1.createQuery("select sum(" + sqlField_Summation + ") from Lshsje where itemNo = :a and spNo1 = :b and spNo2 = :c");//这里有个问题，spno有两个spno1和spno2，目前不知道如何比较
                query3.setString("a", itemNo);
                query3.setString("b", departmentSpNo);
                query3.setString("c", productSpNo);
                result = (double) query3.uniqueResult();
            }

            System.out.println(result);//测试
            tx1.commit();
            close(session1);
            return result;
        } catch (Exception e) {
            tx1.rollback();
            close(session1);
            e.printStackTrace();
            return 0;
        }
    }

    //获取项目的名称列表
    public List getItemNam(String selectCompany, String selectProduct) {
        List<String> itemNoList = getItemNo(selectCompany, selectProduct);
        List<String> DefineditemNoList = getDefinedItemNo();
        List<String> itemNameList = new ArrayList<>();//存放销售方式名称
        String itemName;

        Session session = null;
        Transaction tx = null;
        session = getSession();
        tx = session.beginTransaction();

        //从科目字典里获取科目名称
        if (itemNoList != null) {
            for (String itemId : itemNoList) {
                Query query = session.createQuery("select itemName from Lskmzd where itemNo = :a");
                query.setString("a", itemId);
                itemName = (String) query.uniqueResult();
                itemNameList.add(itemName);
            }
        }

        //从CBQTSZ表中获取自定义字段的名称
        if (DefineditemNoList != null) {
            for (String definedItemId : DefineditemNoList) {
                Query query = session.createQuery("select itemName from Cbqtsz where itemNo = :a");
                query.setString("a", definedItemId);
                itemName = (String) query.uniqueResult();
                itemNameList.add(itemName);
            }
        }

        itemNameList.add("商品量");//这是要单独添加的，注意这个只有上面的表里有，方气里面没有
        if (itemNameList.size() > 0) {
            tx.commit();
            close(session);
            return itemNameList;
        } else {
            tx.commit();
            close(session);
            return null;
        }
    }
}
