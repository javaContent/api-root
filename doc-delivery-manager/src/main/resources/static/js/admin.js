$(function () {
    /**
     * 分页插件配置项
     */
    $('.pagination').pagination({
        coping: true,
        totalData: 1000, // 数据总数
        isData: true, // 显示数据条数按钮
        current: 1, // 当前页
        showData: 20, // 每页10条
        isHide: true,
        jump: true,
        jumpBtn: "GO",
        prevContent: '上一页',
        homePage: "首页",
        nextContent: '下一页',
        endPage: "末页",
        callback: function (api) {
            var page = api.getCurrent() - 1; // 点击按钮的当前页
        }
    });


    /**
     * 顶部筛选下拉框 效果
     */
    (function () {

        // 下拉框显隐
        $(".sc_selbox").click(function () {
            $(this).find(".sc_selopt").show();
        }).mouseleave(function () {
            $(this).find(".sc_selopt").hide();
        });

        // 筛选对应的学校显示
        $(".sc_selbox .se-val").on("keyup", function () {
            var se_val = $(this).val();
            var t_p = $(this).parents(".sc_selopt").find("p");
            if (se_val == "") {
                t_p.show();
            } else {
                t_p.hide();
                $(this).parents(".sc_selopt").find("p:contains(" + se_val + ")").show();
            }
        });

        /*点击下拉框内p标签传值*/
        $(".sc_selbox p").on("click", function () {
            var val = $(this).text();
            $(this).parents(".sc_selbox").find("#section_lx").text(val);
        });
        // 设置input[name=school]的值并提交后台
        $(".sc_selbox p.school").on("click", function () {
            var flag = $(this).attr("schoolFlag");
            $('input[name=school]').val(flag);
            // $('#change_form').submit();
        });
        // 设置input[name=type]的值并提交后台
        $(".sc_selbox p.type").on("click", function () {
            var val = $(this).attr("value");
            $('input[name=type]').val(val);
            // $('#change_form').submit();
        });

    }());


    /**
     * 各种 提交表单操作
     */
    (function () {

        //选择学校 下拉框
        $('.sc_selopt p.school').click(function () {
            var vals = $(this).attr("schoolFlag");
            $('input[name=school]').val(vals);
            $('#change_form').submit();
        });

        //文献互助状态 下拉框
        $('.sc_selopt p.type').click(function () {
            var vals = $(this).attr("value");
            $('input[name=type]').val(vals);
            $('#change_form').submit();
        });

        //复用按钮事件
        $('.data-table').on('click', '.Reuse', function (e) {
            var id = $(this).attr("data_id");
            $('#id_input').val($(this).attr('data_id'));
            $('#reuse').attr("checked", true);
            $(this).parents("td").addClass("self-chuli");
            submitProcess(e, {cla: 'Reuse', uid: id});
        })

        //已复用按钮事件
        $('.data-table').on('click', '.upReuse', function (e) {
            var id = $(this).attr("data_id");
            $('#id_input').val($(this).attr('data_id'));
            $('#upReuse').attr("checked", true);
            $(this).parents("td").addClass("self-chuli");
            submitProcess(e, {cla: 'upReuse', uid: id});
        })

        // 点击处理按钮
        $(".data-table").on('click', '.handle', function () {
            var id = $(this).attr("data_id");
            var title = $(this).attr("data_title");
            var url = $(this).attr("data_url");
            var type = $(this).attr("data_type");
            $(this).parents("td").addClass("self-chuli");
            $('#id_input').val($(this).attr('data_id'));
            $('#title_div').text(title.replace('<b>', '').replace('</b>', ''));
            $('#url_href').attr('href', url).text(url);
            $('#file_input').val("");
            if (type == 0) {
                $('.types').show();
            } else if (type == 2 || type == 4) {//第三方处理
                $('.types').hide();
            }
        })

        // 点击审核按钮
        $(".data-table").on('click', '.help', function () {
            var id = $(this).attr("data_id");
            var title = $(this).attr("data_title");
            var path = $(this).attr("data_path");

            $('#docdeliverHelp input.id').val(id);
            $('#docdeliverHelp span.title').text(title.replace('<b>', '').replace('</b>', ''));
            $('#docdeliverHelp a.path').attr("href", path);
        });

        //处理弹窗内 的 处理方式选择
        $('.processType').click(function () {
            var value = $(this).val();
            if (value == 1) {
                $('#file_line').show();
                $('#file_input').attr('disabled', false);
            } else {
                $('#file_line').hide();
                $('#file_input').attr('disabled', true);
            }
        });

        // 提交 处理弹窗表单
        $("#docdeliver .btnEnsure").on("click", function (e) {
            submitProcess(e)
        });

        function submitProcess(e, obj) {
            e.preventDefault();
            var $btn = $(e.target);
            var selfTd = $(".self-chuli");
            var formdata = new FormData($("#process_form")[0]);
            if (!$("#file_input").val() && selfTd.find('.oprbtn').hasClass('handle')) {
                layer.msg("请选择上传文件！");
                $(".layui-layer-dialog .layui-layer-content").css({
                    background: 'none'
                })
                return
            }
            (function (that, opts, btn) {
                // if(btn.hasClass('upReuse') || btn.hasClass('Reuse')){
                // 	var reuse = true;		
                // }else{
                // 	var reuse = false;
                // }
                $.ajax({
                    type: "post",
                    dataType: "json",
                    url: "list/process",
                    processData: false, //需设置为false。因为data值是FormData对象，不需要对数据做处理
                    contentType: false, //需设置为false。因为是FormData对象，且已经声明了属性enctype="multipar
                    data: formdata,
                    success: function (result) {
                        if (result.message === "success") {
                            if (opts) {
                                var cla = opts.cla == 'upReuse' ? 'Reuse' : 'upReuse',
                                    text = opts.cla == 'upReuse' ? '复用' : '已复用';
                                selfTd.find('span.btn').html('<a data-thickcon="docdeliverReuse" data_id="' + opts.uid + '" class="oprbtn btngreen ' + cla + '">' + text + '</a>')
                            } else {
                                selfTd.html('<span><a href="javascript:void(0)" onclick="location.reload()" class="btngreen oprbtn">刷新</a></span>')
                            }
                        }
                    },
                    error: function () {

                        if (opts) {
                            selfTd.find('span.btn').html('<a href="javascript:void(0)" onclick="location.reload()" class="btngreen oprbtn">处理失败</a>');
                        } else {
                            selfTd.html('<span><a href="javascript:void(0)" onclick="location.reload()" class="btngreen oprbtn">处理失败</a></span>')
                        }
                    }
                })
                if (opts) {
                    selfTd.find('span.btn').html('<span>处理中</span>');
                } else {
                    selfTd.html('<span>处理中</span>');
                }

            }(selfTd, obj, $btn))

            selfTd.removeClass('self-chuli');

            $("#docdeliver .btnCancle").click();

            layer.msg("已提交处理！");

            return false;
        }

    }());


    /**
     * 处理 - 审核 弹窗 效果
     */
    (function () {

        // 显示 弹窗
        $(".data-table").on('click', '.oprbtn', function () {
            var eleid = $(this).attr('data-thickcon'),
                thickDom = $("#" + eleid);
            tit = $(this).text();
            if (eleid && thickDom.length) {
                thickDom.show();
                thickDom.parents(".thickWarp").show().find(".thickheadTit").text(tit);
                var height = thickDom.outerHeight();
                if (height > 506) {
                    height = 506;
                    thickDom.parents(".thickbody").css({
                        maxHeight: "506px",
                        overflowY: "auto"
                    })
                }
                thickDom.parents(".thickinWrap").css({
                    height: (height + 36) + "px",
                    marginTop: -(height + 46) / 2 + "px"
                })
            }
        })

        // 关闭 弹窗
        $(".thickWarp .thickBj,.thickWarp .closeThick,.thickWarp .btnCancle")
            .click(function () {
                $('.self-chuli').removeClass('self-chuli');
                $(".thickWarp").hide().find(".thickbody").children().hide();
            })

    }());
})

