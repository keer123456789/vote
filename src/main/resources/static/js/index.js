function login() {
    var username = document.getElementById('login-user').value;
    var pwd = document.getElementById('login-pwd').value;

    const xhr = new XMLHttpRequest();
    var url = '/login?username=' + username + '&pwd=' + pwd;
    $.get({
        url: url,
        // headers: {
        //     "Content-Type": "application/json; charset=utf-8"
        // }
    }, function (allData) {
        if (allData.code === 200) {
            var info = "username=" + username + "&isVote=" + allData.data;
            var base64 = new BASE64();
            if(username!='wcy'){
                window.location.href = "/student.html?info=" + base64.encode(info)
            }else{
                window.location.href = "/teacher.html?info=" + base64.encode(info)
            }

        } else {
            alert("登录失败！")
        }
    });

}