/**
 * Created by ChenZH on 2018/7/2.
 */
var level_ = 1;//级数
var upper_No_ = '';//编号,在点击下级的时候获得，用于返回上一级
var upper_name_ = [];
var sub_stru_ = [];//存储科目结构
var current_date_ = '';
var query_date_ = '';
var range_ = [];
var data_ = [];
// var data_ = [
//     {"itemNo":"00010000000000000000000","lskmzd":{"item":"1","itemNo":"00010000000000000000000","itemName":"国际长途","ele":"01","departmentId":null,"accType":"J","spType":"","exchang":0,"supAcc1":"","supAcc2":"","budgetMoney":null,"journal":0,"finLevel":1,"balance":null,"debitMoney":null,"creditMoney":null,"supMoney":null,"debitMoneySup":null,"creditMoneySup":null,"debitMoneyAcm":null,"creditMoneyAcm":null,"debitMoney01":null,"creditMoney01":null,"balance01":111.0,"debitMoney02":null,"creditMoney02":null,"balance02":null,"debitMoney03":null,"creditMoney03":null,"balance03":null,"debitMoney04":null,"creditMoney04":null,"balance04":null,"debitMoney05":null,"creditMoney05":null,"balance05":null,"debitMoney06":300.0,"creditMoney06":0.0,"balance06":null,"debitMoney07":null,"creditMoney07":null,"balance07":null,"debitMoney08":null,"creditMoney08":null,"balance08":null,"debitMoney09":null,"creditMoney09":null,"balance09":null,"debitMoney10":null,"creditMoney10":null,"balance10":null,"debitMoney11":null,"creditMoney11":null,"balance11":null,"debitMoney12":null,"creditMoney12":null,"balance12":null,"fKzjm":null,"fKjqj":null,"fDz":null,"fDzok":null,"fPzsy":null,"fDykm":null,"fPjmc":null,"fSjly":null,"fYssx":null,"fYsxx":null,"fDf13":null,"fJf13":null,"fYe13":null},"lskmsl":{"kmslAutoId":1,"itemNo":"00020000000000000000000","head1":"","head2":null,"head3":null,"head4":null,"head5":null,"head6":null,"budQty":null,"leftQty":null,"unitPrice":null,"debitQty":null,"creditQty":null,"supQty":null,"debitQtySup":null,"creditQtySup":null,"debitQtyAcm":null,"creditQtyAcm":null,"debitQty01":null,"creditQty01":null,"leftQty01":null,"debitQty02":null,"creditQty02":null,"leftQty02":null,"debitQty03":null,"creditQty03":null,"leftQty03":null,"debitQty04":null,"creditQty04":null,"leftQty04":null,"debitQty05":null,"creditQty05":null,"leftQty05":null,"debitQty06":null,"creditQty06":null,"leftQty06":null,"debitQty07":null,"creditQty07":null,"leftQty07":null,"debitQty08":null,"creditQty08":null,"leftQty08":null,"debitQty09":null,"creditQty09":null,"leftQty09":null,"debitQty10":null,"creditQty10":null,"leftQty10":null,"debitQty11":null,"creditQty11":null,"leftQty11":null,"debitQty12":null,"creditQty12":null,"leftQty12":null,"fYsxl":null,"fDs13":null,"fJs13":null,"fSl13":null}},
//     {"itemNo":"00020000000000000000000","lskmzd":{"item":"1","itemNo":"00020000000000000000000","itemName":"理财产品","ele":"01","departmentId":null,"accType":"Y","spType":"","exchang":1,"supAcc1":"01","supAcc2":"03","budgetMoney":null,"journal":1,"finLevel":0,"balance":null,"debitMoney":null,"creditMoney":null,"supMoney":null,"debitMoneySup":null,"creditMoneySup":null,"debitMoneyAcm":null,"creditMoneyAcm":null,"debitMoney01":null,"creditMoney01":null,"balance01":null,"debitMoney02":null,"creditMoney02":null,"balance02":null,"debitMoney03":null,"creditMoney03":null,"balance03":null,"debitMoney04":null,"creditMoney04":null,"balance04":null,"debitMoney05":null,"creditMoney05":null,"balance05":null,"debitMoney06":0.0,"creditMoney06":0.0,"balance06":null,"debitMoney07":null,"creditMoney07":null,"balance07":null,"debitMoney08":null,"creditMoney08":null,"balance08":null,"debitMoney09":null,"creditMoney09":null,"balance09":null,"debitMoney10":null,"creditMoney10":null,"balance10":null,"debitMoney11":null,"creditMoney11":null,"balance11":null,"debitMoney12":null,"creditMoney12":null,"balance12":null,"fKzjm":null,"fKjqj":null,"fDz":null,"fDzok":null,"fPzsy":null,"fDykm":null,"fPjmc":null,"fSjly":null,"fYssx":null,"fYsxx":null,"fDf13":null,"fJf13":null,"fYe13":null},"lskmsl":{"kmslAutoId":1,"itemNo":"00020000000000000000000","head1":"","head2":null,"head3":null,"head4":null,"head5":null,"head6":null,"budQty":null,"leftQty":null,"unitPrice":null,"debitQty":null,"creditQty":null,"supQty":null,"debitQtySup":null,"creditQtySup":null,"debitQtyAcm":null,"creditQtyAcm":null,"debitQty01":null,"creditQty01":null,"leftQty01":null,"debitQty02":null,"creditQty02":null,"leftQty02":null,"debitQty03":null,"creditQty03":null,"leftQty03":null,"debitQty04":null,"creditQty04":null,"leftQty04":null,"debitQty05":null,"creditQty05":null,"leftQty05":null,"debitQty06":null,"creditQty06":null,"leftQty06":null,"debitQty07":null,"creditQty07":null,"leftQty07":null,"debitQty08":null,"creditQty08":null,"leftQty08":null,"debitQty09":null,"creditQty09":null,"leftQty09":null,"debitQty10":null,"creditQty10":null,"leftQty10":null,"debitQty11":null,"creditQty11":null,"leftQty11":null,"debitQty12":null,"creditQty12":null,"leftQty12":null,"fYsxl":null,"fDs13":null,"fJs13":null,"fSl13":null}},
//     {"itemNo":"00090000000000000000000","lskmzd":{"item":"1","itemNo":"00090000000000000000000","itemName":"测试","ele":"01","departmentId":null,"accType":"J","spType":"","exchang":null,"supAcc1":null,"supAcc2":null,"budgetMoney":null,"journal":null,"finLevel":1,"balance":null,"debitMoney":null,"creditMoney":null,"supMoney":1.2121212E7,"debitMoneySup":1.2121212E7,"creditMoneySup":1.2121212E7,"debitMoneyAcm":null,"creditMoneyAcm":null,"debitMoney01":null,"creditMoney01":null,"balance01":null,"debitMoney02":null,"creditMoney02":null,"balance02":null,"debitMoney03":null,"creditMoney03":null,"balance03":null,"debitMoney04":null,"creditMoney04":null,"balance04":null,"debitMoney05":null,"creditMoney05":null,"balance05":null,"debitMoney06":null,"creditMoney06":null,"balance06":null,"debitMoney07":null,"creditMoney07":null,"balance07":null,"debitMoney08":null,"creditMoney08":null,"balance08":null,"debitMoney09":null,"creditMoney09":null,"balance09":null,"debitMoney10":null,"creditMoney10":null,"balance10":null,"debitMoney11":null,"creditMoney11":null,"balance11":null,"debitMoney12":null,"creditMoney12":null,"balance12":null,"fKzjm":null,"fKjqj":null,"fDz":null,"fDzok":null,"fPzsy":null,"fDykm":null,"fPjmc":null,"fSjly":null,"fYssx":null,"fYsxx":null,"fDf13":null,"fJf13":null,"fYe13":null},"lskmsl":{"kmslAutoId":1,"itemNo":"00020000000000000000000","head1":"","head2":null,"head3":null,"head4":null,"head5":null,"head6":null,"budQty":null,"leftQty":null,"unitPrice":null,"debitQty":null,"creditQty":null,"supQty":null,"debitQtySup":null,"creditQtySup":null,"debitQtyAcm":null,"creditQtyAcm":null,"debitQty01":null,"creditQty01":null,"leftQty01":null,"debitQty02":null,"creditQty02":null,"leftQty02":null,"debitQty03":null,"creditQty03":null,"leftQty03":null,"debitQty04":null,"creditQty04":null,"leftQty04":null,"debitQty05":null,"creditQty05":null,"leftQty05":null,"debitQty06":null,"creditQty06":null,"leftQty06":null,"debitQty07":null,"creditQty07":null,"leftQty07":null,"debitQty08":null,"creditQty08":null,"leftQty08":null,"debitQty09":null,"creditQty09":null,"leftQty09":null,"debitQty10":null,"creditQty10":null,"leftQty10":null,"debitQty11":null,"creditQty11":null,"leftQty11":null,"debitQty12":null,"creditQty12":null,"leftQty12":null,"fYsxl":null,"fDs13":null,"fJs13":null,"fSl13":null}},
//     {"itemNo":"10020000000000000000000","lskmzd":{"item":"1","itemNo":"10020000000000000000000","itemName":"买黄瓜","ele":"01","departmentId":null,"accType":"Y","spType":"","exchang":1,"supAcc1":"01","supAcc2":"03","budgetMoney":null,"journal":1,"finLevel":1,"balance":null,"debitMoney":null,"creditMoney":null,"supMoney":null,"debitMoneySup":null,"creditMoneySup":null,"debitMoneyAcm":null,"creditMoneyAcm":null,"debitMoney01":null,"creditMoney01":null,"balance01":null,"debitMoney02":null,"creditMoney02":null,"balance02":null,"debitMoney03":null,"creditMoney03":null,"balance03":null,"debitMoney04":null,"creditMoney04":null,"balance04":null,"debitMoney05":null,"creditMoney05":null,"balance05":null,"debitMoney06":0.0,"creditMoney06":0.0,"balance06":null,"debitMoney07":null,"creditMoney07":null,"balance07":null,"debitMoney08":null,"creditMoney08":null,"balance08":null,"debitMoney09":null,"creditMoney09":null,"balance09":null,"debitMoney10":null,"creditMoney10":null,"balance10":null,"debitMoney11":null,"creditMoney11":null,"balance11":null,"debitMoney12":null,"creditMoney12":null,"balance12":null,"fKzjm":null,"fKjqj":null,"fDz":null,"fDzok":null,"fPzsy":null,"fDykm":null,"fPjmc":null,"fSjly":null,"fYssx":null,"fYsxx":null,"fDf13":null,"fJf13":null,"fYe13":null},"lskmsl":{"kmslAutoId":1,"itemNo":"00020000000000000000000","head1":"","head2":null,"head3":null,"head4":null,"head5":null,"head6":null,"budQty":null,"leftQty":null,"unitPrice":null,"debitQty":null,"creditQty":null,"supQty":null,"debitQtySup":null,"creditQtySup":null,"debitQtyAcm":null,"creditQtyAcm":null,"debitQty01":null,"creditQty01":null,"leftQty01":null,"debitQty02":null,"creditQty02":null,"leftQty02":null,"debitQty03":null,"creditQty03":null,"leftQty03":null,"debitQty04":null,"creditQty04":null,"leftQty04":null,"debitQty05":null,"creditQty05":null,"leftQty05":null,"debitQty06":null,"creditQty06":null,"leftQty06":null,"debitQty07":null,"creditQty07":null,"leftQty07":null,"debitQty08":null,"creditQty08":null,"leftQty08":null,"debitQty09":null,"creditQty09":null,"leftQty09":null,"debitQty10":null,"creditQty10":null,"leftQty10":null,"debitQty11":null,"creditQty11":null,"leftQty11":null,"debitQty12":null,"creditQty12":null,"leftQty12":null,"fYsxl":null,"fDs13":null,"fJs13":null,"fSl13":null}},
//     {"itemNo":"11110000000000000000000","lskmzd":{"item":"1","itemNo":"11110000000000000000000","itemName":"spaceX","ele":"01","departmentId":null,"accType":"Y","spType":"","exchang":null,"supAcc1":"","supAcc2":"","budgetMoney":null,"journal":0,"finLevel":1,"balance":null,"debitMoney":null,"creditMoney":null,"supMoney":null,"debitMoneySup":null,"creditMoneySup":null,"debitMoneyAcm":null,"creditMoneyAcm":null,"debitMoney01":null,"creditMoney01":null,"balance01":null,"debitMoney02":null,"creditMoney02":null,"balance02":null,"debitMoney03":null,"creditMoney03":null,"balance03":null,"debitMoney04":null,"creditMoney04":null,"balance04":null,"debitMoney05":null,"creditMoney05":null,"balance05":null,"debitMoney06":30.0,"creditMoney06":0.0,"balance06":null,"debitMoney07":null,"creditMoney07":null,"balance07":null,"debitMoney08":null,"creditMoney08":null,"balance08":null,"debitMoney09":null,"creditMoney09":null,"balance09":null,"debitMoney10":null,"creditMoney10":null,"balance10":null,"debitMoney11":null,"creditMoney11":null,"balance11":null,"debitMoney12":null,"creditMoney12":null,"balance12":null,"fKzjm":null,"fKjqj":null,"fDz":null,"fDzok":null,"fPzsy":null,"fDykm":null,"fPjmc":null,"fSjly":null,"fYssx":null,"fYsxx":null,"fDf13":null,"fJf13":null,"fYe13":null},"lskmsl":{"kmslAutoId":1,"itemNo":"00020000000000000000000","head1":"","head2":null,"head3":null,"head4":null,"head5":null,"head6":null,"budQty":null,"leftQty":null,"unitPrice":null,"debitQty":null,"creditQty":null,"supQty":null,"debitQtySup":null,"creditQtySup":null,"debitQtyAcm":null,"creditQtyAcm":null,"debitQty01":null,"creditQty01":null,"leftQty01":null,"debitQty02":null,"creditQty02":null,"leftQty02":null,"debitQty03":null,"creditQty03":null,"leftQty03":null,"debitQty04":null,"creditQty04":null,"leftQty04":null,"debitQty05":null,"creditQty05":null,"leftQty05":null,"debitQty06":null,"creditQty06":null,"leftQty06":null,"debitQty07":null,"creditQty07":null,"leftQty07":null,"debitQty08":null,"creditQty08":null,"leftQty08":null,"debitQty09":null,"creditQty09":null,"leftQty09":null,"debitQty10":null,"creditQty10":null,"leftQty10":null,"debitQty11":null,"creditQty11":null,"leftQty11":null,"debitQty12":null,"creditQty12":null,"leftQty12":null,"fYsxl":null,"fDs13":null,"fJs13":null,"fSl13":null}},
//     {"itemNo":"11220000000000000000000","lskmzd":{"item":"1","itemNo":"11220000000000000000000","itemName":"NASA","ele":"02","departmentId":null,"accType":"Y","spType":"","exchang":null,"supAcc1":"","supAcc2":"","budgetMoney":null,"journal":0,"finLevel":1,"balance":null,"debitMoney":null,"creditMoney":null,"supMoney":1.0,"debitMoneySup":1.0,"creditMoneySup":1.0,"debitMoneyAcm":null,"creditMoneyAcm":null,"debitMoney01":null,"creditMoney01":null,"balance01":1.0,"debitMoney02":null,"creditMoney02":null,"balance02":null,"debitMoney03":null,"creditMoney03":null,"balance03":null,"debitMoney04":null,"creditMoney04":null,"balance04":null,"debitMoney05":null,"creditMoney05":null,"balance05":null,"debitMoney06":0.0,"creditMoney06":0.0,"balance06":null,"debitMoney07":null,"creditMoney07":null,"balance07":null,"debitMoney08":null,"creditMoney08":null,"balance08":null,"debitMoney09":null,"creditMoney09":null,"balance09":null,"debitMoney10":null,"creditMoney10":null,"balance10":null,"debitMoney11":null,"creditMoney11":null,"balance11":null,"debitMoney12":null,"creditMoney12":null,"balance12":null,"fKzjm":null,"fKjqj":null,"fDz":null,"fDzok":null,"fPzsy":null,"fDykm":null,"fPjmc":null,"fSjly":null,"fYssx":null,"fYsxx":null,"fDf13":null,"fJf13":null,"fYe13":null},"lskmsl":{"kmslAutoId":1,"itemNo":"00020000000000000000000","head1":"","head2":null,"head3":null,"head4":null,"head5":null,"head6":null,"budQty":null,"leftQty":null,"unitPrice":null,"debitQty":null,"creditQty":null,"supQty":null,"debitQtySup":null,"creditQtySup":null,"debitQtyAcm":null,"creditQtyAcm":null,"debitQty01":null,"creditQty01":null,"leftQty01":null,"debitQty02":null,"creditQty02":null,"leftQty02":null,"debitQty03":null,"creditQty03":null,"leftQty03":null,"debitQty04":null,"creditQty04":null,"leftQty04":null,"debitQty05":null,"creditQty05":null,"leftQty05":null,"debitQty06":null,"creditQty06":null,"leftQty06":null,"debitQty07":null,"creditQty07":null,"leftQty07":null,"debitQty08":null,"creditQty08":null,"leftQty08":null,"debitQty09":null,"creditQty09":null,"leftQty09":null,"debitQty10":null,"creditQty10":null,"leftQty10":null,"debitQty11":null,"creditQty11":null,"leftQty11":null,"debitQty12":null,"creditQty12":null,"leftQty12":null,"fYsxl":null,"fDs13":null,"fJs13":null,"fSl13":null}},
//     {"itemNo":"00010000000000000000000","lskmzd":{"item":"1","itemNo":"00010000000000000000000","itemName":"国际长途","ele":"01","departmentId":null,"accType":"J","spType":"","exchang":0,"supAcc1":"","supAcc2":"","budgetMoney":null,"journal":0,"finLevel":1,"balance":null,"debitMoney":null,"creditMoney":null,"supMoney":null,"debitMoneySup":null,"creditMoneySup":null,"debitMoneyAcm":null,"creditMoneyAcm":null,"debitMoney01":null,"creditMoney01":null,"balance01":111.0,"debitMoney02":null,"creditMoney02":null,"balance02":null,"debitMoney03":null,"creditMoney03":null,"balance03":null,"debitMoney04":null,"creditMoney04":null,"balance04":null,"debitMoney05":null,"creditMoney05":null,"balance05":null,"debitMoney06":300.0,"creditMoney06":0.0,"balance06":null,"debitMoney07":null,"creditMoney07":null,"balance07":null,"debitMoney08":null,"creditMoney08":null,"balance08":null,"debitMoney09":null,"creditMoney09":null,"balance09":null,"debitMoney10":null,"creditMoney10":null,"balance10":null,"debitMoney11":null,"creditMoney11":null,"balance11":null,"debitMoney12":null,"creditMoney12":null,"balance12":null,"fKzjm":null,"fKjqj":null,"fDz":null,"fDzok":null,"fPzsy":null,"fDykm":null,"fPjmc":null,"fSjly":null,"fYssx":null,"fYsxx":null,"fDf13":null,"fJf13":null,"fYe13":null},"lskmsl":{"kmslAutoId":1,"itemNo":"00020000000000000000000","head1":"","head2":null,"head3":null,"head4":null,"head5":null,"head6":null,"budQty":null,"leftQty":null,"unitPrice":null,"debitQty":null,"creditQty":null,"supQty":null,"debitQtySup":null,"creditQtySup":null,"debitQtyAcm":null,"creditQtyAcm":null,"debitQty01":null,"creditQty01":null,"leftQty01":null,"debitQty02":null,"creditQty02":null,"leftQty02":null,"debitQty03":null,"creditQty03":null,"leftQty03":null,"debitQty04":null,"creditQty04":null,"leftQty04":null,"debitQty05":null,"creditQty05":null,"leftQty05":null,"debitQty06":null,"creditQty06":null,"leftQty06":null,"debitQty07":null,"creditQty07":null,"leftQty07":null,"debitQty08":null,"creditQty08":null,"leftQty08":null,"debitQty09":null,"creditQty09":null,"leftQty09":null,"debitQty10":null,"creditQty10":null,"leftQty10":null,"debitQty11":null,"creditQty11":null,"leftQty11":null,"debitQty12":null,"creditQty12":null,"leftQty12":null,"fYsxl":null,"fDs13":null,"fJs13":null,"fSl13":null}},
//     {"itemNo":"00020000000000000000000","lskmzd":{"item":"1","itemNo":"00020000000000000000000","itemName":"理财产品","ele":"01","departmentId":null,"accType":"Y","spType":"","exchang":1,"supAcc1":"01","supAcc2":"03","budgetMoney":null,"journal":1,"finLevel":0,"balance":null,"debitMoney":null,"creditMoney":null,"supMoney":null,"debitMoneySup":null,"creditMoneySup":null,"debitMoneyAcm":null,"creditMoneyAcm":null,"debitMoney01":null,"creditMoney01":null,"balance01":null,"debitMoney02":null,"creditMoney02":null,"balance02":null,"debitMoney03":null,"creditMoney03":null,"balance03":null,"debitMoney04":null,"creditMoney04":null,"balance04":null,"debitMoney05":null,"creditMoney05":null,"balance05":null,"debitMoney06":0.0,"creditMoney06":0.0,"balance06":null,"debitMoney07":null,"creditMoney07":null,"balance07":null,"debitMoney08":null,"creditMoney08":null,"balance08":null,"debitMoney09":null,"creditMoney09":null,"balance09":null,"debitMoney10":null,"creditMoney10":null,"balance10":null,"debitMoney11":null,"creditMoney11":null,"balance11":null,"debitMoney12":null,"creditMoney12":null,"balance12":null,"fKzjm":null,"fKjqj":null,"fDz":null,"fDzok":null,"fPzsy":null,"fDykm":null,"fPjmc":null,"fSjly":null,"fYssx":null,"fYsxx":null,"fDf13":null,"fJf13":null,"fYe13":null},"lskmsl":{"kmslAutoId":1,"itemNo":"00020000000000000000000","head1":"","head2":null,"head3":null,"head4":null,"head5":null,"head6":null,"budQty":null,"leftQty":null,"unitPrice":null,"debitQty":null,"creditQty":null,"supQty":null,"debitQtySup":null,"creditQtySup":null,"debitQtyAcm":null,"creditQtyAcm":null,"debitQty01":null,"creditQty01":null,"leftQty01":null,"debitQty02":null,"creditQty02":null,"leftQty02":null,"debitQty03":null,"creditQty03":null,"leftQty03":null,"debitQty04":null,"creditQty04":null,"leftQty04":null,"debitQty05":null,"creditQty05":null,"leftQty05":null,"debitQty06":null,"creditQty06":null,"leftQty06":null,"debitQty07":null,"creditQty07":null,"leftQty07":null,"debitQty08":null,"creditQty08":null,"leftQty08":null,"debitQty09":null,"creditQty09":null,"leftQty09":null,"debitQty10":null,"creditQty10":null,"leftQty10":null,"debitQty11":null,"creditQty11":null,"leftQty11":null,"debitQty12":null,"creditQty12":null,"leftQty12":null,"fYsxl":null,"fDs13":null,"fJs13":null,"fSl13":null}},
//     {"itemNo":"00090000000000000000000","lskmzd":{"item":"1","itemNo":"00090000000000000000000","itemName":"测试","ele":"01","departmentId":null,"accType":"J","spType":"","exchang":null,"supAcc1":null,"supAcc2":null,"budgetMoney":null,"journal":null,"finLevel":1,"balance":null,"debitMoney":null,"creditMoney":null,"supMoney":1.2121212E7,"debitMoneySup":1.2121212E7,"creditMoneySup":1.2121212E7,"debitMoneyAcm":null,"creditMoneyAcm":null,"debitMoney01":null,"creditMoney01":null,"balance01":null,"debitMoney02":null,"creditMoney02":null,"balance02":null,"debitMoney03":null,"creditMoney03":null,"balance03":null,"debitMoney04":null,"creditMoney04":null,"balance04":null,"debitMoney05":null,"creditMoney05":null,"balance05":null,"debitMoney06":null,"creditMoney06":null,"balance06":null,"debitMoney07":null,"creditMoney07":null,"balance07":null,"debitMoney08":null,"creditMoney08":null,"balance08":null,"debitMoney09":null,"creditMoney09":null,"balance09":null,"debitMoney10":null,"creditMoney10":null,"balance10":null,"debitMoney11":null,"creditMoney11":null,"balance11":null,"debitMoney12":null,"creditMoney12":null,"balance12":null,"fKzjm":null,"fKjqj":null,"fDz":null,"fDzok":null,"fPzsy":null,"fDykm":null,"fPjmc":null,"fSjly":null,"fYssx":null,"fYsxx":null,"fDf13":null,"fJf13":null,"fYe13":null},"lskmsl":{"kmslAutoId":1,"itemNo":"00020000000000000000000","head1":"","head2":null,"head3":null,"head4":null,"head5":null,"head6":null,"budQty":null,"leftQty":null,"unitPrice":null,"debitQty":null,"creditQty":null,"supQty":null,"debitQtySup":null,"creditQtySup":null,"debitQtyAcm":null,"creditQtyAcm":null,"debitQty01":null,"creditQty01":null,"leftQty01":null,"debitQty02":null,"creditQty02":null,"leftQty02":null,"debitQty03":null,"creditQty03":null,"leftQty03":null,"debitQty04":null,"creditQty04":null,"leftQty04":null,"debitQty05":null,"creditQty05":null,"leftQty05":null,"debitQty06":null,"creditQty06":null,"leftQty06":null,"debitQty07":null,"creditQty07":null,"leftQty07":null,"debitQty08":null,"creditQty08":null,"leftQty08":null,"debitQty09":null,"creditQty09":null,"leftQty09":null,"debitQty10":null,"creditQty10":null,"leftQty10":null,"debitQty11":null,"creditQty11":null,"leftQty11":null,"debitQty12":null,"creditQty12":null,"leftQty12":null,"fYsxl":null,"fDs13":null,"fJs13":null,"fSl13":null}},
//     {"itemNo":"10020000000000000000000","lskmzd":{"item":"1","itemNo":"10020000000000000000000","itemName":"买黄瓜","ele":"01","departmentId":null,"accType":"Y","spType":"","exchang":1,"supAcc1":"01","supAcc2":"03","budgetMoney":null,"journal":1,"finLevel":1,"balance":null,"debitMoney":null,"creditMoney":null,"supMoney":null,"debitMoneySup":null,"creditMoneySup":null,"debitMoneyAcm":null,"creditMoneyAcm":null,"debitMoney01":null,"creditMoney01":null,"balance01":null,"debitMoney02":null,"creditMoney02":null,"balance02":null,"debitMoney03":null,"creditMoney03":null,"balance03":null,"debitMoney04":null,"creditMoney04":null,"balance04":null,"debitMoney05":null,"creditMoney05":null,"balance05":null,"debitMoney06":0.0,"creditMoney06":0.0,"balance06":null,"debitMoney07":null,"creditMoney07":null,"balance07":null,"debitMoney08":null,"creditMoney08":null,"balance08":null,"debitMoney09":null,"creditMoney09":null,"balance09":null,"debitMoney10":null,"creditMoney10":null,"balance10":null,"debitMoney11":null,"creditMoney11":null,"balance11":null,"debitMoney12":null,"creditMoney12":null,"balance12":null,"fKzjm":null,"fKjqj":null,"fDz":null,"fDzok":null,"fPzsy":null,"fDykm":null,"fPjmc":null,"fSjly":null,"fYssx":null,"fYsxx":null,"fDf13":null,"fJf13":null,"fYe13":null},"lskmsl":{"kmslAutoId":1,"itemNo":"00020000000000000000000","head1":"","head2":null,"head3":null,"head4":null,"head5":null,"head6":null,"budQty":null,"leftQty":null,"unitPrice":null,"debitQty":null,"creditQty":null,"supQty":null,"debitQtySup":null,"creditQtySup":null,"debitQtyAcm":null,"creditQtyAcm":null,"debitQty01":null,"creditQty01":null,"leftQty01":null,"debitQty02":null,"creditQty02":null,"leftQty02":null,"debitQty03":null,"creditQty03":null,"leftQty03":null,"debitQty04":null,"creditQty04":null,"leftQty04":null,"debitQty05":null,"creditQty05":null,"leftQty05":null,"debitQty06":null,"creditQty06":null,"leftQty06":null,"debitQty07":null,"creditQty07":null,"leftQty07":null,"debitQty08":null,"creditQty08":null,"leftQty08":null,"debitQty09":null,"creditQty09":null,"leftQty09":null,"debitQty10":null,"creditQty10":null,"leftQty10":null,"debitQty11":null,"creditQty11":null,"leftQty11":null,"debitQty12":null,"creditQty12":null,"leftQty12":null,"fYsxl":null,"fDs13":null,"fJs13":null,"fSl13":null}},
//     {"itemNo":"11110000000000000000000","lskmzd":{"item":"1","itemNo":"11110000000000000000000","itemName":"spaceX","ele":"01","departmentId":null,"accType":"Y","spType":"","exchang":null,"supAcc1":"","supAcc2":"","budgetMoney":null,"journal":0,"finLevel":1,"balance":null,"debitMoney":null,"creditMoney":null,"supMoney":null,"debitMoneySup":null,"creditMoneySup":null,"debitMoneyAcm":null,"creditMoneyAcm":null,"debitMoney01":null,"creditMoney01":null,"balance01":null,"debitMoney02":null,"creditMoney02":null,"balance02":null,"debitMoney03":null,"creditMoney03":null,"balance03":null,"debitMoney04":null,"creditMoney04":null,"balance04":null,"debitMoney05":null,"creditMoney05":null,"balance05":null,"debitMoney06":30.0,"creditMoney06":0.0,"balance06":null,"debitMoney07":null,"creditMoney07":null,"balance07":null,"debitMoney08":null,"creditMoney08":null,"balance08":null,"debitMoney09":null,"creditMoney09":null,"balance09":null,"debitMoney10":null,"creditMoney10":null,"balance10":null,"debitMoney11":null,"creditMoney11":null,"balance11":null,"debitMoney12":null,"creditMoney12":null,"balance12":null,"fKzjm":null,"fKjqj":null,"fDz":null,"fDzok":null,"fPzsy":null,"fDykm":null,"fPjmc":null,"fSjly":null,"fYssx":null,"fYsxx":null,"fDf13":null,"fJf13":null,"fYe13":null},"lskmsl":{"kmslAutoId":1,"itemNo":"00020000000000000000000","head1":"","head2":null,"head3":null,"head4":null,"head5":null,"head6":null,"budQty":null,"leftQty":null,"unitPrice":null,"debitQty":null,"creditQty":null,"supQty":null,"debitQtySup":null,"creditQtySup":null,"debitQtyAcm":null,"creditQtyAcm":null,"debitQty01":null,"creditQty01":null,"leftQty01":null,"debitQty02":null,"creditQty02":null,"leftQty02":null,"debitQty03":null,"creditQty03":null,"leftQty03":null,"debitQty04":null,"creditQty04":null,"leftQty04":null,"debitQty05":null,"creditQty05":null,"leftQty05":null,"debitQty06":null,"creditQty06":null,"leftQty06":null,"debitQty07":null,"creditQty07":null,"leftQty07":null,"debitQty08":null,"creditQty08":null,"leftQty08":null,"debitQty09":null,"creditQty09":null,"leftQty09":null,"debitQty10":null,"creditQty10":null,"leftQty10":null,"debitQty11":null,"creditQty11":null,"leftQty11":null,"debitQty12":null,"creditQty12":null,"leftQty12":null,"fYsxl":null,"fDs13":null,"fJs13":null,"fSl13":null}},
//     {"itemNo":"11220000000000000000000","lskmzd":{"item":"1","itemNo":"11220000000000000000000","itemName":"NASA","ele":"02","departmentId":null,"accType":"Y","spType":"","exchang":null,"supAcc1":"","supAcc2":"","budgetMoney":null,"journal":0,"finLevel":1,"balance":null,"debitMoney":null,"creditMoney":null,"supMoney":1.0,"debitMoneySup":1.0,"creditMoneySup":1.0,"debitMoneyAcm":null,"creditMoneyAcm":null,"debitMoney01":null,"creditMoney01":null,"balance01":1.0,"debitMoney02":null,"creditMoney02":null,"balance02":null,"debitMoney03":null,"creditMoney03":null,"balance03":null,"debitMoney04":null,"creditMoney04":null,"balance04":null,"debitMoney05":null,"creditMoney05":null,"balance05":null,"debitMoney06":0.0,"creditMoney06":0.0,"balance06":null,"debitMoney07":null,"creditMoney07":null,"balance07":null,"debitMoney08":null,"creditMoney08":null,"balance08":null,"debitMoney09":null,"creditMoney09":null,"balance09":null,"debitMoney10":null,"creditMoney10":null,"balance10":null,"debitMoney11":null,"creditMoney11":null,"balance11":null,"debitMoney12":null,"creditMoney12":null,"balance12":null,"fKzjm":null,"fKjqj":null,"fDz":null,"fDzok":null,"fPzsy":null,"fDykm":null,"fPjmc":null,"fSjly":null,"fYssx":null,"fYsxx":null,"fDf13":null,"fJf13":null,"fYe13":null},"lskmsl":{"kmslAutoId":1,"itemNo":"00020000000000000000000","head1":"","head2":null,"head3":null,"head4":null,"head5":null,"head6":null,"budQty":null,"leftQty":null,"unitPrice":null,"debitQty":null,"creditQty":null,"supQty":null,"debitQtySup":null,"creditQtySup":null,"debitQtyAcm":null,"creditQtyAcm":null,"debitQty01":null,"creditQty01":null,"leftQty01":null,"debitQty02":null,"creditQty02":null,"leftQty02":null,"debitQty03":null,"creditQty03":null,"leftQty03":null,"debitQty04":null,"creditQty04":null,"leftQty04":null,"debitQty05":null,"creditQty05":null,"leftQty05":null,"debitQty06":null,"creditQty06":null,"leftQty06":null,"debitQty07":null,"creditQty07":null,"leftQty07":null,"debitQty08":null,"creditQty08":null,"leftQty08":null,"debitQty09":null,"creditQty09":null,"leftQty09":null,"debitQty10":null,"creditQty10":null,"leftQty10":null,"debitQty11":null,"creditQty11":null,"leftQty11":null,"debitQty12":null,"creditQty12":null,"leftQty12":null,"fYsxl":null,"fDs13":null,"fJs13":null,"fSl13":null}}];

    $(function () {
    dealConfigs();
    showCaption(upper_No_, level_.toString());

    var sql = [];
    var from_to = 'from';
    var actype = 'money';
    var remain = 'one';
    var yearOmonth = 'month';
    var is_show_all = 0;

    $('#main_window').css("display", "none");

    $('#yes_button').click(function () {
        $('#search_window').dialog('close');
        $('#main_window').css("display", "block");

        var query_month = $('#month').val() < 10 ? '0' + $('#month').val() : $('#month').val();
        var query_day = $('#day').val() < 10 ? '0' + $('#day').val() : $('#day').val();
        query_date_ = $('#year').val() + query_month + query_day;

        range_ = [$('#from').val(), $('#to').val()];
        actype = $("input[name='actype']:checked").val();
        remain = $("input[name='remain']:checked").val();

        obj.switchYearNMonth(false);
        setDatagridByformat(actype, remain, yearOmonth);

        queryCaption(upper_No_, level_, range_[0], range_[1], is_show_all, 0);

        $('#s_year').val($('#year').val());
        $('#s_month').val($('#month').val());
        $('#s_day').val($('#day').val());
    });

    obj = {
        selectFrom: function () {
            from_to = 'from';
            showCaption(upper_No_, level_);
            $('#help_window').window('open');
        },
        selectTo: function () {
            from_to = 'to';
            showCaption(upper_No_, level_);
            $('#help_window').window('open');
        },

        //上级点击事件
        upper: function () {
            if (level_ == 1) {
                $.messager.alert('提示', '1级科目没有上级', 'info');
                return;
            }
            level_--;
            $('#level').val(level_);
            upper_No_ = getCatNo(upper_No_, level_ - 1);
            $('#superior_account').val(upper_No_);
            upper_name_.pop();
            $('#superior_account_name').val(upper_name_[upper_name_.length - 1]);
            queryCaption(upper_No_, level_, range_[0], range_[1], is_show_all, 0);
        },
        //下级点击事件
        next: function () {
            var row = $('#main_table').datagrid('getSelected');
            //判断是否选中行
            if (row == null) {
                $.messager.alert('提示', '请选择科目', 'info');
                return;
            }
            level_++;
            $('#level').val(level_);
            upper_No_ = getCatNo(row.itemNo, level_ - 1); //获取选中的分类编号
            $('#superior_account').val(upper_No_);
            upper_name_.push(row.itemName);
            $('#superior_account_name').val(upper_name_[upper_name_.length - 1]);
            queryCaption(upper_No_, level_, range_[0], range_[1], is_show_all, 0);
        },
        switchYearNMonth: function (switch_to_year) {
            var ym = '月';
            if (switch_to_year) {
                yearOmonth = 'year';
                ym = '年';
                $('#year_button').linkbutton('disable');
                $('#month_button').linkbutton('enable');
            }
            else {
                yearOmonth = 'month';
                $('#year_button').linkbutton('enable');
                $('#month_button').linkbutton('disable');
            }
            var table = $('#main_table');
            table.datagrid('getColumnOption', 'titleSupMoney').title = '上' + ym + '结转';
            table.datagrid('getColumnOption', 'supMoney').title = '上' + ym + '结转';
            table.datagrid('getColumnOption', 'titleSupCreditMoney').title = '上' + ym + '借方结转';
            table.datagrid('getColumnOption', 'thisMonth').title = '本' + ym + '发生';
            table.datagrid('getColumnOption', 'thisMonthCredit').title = '本' + ym + '贷方';
            setDatagridByformat(actype, remain, yearOmonth);
            queryCaption(upper_No_, level_, range_[0], range_[1], is_show_all, 0);
        },
        showAll: function () {
            if (is_show_all) {
                $('#show_button').linkbutton({text: '全显'});
                is_show_all = 0;
                queryCaption(upper_No_, level_, range_[0], range_[1], is_show_all, 0);
            }
            else {
                $('#show_button').linkbutton({text: '分级'});
                is_show_all = 1;
                queryCaption(upper_No_, level_, range_[0], range_[1], is_show_all, 0);
            }
        },
        print: function () {
            printPDF();
        },


        //帮助窗口上级点击事件
        uperLevel_click: function () {
            if (level_ == 1) {
                return;
            }
            level_--;
            $('#level').val(level_);
            upper_No_ = getCatNo(upper_No_, level_ - 1);
            $('#superior_account').val(upper_No_);
            showCaption(upper_No_, level_);
        },
        //帮助窗口下级点击事件
        nextLevel_click: function () {
            var row = $('#itemTable').datagrid('getSelected');
            if (row == null) {
                return;
            }//判断是否选中行
            level_++;
            $('#level').val(level_);
            upper_No_ = getCatNo(row.itemNo, level_ - 1); //获取选中的分类编号
            $('#superior_account').val(upper_No_);
            showCaption(upper_No_, level_);
        },
        //选定科目字典
        selectClick: function () {
            var row = $('#itemTable').datagrid("getSelected");
            $('#help_window').window('close');
            if (from_to == 'from') $('#from').val(row.itemNo);
            else $('#to').val(row.itemNo);
        },


        condSearch: function () {
            $('#cond_window').window('open');
        },

        equal: function () {
            $('#middle_box').textbox('setValue', '=');
        },
        greater: function () {
            $('#middle_box').textbox('setValue', '>');
        },
        less: function () {
            $('#middle_box').textbox('setValue', '<');
        },
        contain: function () {
            $('#middle_box').textbox('setValue', '包含');
        },
        notEqual: function () {
            $('#middle_box').textbox('setValue', '!=');
            $('#middle_box').textbox('setText', '≠');
        },
        notGreater: function () {
            $('#middle_box').textbox('setValue', '<=');
            $('#middle_box').textbox('setText', '≤');
        },
        notLess: function () {
            $('#middle_box').textbox('setValue', '>=');
            $('#middle_box').textbox('setText', '≥');
        },
        and: function () {
            sql.push('and');
            obj.showSql();
        },
        or: function () {
            sql.push('or');
            obj.showSql();
        },
        not: function () {
            sql.push('not');
            obj.showSql();
        },
        left: function () {
            sql.push('( ');
            obj.showSql();
        },
        right: function () {
            sql.push(' ) ');
            obj.showSql();
        },
        backspace: function () {
            sql.pop();
            obj.showSql();

        },
        reselect: function () {
            $('#left_box').textbox('clear');
            $('#middle_box').textbox('clear');
            $('#right_box').textbox('clear');
        },
        confirm: function () {
            sql.push($('#left_box').textbox('getValue'));

            if ($('#middle_box').textbox('getValue') == '包含') {
                sql.push('like', '\'%' + $('#right_box').textbox('getValue') + '%\'');
            }
            else {
                sql.push($('#middle_box').textbox('getValue'), '\'' + $('#right_box').textbox('getValue') + '\'');
            }

            $('#left_box').textbox('clear');
            $('#middle_box').textbox('clear');
            $('#right_box').textbox('clear');

            obj.showSql();
        },
        query: function () {
            queryCaption(upper_No_, level_, range_[0], range_[1], is_show_all, sql);
        },

        showSql: function () {
            var total = '';
            for (var i = 0; i < sql.length; i++) {
                var print = sql[i];
                switch (print) {
                    case 'like':
                        print = '包含';
                        break;
                    case 'and':
                        print = '并且';
                        break;
                    case 'or':
                        print = '或';
                        break;
                    case 'not':
                        print = '和';
                        break;
                    case '!=':
                        print = '≠';
                        break;
                    case '>=':
                        print = '≥';
                        break;
                    case '<=':
                        print = '≤';
                        break;

                    case 'm.itemNo':
                        print = '科目编号';
                        break;
                    case 'm.itemName':
                        print = '科目名称';
                        break;
                    case 'm.supMoneyMonth':
                        print = '上月结转金额';
                        break;
                    case 'm.creditMoneyMonth':
                        print = '本月贷方金额';
                        break;
                    case 'm.debitMoneyMonth':
                        print = '本月借方金额';
                        break;
                    case 'm.balance':
                        print = '当前金额余额';
                        break;
                    case 'm.supMoney':
                        print = '上年结转金额';
                        break;
                    case 'm.debitMoneyAcm':
                        print = '本年借方金额';
                        break;
                    case 'm.creditMoneyAcm':
                        print = '本年贷方金额';
                        break;
                    case 'q.supQtyMonth':
                        print = '上月结转数量';
                        break;
                    case 'q.creditQtyMonth':
                        print = '本月贷方数量';
                        break;
                    case 'q.debitQtyMonth':
                        print = '本月借方数量';
                        break;
                    case 'q.leftQty':
                        print = '当前数量余额';
                        break;
                    case 'q.supQty':
                        print = '上年结转数量';
                        break;
                    case 'q.debitQtyAcm':
                        print = '本年借方数量';
                        break;
                    case 'q.creditQtyAcm':
                        print = '本年贷方数量';
                        break;
                }
                total = total + print + " ";
            }
            $('#sql_box').textbox('setValue', total);
        }
    };

    $('#sql_box').textbox({
        width: 415,
        height: 140,
        multiline: true,
        editable: false
    });

    $('#cond_window').window({
        width: 470,
        height: 550,
        padding: 10,
        title: '条件查询',
        icon: 'icon-search',
        collapsible: false,
        minimizable: false,
        closed: true,
        resizable: false,
        maximizable: false,
        modal: true
    });

    $('#cond_list').datalist({
        height: '100%',
        checkbox: false,
        onClickRow: function (index, row) {
            $('#left_box').textbox('setValue', row.value);
            $('#left_box').textbox('setText', row.text);
        }
    });

    $('#main_table').datagrid({
        width: '100%',
        multiSort: false,
        toolbar: '#button_group',
        singleSelect: true,
        showFooter: true,
        style: {},
        //双击进入下一级事件
        onDblClickRow: function (rowIndex, rowData) {
            obj.next();
        },
        columns: [
            [{
                field: 'itemNo',
                title: '科目编号',
                width: '8%',
                rowspan: 2,
                halign: 'center',
                align: 'left',
                formatter: function (value, row, index) {
                    var level = level_;
                    var offset = '';
                    if (is_show_all) {
                        level = row.lskmzd['item'];
                        for (var i = 1; i < level; i++) {
                            offset += '&nbsp;&nbsp;';
                        }
                    }
                    var length = getNumLength(level - 1);
                    var no = value.substr(length, sub_stru_[level - 1]);
                    return offset + no;
                }
            }, {
                field: 'itemName',
                title: '科目名称',
                width: '12%',
                rowspan: 2,
                align: 'center',
                formatter: function (value, row, index) {
                    return row.lskmzd['itemName'];
                }
            }, {
                field: 'bkpDirectionSup',
                title: '借贷方向',
                width: '4%',
                rowspan: 2,
                align: 'center',
                formatter: function (value, row, index) {
                    var m = parseInt(query_date_.substr(4, 2)) - 1;
                    if (m == 0 || yearOmonth == 'year') {
                        if (row.lskmzd['debitMoneySup'] > row.lskmzd['creditMoneySup']) {
                            return '借';
                        }
                        else if (row.lskmzd['debitMoneySup'] < row.lskmzd['creditMoneySup']) {
                            return '贷';
                        }
                        else {
                            return '平';
                        }
                    }
                    else {
                        if (row.lskmzd['debitMoney' + getFullMonth(m)] > row.lskmzd['creditMoney' + getFullMonth(m)]) {
                            return '借';
                        }
                        else if (row.lskmzd['debitMoney' + getFullMonth(m)] < row.lskmzd['creditMoney' + getFullMonth(m)]) {
                            return '贷';
                        }
                        else {
                            return '平';
                        }
                    }
                }
            }, {
                field: 'titleSupMoney',
                title: '上月结转',
                width: '12%',
                colspan: 2,
                align: 'center'
            }, {
                field: 'titleSupCreditMoney',
                title: '上月贷方结转',
                width: '12%',
                colspan: 2,
                align: 'center'
            }, {
                field: 'supMoney',
                title: '上月结转',
                width: '12%',
                rowspan: 2,
                align: 'right',
                halign: 'center',
                hidden: true,
                formatter: function (value, row, index) {
                    var m = parseInt(query_date_.substr(4, 2)) - 1;
                    if (m == 0 || yearOmonth == 'year') value = row.lskmzd['supMoney'];
                    else value = row.lskmzd['balance' + getFullMonth(m)];
                    return thousandFormatter(value);
                }
            }, {
                field: 'thisMonth',
                title: '本月发生',
                width: '12%',
                colspan: 2,
                align: 'center'
            }, {
                field: 'thisMonthCredit',
                title: '本月贷方',
                width: '12%',
                colspan: 2,
                align: 'center'
            }, {
                field: 'bkpDirectionAcm',
                title: '借贷方向',
                width: '4%',
                rowspan: 2,
                align: 'center',
                formatter: function (value, row, index) {
                    if (row.lskmzd['debitMoneyAcm'] > row.lskmzd['creditMoneyAcm']) {
                        return '借';
                    }
                    else if (row.lskmzd['debitMoneyAcm'] < row.lskmzd['creditMoneyAcm']) {
                        return '贷';
                    }
                    else {
                        return '平';
                    }
                }
            }, {
                field: 'titleBalance',
                title: '当前余额',
                colspan: 2,
                width: '12%',
                align: 'center'
            }, {
                field: 'titleCreditBalance',
                title: '当前贷方余额',
                colspan: 2,
                width: '12%',
                align: 'center'
            }, {
                field: 'balance',
                title: '当前余额',
                rowspan: 2,
                width: '12%',
                align: 'right',
                halign: 'center',
                hidden: true,
                formatter: function (value, row, index) {
                    value = row.lskmzd['balance'];
                    return thousandFormatter(value);
                }
            }], [{
                field: 'debitQtySup',
                title: '数量',
                width: '12%',
                align: 'right',
                halign: 'center',
                hidden: true,
                formatter: function (value, row, index) {
                    if (row.lskmsl == null) return null;
                    var m = parseInt(query_date_.substr(4, 2)) - 1;
                    if (remain != 'one') {
                        if (m == 0 || yearOmonth == 'year') value = row.lskmsl['debitQtySup'];
                        else value = row.lskmsl['debitQty' + getFullMonth(m)];
                    }
                    else {
                        if (m == 0 || yearOmonth == 'year') value = row.lskmsl['SupQty'];
                        else value = row.lskmsl['leftQty' + getFullMonth(m)];
                    }
                    return thousandFormatter(value);
                }
            }, {
                field: 'debitMoneySup',
                title: '借方',
                width: '12%',
                align: 'right',
                halign: 'center',
                formatter: function (value, row, index) {
                    var m = parseInt(query_date_.substr(4, 2)) - 1;
                    if (remain != 'one') {
                        if (m == 0 || yearOmonth == 'year') value = row.lskmzd['debitMoneySup'];
                        else value = row.lskmzd['debitMoney' + getFullMonth(m)];
                    }
                    else {
                        if (m == 0 || yearOmonth == 'year') value = row.lskmzd['supMoney'];
                        else value = row.lskmzd['balance' + getFullMonth(m)];
                    }
                    return thousandFormatter(value);
                }
            }, {
                field: 'creditQtySup',
                title: '数量',
                width: '12%',
                align: 'right',
                halign: 'center',
                hidden: true,
                formatter: function (value, row, index) {
                    if (row.lskmsl == null) return null;
                    var m = parseInt(query_date_.substr(4, 2)) - 1;
                    if (m == 0 || yearOmonth == 'year') value = row.lskmsl['creditQtySup'];
                    else value = row.lskmsl['creditQty' + getFullMonth(m)];
                    return thousandFormatter(value);
                }
            }, {
                field: 'creditMoneySup',
                title: '贷方',
                width: '12%',
                align: 'right',
                halign: 'center',
                formatter: function (value, row, index) {
                    var m = parseInt(query_date_.substr(4, 2)) - 1;
                    if (m == 0 || yearOmonth == 'year') value = row.lskmzd['creditMoneySup'];
                    else value = row.lskmzd['creditMoney' + getFullMonth(m)];
                    return thousandFormatter(value);
                }
            }, {
                field: 'debitQty',
                title: '数量',
                width: '12%',
                align: 'right',
                halign: 'center',
                hidden: true,
                formatter: function (value, row, index) {
                    if (row.lskmsl == null) return null;
                    var mm = query_date_.substr(4, 2);
                    if (yearOmonth == 'month') {
                        value = row.lskmsl['debitQty' + mm];
                    }
                    else {
                        value = calYearAcm(row, 'lskmsl', 'debitQty');
                    }
                    return thousandFormatter(value);
                }
            }, {
                field: 'debitMoney',
                title: '借方',
                width: '12%',
                align: 'right',
                halign: 'center',
                formatter: function (value, row, index) {
                    var mm = query_date_.substr(4, 2);
                    if (yearOmonth == 'month') {
                        value = row.lskmzd['debitMoney' + mm];
                    }
                    else {
                        value = calYearAcm(row, 'lskmzd', 'debitMoney');
                    }
                    return thousandFormatter(value);
                }
            }, {
                field: 'creditQty',
                title: '数量',
                width: '12%',
                align: 'right',
                halign: 'center',
                formatter: function (value, row, index) {
                    if (row.lskmsl == null) return null;
                    var mm = query_date_.substr(4, 2);
                    if (yearOmonth == 'month') {
                        value = row.lskmsl['creditQty' + mm];
                    }
                    else {
                        value = calYearAcm(row, 'lskmsl', 'creditQty');
                    }
                    return thousandFormatter(value);
                }
            }, {
                field: 'creditMoney',
                title: '贷方',
                width: '12%',
                align: 'right',
                halign: 'center',
                formatter: function (value, row, index) {
                    var mm = query_date_.substr(4, 2);
                    if (yearOmonth == 'month') {
                        value = row.lskmzd['creditMoney' + mm];
                    }
                    else {
                        value = calYearAcm(row, 'lskmzd', 'creditMoney');
                    }
                    return thousandFormatter(value);
                }
            }, {
                field: 'debitQtyAcm',
                title: '数量',
                width: '12%',
                align: 'right',
                halign: 'center',
                hidden: true,
                formatter: function (value, row, index) {
                    var mm = query_date_.substr(4, 2);
                    if (row.lskmsl == null) value = null;
                    else value = row.lskmsl['leftQty' + mm];
                    return thousandFormatter(value);
                }
            }, {
                field: 'debitMoneyAcm',
                title: '借方',
                width: '12%',
                align: 'right',
                halign: 'center',
                formatter: function (value, row, index) {
                    var mm = query_date_.substr(4, 2);
                    value = row.lskmzd['balance' + mm];
                    return thousandFormatter(value);
                }
            }, {
                field: 'creditQtyAcm',
                title: '数量',
                width: '12%',
                align: 'center',
                hidden: true,
                formatter: function (value, row, index) {
                    // if(row.lskmsl == null) return null;
                    // return row.lskmsl['creditQtyAcm'];
                }
            }, {
                field: 'creditMoneyAcm',
                title: '贷方',
                width: '12%',
                align: 'center',
                formatter: function (value, row, index) {
                    //return row.lskmzd['creditMoneyAcm'];
                }
            }]
        ]
    });

    //帮助窗口数据表格
    $('#itemTable').datagrid({
        width: '100%',
        height: '100%',
        singleSelect: true,
        //双击进入下一级事件
        onDblClickRow: function (rowIndex, rowData) {
            obj.nextLevel_click();
        },
        toolbar: '#tb_helpWin',
        columns: [[
            {
                field: 'itemNo',
                title: '编号',
                width: 120,
                formatter: function (value, row, index) {
                    var length = getNumLength(level_);
                    var no = value.substring(0, length);
                    return no;
                }
            },
            {
                field: 'itemName',
                title: '名称',
                width: 184
            },
            {
                field: 'finLevel',
                title: '明细否',
                width: 80,
                formatter: function (value, row, index) {
                    switch (value) {
                        case 1:
                            return "是";
                        case 0:
                            return "否";
                    }
                }
            },
            {
                field: 'item',
                title: '级数',
                width: 80
            }
        ]]
    });

    //帮助窗口
    $('#help_window').window({
        width: 480,
        height: 500,
        title: '科目编码帮助',
        collapsible: false,
        minimizable: false,
        closed: true,
        resizable: true,
        maximizable: false,
        modal: true
    });

    $('#search_window').window({
        width: 360,
        height: 250,
        padding: 10,
        title: '明细余额查询',
        iconCls: 'icon-search',
        collapsible: false,
        minimizable: false,
        closeable: false,
        resizable: true,
        maximizable: false,
        modal: true
    })

});

