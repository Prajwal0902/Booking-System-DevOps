// Set minimum date to today
document.getElementById('bookingDate').min = new Date().toISOString().split('T')[0];

function updatePackageDetails() {
    const select = document.getElementById('packageSelect');
    const option = select.options[select.selectedIndex];
    
    if(select.value) {
        const price = option.getAttribute('data-price');
        document.getElementById('packageId').value = select.value;
        document.getElementById('packageName').value = option.getAttribute('data-name');
        document.getElementById('totalAmount').value = price;
        document.getElementById('displayPrice').textContent = price;
        document.getElementById('priceInfo').style.display = 'block';
        
        // Update QR code with price
        document.getElementById('qrImage').src = `https://api.qrserver.com/v1/create-qr-code/?size=200x200&data=TravelBooking-USD-${price}`;
    } else {
        document.getElementById('packageId').value = '';
        document.getElementById('packageName').value = '';
        document.getElementById('totalAmount').value = '';
        document.getElementById('priceInfo').style.display = 'none';
    }
}

// Payment method selection
const qrOption = document.getElementById('qrOption');
const cardOption = document.getElementById('cardOption');
const qrCode = document.getElementById('qrCode');
const cardPayment = document.getElementById('cardPayment');

qrOption.addEventListener('click', function() {
    qrOption.classList.add('selected');
    cardOption.classList.remove('selected');
    qrCode.style.display = 'block';
    cardPayment.style.display = 'none';
    document.getElementById('paymentMethod').value = 'QR';
});

cardOption.addEventListener('click', function() {
    cardOption.classList.add('selected');
    qrOption.classList.remove('selected');
    cardPayment.style.display = 'block';
    qrCode.style.display = 'none';
    document.getElementById('paymentMethod').value = 'Card';
});

// Form validation
document.querySelector('form').addEventListener('submit', function(e) {
    const paymentMethod = document.getElementById('paymentMethod').value;
    
    if(!paymentMethod) {
        e.preventDefault();
        alert('Please select a payment method');
        return false;
    }
    
    if(paymentMethod === 'Card') {
        const cardNumber = document.getElementById('cardNumber').value;
        const expiry = document.getElementById('expiry').value;
        const cvv = document.getElementById('cvv').value;
        const cardName = document.getElementById('cardName').value;
        
        if(!cardNumber || !expiry || !cvv || !cardName) {
            e.preventDefault();
            alert('Please fill in all card details');
            return false;
        }
        
        // Generate transaction ID for card payment
        const transactionId = 'TXN' + Date.now() + Math.floor(Math.random() * 1000);
        document.getElementById('transactionId').value = transactionId;
        document.getElementById('paymentStatus').value = 'Completed';
    } else {
        // For QR payment, generate transaction ID
        const transactionId = 'QR' + Date.now() + Math.floor(Math.random() * 1000);
        document.getElementById('transactionId').value = transactionId;
        document.getElementById('paymentStatus').value = 'Pending';
    }
});
