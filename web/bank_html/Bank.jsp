<%--
  Created by IntelliJ IDEA.
  User: LI
  Date: 2018/12/24 0024
  Time: 15:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>银行存储系统</title>
    <link rel="stylesheet" href="../bootstrap-4.1.3-dist/css/bootstrap.min.css">
    <script src="https://cdn.staticfile.org/jquery/3.2.1/jquery.min.js"></script>
    <script src="../bootstrap-4.1.3-dist/js/bootstrap.min.js"></script>
</head>
<script type="text/javascript">
    var user = location.search.replace("?", "");
    var balance;

    function get_balance() {
        $.ajax({
            type: "POST",
            asynch: "false",
            data: {
                type: '获取余额',
                user: user
            },
            url: "${pageContext.request.contextPath}/Bank",
            dataType: 'json',
            success: function (json) {
                //alert(json.起存金额);
                balance = json.账户余额;
                $('#balance').text('您的余额为：' + balance);
                $('#type').text('账户类型：'+json.账户类型);
                $('#rate').text('活期利率：'+json.活期利率);
            }
        });
    }

    function get_Class() {
        $.ajax({
            type: "POST",
            asynch: "false",
            data: {
                user: user,
                type: '定期存款产品'
            },
            url: "${pageContext.request.contextPath}/Bank",
            dataType: 'json',
            success: function (json) {
                $(json).each(function () {
                    $("#time_deposit").append(
                        '<tr>\n' +
                        '<td>' + this.序号 + '</td> \n' +
                        '<td>' + this.产品名称 + '</td> \n' +
                        '<td>' + this.币种 + '</td> \n' +
                        '<td>' + this.挂牌利率 + '</td> \n' +
                        '<td>' + this.起存金额 + '</td> \n' +
                        '<td><button class="btn btn-outline-primary" data-toggle="modal" data-id="' + this.序号 + '" data-target="#Deposit_in">存入</button></td> \n' +
                        '<tr>\n '
                    )
                })
            }
        });
    }

    function Withdraw_money(no) {
        var money = $("#Withdraw_money_amount").val();
        $.ajax({
            type: "POST",
            asynch: "false",
            data: {
                user: user,
                type: '定期存款取款',
                no:no,
                money:money
            },
            url: "${pageContext.request.contextPath}/Bank",
            dataType: 'json',
            success: function (msg) {
                if (2===msg) alert("取款成功");
                else alert("定期余额不足");
                $('#Withdraw_money_Close').click();
                location.reload();
            }
        });
    }


    function Deposit_in(no) {
        var money = $("#Deposit_in_money").val();
        if (money <= 50) alert("金额必须大于50元");
        else if (balance * 1 < money * 1) alert('账户余额不足');
        else {
            $.ajax({
                type: "POST",
                asynch: "false",
                data: {
                    type: '定期存款',
                    No: no,
                    money: money,
                    user: user
                },
                url: "${pageContext.request.contextPath}/Bank",
                dataType: 'json'
            });
            alert('存入成功');
            $('#Deposit_in_Close').click();
            location.reload();
        }
    }

    function get_time_deposit() {
        $.ajax({
            type: "POST",
            asynch: "false",
            data: {
                user: user,
                type: '获取定期存款表'
            },
            url: "${pageContext.request.contextPath}/Bank",
            dataType: 'json',
            success: function (json) {
                $('#time_deposit_Withdraw_table').css('display', 'block');
                $(json).each(function () {
                    $("#time_deposit_Withdraw").append(
                        '<tr>\n' +
                        '<td>' + this.凭证号 + '</td> \n' +
                        '<td>' + this.存储金额 + '</td> \n' +
                        '<td>' + this.产品名称 + '</td> \n' +
                        '<td>' + this.起存日期 + '</td> \n' +
                        '<td>' + this.到期日期 + '</td> \n' +
                        '<td><button class="btn btn-outline-primary" data-toggle="modal" data-id="' + this.凭证号 + '" data-target="#Withdraw_money">取出</button></td> \n' +
                        '<tr>\n '
                    )
                })
            }
        });
    }

    function Deposit(){
        var money = $('#Demand_deposit_money').val();
        $.ajax({
            type: "POST",
            asynch: "false",
            data: {
                type: '活期存款',
                money: money,
                user: user
            },
            url: "${pageContext.request.contextPath}/Bank",
            dataType: 'json'
        });
        alert('存入成功');
        $('#Demand_deposit_Close').click();
        location.reload();
    }

    function Withdraw(){ //活期取款
        var money = $('#Demand_withdrawal_money').val();
        $.ajax({
            type: "POST",
            asynch: "false",
            data: {
                type: '活期取款',
                money: money,
                user: user
            },
            url: "${pageContext.request.contextPath}/Bank",
            dataType: 'json',
            success: function (msg) {
                if (1===msg) alert("取款成功");
                else alert("账户余额不足");
                $('#Demand_withdrawal_Close').click();
                get_balance();
            }
        });
    }

    $(function () {
        $('#Withdraw_money').on('show.bs.modal', function (event) {
            var button = $(event.relatedTarget);
            var button_id = button.data('id');
            var obj = document.getElementById("Withdraw_money_sure");
            obj.setAttribute("onclick", "Withdraw_money(" + button_id + ")");
        })
    });

    $(function () {
        $('#Deposit_in').on('show.bs.modal', function (event) {
            var button = $(event.relatedTarget);
            var button_id = button.data('id');
            var obj = document.getElementById("Deposit_in_sure");
            obj.setAttribute("onclick", "Deposit_in(" + button_id + ")");
        })
    });

