$("#send").on("click", function() {
	var transaction = {};

	transaction.productId = $("#products").val();
	transaction.manufacturer = $("#manufacturer").val();
	transaction.quantity = $("#quantity").val();
	transaction.isEntry = $("#isEntry").val();
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
                 alert("Erro na ação" + err.responseText);
             }
         };
         CFINAC.ajax.post(cfg);
});