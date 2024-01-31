var options = {
			key: /*[[${key}]]*/,
			amount: /*[[${order.amount * 100}]]*/,
			currency: "INR",
			name: "Book Store",
			description: "Best Book Store in the world",
			image: "https://i.pinimg.com/originals/79/a2/6c/79a26c942e0b561b1c59621be1bfc22f.jpg",
			order_id: /*[[${order.orderId}]]*/,
			"callback_url": "http://localhost:1234/confirm-order/[[${order.id}]]",
			prefill: {
				name: /*[[${user.name}]]*/,
				email: /*[[${user.email}]]*/,
				contact: "+91/*[[${user.mobile}]]*/",
			},
			notes: {
				address: "Razorpay Corporate Office",
			},
			theme: {
				color: "blue",
			}
		};

		var rzp1 = new Razorpay(options);
		document.getElementById('rzp-button1').onclick = function (e) {
			rzp1.open();
			e.preventDefault();
		}