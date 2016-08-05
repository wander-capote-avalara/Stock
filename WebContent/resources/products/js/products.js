

	function getProducts() {
		cfg = {
			url : "../rest/products/getProducts/",
			type : "GET",
			success : function(r) {
				showProducts(r);
			},
			error : function(err) {
				alert("errow")
			}
		};
		CFINAC.ajax.post(cfg);
	}

	function showProducts(list) {
		var html = "Products: <select id='products'>";
		for (var x = 0; x < list.length; x++) {
			html += "<option value='" + list[x].id + "'>" + list[x].model
					+ "</option>";
		}
		html += "</select>"

		$("#prods").html(html);
	}

	getProducts();
