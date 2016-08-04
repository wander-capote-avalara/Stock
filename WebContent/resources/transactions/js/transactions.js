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
		url : "../rest/transaction/add",
		data : transaction,
		success : function(r) {
			alert("MAOHEEE" + r);
			update();
		},
		error : function(err) {
			update();
		}
	};
	CFINAC.ajax.post(cfg);
});

function getTransactions(isEntry) {
	cfg = {
		url : "../rest/transaction/getTransactions/?isEntry=" + isEntry,
		type : "GET",
		success : function(r) {
			showTransactions(r, isEntry);
		},
		error : function(err) {
		}
	};
	CFINAC.ajax.post(cfg);
}

function showTransactions(list, type) {
	var html = "<h1>";
	html += type == 0 ? "Entries" : "Outputs";
	html += "</h1>"
	html += "<table class='table table-striped'>"
	html += "<tr>";
	html += "<th>Product Name</th>";
	html += "<th>Manufacturer</th>";
	html += "<th>Quantity</th>";
	if (type == 0) {
		html += "<th>Expected Date</th>";
		html += "<th>Delivery Date</th>";
	} else {
		html += "<th>Output Date</th>";
	}
	html += "</tr>"
	if (list.length == 0) {
		html += "<tr>";
		html += "<td colspan='5' style='text-align:center'>Nothing here!!!</td>";
		html += "</tr>";
	} else {
		for (i = 0; i < list.length; i++) {
			html += "<tr>";
			html += "<td>" + list[i].productName + "</td>";
			html += "<td>" + list[i].manufacturer + "</td>";
			html += "<td>" + list[i].quantity + "</td>";
			if (type == 0) {
				html += "<td>" + list[i].expectedDate + "</td>";
				html += "<td>" + list[i].deliveryDate + "</td>";
			} else {
				html += "<td>" + list[i].outputDate + "</td>";
			}
		}
		html += "</tr>";
	}
	html += "</table>";

	type == 0 ? $("#transactionEntry").html(html) : $("#transactionOutput")
			.html(html);
}

function update() {
	getTransactions(0);
	getTransactions(1);
}

setInterval(update, 1000);