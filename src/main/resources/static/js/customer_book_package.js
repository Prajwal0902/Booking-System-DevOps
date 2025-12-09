// Set date picker defaults
const dateInput = document.getElementById('bookingDate');
const today = new Date();
const tomorrow = new Date(today);
tomorrow.setDate(tomorrow.getDate() + 1);

// Set minimum date to tomorrow (bookings start from next day)
dateInput.min = tomorrow.toISOString().split('T')[0];

// Set default value to tomorrow if not set
if (!dateInput.value) {
    dateInput.value = tomorrow.toISOString().split('T')[0];
}

// Set maximum date to 1 year from today
const maxDate = new Date(today);
maxDate.setFullYear(maxDate.getFullYear() + 1);
dateInput.max = maxDate.toISOString().split('T')[0];

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
        document.getElementById('qrImage').src = `https://api.qrserver.com/v1/create-qr-code/?size=200x200&data=BookingSystem-USD-${price}`;
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

// Form validation with mock payment simulation
document.querySelector('form').addEventListener('submit', function(e) {
    const paymentMethod = document.getElementById('paymentMethod').value;
    
    if(!paymentMethod) {
        e.preventDefault();
        alert('Please select a payment method');
        return false;
    }
    
    // Simulate payment processing
    const submitBtn = document.getElementById('submitBtn');
    submitBtn.disabled = true;
    submitBtn.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Processing Mock Payment...';
    
    if(paymentMethod === 'Card') {
        const cardNumber = document.getElementById('cardNumber').value.replace(/\s/g, '');
        const expiry = document.getElementById('expiry').value;
        const cvv = document.getElementById('cvv').value;
        const cardName = document.getElementById('cardName').value;
        
        if(!cardNumber || !expiry || !cvv || !cardName) {
            e.preventDefault();
            alert('Please fill in all card details');
            submitBtn.disabled = false;
            submitBtn.innerHTML = '<i class="fas fa-check-circle"></i> Complete Booking';
            return false;
        }
        
        // Validate card number format (basic check)
        if(cardNumber.length < 13 || cardNumber.length > 19) {
            e.preventDefault();
            alert('Please enter a valid card number (13-19 digits)');
            submitBtn.disabled = false;
            submitBtn.innerHTML = '<i class="fas fa-check-circle"></i> Complete Booking';
            return false;
        }
        
        // Generate mock transaction ID for card payment
        const transactionId = 'MOCK-CARD-' + Date.now() + '-' + Math.floor(Math.random() * 10000);
        document.getElementById('transactionId').value = transactionId;
        document.getElementById('paymentStatus').value = 'Completed';
        
        // Show success message
        setTimeout(() => {
            alert('✓ Mock Card Payment Successful!\nTransaction ID: ' + transactionId + '\nThis is a simulated payment for demo purposes.');
        }, 500);
        
    } else {
        // For QR payment, generate mock transaction ID
        const transactionId = 'MOCK-QR-' + Date.now() + '-' + Math.floor(Math.random() * 10000);
        document.getElementById('transactionId').value = transactionId;
        document.getElementById('paymentStatus').value = 'Completed';
        
        // Show success message
        setTimeout(() => {
            alert('✓ Mock QR Payment Successful!\nTransaction ID: ' + transactionId + '\nThis is a simulated payment for demo purposes.');
        }, 500);
    }
});