function dealConfigs() {
    $.ajax({
        type: 'POST',
        url: getRealPath() + '/FinanceSearch/getConfigsForSearch/',
        contentType: 'application/json',
        success: function (result) {
            if (result.code == 100) {
                //处理科目结构
                var stru = result.extend.sub_stru.split("");
                for (var i = 0; i < stru.length; i++) {
                    sub_stru_[i] = stru[i];
                }
                //处理财务日期
                current_date_ = result.extend.current_date;
                $('#year').numberbox('setValue', parseInt(current_date_.substr(0, 4)));
                $('#month').numberbox('setValue', parseInt(current_date_.substr(4, 2)));
                $('#day').numberbox('setValue', parseInt(current_date_.substr(6, 2)));
            }
        }
    });
}

//在发送前处理sql语句
function dealSql(sql) {
    var new_sql = [];
    var qty_flag = false;
    for (var i = 0; i < sql.length; i++) {
        var cur = sql[i];
        if (cur == 'm.supMoneyMonth' || cur == 'q.supQtyMonth') {
            var mm = getFullMonth(parseInt(query_date_.substr(4, 2)) - 1);
            var tmp = cur.slice(0, -5);
            if (mm == '00') {
                new_sql.push(tmp);
            }
            else {
                new_sql.push(tmp + mm);
            }
        }
        else if (cur == 'm.creditMoneyMonth' || cur == 'm.debitMoneyMonth' || cur == 'q.creditQtyMonth' || cur == 'q.debitQtyMonth') {
            var mm = query_date_.substr(4, 2);
            var tmp = cur.slice(0, -5);
            new_sql.push(tmp + mm);
        }
        else if (cur == 'm.creditMoneyAcm' || cur == 'm.debitMoneyAcm' || cur == 'q.creditQtyAcm' || cur == 'q.debitQtyAcm') {
            new_sql.push(getYearAcmStr(cur.slice(0, -3)));
        }
        else {
            new_sql.push(cur);
        }
        if (!qty_flag && (cur == 'q.supQtyMonth' || cur == 'q.leftQty' || cur == 'q.supQty' || cur == 'q.creditQtyMonth' || cur == 'q.debitQtyMonth' || cur == 'q.debitQtyAcm' || cur == 'q.creditQtyAcm')) {
            new_sql.unshift('m.itemNo', '=', 'q.itemNo', 'and');
            qty_flag = true;
        }
    }
    return new_sql.join(' ');
}

