let socket = (function(){
    let socket;
    let dispatchHanler;

    function connect(cb){
        if(window.WebSocket){
            socket = new WebSocket("ws://104.197.113.97:30087/ws");
            socket.onmessage = function (event) {
                // var ta = document.getElementById("responseText");
                // ta.value = ta.value + "\n"+event.data;
                console.log(event.data)
                if(dispatchHanler){
                    dispatchHanler(event.data);
                }
            }
            socket.onopen = function(event){
                // var ta = document.getElementById("responseText");
                // ta.value = "连接开启";
                console.log("open")
                cb();
            }
            socket.onclose = function (event) {
                // var ta = document.getElementById("responseText");
                // ta.value = ta.value +"\n"+"连接关闭";
                console.log("close")
            }
        }else{
            alert("Not Support WebSocket！");
        }
    }

    
    //发送数据
    function send(message){
        if(!window.WebSocket){
            return;
        }
        //当websocket状态打开
        if(socket.readyState == WebSocket.OPEN){
            socket.send(message);
        }else{
            alert("连接没有开启");
        }
    }

    //发送数据
    function dispatch(handler){
        dispatchHanler = handler;
    }

    return {
        connect: connect,
        send: send,
        dispatch: dispatch
    }
})();

let tools = (function () {

    let myInfo = {}
    let talkTo = {}
    let users = {}

    function getNowFormatDate() {
            var date = new Date();
            var seperator1 = "-";
            var seperator2 = ":";
            var month = date.getMonth() + 1;
            var strDate = date.getDate();
            if (month >= 1 && month <= 9) {
                month = "0" + month;
            }
            if (strDate >= 0 && strDate <= 9) {
                strDate = "0" + strDate;
            }
            var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
                    + " " + date.getHours() + seperator2 + date.getMinutes()
                    + seperator2 + date.getSeconds();
            return currentdate;
        }
    

    $("#send").keypress(function(event){
        var keycode = (event.keyCode ? event.keyCode : event.which);
        if(keycode == '13'){
            if(!talkTo.id){
                alert("choose someone to talk")
                return;
            }
            let msg = {
            "dir" : "right",
            "from" : myInfo.name,
            "from_id": myInfo.id,
            "to_id": talkTo.id,
            "date" : getNowFormatDate(),
            "content" : $("#send").val()
            }
            socket.send(msgFormat("Msg",myInfo.id,talkTo.id,$("#send").val()));
            addChat(msg);
            $("#send").val("");
        }
    });

    $("#register-talk-input").keypress(function(event){
        var keycode = (event.keyCode ? event.keyCode : event.which);
        if(keycode == '13'){
            
        let data = {name: $("#register-talk-input").val()}
        console.log(data);
        api.POST('chatgen',
            data,
            function success(res){
                console.log(res);
                storage.setChatId(res.id);
                myInfo = res;
                $("#register-talk-input").val("");
                $("#register-talk").hide();
                socket.connect();
            },function fail(res){
                console.log(res);
        });
            

        }
    });

    

    function addChat(msg){
        

        let h = `<div class="chat-message ${msg.dir}">
                    <img class="message-avatar" src="https://bootdey.com/img/Content/avatar/avatar1.png" alt="">
                    <div class="message">
                        <a class="message-author" href="#">${msg.from?msg.from:"Anonymous"}</a>
                        <span class="message-date"> ${msg.date} </span>
                        <span class="message-content">
                            ${msg.content}
                        </span>
                    </div>
                </div>`

        $("#chat-discussion").append(h);
    }

    function addUser(user){
        if(user.id == myInfo.id){
            return;
        }
        users[user.id] = user;
        let h = `<div class="chat-user" id="${user.id}">
                    <img class="chat-avatar" src="https://bootdey.com/img/Content/avatar/avatar1.png" alt="">
                            <div class="chat-user-name">
                                <a href="#">${user.name?user.name:"Anonymous"}</a>
                            </div>
                </div>`
        
        $("#users-list").append(h);
        $("#"+user.id).click(function(){
            tools.select(user.id);
        });
    }

    function select(id){
        console.log(id);
        talkTo = users[id];
        console.log(users);
        console.log(talkTo);
        $("#chat-to").html(talkTo.name?talkTo.name:"Anonymous");
    }

    function setInfo(user){
        myInfo = user
    }

    function getInfo(user){
        return myInfo;
    }

    function getTalkTo(){
        return talkTo;
    }

    function clear(){
        myInfo = {};
        talkTo = {};
        users = {};


        $("#chat-to").empty();
        $("#users-list").empty();
        $("#chat-discussion").empty();

    }

    function updateUsers(){
        users = {};
        api.GET('chatall',
            function success(users){
                console.log(users);
                users.forEach(element => {
                    tools.addUser(element.user);
                });

            },function fail(res){
                console.log(res);
        });
    }

    function init(){
        if(storage.getChatId()){
            $("#register-talk-input").val("");
            $("#register-talk").hide();
            let id  = storage.getChatId();
            socket.connect(function(){
                socket.send(msgFormat("On",id,"","login"))
                socket.send(msgFormat("All",id,"","all"))
            });
            
            socket.dispatch(function(data){
                data = JSON.parse(data);
                console.log(data);
                if(data.type=="All"){
                    data.users.forEach(e=>{
                        if(e){
                            addUser(e.user);
                        }
                    })
                }

                if(data.type=="On"){
                    setInfo(data.users[0].user);
                }

                if(data.type=="Msg"){
                    addChat(chatFormat(data));
                }
            });

            // api.POST('chaton',
            //     data,
            //     function success(res){
            //         tools.setInfo(res.user);
            //         $("#register-talk-input").val("");
            //         $("#register-talk").hide();
            //         console.log(res);
            //         socket.connect();
            //         updateUsers();
            //     },function fail(res){
            //         console.log(res);
            // });
        }
    }

    function msgFormat(type,from,to,content){
        return JSON.stringify({"type":type,"fromId":from,"toId":to,"content":content});
    }

    function chatFormat(data){
        let user = users[data.fromId];
        return {
            "dir" : "left",
            "from" : user.name,
            "from_id": user.id,
            "to_id": data.toId,
            "date" : getNowFormatDate(),
            "content" : data.content
            }
    }
    

    return {
        "init": init,
        "addChat": addChat,
        "addUser": addUser,
        "setInfo": setInfo,
        "getInfo": getInfo,
        "select": select,
        "getTalkTo": getTalkTo,
        "clear": clear
    }
})();

$(function(){

    function init(){
        tools.init();
    }

    init();

    
    function test(){
        let msg = {
            "dir" : "left",
            "from" : "Michael Smith",
            "date" : "Mon Jan 26 2015 - 18:39:23",
            "content" : "Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat."
        }
        let user1 = {
            "id":"134215325235325",
            "name": "bruee"
        }

        tools.setInfo(user1)

        // tools.addChat(msg);

        let user2 = {
            "id":"1342153252353999",
            "name": "Xiangkui"
        }


        tools.addUser(user2)

    }

})