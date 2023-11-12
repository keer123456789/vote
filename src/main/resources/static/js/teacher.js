var user = '';
(function () {
    if (location.search.split('=').length < 2) {
        window.location.href = "/index.html"
    } else {
        var base64 = new BASE64();
        var info = base64.decode(location.search.split('=')[1])
        user = info.split("&")[0].split('=')[1]
        if (user != 'wcy') {
            window.location.href = "/index.html"
        }
    }
    getDetail();
    getUserVoteInfo();
    getResult();
})();

/**
 * 获取投票详情信息
 */
function getDetail() {
    $.get({
        url: "/vetoInfo",
        // headers: {
        //     "Content-Type": "application/json; charset=utf-8"
        // }
    }, function (allData) {
        if (allData.code === 200) {
            console.log(allData.data)
            var data = allData.data;
            buildAllAwardDetail(data);
        } else {
            alert("获取失败！")
        }
    });
}

/**
 * 构建详情页面
 * @param data
 */
function buildAllAwardDetail(data) {
    buildDetail(data.zx, 'zx');
    buildDetail(data.mg, 'mg');
    buildDetail(data.cx, 'cx');
    buildDetail(data.kj, 'kj');
    buildDetail(data.tj, 'tj');
}

function buildDetail(array, type) {
    var dom;
    if (type == 'zx') {
        dom = $('#detailZx')
    } else if (type == 'mg') {
        dom = $('#detailMg')
    } else if (type == 'kj') {
        dom = $('#detailKj')
    } else if (type == 'cx') {
        dom = $('#detailCx')
    } else if (type == 'tj') {
        dom = $('#detailTj')
    }
    var html = '';
    for (var i = 0; i < array.length; i++) {
        const detail = array[i];
        html += "<ul class=\'vote-body\'><li class=\'vote-item\'> <p style=\'margin-top: 10px\'>" + getName(detail.username) + "</p> </li> <li class=\'vote-item\'> <p style=\'margin-top: 10px\'>" + detail.num + "</p> </li> <li class=\'vote-item\'> <button type=\'button\' onClick=\"teacherVote('" + detail.username + "' ,'" + type + "')\" class=\'btn btn-success\' style=\'margin-top: 7px\'>投票 </button> </li></ul>"
    }
    dom.append(html)
}


/**
 * 获取投票最终结果
 */
function getResult() {
    $.get({
        url: "/vetoResult/tea",
        // headers: {
        //     "Content-Type": "application/json; charset=utf-8"
        // }
    }, function (allData) {
        if (allData.code === 200) {
            console.log(allData.data)
            var data = allData.data;
            console.log(getName(data.zx))
            $('#res-zx').text(getName(data.zx));
            $('#res-mg').text(getName(data.mg));
            $('#res-cx').text(getName(data.cx));
            $('#res-kj').text(getName(data.kj));
            $('#res-tj').text(getName(data.tj));
            $('#result-vote').show();
        } else {
            alert("登录失败！")
        }
    });

}

function getUserVoteInfo() {
    $.get({
        url: "/userVetoInfo",
        // headers: {
        //     "Content-Type": "application/json; charset=utf-8"
        // }
    }, function (allData) {
        if (allData.code === 200) {
            console.log(allData.data)
            var data = allData.data;
            $('#user-veto-info').append(buildUserVoteInfo(data));
        } else {
            alert("登录失败！")
        }
    });

}

function buildUserVoteInfo(data) {
    var html = '';
    for (let key in data) {
        var flag = '否';
        if (data[key] === 'true') {
            flag = '是'
        }
        html += "  <ul class=\"vote-body\" ><li class=\"vote-item\"><p style=\"margin-top: 10px\">" + getName(key) + "</p></li><li class=\"vote-item\"> <p style=\"margin-top: 10px\">" + flag + "</p></li><li class=\"vote-item\"><button type=\"button\" onClick=\"reVote('"+key+"')\" class=\"btn btn-success\" style=\"margin-top: 7px\">重新投票</button></li></ul>"
    }
    return html;
}

function getName(value) {
    return value.replace('group', '') + "组";
}

function reVote(username) {
    console.log(username)
    $.get({
        url: "/reVote?username=" + username,
    }, function (allData) {
        if (allData.code === 200) {
            alert("操作成功！")
            console.log(allData.data)
            location.reload()
        } else {
            alert("投票失败！")
        }
    });
}

function teacherVote(username, award) {
    console.log(username, award)
    $.get({
        url: "/vote/tea?username=" + username + "&award=" + award,
        // headers: {
        //     "Content-Type": "application/json; charset=utf-8"
        // }
    }, function (allData) {
        if (allData.code === 200) {
            console.log(allData.data)
            location.reload()
        } else {
            alert("投票失败！")
        }
    });
}

function clearVoteInfo(){
    $.get({
        url: "/clearVoteInfo",
        // headers: {
        //     "Content-Type": "application/json; charset=utf-8"
        // }
    }, function (allData) {
        if (allData.code === 200) {
            alert("操作成功")
            location.reload()
        } else {
            alert("操作失败！")
        }
    });
}

