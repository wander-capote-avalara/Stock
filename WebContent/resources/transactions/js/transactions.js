$("#send").on("click", function() {
	var transaction = {};

	transaction.productId = $("#products").val();
	transaction.manufacturer = $("#manufacturer").val();
	transaction.quantity = $("#quantity").val();
	transaction.isEntry = $("#type").val();
	if (transaction.isEntry == 0) {
		transaction.expectedDate = $("#expectedDate").val();
		transaction.deliveryDate = $("#deliveryDate").val();
	} else {
		transaction.outputDate = $("#outputDate").val();
	}

	 cfg = {
             url: "../rest/transaction/add",
             data: transaction,
             success: function(r) {
                alert("MAOHEEE"+r);
             },
             error: function(err) {
                 
             }
         };
         CFINAC.ajax.post(cfg);
});

function getTransactions(isEntry){
	cfg = {
            url: "../rest/transaction/getTransactions/?isEntry="+isEntry,
            success: function(r) {
               showTransactions(r, isEntry);
            },
            error: function(err) {
                
            }
        };
        CFINAC.ajax.post(cfg);
}

function showTransactions(list, type){
	alert(list);
	alert(type);
}

getTransactions(0);
getTransactions(1);