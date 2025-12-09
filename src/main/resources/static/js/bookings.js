$(document).ready(function() {
    $('#bookingsTable').DataTable({
        pageLength: 10,
        order: [[0, 'desc']]
    });
    
    // Set today's date as default
    document.getElementById('bookingDate').valueAsDate = new Date();
});

function updatePackageInfo() {
    const select = document.getElementById('packageSelect');
    const option = select.options[select.selectedIndex];
    
    document.getElementById('packageId').value = select.value;
    document.getElementById('packageName').value = option.getAttribute('data-name');
    document.getElementById('totalAmount').value = option.getAttribute('data-price');
}

function addBooking() {
    document.getElementById('bookingModalTitle').textContent = 'Add Booking';
    document.getElementById('bookingForm').action = '/bookings';
    document.getElementById('bookingForm').reset();
    // Remove the bookingId field name for new bookings to avoid validation error
    const bookingIdField = document.getElementById('bookingId');
    bookingIdField.removeAttribute('name');
    bookingIdField.value = '';
    document.getElementById('bookingDate').valueAsDate = new Date();
    const modal = new bootstrap.Modal(document.getElementById('bookingModal'));
    modal.show();
}

function editBooking(button) {
    const id = button.getAttribute('data-id');
    const name = button.getAttribute('data-name');
    const email = button.getAttribute('data-email');
    const phone = button.getAttribute('data-phone');
    const pkgId = button.getAttribute('data-pkgid');
    const pkgName = button.getAttribute('data-pkgname');
    const date = button.getAttribute('data-date');
    const amount = button.getAttribute('data-amount');
    const status = button.getAttribute('data-status');
    const payment = button.getAttribute('data-payment');
    const txn = button.getAttribute('data-txn');
    const payStatus = button.getAttribute('data-paystatus');
    
    document.getElementById('bookingModalTitle').textContent = 'Edit Booking';
    // Re-add the name attribute for edit
    const bookingIdField = document.getElementById('bookingId');
    bookingIdField.setAttribute('name', 'bookingId');
    bookingIdField.value = id;
    document.getElementById('customerName').value = name;
    document.getElementById('customerEmail').value = email;
    document.getElementById('customerPhone').value = phone;
    document.getElementById('packageSelect').value = pkgId;
    document.getElementById('packageId').value = pkgId;
    document.getElementById('packageName').value = pkgName;
    document.getElementById('bookingDate').value = date;
    document.getElementById('totalAmount').value = amount;
    document.getElementById('status').value = status;
    document.getElementById('paymentMethod').value = payment || '';
    document.getElementById('transactionId').value = txn || '';
    document.getElementById('paymentStatus').value = payStatus || 'Pending';
    document.getElementById('bookingForm').action = '/update-booking/' + id;
    
    const modal = new bootstrap.Modal(document.getElementById('bookingModal'));
    modal.show();
}
