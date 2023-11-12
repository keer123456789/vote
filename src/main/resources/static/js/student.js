var user = '';
(function () {
    if (location.search.split('=').length < 2) {
        window.location.href = "/index.html"
    } else {
        var base64 = new BASE64();
        var info = base64.decode(location.search.split('=')[1])
        user = info.split("&")[0].split('=')[1]

        console.log(user)
        getIsVote(user);
    }
})();

function getIsVote(username) {
    $.get({
        url: "/isVote?username=" + username,
        // headers: {
        //     "Content-Type": "application/json; charset=utf-8"
        // }
    }, function (allData) {
        if (allData.code === 200) {
            console.log(allData.data)
            if (allData.data) {
                $("#btn-vote").hide()
                $("#btn-result").show()
            } else {
                $("#btn-vote").show()
                $("#btn-result").hide()
            }
        } else {
            alert("登录失败！")
        }
    });
}

function vote() {
    // if (isVote == 'true') {
    //     alert("已经投过票了，请不要重复投票！")
    //     return
    // }
    const zx = $('input[name="voteZx"]:checked').val();
    const mg = $('input[name="voteMg"]:checked').val();
    const cx = $('input[name="voteCx"]:checked').val();
    const kj = $('input[name="voteKj"]:checked').val();
    const tj = $('input[name="voteTj"]:checked').val();
    console.log(zx, mg, cx, kj, tj)
    console.log(location.header)
    const voteInfo = {
        userName: user,
        zx: zx,
        mg: mg,
        cx: cx,
        kj: kj,
        tj: tj
    }
    $.post({
            url: "/vote/stu",
            headers: {
                "Content-Type": "application/json; charset=utf-8",
                "user": user
            },

        }, JSON.stringify(voteInfo), function (allData) {
            console.log(allData)
            if (allData.code === 200) {
                isVote = 'false'

                alert("投票成功！")
                location.reload();
            } else {
                alert("投票失败！")
            }
        }
    )
    ;
}


function getResult() {
    $.get({
        url: "/vetoResult",
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
            alert(allData.msg)
            $('#result-vote').hide();
        }
    });

}

function getName(value) {
    return value.replace('group', '') + "组";
}
