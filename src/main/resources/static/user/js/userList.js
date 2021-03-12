layui.use(['table','tree'], function(){
    var table = layui.table,
        tree = layui.tree;

    var data = [{
        title: '一级1'
        ,id: 1
        ,field: 'name1'
        ,checked: true
        ,spread: true
        ,children: [{
            title: '二级1-1 可允许跳转'
            ,id: 3
            ,field: 'name11'
            ,href: 'https://www.layui.com/'
            ,children: [{
                title: '三级1-1-3'
                ,id: 23
                ,field: ''
                ,children: [{
                    title: '四级1-1-3-1'
                    ,id: 24
                    ,field: ''
                    ,children: [{
                        title: '五级1-1-3-1-1'
                        ,id: 30
                        ,field: ''
                    },{
                        title: '五级1-1-3-1-2'
                        ,id: 31
                        ,field: ''
                    }]
                }]
            },{
                title: '三级1-1-1'
                ,id: 7
                ,field: ''
                ,children: [{
                    title: '四级1-1-1-1 可允许跳转'
                    ,id: 15
                    ,field: ''
                    ,href: 'https://www.layui.com/doc/'
                }]
            },{
                title: '三级1-1-2'
                ,id: 8
                ,field: ''
                ,children: [{
                    title: '四级1-1-2-1'
                    ,id: 32
                    ,field: ''
                }]
            }]
        },{
            title: '二级1-2'
            ,id: 4
            ,spread: true
            ,children: [{
                title: '三级1-2-1'
                ,id: 9
                ,field: ''
                ,disabled: true
            },{
                title: '三级1-2-2'
                ,id: 10
                ,field: ''
            }]
        },{
            title: '二级1-3'
            ,id: 20
            ,field: ''
            ,children: [{
                title: '三级1-3-1'
                ,id: 21
                ,field: ''
            },{
                title: '三级1-3-2'
                ,id: 22
                ,field: ''
            }]
        }]
    },{
        title: '一级2'
        ,id: 2
        ,field: ''
        ,spread: true
        ,children: [{
            title: '二级2-1'
            ,id: 5
            ,field: ''
            ,spread: true
            ,children: [{
                title: '三级2-1-1'
                ,id: 11
                ,field: ''
            },{
                title: '三级2-1-2'
                ,id: 12
                ,field: ''
            }]
        },{
            title: '二级2-2'
            ,id: 6
            ,field: ''
            ,children: [{
                title: '三级2-2-1'
                ,id: 13
                ,field: ''
            },{
                title: '三级2-2-2'
                ,id: 14
                ,field: ''
                ,disabled: true
            }]
        }]
    },{
        title: '一级3'
        ,id: 16
        ,field: ''
        ,children: [{
            title: '二级3-1'
            ,id: 17
            ,field: ''
            ,fixed: true
            ,children: [{
                title: '三级3-1-1'
                ,id: 18
                ,field: ''
            },{
                title: '三级3-1-2'
                ,id: 19
                ,field: ''
            }]
        },{
            title: '二级3-2'
            ,id: 27
            ,field: ''
            ,children: [{
                title: '三级3-2-1'
                ,id: 28
                ,field: ''
            },{
                title: '三级3-2-2'
                ,id: 29
                ,field: ''
            }]
        }]
    }]

    tree.render({
        elem: '#myPermission'
        ,showCheckbox: true  //是否显示复选框
        ,data: data
        ,isJump: true  //link 为参数匹配
    });

    table.render({
        elem: '#roleList'
        ,url:'http://127.0.0.1/user/queryUserList'
        ,method: 'post'
        ,contentType: "application/json; charset=utf-8"
        ,dataType:"json"
        ,toolbar: '#toolbar' //开启头部工具栏，并为其绑定左侧模板
        ,defaultToolbar: ['filter']
        ,title: '用户数据表'
        ,cols: [[
            {field:'rk', title:'序号', width:80, align:'center'}
            ,{field:'nickname', title:'用户昵称', width:170, align:'center',
                templet : function(data) {// 替换数据
                    if(data.nickname == null){
                        return "暂无";
                    }else{
                        return data.nickname;
                    }
                }
             }
            ,{field:'email', title:'用户邮箱', width:170, align:'center',
                templet : function(data) {// 替换数据
                    if(data.email == null){
                        return "暂无";
                    }else{
                        return data.email;
                    }
                }
             }
            ,{field:'rolename', title:'所属角色', width:100, align:'center',
                templet : function(data) {// 替换数据
                    if(data.rolename == null){
                        return "暂无";
                    }else{
                        return data.rolename;
                    }
                }
            }
            ,{field:'signature', title:'个性签名', width:170, align:'center',
                templet : function(data) {// 替换数据
                    if(data.signature == null){
                        return "暂无";
                    }else{
                        return data.signature;
                    }
                }
            }
            ,{fixed: 'right', title:'操作', toolbar: '#bar', width:100, align:'center'}
        ]]
        ,height : "700px"
        ,contentType: "application/json; charset=utf-8"
        ,dataType:"json"
        , page: { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
            layout: ['limit', 'count', 'prev', 'page', 'next', 'skip'] //自定义分页布局
            //,curr: 5 //设定初始在第 5 页
            , groups: 1 //只显示 1 个连续页码
            , first: false //不显示首页
            , last: false //不显示尾页
        }
    });

    //头工具栏事件
    table.on('toolbar(roleList)', function(obj){
        var checkStatus = table.checkStatus(obj.config.id);
        switch(obj.event){
            case 'addRole':
                openWin('../role/addRole.html', '新增角色', '', 510,600);
                break;
        };
    });

    //监听行工具事件
    table.on('tool(roleList)', function(obj){
        var data = obj.data;
        //console.log(obj)
        if(obj.event === 'del'){
            layer.confirm('真的删除行么', function(index){
                obj.del();
                layer.close(index);
            });
        } else if(obj.event === 'edit'){
            layer.prompt({
                formType: 2
                ,value: data.email
            }, function(value, index){
                obj.update({
                    email: value
                });
                layer.close(index);
            });
        }
    });
});