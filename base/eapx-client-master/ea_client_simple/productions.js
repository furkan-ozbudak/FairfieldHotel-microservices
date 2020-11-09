let productions = (function () {
    function tocart(id) {
        console.log(id);
    }

    function makePro2(data) {
        let ret = `<div class="col-sm-6" id="card_${data.id}"><div class="card gedf-card"">
            <div class="card-header bg-transparent border-success">${data.name}</div>
            <div class="card-body text-success">
            <h5 class="card-title">Price Only $${data.price}</h5>
        <p class="card-text">
            <div>${data.productContent}.</div>
            <img src="${data.pic}" width="30%">
        </p>
        <button class="btn btn-primary" onclick="productions.tocart(${data.id})">To Cart</button>
        </div>
        </div></div>`;
        return ret;
    }


    function addFromFront(data){
        $("#editorPanel").after(makePro2(data));
    }

    function addFromEnd(data){
        // $("#postsContainer").append(makePro2(data));
        $("#procardroot").append(makePro2(data));
    }

    function removeAll(){
        $(".myposts").remove();
    }

    function seller_request() {
        removeAll();
        api.GET("api/product/all/"+storage.getUID(),
        function success(res){
            console.log(res);
            res.forEach(element => {
                addFromEnd(element);
            });
        },function fail(res){
            console.log(res);
        });
    }

    function buyer_request() {
        removeAll();
        api.GET("all_products",
        function success(res){
            console.log(res);
            res.forEach(element => {
                addFromEnd(element);
            });
        },function fail(res){
            console.log(res);
        });
    }

    return {
        "init_seller": function () {
            seller_request();
        },
        "init_buyer": function () {
            buyer_request();
        },
        "addFromFront" : addFromFront,
        "addFromEnd" : addFromEnd,
        "removeAll" : removeAll,
        "tocart": tocart
    }
})();