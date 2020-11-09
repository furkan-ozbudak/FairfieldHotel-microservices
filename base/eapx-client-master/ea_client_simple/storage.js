let storage = (function () {
    function getToken() {
        return window.localStorage.getItem("token");
    };
    
    function getEmail() {
        return window.localStorage.getItem("email");
    };

    function getRole(){
        return window.localStorage.getItem("role");
    }

    function getUID(){
        return window.localStorage.getItem("uid");
    }

    function save(data){
        window.localStorage.setItem("email", data.email)
        window.localStorage.setItem("token", data.token)
        window.localStorage.setItem("role", data.role)
        window.localStorage.setItem("uid", data.uid)
    }

    function setChatId(id){
        window.localStorage.setItem("cid", id)
    }

    function getChatId(id){
       return window.localStorage.getItem("cid");
    }

    return {
        save: save,
        getEmail: getEmail,
        getRole: getRole,
        getToken: getToken,
        getUID: getUID,
        setChatId: setChatId,
        getChatId: getChatId
    }
})();