</script>
<body onload="get_Class();get_balance()">
<div>
    <h3>办理定期存款账</h3>
    <div style="width: 60%">
        <table class="table table-hover">
            <thead>
            <tr>
                <th>序号</th>
                <th>产品名称</th>
                <th>币种</th>
                <th>挂牌利率(%)</th>
                <th>起存金额(元)</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody id="time_deposit">
            </tbody>
        </table>
        <%-- <button class="btn btn-outline-primary" data-toggle="modal"  data-target="#Withdraw_money">查看我的存款</button>--%>
        <button class="btn btn-outline-primary" onclick="get_time_deposit()">查看我的定期存款</button>
        <button class="btn btn-outline-primary" data-toggle="modal"  data-target="#Demand_deposit">存款</button>
        <button class="btn btn-outline-primary" data-toggle="modal"  data-target="#Demand_withdrawal">取款</button>
        <div style="width: 80%;display: none" id="time_deposit_Withdraw_table">
            <table class="table table-hover">
                <thead>
                <tr>
                    <th>凭证ID</th>
                    <th>存储金额</th>
                    <th>产品类别</th>
                    <th>起存日期</th>
                    <th>到期日期</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody id="time_deposit_Withdraw">
                </tbody>
            </table>
        </div>


        <div style="margin-top: 20px"><h3 id="balance"></h3> <h3 id="type"></h3> <h3 id="rate"></h3></div>

        <div class="modal fade" id="Deposit_in"> //定期存款
            <div class="modal-dialog">
                <div class="modal-content">
                    <%-- 头部--%>
                    <div class="modal-header">
                        <h5 class="modal-title">请输入存入的金额</h5>
                        <button id="Deposit_in_Close" class="close" data-dismiss="modal">&times;</button>
                    </div>
                    <%--界面--%>
                    <div class="modal-body">
                    <textarea id="Deposit_in_money" class="form-control" style="height: 40px;margin-bottom: 10px"
                              title="Deposit_in_money"></textarea>
                        <button type="button" id="Deposit_in_sure" class="btn btn-primary" onclick="">确定
                        </button>
                    </div>
                </div>
            </div>
        </div>

        <div class="modal fade" id="Demand_deposit"> //活期存款
            <div class="modal-dialog">
                <div class="modal-content">
                    <%-- 头部--%>
                    <div class="modal-header">
                        <h5 class="modal-title">请输入存入的金额</h5>
                        <button id="Demand_deposit_Close" class="close" data-dismiss="modal">&times;</button>
                    </div>
                    <%--界面--%>
                    <div class="modal-body">
                    <textarea id="Demand_deposit_money" class="form-control" style="height: 40px;margin-bottom: 10px"
                              title="Deposit_in_money"></textarea>
                        <button type="button" id="Demand_deposit_sure" class="btn btn-primary" onclick="Deposit()">确定
                        </button>
                    </div>
                </div>
            </div>
        </div>

        <div class="modal fade" id="Withdraw_money"> //定期取款
            <div class="modal-dialog">
                <div class="modal-content">
                    <%-- 头部--%>
                    <div class="modal-header">
                        <h5 class="modal-title">请输入取出的金额(未满期则取出的金额将按照活期利息返利)</h5>
                        <button id="Withdraw_money_Close" class="close" data-dismiss="modal">&times;</button>
                    </div>
                    <%--界面--%>
                    <div class="modal-body">
                    <textarea id="Withdraw_money_amount" class="form-control" style="height: 40px;margin-bottom: 10px"
                              title="Deposit_in_money"></textarea>
                        <button type="button" id="Withdraw_money_sure" class="btn btn-primary" onclick="">确定
                        </button>
                    </div>
                </div>
            </div>
        </div>

        <div class="modal fade" id="Demand_withdrawal"> //活期取款
            <div class="modal-dialog">
                <div class="modal-content">
                    <%-- 头部--%>
                    <div class="modal-header">
                        <h5 class="modal-title">请输入取出的金额</h5>
                        <button id="Demand_withdrawal_Close" class="close" data-dismiss="modal">&times;</button>
                    </div>
                    <%--界面--%>
                    <div class="modal-body">
                    <textarea id="Demand_withdrawal_money" class="form-control" style="height: 40px;margin-bottom: 10px"
                              title="Deposit_in_money"></textarea>
                        <button type="button" id="Demand_withdrawal_sure" class="btn btn-primary" onclick="Withdraw()">确定
                        </button>
                    </div>
                </div>
            </div>
        </div>

    </div>
</div>
</body>
</html>
