function vote() {
    const selectedOption = document.querySelector('input[name="vote"]:checked');
    if (selectedOption) {
        const optionId = selectedOption.id;
        const optionVotes = document.getElementById(optionId + '-votes');
        console.log(optionVotes)
        optionVotes.textContent = parseInt(optionVotes.textContent) + 1;
        selectedOption.checked = false;
    }
}


function submitVote() {
    const option1Votes = parseInt(document.getElementById('option1-votes').textContent);
    const option2Votes = parseInt(document.getElementById('option2-votes').textContent);

    const votingData = {
        option1: option1Votes,
        option2: option2Votes,
        // 可以添加更多选项的投票结果
    };

    // 使用Ajax发送JSON数据到后端
    const xhr = new XMLHttpRequest();
    xhr.open('POST', 'your_backend_url_here', true);
    xhr.setRequestHeader('Content-Type', 'application/json;charset=UTF-8');
    xhr.send(JSON.stringify(votingData));

    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            // 处理后端的响应，如果有需要的话
        }
    };
}