//获取查询本年发生额的sql语句字符串
function getYearAcmStr(str) {
    var mm = query_date_.substr(4, 2);
    var res = '';
    for (var i = 1; i <= parseInt(mm); i++) {
        if (i == 1) {
            res += str + getFullMonth(i);
        }
        else {
            res += '+' + str + getFullMonth(i);
        }
    }
    return res;
}

//计算当前科目本年发生额
function calYearAcm(row, table, str) {
    var mm = query_date_.substr(4, 2);
    var acm = 0;
    for (var i = 1; i <= parseInt(mm); i++) {
        acm += row[table][str + getFullMonth(i)];
    }
    return acm;
}

//初始范围查询
function queryCaption(num, level, from, to, is_show_all, sql) {
    var new_num = "";
    if (num == "") {
        new_num = "0";
    }
    else {
        new_num = num;
    }

    var new_sql = '';
    if (sql == 0) {
        new_sql = '0';
    }
    else {
        new_sql = dealSql(sql);
    }
    var data = {
        "sql": new_sql
    };

    var is_show = is_show_all ? 1 : 0;

    $.ajax({
        type: 'POST',
        url: getRealPath() + '/FinanceSearch/queryCaption/' + new_num + '/' + level + '/' + from + '/' + to + '/' + is_show,
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: function (result) {
            if (result.code == 100) {
                var data = result.extend.lskmzdNLskmslQueryVos;
                $('#main_table').datagrid('loadData', data);
                data_ = data;
                //reloadFooterTotal();
            } else {
                var data = result.extend.errorInfo;
                $.messager.alert('提示', data, 'info');
            }
        }
    });
}

//显示当前级的科目
function showCaption(num, level) {
    var new_num = "";
    if (num == "") {
        new_num = "0";
    }
    else {
        new_num = num;
    }
    $.ajax({
        type: 'POST',
        url: getRealPath() + '/CaptionOfAccount/queryCaptionOfAccountByLevel/' + new_num + '/' + level,
        contentType: 'application/json',
        success: function (result) {
            if (result.code == 100) {
                var data = result.extend.lskmzdList;
                $('#itemTable').datagrid('loadData', data);
            }
        }
    });
}

function getFullMonth(number) {
    return number >= 10 ? number.toString() : '0' + number.toString();
}

//计算当前级别科目编号长度
function getNumLength(level) {
    if (level == 0) {
        return 0;
    }
    var length = 0;
    for (var i = 0; i < level; i++) {
        length += parseInt(sub_stru_[i]);
    }
    return length;
}

//计算上级科目编号
function getCatNo(num, level) {
    var length = getNumLength(level);
    var no = num.substring(0, length);
    return no;
}

//计算补零长度
function getZeroLength(level) {
    var length = 0;
    for (var i = level; i < sub_stru_.length; i++) {
        length += parseInt(sub_stru_[i]);
    }
    return length;
}

function setDatagridByformat(actype, remain, yearOmonth) {
    var table = $('#main_table');

    var ym = '月';
    if (yearOmonth == 'year') {
        ym = '年';
    }

    if (actype == 'money' && remain == 'one') {
        //上月：仅显示上月结转直接标题，二级标题全部隐藏
        table.datagrid('hideColumn', 'titleSupMoney');
        table.datagrid('hideColumn', 'titleSupCreditMoney');
        table.datagrid('showColumn', 'supMoney');
        table.datagrid('getColumnOption', 'supMoney').title = '上' + ym + '结转';
        table.datagrid('showColumn', 'bkpDirectionSup');

        table.datagrid('hideColumn', 'creditMoneySup');
        table.datagrid('hideColumn', 'debitMoneySup');
        table.datagrid('hideColumn', 'creditQtySup');
        table.datagrid('hideColumn', 'debitQtySup');

        //本月：不显示本月贷方，数量相关不显示
        table.datagrid('hideColumn', 'thisMonthCredit');
        table.datagrid('getColumnOption', 'thisMonth').title = '本' + ym + '发生';

        table.datagrid('hideColumn', 'creditQty');
        table.datagrid('hideColumn', 'debitQty');
        table.datagrid('getColumnOption', 'debitMoney').title = '借方';
        table.datagrid('getColumnOption', 'creditMoney').title = '贷方';

        //当前：仅显示当前余额直接标题，二级标题全部隐藏
        table.datagrid('hideColumn', 'titleBalance');
        table.datagrid('hideColumn', 'titleCreditBalance');
        table.datagrid('showColumn', 'balance');
        table.datagrid('getColumnOption', 'balance').title = '当前余额';
        table.datagrid('showColumn', 'bkpDirectionAcm');

        table.datagrid('hideColumn', 'creditMoneyAcm');
        table.datagrid('hideColumn', 'debitMoneyAcm');
        table.datagrid('hideColumn', 'creditQtyAcm');
        table.datagrid('hideColumn', 'debitQtyAcm');
    }

    if (actype == 'money' && remain == 'two') {
        //上月：不显示上月贷方，显示一级标题，数量相关不显示
        table.datagrid('showColumn', 'titleSupMoney');
        table.datagrid('hideColumn', 'titleSupCreditMoney');
        table.datagrid('hideColumn', 'supMoney');
        table.datagrid('getColumnOption', 'titleSupMoney').title = '上' + ym + '结转';
        table.datagrid('hideColumn', 'bkpDirectionSup');

        table.datagrid('showColumn', 'creditMoneySup');
        table.datagrid('showColumn', 'debitMoneySup');
        table.datagrid('hideColumn', 'creditQtySup');
        table.datagrid('hideColumn', 'debitQtySup');

        table.datagrid('getColumnOption', 'debitMoneySup').title = '借方';
        table.datagrid('getColumnOption', 'creditMoneySup').title = '贷方';

        //本月：不显示本月贷方，数量相关不显示
        table.datagrid('hideColumn', 'thisMonthCredit');
        table.datagrid('getColumnOption', 'thisMonth').title = '本' + ym + '发生';

        table.datagrid('hideColumn', 'creditQty');
        table.datagrid('hideColumn', 'debitQty');
        table.datagrid('getColumnOption', 'debitMoney').title = '借方';
        table.datagrid('getColumnOption', 'creditMoney').title = '贷方';

        //当前：不显示当前贷方，显示一级标题，数量相关不显示
        table.datagrid('showColumn', 'titleBalance');
        table.datagrid('hideColumn', 'titleCreditBalance');
        table.datagrid('hideColumn', 'balance');
        table.datagrid('getColumnOption', 'titleBalance').title = '当前余额';
        table.datagrid('hideColumn', 'bkpDirectionAcm');

        table.datagrid('showColumn', 'creditMoneyAcm');
        table.datagrid('showColumn', 'debitMoneyAcm');
        table.datagrid('hideColumn', 'creditQtyAcm');
        table.datagrid('hideColumn', 'debitQtyAcm');

        table.datagrid('getColumnOption', 'debitMoneyAcm').title = '借方';
        table.datagrid('getColumnOption', 'creditMoneyAcm').title = '贷方';
    }

    if (actype == 'qty' && remain == 'one') {
        //上月：显示上月结转一级标题，二级标题改为数量金额
        table.datagrid('showColumn', 'titleSupMoney');
        table.datagrid('hideColumn', 'titleSupCreditMoney');
        table.datagrid('hideColumn', 'supMoney');
        table.datagrid('getColumnOption', 'titleSupMoney').title = '上' + ym + '结转';
        table.datagrid('showColumn', 'bkpDirectionSup');
        //用原借方处debit显示总发生额，贷方不显示
        table.datagrid('hideColumn', 'creditMoneySup');
        table.datagrid('showColumn', 'debitMoneySup');
        table.datagrid('hideColumn', 'creditQtySup');
        table.datagrid('showColumn', 'debitQtySup');
        table.datagrid('getColumnOption', 'debitMoneySup').title = '金额';

        //本月：本月发生改名为本月借方，隐藏直接标题，显示数量相关
        table.datagrid('showColumn', 'thisMonthCredit');
        table.datagrid('getColumnOption', 'thisMonth').title = '本' + ym + '借方';

        table.datagrid('showColumn', 'creditQty');
        table.datagrid('showColumn', 'debitQty');
        table.datagrid('getColumnOption', 'debitMoney').title = '金额';
        table.datagrid('getColumnOption', 'creditMoney').title = '金额';

        //当前：显示当前余额一级标题，二级标题改为数量金额
        table.datagrid('showColumn', 'titleBalance');
        table.datagrid('hideColumn', 'titleCreditBalance');
        table.datagrid('hideColumn', 'balance');
        table.datagrid('getColumnOption', 'titleBalance').title = '当前余额';
        table.datagrid('showColumn', 'bkpDirectionAcm');

        table.datagrid('hideColumn', 'creditMoneyAcm');
        table.datagrid('showColumn', 'debitMoneyAcm');
        table.datagrid('hideColumn', 'creditQtyAcm');
        table.datagrid('showColumn', 'debitQtyAcm');
        table.datagrid('getColumnOption', 'debitMoneyAcm').title = '金额';
    }

    if (actype == 'qty' && remain == 'two') {
        //上月：显示上月结转一级标题，二级标题改为数量金额
        table.datagrid('showColumn', 'titleSupMoney');
        table.datagrid('showColumn', 'titleSupCreditMoney');
        table.datagrid('hideColumn', 'supMoney');
        table.datagrid('getColumnOption', 'titleSupMoney').title = '上' + ym + '借方结转';
        table.datagrid('hideColumn', 'bkpDirectionSup');

        table.datagrid('showColumn', 'creditMoneySup');
        table.datagrid('showColumn', 'debitMoneySup');
        table.datagrid('showColumn', 'creditQtySup');
        table.datagrid('showColumn', 'debitQtySup');
        table.datagrid('getColumnOption', 'debitMoneySup').title = '金额';
        table.datagrid('getColumnOption', 'creditMoneySup').title = '金额';

        //本月：本月发生改名为本月借方，隐藏直接标题，显示数量相关
        table.datagrid('showColumn', 'thisMonthCredit');
        table.datagrid('getColumnOption', 'thisMonth').title = '本' + ym + '借方';

        table.datagrid('showColumn', 'creditQty');
        table.datagrid('showColumn', 'debitQty');
        table.datagrid('getColumnOption', 'debitMoney').title = '金额';
        table.datagrid('getColumnOption', 'creditMoney').title = '金额';

        //当前：显示当前余额一级标题，二级标题改为数量金额
        table.datagrid('showColumn', 'titleBalance');
        table.datagrid('showColumn', 'titleCreditBalance');
        table.datagrid('hideColumn', 'balance');
        table.datagrid('getColumnOption', 'titleBalance').title = '当前借方余额';
        table.datagrid('hideColumn', 'bkpDirectionAcm');

        table.datagrid('showColumn', 'creditMoneyAcm');
        table.datagrid('showColumn', 'debitMoneyAcm');
        table.datagrid('showColumn', 'creditQtyAcm');
        table.datagrid('showColumn', 'debitQtyAcm');
        table.datagrid('getColumnOption', 'creditMoneyAcm').title = '金额';
        table.datagrid('getColumnOption', 'debitMoneyAcm').title = '金额';
    }
    table.datagrid();
}

//获取路径
// function getRealPath(){
//
//     var localObj = window.location;
//     var contextPath = localObj.pathname.split("/")[1];
//     var basePath = localObj.protocol + "//" + localObj.host + "/"+ contextPath;
//
//     return basePath ;
// }